package com.blog.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.domain.User;

@Service
public class UserService {
	
	List<User> users;
	
	@Autowired
	IdGenerator idGenerator;
	
	@PostConstruct
	private void init() {
		users = new ArrayList<>();
	}
	
	public List<User> list() {
		return users;
	}
	
	public User getUser(Long id) {
		User user = users.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst().get();
		return user;
	}
	
	public User save(User newUser) {
		User user = new User();
		user.setId(idGenerator.generate());
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setUsername(newUser.getUsername());
		users.add(user);
		return user;
	}
	
	public User update(User updatedUser) {
		User user = getUser(updatedUser.getId());
		user.setFirstName(updatedUser.getFirstName());
		user.setLastName(updatedUser.getLastName());
		user.setUsername(updatedUser.getUsername());
		return user;
	}
	
	public void delete(Long id) {
		User user = getUser(id);
		users.remove(user);
	}
	
}
