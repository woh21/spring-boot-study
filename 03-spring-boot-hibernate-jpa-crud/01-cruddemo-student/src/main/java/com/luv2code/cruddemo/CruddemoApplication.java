package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.StudentDAO;
import com.luv2code.cruddemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

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
			//createMultipleStudents(studentDAO);

			//readStudent(studentDAO);

			//queryForStudents(studentDAO);

			//queryForStudentsByLastName(studentDAO);

			//updateStudent(studentDAO);

			//deleteStudent(studentDAO);

			deleteAllStudents(studentDAO);
		};
	}

	private void deleteAllStudents(Object studentDAO) {

		System.out.println("Deleting all students");
		int numRowsDeleted=studentDAO.deleteAll();
		System.out.println("Deleted row count: "+numRowsDeleted);
	}

	private void deleteStudent(Object studentDAO) {

		int studentId = 3;
		System.out.println("Deleting student id"+studentId);
		studentDAO.delete(studentId);
	}

	private void updateStudent(Object studentDAO) {

		//retrieve student based on the id: primary key
		int studentId = 1;
		System.out.println("Getting student with id:"++studentId);
		//change first name to "John"
		System.out.println("Updating student...");
		myStudent.setFirstName("John");
		//update the student
		studentDAO.update(myStudent);
		//display the updated student
		System.out.println("Updated student:" + myStudent);
	}
	private void queryForStudentsByLastName(Object studentDAO) {

		//get a list of students
		List<Student> theStudents = studentDAO.findByLastName(theLastName:"Duck");

		//display list of students
		for (Student tempStudent : theStudents){
			System.out.println(tempStudent);
		}
	}

	private void queryForStudents(StudentDAO studentDAO) {

		//get a list of students
		List<Student> theStudents = studentDAO.findAll();

		//display list of students
		for(Student tempStudent : theStudents) {
			System.out.println(tempStudent);
		}


	}

	private void readStudent(StudentDAO studentDAO) {

		// create a student object
		System.out.println("Creating new student object...");
		Student student = new Student(firstName:"Daffy", lastName:"Duck", email:"daffy@luv2code.com");
		//save the student
		System.out.println("Saving the student...");
		studentDAO.save(tempStudent);
		//display id of the saved student
		int theId = tempStudent.getId();
		System.out.println("Saved student. Generated id " + theId);
		// retrieve student based on the id: primary key
		System.out.println("Retrieving student with id :"+theId);
		student myStudent = studentDAO.findById(theId);
		//display student
		System.out.println("Found the student:"+myStudent);
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
