package com.a205.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.a205.dto.Post;
import com.a205.dto.Post_vote;

@Repository
public class PostDaoImpl implements PostDao {

	private final static String ns = "com.a205.model.postmapper.";

	@Autowired
	SqlSession session;

	@Override
	public Post selectOne(int p_id) {
		String statement = ns + "selectOne";
		return session.selectOne(statement, p_id);
	}
	
//	@Override
//	public List<Post> searchAll() {
//		String statement = ns + "select";
//
//		return session.selectList(statement);
//	}

	@Override
	public boolean addPostVote(Post_vote post_vote) {
		String statement = ns + "insertPostVote";
		return session.insert(statement, post_vote)>0 ;
	}

	@Override
	public boolean add(Post Post) {
		String statement = ns + "insert";
		return session.insert(statement, Post) > 0;
	}

	@Override
	public boolean update(Post Post) {
		String statement = ns + "update";
		return session.update(statement, Post) > 0;
	}

//	@Override
//	public boolean remove(int p_id, int m_id) {
//		String statement = ns + "delete";
//		return session.delete(statement, p_id) > 0;
//	}
	
	@Override
	public boolean remove(int p_id) {
		String statement = ns + "delete";
		return session.delete(statement, p_id) > 0;
	}

	@Override
	public int getid() {
		String statement = ns + "nextPostId";
		return session.selectOne(statement);
	}
	
	/*
	@Override
	public int addViewCnt(int no) {
		// TODO Auto-generated method stub

		return session.update(ns + "addViewCnt", no);
	}

	@Override
	public int getViewCnt(int no) {
		// TODO Auto-generated method stub
		return session.selectOne(ns + "getViewCnt", no);
	}

	@Override
	public List<Post> searchByCondition(String condition, String key) {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		key = "%" + key + "%";
		map.put("condition", condition);
		map.put("key", key);
		return session.selectList(ns + "searchByCondition", map);
	}

	
	@Override
	public int getBoardListCnt() {

	
		return session.selectOne(ns + "getBoardListCnt");
	}

	@Override
	public List<Post> searchbypage(Pagination p) {
		
		return session.selectList(ns + "getBoardList",p);
	}
	*/
}