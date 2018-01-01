package com.cognizant.pm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cognizant.pm.dao.User;
import com.cognizant.pm.repository.UserRepository;


@Controller   
@RequestMapping(path="/user")
public class UserController {
	
	@Autowired 
	private UserRepository userRepository;

	@PostMapping(path="/add")
	public @ResponseBody User addNewUser (@RequestBody User user) {

		User n = new User();
		n.setFirstName(user.getFirstName());
		n.setLastName(user.getLastName());
		n.setEmployeeId(user.getEmployeeId());
		return userRepository.save(n);
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@PutMapping(path="/update")
	public @ResponseBody User updateUser(@RequestBody User user){
		
		User u = userRepository.findOne(user.getUserId());
		u.setEmployeeId(user.getEmployeeId());
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		return userRepository.save(u);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteUser(@PathVariable("id") Integer id){
	     userRepository.delete(id);
	     return "return";
		
	}
}

