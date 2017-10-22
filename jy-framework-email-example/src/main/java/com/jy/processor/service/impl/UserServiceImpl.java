package com.jy.processor.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jy.processor.dao.UserMapper;
import com.jy.processor.entity.User;
import com.jy.processor.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean insert(User user) {
		return userMapper.insert(user) > 0 ? true : false;
	}
	
	@Override
	public List<User> findAll() {
		return userMapper.findAll();
	}

	@Override
	public List<User> findByUserIds(List<Integer> ids) {
		return userMapper.findByUserIds(ids);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void transactionTestSucess() {
		User u = new User();  
        u.setUserId(13);  
        u.setAge(25);  
        u.setName("war3 1.27");  
        userMapper.insert(u);  
          
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void transactionTestFailure() throws IllegalAccessException {
		User u = new User();  
        u.setUserId(13);  
        u.setAge(25);  
        u.setName("war3 1.27 good");  
        userMapper.insert(u);  
          
        throw new IllegalAccessException(); 

	}

}
