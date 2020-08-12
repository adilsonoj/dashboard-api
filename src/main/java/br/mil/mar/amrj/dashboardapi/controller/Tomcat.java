package br.mil.mar.amrj.dashboardapi.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(value = "/tomcat")
public class Tomcat {
	final Logger logger = LoggerFactory.getLogger(Tomcat.class);
	private String user = "admin" + ":" + "tomcat";
	
	@GetMapping("list")
	public ResponseEntity<List<String>> list() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		headers.set("Authorization", "Basic " + Base64Utils.encodeToString((user).getBytes()));
		String url = "http://10.1.32.181:8080/manager/text/list";
		
		HttpEntity<String> entityReq = new HttpEntity<String>(null, headers);

		ResponseEntity<String> respEntity = restTemplate
		    .exchange(url, HttpMethod.GET, entityReq, String.class);

		List<String> lista = Arrays.asList(respEntity.getBody().split("/"));
		logger.info("List");

		return ResponseEntity.ok(lista);

	}
	
	@GetMapping("status")
	public ResponseEntity<String> list2() {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_XML);
		headers.set("Authorization", "Basic " + Base64Utils.encodeToString((user).getBytes()));
		
		String url = "http://10.1.32.181:8080/manager/text/vminfo";
		
		HttpEntity<String> entityReq = new HttpEntity<String>(null, headers);

		ResponseEntity<String> respEntity = restTemplate
		    .exchange(url, HttpMethod.GET, entityReq, String.class);

		System.out.println(respEntity.getBody().indexOf("heap committed"));
		ObjectMapper objectMapper = new ObjectMapper();
		return respEntity;

	}
	

}
