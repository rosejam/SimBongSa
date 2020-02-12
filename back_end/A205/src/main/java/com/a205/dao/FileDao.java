package com.a205.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.a205.dto.UploadFile;
 	
@Repository
public interface FileDao{
//	Iterable<UploadFile> findAll();
	List<UploadFile> findById(int p_id);
	void insertFile(Map<String, Object> map);
}