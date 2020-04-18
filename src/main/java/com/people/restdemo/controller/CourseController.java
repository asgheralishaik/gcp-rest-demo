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

import javax.validation.Valid;

/**
 * CourseController to perform RESTFul operations on Course Resource
 */
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * Get all courses in Single Page
     * @param pageable parameter to set paging options,i:e to retrieve specific page number pass specific page.
     * @return Retirved Page with Courses
     */
    @GetMapping
    public ResponseEntity<Page<Course>> getAllCoursesByPage(Pageable pageable) {
        return new ResponseEntity<>(courseService.findAllCoursesByPage(pageable), HttpStatus.OK);
    }

    /**
     * Retrieve Course by courseCode
     * @param courseCode coursecourse passed
     * @return Requested Course
     */
    @GetMapping("/{courseCode}")
    public ResponseEntity<Course> getAllCoursesById(@PathVariable("courseCode") String courseCode) {
        return new ResponseEntity<>(courseService.findCourseByCourseCode(courseCode), HttpStatus.OK);
    }

    /**
     * Retrieve All Courses
     * @return all courses in single page
     */
    @GetMapping("/all")
    public Page<Course> getAllCourses() {
        return courseService.findAllCourses();
    }

    /**
     * Create a Course
     * @param course Course to be created
     * @return the created course
     */
    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        Course updated = courseService.createCourse(course);
        return new ResponseEntity<Course>(updated, new HttpHeaders(), HttpStatus.CREATED);
    }

    /**
     * Update a Course
     * @param course Course to be updated
     * @return the updated course
     */

    @PutMapping
    public ResponseEntity<Course> updateCourse(@Valid @RequestBody Course course) {
        Course updated = courseService.updateCourse(course);
        return new ResponseEntity<Course>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Course to be Deleted
     * @param courseCode the courseCode to be deleted
     * @return Success if Deleted,404 if not found
     */
    @DeleteMapping("/{courseCode}")
    public ResponseEntity<HttpStatus> deleteCourseById(@PathVariable("courseCode") String courseCode) {
        courseService.deleteCourseByCourseCode(courseCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
