package com.people.restdemo.service;

import com.people.restdemo.domain.Course;
import com.people.restdemo.exception.CourseNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    Page<Course> findAllCoursesByPage(Pageable pageable);
    Page<Course> findAllCourses();
    Course findCourseById(long id);
    Course createCourse(Course course);
    Course updateCourse(Course course);
    void deleteCourseById(long id) throws CourseNotFoundException;


}
