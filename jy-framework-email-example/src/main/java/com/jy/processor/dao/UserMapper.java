package com.jy.processor.dao;

import java.util.List;

import com.jy.processor.entity.User;

public interface UserMapper {

	Integer insert(User u);

	List<User> findAll();

	List<User> findByUserIds(List<Integer> userIds);

}
