package com.a205.dao;

import java.util.List;

import com.a205.dto.Member;
import com.a205.dto.Follow;

public interface FollowDAO {

	public boolean add(String userId, String followee);
	
	public boolean remove(String userId, String followee);


}
