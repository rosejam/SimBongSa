package com.a205.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.a205.dao.MemberDAO;
import com.a205.service.UserMailSendService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Controller
public class EmailController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberRestController.class);
	
	@Autowired
	private UserMailSendService mailsender;
	
	@Autowired
	private MemberDAO memberDao;
	
	private ResponseEntity<Map<String, Object>> response(Object data, boolean status, HttpStatus hStatus){ 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", status);
		resultMap.put("data", data);
		return new ResponseEntity<>(resultMap, hStatus);
	}
	
	@PostMapping("/email/regist")
	@ApiOperation("전달받은 이메일 정보를 등록한다.")
	public ResponseEntity<Map<String, Object>> registEmail(@RequestBody String m_email) {
		try {
			boolean result = mailsender.mailSendWithUserKey(m_email);
			return response(result, true, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			logger.error("이메일 등록 실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/email/enter")
	@ApiOperation("전달받은 이메일 인증한다.")
	public ResponseEntity<Map<String, Object>> enterEmail(@RequestParam String m_email, @RequestParam String m_key) {
		try {
			boolean result =  memberDao.alter_userKey(m_email, m_key)>0 ? true : false;
			return response(result, true, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			logger.error("이메일 등록 실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}
}
