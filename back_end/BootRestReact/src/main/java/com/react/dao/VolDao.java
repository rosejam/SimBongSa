package com.react.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.react.util.MyBatisUtil2;
import com.react.vo.Vol;

public class VolDao {
	
	public void addVol(Vol vol) throws Exception {//모든 직원 정보
		SqlSession session = MyBatisUtil2.getSqlSession();
		session.insert("volunteer.addVol", vol);
		session.commit();
		//return true;
	}
	
	public void updateVol(Vol vol) throws Exception {
		SqlSession session = MyBatisUtil2.getSqlSession();
		session.update("volunteer.updateVol", vol);
	}
	
	public void updateAppnow(int v_id, int num) throws Exception {
		SqlSession session = MyBatisUtil2.getSqlSession();
		Map<String, Integer> map = new HashMap<>();
		map.put("v_id", v_id);
		map.put("num", num);
		
		session.update("volunteer.updateVol", map);
	}
	
}
