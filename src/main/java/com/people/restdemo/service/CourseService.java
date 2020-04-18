package com.people.restdemo.service;

import com.people.restdemo.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service to expose Operations on Courses
 */
public interface CourseService {
    Page<Course> findAllCoursesByPage(Pageable pageable);
    Page<Course> findAllCourses();
    Course findCourseByCourseCode(String courseCode);
    Course createCourse(Course course);
    Course updateCourse(Course course);
    void deleteCourseByCourseCode(String courseCode);


}
