package com.CoursePlatform.dao;

import com.CoursePlatform.model.Course;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseJdbcDAO implements DAO<Course>{
	
	private static final Logger log = LoggerFactory.getLogger(CourseJdbcDAO.class);	
	private JdbcTemplate jdbcTemplate;
	
	public CourseJdbcDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
     * Maps a row in the database to a Course
     */
    RowMapper<Course> rowMapper = (rs, rowNum) -> {
        Course course = new Course();
        course.setCourseId(rs.getInt("course_id"));
        course.setTitle(rs.getString("title"));
        course.setDescription(rs.getString("description"));
        course.setLink(rs.getString("link"));
        return course;
    };

	@Override
	public List<Course> list() {
		String sql = "SELECT course_id, title, description, link FROM course";
		return jdbcTemplate.query(sql,rowMapper);
	}

	@Override
	public void create(Course course) {
		String sql = "INSERT INTO course(title, description, link) VALUES (?,?,?)";
		int insert = jdbcTemplate.update(sql, course.getTitle(), course.getDescription(), course.getLink());
		if(insert ==1) {
			System.out.println("New course created: " + course.getTitle());
		}
	}

	@Override
	public Optional<Course> get(int id) {
		String sql = "SELECT course_id, title, description, link FROM course WHERE course_id =?";
		Course course = null;
		try {
			course = jdbcTemplate.queryForObject(sql, rowMapper, new Object[] {id});
		} catch (DataAccessException ex){
			log.info("Course not found: " + id);
		}
		return Optional.ofNullable(course);
	}

	@Override
	public void update(Course course, int id) {
		String sql = "UPDATE course SET title = ?, description = ?, link = ? WHERE course_id =?";
		int update = jdbcTemplate.update(sql, course.getTitle(), course.getDescription(), course.getLink(), id);
		if (update == 1) {
			log.info("Course updated " + course.getTitle());
		}
	}

	@Override
	public void delete(int id) {
		jdbcTemplate.update("DELETE from course WHERE course_id = ?", id);
	}

	

}
