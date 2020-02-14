package com.a205.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.a205.dto.Category;
import com.a205.dto.Member;
import com.a205.dto.MyFilter;
import com.a205.dto.Vol;
import com.a205.dto.Vol_Mini;

@Repository
public class VolDaoImpl implements VolDao {

	private final static String ns = "com.a205.model.volmapper.";

	@Autowired
	SqlSession session;

	@Autowired
	CategoryDAO categoryDao;
	
	
	public List<Vol_Mini> searchVolPage(int listSize, int startList) {
		String statement = ns + "selectList";
		
		Map<String, Integer> map = new HashMap<>();
		map.put("listSize", listSize);
		map.put("startList", (startList-1)*listSize);
		
		return session.selectList(statement, map);
	}
	
	public Vol searchVol(int v_id) {
		String statement = ns + "selectOne";
		
		return session.selectOne(statement, v_id);
	}

	@Override
	public List<Vol> searchByFilter(int listSize, int startList, MyFilter my) {
		String statement1 = ns + "selectByFilter"; 	//봉사명
		String statement2 = ns + "selectByFilter2"; //지역
		List<String> ca_Id = new ArrayList<String>();
		
		MyFilter m = new MyFilter();
		
		if(my.getV_pBgnD().equals("")) {
			m.setV_pBgnD(null);
		}else {
			m.setV_pBgnD(my.getV_pBgnD());
		}
		
		if(my.getV_pEndD().equals("")) {
			m.setV_pEndD(null);
		}else {
			m.setV_pEndD(my.getV_pEndD());
		}
		
		m.setV_pstatus(my.getV_pstatus());

		if(my.getV_bgnTm().equals("")) {
			m.setV_bgnTm(null);
		}else {
			m.setV_bgnTm(my.getV_bgnTm());
		}
		
		if(my.getV_endTm().equals("")) {
			m.setV_endTm(null);
		}else {
			m.setV_endTm(my.getV_endTm());
		}
		
		if(my.getR_sidoNm().equals("")) {
			m.setR_sidoNm(null);
		}else {
			m.setR_sidoNm(my.getR_sidoNm());
			System.out.println(m.getR_sidoNm());

		}
		
		if(my.getR_gugunNm().equals("")) {
			m.setR_gugunNm(null);
		}else {

			m.setR_gugunNm(my.getR_gugunNm());
			System.out.println(m.getR_gugunNm());

		}
		
		if(my.getVol_title().equals("")) {
			m.setVol_title(null);
		}else {
			m.setVol_title(my.getVol_title());
		}
		
		m.setListSize(listSize);
		m.setStartList((startList-1)*listSize);	
		
		if(my.getCa_highNm().equals("")) {
			my.setCa_highNm(null);
		}else {
			m.setCa_highNm(my.getCa_highNm());
			List<Category> ca = categoryDao.selectList(my.getCa_highNm());
			for(Category c : ca) {
				ca_Id.add(c.getCa_id().toString());
			}			
			m.setCa_id(ca_Id);
		}
		
		List<Vol> f1 = session.selectList(statement1, m);
		List<Vol> f2 = session.selectList(statement2, m);
		
		for(Vol v2 : f2) {
			boolean check =true;
			for(Vol v1 : f1) {
				if(v2.getV_id()==v1.getV_id()) check=false;
			}
			if(check) f1.add(v2);
		}
		System.out.println(m.getVol_title());
		return f1;
	}
}