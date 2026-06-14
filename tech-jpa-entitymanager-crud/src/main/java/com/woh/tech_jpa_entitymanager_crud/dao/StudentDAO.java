package com.woh.tech_jpa_entitymanager_crud.dao;

import com.woh.tech_jpa_entitymanager_crud.entity.Student;

import java.util.List;

public interface StudentDAO {

    void save(Student student);

    Student findById(Integer id);

    List<Student> findAll();

    List<Student> findByLastName(String lastName);

    void update(Student student);

    void delete(Integer id);

    int deleteAll();
}