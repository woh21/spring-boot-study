package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.StudentDAO;
import com.luv2code.cruddemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO) {

		return runner ->{
			//createStudent(studentDAO);

			Object studentDAO;
			createMultipleStudents(studentDAO);
		};
	}

	private void createMultipleStudents(Object studentDAO) {

		//create multiple students
		System.out.println("Creating 3 student object...");
		Student tempstudent1 = new Student(firstName: "John", lastName: "Doe", email: "john@luv2code.com");
		Student tempstudent2 = new Student(firstName: "Mary", lastName: "Public", email: "mary@luv2code.com");
		Student tempstudent3 = new Student(firstName: "Bonita", lastName: "Applebum", email: "bonita@luv2code.com");
		//save the student objects
		System.out.println("Saving the students...");
		studentDAO.save(tempstudent1);
		studentDAO.save(tempstudent2);
		studentDAO.save(tempstudent3);
	}

	private void createStudent(StudentDAO studentDAO) {

		// create the student object
		System.out.println("Creating new student object...");
		Student tempstudent = new Student(firstName: "Paul", lastName: "Doe", email: "paul@luv2code.com");
		//save the student object
		System.out.println("Saving student object...");
		studentDAO.save(tempStudent);

		//display id of the saved student
		System.out.println("Saved student. Generated id: " + tempStudent.getId());
	}
}
