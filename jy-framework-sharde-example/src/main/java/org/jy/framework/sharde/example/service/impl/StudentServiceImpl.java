package org.jy.framework.sharde.example.service.impl;

import org.jy.framework.sharde.example.dao.StudentMapper;
import org.jy.framework.sharde.example.entity.Student;
import org.jy.framework.sharde.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentMapper studentMapper;

	@Override
	public boolean insert(Student student) {
		return studentMapper.insert(student) > 0 ? true : false;
	}

}
