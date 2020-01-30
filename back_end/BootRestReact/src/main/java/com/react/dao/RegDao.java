package com.react.dao;

import org.apache.ibatis.session.SqlSession;

import com.react.util.MyBatisUtil2;
import com.react.vo.Region;

public class RegDao {
	
	public boolean addReg(Region reg) throws Exception {//모든 직원 정보
		SqlSession session = MyBatisUtil2.getSqlSession();
		session.insert("volunteer.addReg", reg);
		session.commit();
		return true;
	}

}
