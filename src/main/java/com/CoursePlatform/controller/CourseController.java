package com.CoursePlatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CoursePlatform.dao.CourseJdbcDAO;
import com.CoursePlatform.model.Course;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
	
	private final CourseJdbcDAO dao;
	
	@Autowired
	public CourseController(CourseJdbcDAO dao) {
		this.dao = dao;
	}
	
	@GetMapping
	public List<Course> getAllCourses(){
		return dao.list();
	}
	
	@GetMapping(path= "{id}")
	public Course getCourseById(@PathVariable("id") int id) {
		return dao.get(id).orElse(null);
	}
	
	@PostMapping
	public void addCourse(@RequestBody Course newCourse) {
		dao.create(newCourse);
	}
	
	@PutMapping(path = "{id}")
	public void updateCourseById(@PathVariable("id") int id, @RequestBody Course newCourse) {
		dao.update(newCourse, id);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteCourseById(@PathVariable("id") int id) {
		dao.delete(id);
	}
	
}
