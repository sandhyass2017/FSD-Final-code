package com.cognizant.pm.repository;


import org.springframework.data.repository.CrudRepository;

import com.cognizant.pm.dao.User;


public interface UserRepository extends CrudRepository<User, Integer> {

}