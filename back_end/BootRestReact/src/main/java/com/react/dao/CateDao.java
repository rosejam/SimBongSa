package com.react.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.react.util.MyBatisUtil2;
import com.react.vo.Category;

//@Repository
public class CateDao {
	
	public boolean addCate(Category cate) throws Exception {//모든 직원 정보
		SqlSession session = MyBatisUtil2.getSqlSession();
		session.insert("volunteer.addCate", cate);
		session.commit();
		return true;
	}
	
	public int getCateCd(String lowNm) throws Exception {//모든 직원 정보
		SqlSession session = MyBatisUtil2.getSqlSession();
		
		return session.selectOne("volunteer.getCate", lowNm);
	}
}
