package com.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.domain.User;
import com.blog.api.service.UserService;

@RestController
@RequestMapping(path="/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<User> list() {
		return userService.list();
	}
	
	@RequestMapping(path="/{id}", method=RequestMethod.GET)
	public User show(@PathVariable Long id) {
		return userService.getUser(id);
	}
	
	@RequestMapping(path="/{id}", method=RequestMethod.PUT)
	public User update(@PathVariable Long id, @RequestBody User updatedUser) {
		updatedUser.setId(id);
		return userService.update(updatedUser);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public User save(@RequestBody User newUser) {
		return userService.save(newUser);
	}
	
	@RequestMapping(path="/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		userService.delete(id);
	}

}
