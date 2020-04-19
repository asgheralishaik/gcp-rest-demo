package com.people.restdemo.controller;

import com.people.restdemo.domain.Student;
import com.people.restdemo.exception.CourseNotFoundException;
import com.people.restdemo.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * StudentController to perform RESTFul operations on Student Resource
 */
@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Get all students in Single Page
     *
     * @param pageable parameter to set paging options,i:e to retrieve specific page number pass specific page.
     * @return Retirved Page with Students
     */

    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudentsByPage(Pageable pageable) {
        log.info("getting all students with default page size");
        return new ResponseEntity<>(studentService.findAllStudentByPage(pageable), HttpStatus.OK);
    }

    /**
     * Retrieve Course by id
     *
     * @param id student id to be retrieved
     * @return Requested Student
     */

    @GetMapping("/{id}")
    public ResponseEntity<Student> getAllStudentsById(@PathVariable("id") Long id) {
        log.info("getting student with id : {}", id);
        return new ResponseEntity<>(studentService.findStudentById(id), HttpStatus.OK);
    }

    /**
     * Gets all Students in Single page
     *
     * @return Page containing all students
     */
    @GetMapping("/all")
    public Page<Student> getAllStudents() {
        return studentService.findAllStudents();
    }

    /**
     * Create a new Student
     *
     * @param student the student to be created in json format
     * @return the student created
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        log.debug("creating student with info {}", student);
        Student studentUpdated = studentService.createStudent(student);
        return new ResponseEntity<Student>(studentUpdated, new HttpHeaders(), HttpStatus.CREATED);
    }

    /**
     * Updates a Existing Student
     *
     * @param student the Student inforation to be updated
     * @return the updated student information
     */
    @PutMapping
    public ResponseEntity<Student> updateCourse(@Valid @RequestBody Student student) {
        log.info("updating student with id : {}", student.getId());
        Student studentUpdated = studentService.updateStudent(student);
        return new ResponseEntity<Student>(studentUpdated, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Deletes a Student by Id
     *
     * @param id the student id to be deleted
     * @return OK if deleted,404 in not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCourseById(@PathVariable("id") Long id) {
        log.info("deleting student with id : {}", id);
        studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
