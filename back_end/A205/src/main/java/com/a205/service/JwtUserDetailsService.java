package com.a205.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.a205.dao.MemberDAO;
import com.a205.dto.Member;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberDAO memberDao;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberDao.searchByEmail(username);
		if (member == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(member.getM_email(), member.getM_password(),
				new ArrayList<>());
	}
	
	public int loadUserIdByUsername(String username) {
		Member member = memberDao.searchByEmail(username);
		return member.getM_id();
	}
	
	public Member save(Member member) {
		Member newMember = new Member();
		newMember.setM_userid(member.getM_userid());
		newMember.setM_email(member.getM_email());
		newMember.setM_password(bcryptEncoder.encode(member.getM_password()));
		memberDao.add(newMember);
		return memberDao.searchByEmail(newMember.getM_email());
	}
}