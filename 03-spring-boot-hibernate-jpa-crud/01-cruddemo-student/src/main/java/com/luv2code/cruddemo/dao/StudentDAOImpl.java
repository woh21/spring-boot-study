package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jdk.internal.org.jline.utils.Colors.s;

@Repository
public class StudentDAOImpl implements StudentDAO{

    //define field for entity manager
    private EntityManager entityManager;

    //inject entity manager using constructor injection
    @Autowired
    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //implement save method
    @Override
    @Transactional
    public void save(Student thestudent) {
        entityManager.persist(theStudent);
    }

    @Override
    public Student findById(int id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public List<Student> findAll() {
        //create query
        TypedQuery<student> theQuery = entityManager.createQuery(s: "FROM Student order by lastName", student.class);

        //returnj query results
        return theQuery.getResultList();
    }

    @Override
    public List<Student> findByName(String theLastName) {
        //create query
        TypedQuery<student> theQuery = entityManager.createQuery(
                                            s:"FROM Student WHERE lastName=:theData",Student.class);
        //set query parameters
        theQuery.setParameter(s:"theData",theLastName);
        //return query results

        return theQuery.getResultList();
    }
}











