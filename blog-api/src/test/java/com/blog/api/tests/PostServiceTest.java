package com.blog.api.tests;

import static org.mockito.Mockito.*;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.verification.TooLittleActualInvocations;
import org.mockito.exceptions.verification.TooManyActualInvocations;
import org.testng.annotations.Test;

import com.blog.api.controller.UserController;
//import org.junit.Test;
import com.blog.api.domain.User;
import com.blog.api.service.UserService;
import com.blog.api.service.IdGenerator;
import java.util.NoSuchElementException;

import org.apache.http.protocol.ResponseDate;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.testng.annotations.Test;

import com.blog.api.domain.Comment;
import com.blog.api.domain.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.json.JSONArray;

public class PostServiceTest {
	
	//POST Post api negative test case
		@Test
		public void test_POST_Post_Api(){
			
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				
				Post post = new Post();
				post.setUsername("magiczee");
				post.setType("test type");
				post.setContent("test content");
				
				HttpResponse<JsonNode> jsonResponsePost;
				JsonNode postBody = new JsonNode(mapper.writeValueAsString(post));
				jsonResponsePost = Unirest.post("http://localhost:8080/blog/api/posts")
						  .header("accept", MediaType.APPLICATION_JSON_VALUE)
						  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						  .body(postBody)
						  .asJson();
				
				assertNotNull(jsonResponsePost.getBody());
				assertEquals(jsonResponsePost.getStatus(), 500);
				

				
			} catch (UnirestException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	

}
