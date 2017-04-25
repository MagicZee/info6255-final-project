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



public class UserServiceTest {
//
//	@Mock
//	private User user;
//	
//	@InjectMocks
//	private UserService service;
//	
//	@Before
//	public void setup(){
//		MockitoAnnotations.initMocks(this);
//	}
	//@Test
//	public void testA(){
//		IdGenerator mock = mock(IdGenerator.class);
//		Long mockId = mock.generate();
//		
//		User mockUser = mock(User.class);
//		//mockUser.setId(mock.generate());
//		when(mockUser.getId()).thenReturn(mockId);
//		when(mockUser.getFirstName()).thenReturn("I am a firstName");
//		when(mockUser.getLastName()).thenReturn("I am a lastName");
//		when(mockUser.getUsername()).thenReturn("I am a userName");
//		
//		
//		
//		UserService service = new UserService();
//		//service.save(mockUser);
//		List<User> u = new ArrayList<>();
//		u.add(mockUser);
//		when(service.list()).thenReturn(u);
//		
//		assertEquals(service.list().get(0).getFirstName(), "I am a firstName");
//	}
	
	@Test
	public void test_POST_users_api() {
		HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setFirstName("ming");
		user.setLastName("zi");
		user.setUsername("zi.m");
		
		try {
			JsonNode body = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.post("http://localhost:8080/blog/api/users")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			JSONObject responseBody = jsonResponse.getBody().getObject();
			assertNotNull(jsonResponse.getBody());
			assertEquals(responseBody.getString("firstName"), user.getFirstName());
			
			//cleanup the test data
			String id = Long.toString(responseBody.getLong("id"));
			jsonResponse = Unirest.delete("http://localhost:8080/blog/api/users/"+ id)
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	
	//Invalid endpoint test
	@Test
	public void test_invalidEndPoint(){
		HttpResponse <JsonNode> jsonResponse;
		ObjectMapper mapper = new ObjectMapper();
		
		Post post = new Post();
		post.setUsername("I am a username");
		post.setType("I am a type");
		post.setContent("I am a content");
		
		try{
			//POST a Post with invalid endpoint
			JsonNode body = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.post("http://localhost:8080/blog/api/persons")
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(body)
					.asJson();
			
			JSONObject responseBody = jsonResponse.getBody().getObject();
			//String id = Long.toString(responseBody.getLong("id"));
			assertEquals(jsonResponse.getStatus(), 404);
		
		}catch(UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

	//GET user by id test case
	@Test
	public void test_post_getUser_api(){
		HttpResponse<JsonNode> jsonResponse;
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setFirstName("Lili");
		user.setLastName("Liu");
		user.setUsername("liu.lili");
		
		try{
			JsonNode body = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.post("http://localhost:8080/blog/api/users")
				  .header("accept", MediaType.APPLICATION_JSON_VALUE)
				  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
				  .body(body)
				  .asJson();
			
			JSONObject responseBody = jsonResponse.getBody().getObject();
			String userId = Long.toString(responseBody.getLong("id"));
			
			jsonResponse = Unirest.get("http://localhost:8080/blog/api/users/" + userId)
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.asJson();
					
			JSONObject responseBody1 = jsonResponse.getBody().getObject();
			assertNotNull(jsonResponse.getBody());
			assertEquals(responseBody1.getString("username"), responseBody.getString("username"));
			assertEquals(responseBody1.getString("firstName"), responseBody.getString("firstName"));
			assertEquals(responseBody1.getString("lastName"), responseBody.getString("lastName"));
			
			jsonResponse = Unirest.delete("http://localhost:8080/blog/api/users/" + userId)
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(body)
					.asJson();
			
			
		}catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	//PUT user positive test
	@Test
	public void test_PUT_userApi(){
HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setFirstName("ming");
		user.setLastName("zi");
		user.setUsername("zi.m");
		
		try {
			JsonNode body = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.post("http://localhost:8080/blog/api/users")
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			JSONObject responseBody = jsonResponse.getBody().getObject();
			assertNotNull(jsonResponse.getBody());
			assertEquals(responseBody.getString("firstName"), user.getFirstName());
			
			String userId = Long.toString(responseBody.getLong("id"));
			Long longId = responseBody.getLong("id");
			String userName = responseBody.getString("username");
			
			User newUser = new User();
			newUser.setId(longId);
			newUser.setUsername("I am a updated Username");
			newUser.setFirstName("I am a updated firstname");
			newUser.setLastName("I am a updated lastname");
			
			//put the post

			JsonNode putBody = new JsonNode(mapper.writeValueAsString(newUser));
			jsonResponse = Unirest.put("Http://localhost:8080/blog/api/users/" + userId)
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(putBody)
					.asJson();
			JSONObject responseBodyUserPut = jsonResponse.getBody().getObject();
			assertEquals(responseBodyUserPut.getString("username"), newUser.getUsername());

			
			
			//cleanup the test data
			String id = Long.toString(responseBody.getLong("id"));
			jsonResponse = Unirest.delete("http://localhost:8080/blog/api/users/"+ id)
					  .header("accept", MediaType.APPLICATION_JSON_VALUE)
					  .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					  .body(body)
					  .asJson();
			
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	
}
