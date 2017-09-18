package org.jy.framework.sharde.example.dao;

import java.util.List;

import org.jy.framework.sharde.example.entity.User;

public interface UserMapper {

	Integer insert(User u);

	List<User> findAll();

	List<User> findByUserIds(List<Integer> userIds);

}
