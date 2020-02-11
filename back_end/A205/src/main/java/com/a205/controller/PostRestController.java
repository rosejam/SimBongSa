package com.a205.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.a205.config.JwtTokenUtil;
import com.a205.dao.FollowDAO;
import com.a205.dao.MemberDAO;
import com.a205.dto.Post;
import com.a205.service.FileUploadDownloadService;
import com.a205.service.JwtUserDetailsService;
import com.a205.service.PostService;
import com.file.payload.FileUploadResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = "*")
public class PostRestController {
	private static final Logger logger = LoggerFactory.getLogger(PostRestController.class);

	@Autowired
	PostService service;
	
	@Autowired
	MemberDAO memberDao;
	
	@Autowired
	FollowDAO followDao;
	
	@Autowired
	FileUploadDownloadService f_service;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;


	private ResponseEntity<Map<String, Object>> response(Object data, boolean status, HttpStatus hstatus) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", status);
		resultMap.put("data", data);
		return new ResponseEntity<>(resultMap, hstatus);
	}

	@PostMapping("/Post")
	@ApiOperation("전달받은 포스트 정보를 등록한다.")
	public ResponseEntity<Map<String, Object>> insertMember(@RequestPart Post post,
			@RequestPart(required = false) MultipartFile[] files) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			boolean result = service.add(post);
			// service.add(Post);
			List<FileUploadResponse> fileResponses = null;
			if (files.length != 0) {
				int p_id = service.getid();
				// List<FileUploadResponse> fileResponse= uploadMultipleFiles(files, p_id);
				fileResponses = uploadMultipleFiles(files, p_id);
			}
			resultMap.put("result", result);
			resultMap.put("fileResponses", fileResponses);
			
			return response(resultMap, true, HttpStatus.CREATED);
			// return response(fileResponse, true, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			logger.error("포스트 등록 실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}
	
//	@GetMapping("/Post")
//	@ApiOperation("전체 포스트 정보를 반환한다.")
//	public ResponseEntity<Map<String, Object>> getAllMember() {
//		try {
//			List<Post> list = service.searchAll();
//			return response(list, true, HttpStatus.OK);
//		} catch (Exception e) {
//			logger.error("목록조회실패", e);
//			return response(e.getMessage(), false, HttpStatus.CONFLICT);
//		}
//	}
		
	@GetMapping("/Post/Feeds")
	@ApiOperation("header 토큰에 담겨진 유저정보를 기준으로, 그 사람이 팔로우한 사람의 게시글들만 모아 리스트 형식으로 반환한다.")
	public ResponseEntity<Map<String, Object>> getPostFeeds(HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");
		String userEmail = null;
		String jwtToken = null;
		System.out.println(requestTokenHeader.toString());
		jwtToken = requestTokenHeader.substring(7);

		userEmail = jwtTokenUtil.getUsernameFromToken(jwtToken);

		try {
			String userId = memberDao.searchByEmail(userEmail).getM_userid();
			
			List<Integer> my_following_m_ids = followDao.searchFolloweesByClient(userId);
			
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Post post = service.selectOne(p_id);
			
			String[] uris = getMultipleFiles(p_id);
			
			resultMap.put("post", post);
			resultMap.put("uris", uris);
			
			return response(resultMap, true, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("포스트조회실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}

	

	@GetMapping("/Post/{p_id}")
	@ApiOperation("p_id의 포스트 및 첨부파일 경로 리스트를 반환한다.")
	public ResponseEntity<Map<String, Object>> getPost(@PathVariable int p_id) {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Post post = service.selectOne(p_id);
			
			String[] uris = getMultipleFiles(p_id);
			
			resultMap.put("post", post);
			resultMap.put("uris", uris);
			
			return response(resultMap, true, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("포스트조회실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}

	/*
	 * @RequestMapping(method = RequestMethod.GET, value = "/Post/Page")
	 * 
	 * @ApiOperation("포스트 페이지를 반환한다.") public ResponseEntity<Map<String, Object>>
	 * getFoodPage(@RequestParam Map<String,String> params) { try {
	 * 
	 * Integer page= Integer.parseInt(params.get("page")); Integer range=
	 * Integer.parseInt(params.get("range"));
	 * 
	 * Integer foodlistCnt = service.getTotalPostListCnt();
	 * 
	 * Pagination p = new Pagination();
	 * 
	 * p.pageInfo(page, range, foodlistCnt);
	 * 
	 * List<Post> foodres = service.getPostpage(p); HashMap<String,Object> res=new
	 * HashMap<>(); res.put("items", foodres); res.put("pagination", p);
	 * 
	 * return response(res, true, HttpStatus.OK); } catch (Exception e) {
	 * logger.error("포스트조회실패", e); return response(e.getMessage(), false,
	 * HttpStatus.CONFLICT); } }
	 */


	////업데이트는 일단 보류
	
//	@PutMapping("/Post")
//	@ApiOperation("전달받은 포스트 정보를 업데이트한다.")
//	public ResponseEntity<Map<String, Object>> updateMember(@RequestBody Post Post) {
//		try {
//			boolean result = service.update(Post);
//			return response(result, true, HttpStatus.OK);
//		} catch (Exception e) {
//			logger.error("포스트 정보 수정 실패", e);
//			return response(e.getMessage(), false, HttpStatus.CONFLICT);
//		}
//	}

	@DeleteMapping("/Post/{p_id}")
	@ApiOperation("전달받은 포스트 정보를 삭제한다.")
	public ResponseEntity<Map<String, Object>> deleteMember(@PathVariable int p_id, HttpSession session) {
		try {
			boolean result = service.remove(p_id);
			if (result == true) {
				session.invalidate();
			}
			return response(result, true, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("포스트 삭제 실패", e);
			return response(e.getMessage(), false, HttpStatus.CONFLICT);
		}
	}

//	@GetMapping("Post/search")
//	public ResponseEntity<Map<String, Object>> getPostListView(@RequestParam String condition,
//			@RequestParam String key) {
//		try {
//			System.out.printf("c %s  k %s \n", condition, key);
//			List<Post> Postlist = service.searchByCondition(condition, key);
//			System.out.println(Postlist);
//			return response(Postlist, true, HttpStatus.OK);
//		} catch (Exception e) {
//			logger.error("공지 검색  실패", e);
//			return response(e.getMessage(), false, HttpStatus.CONFLICT);
//			// TODO: handle exception
//		}
//
//		// System.out.println(service.searchAll());
//
//	}
	//////////////////////////
	// 이 아래로 파일 업로드용 부분
	// @PostMapping("/uploadFile") //단일 파일 업로드
	public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file, int p_id) {

		String storedFileName = f_service.storeFile(file, p_id);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(storedFileName).toUriString();

		return new FileUploadResponse(file.getOriginalFilename(), fileDownloadUri, file.getContentType(),
				file.getSize());
	}
	// @PostMapping("/uploadMultipleFiles") // 다중 파일 업로드
	public List<FileUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, int p_id) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file, p_id)).collect(Collectors.toList());
	}

	//////////////////////////
	// 이 아래로 포스트에 해당하는 파일의 경로들을 받아옴
	private String[] getMultipleFiles(int p_id) {
		
		return null;
	}
	
}
