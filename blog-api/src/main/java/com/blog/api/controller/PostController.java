package com.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.api.domain.Post;
import com.blog.api.service.PostService;

@Controller
@RequestMapping(path="/posts")
public class PostController {
	
	@Autowired
	PostService postService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Post> list() {
		List<Post> posts = postService.getAllPosts();
		return posts;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Post save(@RequestBody Post post) {
		return postService.add(post);
	}
	
	@RequestMapping(path="/{id}", method=RequestMethod.GET)
	public Post get(@PathVariable Long id) {
		Post post = postService.getPost(id);
		return post;
	}
	
	@RequestMapping(path="/{id}", method=RequestMethod.PUT) 
	public Post update(@RequestBody Post post) {
		return postService.update(post);
	}
	
	@RequestMapping(path="/{id}", method=RequestMethod.DELETE)
	public void delete(@RequestParam Long id) {
		postService.delete(id);
	}
}
