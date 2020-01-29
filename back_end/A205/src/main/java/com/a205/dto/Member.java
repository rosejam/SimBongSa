package com.a205.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	private String m_id;
	private String m_password;
	private String m_email;
	private String m_bgnTm;
	private String m_endTm;
	private String m_age;
	private String m_userid;
	private String m_address;
	private String m_authkey;
	private String m_authstatus;

}
