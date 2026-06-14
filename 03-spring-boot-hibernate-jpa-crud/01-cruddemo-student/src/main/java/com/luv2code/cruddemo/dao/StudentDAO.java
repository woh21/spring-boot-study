package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Student;

import java.util.List;

public interface StudentDAO {

    void save(Student thestudent);

    Student findById(int id);

    List<Student> findAll();

    List<Student> findByName(String theLastName);
}
