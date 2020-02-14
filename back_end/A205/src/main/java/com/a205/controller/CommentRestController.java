package com.a205.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.a205.dao.MemberDAO;
import com.a205.dto.Comment;
import com.a205.dto.Comment_mini;
import com.a205.dto.Comment_update;
import com.a205.service.CommentService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RequestMapping("/rest")
@RestController
public class CommentRestController {

	private static final Logger Logger = LoggerFactory.getLogger(CommentRestController.class);

	@Autowired
	CommentService service;

	private ResponseEntity<Map<String, Object>> response(Object data, boolean status, HttpStatus hstatus) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", status);
		resultMap.put("data", data);
		return new ResponseEntity<>(resultMap, hstatus);
	}

	@GetMapping("/Comment/{p_id}")
	@ApiOperation("해당하는 포스트에 달린 모든 댓글을 가져온다 .")
	public ResponseEntity<Map<String, Object>> getComments(@PathVariable int p_id) {
		try {
			List<Comment> comments = service.searchListComments(p_id);
			return response(comments, true, HttpStatus.OK);
		} catch (Exception e) {
			Logger.error("댓글 불러오기 실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/Comment")
	@ApiOperation("No에 해당하는 하나의 포스트에 댓글을 남긴다.")
	public ResponseEntity<Map<String, Object>> postComment(@RequestBody Comment_mini comment) {
		try {
			boolean result = service.add(comment.getP_id(), comment.getM_id(), comment.getC_content());
			return response(result, true, HttpStatus.OK);
		} catch (Exception e) {
			Logger.error("댓글생성실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/Comment/{c_id}")
	@ApiOperation("No에 해당하는 하나의 포스트에 해당 댓글을 지운다.")
	public ResponseEntity<Map<String, Object>> removeComment(@PathVariable int c_id) {
		try {
//			int comment_m_id = service.searchOne(c_id).getM_id();
//			
//			if (comment_m_id == m_id) {
			boolean result = service.remove(c_id);
			return response(result, true, HttpStatus.OK);
//			}else {
//				
//				return response(false, true, HttpStatus.BAD_REQUEST);
//			}
		} catch (Exception e) {
			Logger.error("댓글삭제실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/Comment")
	@ApiOperation("No에 해당하는 하나의 포스트에 해당 댓글을 수정한다.")
	public ResponseEntity<Map<String, Object>> postComment(@RequestBody Comment_update comment) {

		try {
//			Comment comment_obj = service.searchOne(c_id);
//			int comment_m_id = comment_obj.getM_id();
//
//			if (comment_m_id == m_id) {
//				comment_obj.setC_content(new_comment.getC_content());
			boolean result = service.update(comment);
			return response(result, true, HttpStatus.OK);
//			return response(null, true, HttpStatus.OK);
//			} else {
//
//				return response(false, true, HttpStatus.BAD_REQUEST);
//			}
		} catch (Exception e) {
			Logger.error("댓글삭제실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}

}
