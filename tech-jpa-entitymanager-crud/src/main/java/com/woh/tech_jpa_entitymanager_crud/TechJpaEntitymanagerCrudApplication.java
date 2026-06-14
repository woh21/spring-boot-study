package com.woh.tech_jpa_entitymanager_crud;

import com.woh.tech_jpa_entitymanager_crud.dao.StudentDAO;
import com.woh.tech_jpa_entitymanager_crud.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TechJpaEntitymanagerCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechJpaEntitymanagerCrudApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
		return runner -> {
			createMultipleStudents(studentDAO);
			queryForStudents(studentDAO);
			queryForStudentsByLastName(studentDAO);
			updateStudent(studentDAO);
			//deleteStudent(studentDAO);
			//deleteAllStudents(studentDAO);
		};
	}

	private void createMultipleStudents(StudentDAO studentDAO) {
		System.out.println("Creating students...");

		Student student1 = new Student("John", "Doe", "john@test.com");
		Student student2 = new Student("Mary", "Public", "mary@test.com");
		Student student3 = new Student("Daffy", "Duck", "daffy@test.com");

		System.out.println("Saving students...");

		studentDAO.save(student1);
		studentDAO.save(student2);
		studentDAO.save(student3);

		System.out.println("Saved students.");
	}

	private void queryForStudents(StudentDAO studentDAO) {
		System.out.println("\nFinding all students...");

		List<Student> students = studentDAO.findAll();

		for (Student student : students) {
			System.out.println(student);
		}
	}

	private void queryForStudentsByLastName(StudentDAO studentDAO) {
		System.out.println("\nFinding students by last name: Doe");

		List<Student> students = studentDAO.findByLastName("Doe");

		for (Student student : students) {
			System.out.println(student);
		}
	}

	private void updateStudent(StudentDAO studentDAO) {
		System.out.println("\nUpdating student...");

		Student student = studentDAO.findByLastName("Doe").get(0);

		student.setFirstName("Scooby");

		studentDAO.update(student);

		System.out.println("Updated student: " + student);
	}

	private void deleteStudent(StudentDAO studentDAO) {
		System.out.println("\nDeleting one student...");

		Student student = studentDAO.findByLastName("Duck").get(0);

		studentDAO.delete(student.getId());

		System.out.println("Deleted student id: " + student.getId());
	}

	private void deleteAllStudents(StudentDAO studentDAO) {
		System.out.println("\nDeleting all students...");

		int deletedCount = studentDAO.deleteAll();

		System.out.println("Deleted row count: " + deletedCount);
	}
}