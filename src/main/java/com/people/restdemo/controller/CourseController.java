package com.people.restdemo.controller;

import com.people.restdemo.domain.Course;
import com.people.restdemo.exception.CourseNotFoundException;
import com.people.restdemo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<Page<Course>> getAllCoursesByPage(Pageable pageable) {
        return new ResponseEntity<>(courseService.findAllCoursesByPage(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getAllCoursesById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courseService.findCourseById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public Page<Course> getAllCourses() {
        return courseService.findAllCourses();
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course)
            throws CourseNotFoundException {
        Course updated = courseService.createCourse(course);
        return new ResponseEntity<Course>(updated, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) {
        Course updated = courseService.updateCourse(course);
        return new ResponseEntity<Course>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCourseById(@PathVariable("id") Long id) {
        courseService.deleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
