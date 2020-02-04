package com.a205.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a205.dto.Member;
import com.a205.service.MemberService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest")
public class MemberRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberRestController.class);
	
	@Autowired
	MemberService service;

	private ResponseEntity<Map<String, Object>> response(Object data, boolean status, HttpStatus hStatus){ 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", status);
		resultMap.put("data", data);
		return new ResponseEntity<>(resultMap, hStatus);
	}
	
	@GetMapping("/Member")
	@ApiOperation("전체 회원정보를 반환한다.")
	public ResponseEntity<Map<String, Object>> getAllMember(){
		try {
			List<Member> list = service.searchAll();
			return response(list, true, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("목록조회실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/Member/{userId}")
	@ApiOperation("ID에 해당하는 하나의 회원정보를 반환한다. 요청을 보낸 userid(session에 저장한 데이터)와 검색대상의 userid가 같을 경우에만 값 반환 .")
	public ResponseEntity<Map<String, Object>> getMember(@PathVariable String userId, HttpSession session){
		try {
			
			Member member = service.search(userId);
// 현재 유저검색은 로그인 된 사람만 가능
			if (member.getM_userid() !=  null ) {
				System.out.println(member.getM_userid() + ", " + session.getAttribute("userid"));

				return response(member, true, HttpStatus.OK);
			} else {
				System.out.println(member.getM_userid() + ", " + session.getAttribute("userid"));
				return response(null, true, HttpStatus.OK);
			}
		}catch(Exception e) {
			logger.error("회원조회실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/CheckId/{userId}")
	@ApiOperation("현재 DB에서 id가 존재하는지 체크 후 boolean 값으로 반환")
	public ResponseEntity<Map<String,Object>> checkId(@PathVariable String userId){
		try {
			if(service.checkID(userId)) {
				return response(true, true, HttpStatus.OK);
			}else {
				return response(false, true, HttpStatus.OK);
			}
		}catch(Exception e) {
			logger.error("회원조회실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/CheckEmail/{email}")
	@ApiOperation("현재 DB에서 email이 존재하는지 체크 후 boolean 값으로 반환")
	public ResponseEntity<Map<String,Object>> checkEmail(@PathVariable String email){
		try {
			if(service.checkEmail(email)) {
				return response(true, true, HttpStatus.OK);
			}else {
				return response(false, true, HttpStatus.OK);
			}
		}catch(Exception e) {
			logger.error("회원조회실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}
	
	
	@PostMapping("/Member")
	@ApiOperation("전달받은 회원정보를 저장한다.")
	public ResponseEntity<Map<String, Object>> insertMember(@RequestBody Member member){
		try {
			boolean result = service.add(member);
			System.out.println("---------------------" + member + "---------------------");

			return response(result, true, HttpStatus.CREATED);
		}catch(RuntimeException e) {
			logger.error("회원 가입 실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}
	
	@PutMapping("/Member")
	@ApiOperation("전달받은 회원정보를 업데이트한다.")
	public ResponseEntity<Map<String, Object>> updateMember(@RequestBody Member member){
		try {
			boolean result = service.update(member);
			return response(result, true, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("회원정보 수정 실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}

	
	
	@DeleteMapping("/Member/{id}")
	@ApiOperation("전달받은 회원정보를 삭제한다.")
	public ResponseEntity<Map<String, Object>> deleteMember(@PathVariable String id, HttpSession session){
		try {
			boolean result = service.remove(id);
			if(result==true) {
				session.invalidate();
			}
			return response(result, true, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("회원 탈퇴 실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}





}
