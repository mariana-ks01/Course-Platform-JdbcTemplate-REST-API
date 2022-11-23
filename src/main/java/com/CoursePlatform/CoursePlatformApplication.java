package com.CoursePlatform;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.CoursePlatform.dao.DAO;
import com.CoursePlatform.model.Course;

@SpringBootApplication
public class CoursePlatformApplication {
	
	private static DAO<Course> dao;
	
	public CoursePlatformApplication(DAO<Course> dao) {
		this.dao = dao;
	}

	public static void main(String[] args) {
		SpringApplication.run(CoursePlatformApplication.class, args);
		
		System.out.println("\n Create Course ------------------ \n");
		Course springVue = new Course("Spring Boot + Vue", "New Course", "https://www.danvega.dev/courses");
		dao.create(springVue);
		
		System.out.println("\n One Course ------------------ \n");
		Optional<Course> firstOne = dao.get(1);
		System.out.println(firstOne.get());
		
		springVue.setDescription("Learn to build Vue apps that talk to Spring Boot");
		dao.update(springVue, 6);
		
		dao.delete(4);
		
		System.out.println("\n All Courses ------------------ \n");
		List<Course> courses = dao.list();
		courses.forEach(System.out::println);
	}

}
