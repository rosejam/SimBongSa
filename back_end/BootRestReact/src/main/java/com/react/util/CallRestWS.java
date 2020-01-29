package com.react.util;


import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;

public class CallRestWS {
	public String restClient() throws Exception{ //일단 퍼블릭으로 바꿔봄
		String addr = "http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrSearchWordList"+"?ServiceKey=";
		String serviceKey = "es9jiHOYsOYG9C2%2BzmyeSFxtnzPNcWqLMis2T6Ol2mU1rXVIUntZUjLnFo6W%2B5dKu3den7QwTLrY%2FSTg%2Fa%2F%2BCQ%3D%3D";
		String parameter = "";
		
		//인증키(서비스키) url인코딩
		serviceKey = URLEncoder.encode(serviceKey, "UTF-8");
		
		//parameter setting
		parameter = parameter + "&" + "region=10";
		//parameter = parameter + "&" + "_type=json";
		parameter = parameter + "&" + "pageNo=2";
		
		
		addr = addr + serviceKey + parameter;
		
		URL url = new URL(addr);
		InputStream in = url.openStream(); 
		CachedOutputStream bos = new CachedOutputStream();
		IOUtils.copy(in, bos);
		in.close();
		bos.close();
		return bos.getOut().toString();
	}
}
