package org.jy.framework.sharde.example.service.impl;

import java.util.List;

import org.jy.framework.sharde.example.dao.StudentMapper;
import org.jy.framework.sharde.example.dao.UserMapper;
import org.jy.framework.sharde.example.entity.Student;
import org.jy.framework.sharde.example.entity.User;
import org.jy.framework.sharde.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private StudentMapper studentMapper;

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
          
        Student student = new Student();  
        student.setStudentId(21);  
        student.setAge(21);  
        student.setName("hehe");  
        studentMapper.insert(student);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void transactionTestFailure() throws IllegalAccessException {
		User u = new User();  
        u.setUserId(13);  
        u.setAge(25);  
        u.setName("war3 1.27 good");  
        userMapper.insert(u);  
          
        Student student = new Student();  
        student.setStudentId(21);  
        student.setAge(21);  
        student.setName("hehe1");  
        studentMapper.insert(student);  
        throw new IllegalAccessException(); 

	}

}
