package org.jy.framework.sharde.example.service.impl;

import javax.annotation.Resource;

import org.jy.framework.sharde.example.dao.StudentMapper;
import org.jy.framework.sharde.example.entity.Student;
import org.jy.framework.sharde.example.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Resource
	private StudentMapper studentMapper;

	@Override
	public boolean insert(Student student) {
		return studentMapper.insert(student) > 0 ? true : false;
	}

}
