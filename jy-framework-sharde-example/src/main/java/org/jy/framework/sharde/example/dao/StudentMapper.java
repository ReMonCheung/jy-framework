package org.jy.framework.sharde.example.dao;

import java.util.List;

import org.jy.framework.sharde.example.entity.Student;

public interface StudentMapper {

	Integer insert(Student s);

	List<Student> findAll();

	List<Student> findByStudentIds(List<Integer> studentIds);

}
