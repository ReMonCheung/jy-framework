package com.jy.processor.service;

import java.util.List;

import com.jy.processor.entity.User;

public interface UserService {

	boolean insert(User user);
	
	List<User> findAll();
	
	List<User> findByUserIds(List<Integer> ids);
	
	void transactionTestSucess();
	
	void transactionTestFailure() throws IllegalAccessException;
	
}
