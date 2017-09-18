package org.jy.framework.sharde.example.service;

import java.util.List;

import org.jy.framework.sharde.example.entity.User;

public interface UserService {

	boolean insert(User user);
	
	List<User> findAll();
	
	List<User> findByUserIds(List<Integer> ids);
	
	void transactionTestSucess();
	
	void transactionTestFailure() throws IllegalAccessException;
	
}
