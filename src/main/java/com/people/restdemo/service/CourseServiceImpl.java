package com.people.restdemo.service;

import com.people.restdemo.domain.Course;
import com.people.restdemo.exception.CourseNotFoundException;
import com.people.restdemo.exception.CourseUpdateException;
import com.people.restdemo.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private static final String COURSE_NOT_FOUND_EXCEPTION = "Course with id: %s not found";
    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Override
    public Page<Course> findAllCoursesByPage(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Page<Course> findAllCourses() {
        return courseRepository.findAll(Pageable.unpaged());
    }

    @Override
    public Course findCourseById(long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new CourseNotFoundException(String.format(COURSE_NOT_FOUND_EXCEPTION, id));
        }
    }

    public Course createCourse(Course courseToBeCreated) {
        Course newCourse = Course.builder().code(courseToBeCreated.getCode()).name(courseToBeCreated.getName()).build();
        newCourse = courseRepository.save(newCourse);
        return newCourse;

    }

    public Course updateCourse(Course courseToBeCreated) {
        Optional<Course> existingCourse = courseRepository.findById(courseToBeCreated.getId());

        if (existingCourse.isPresent()) {
            Course newCourse = existingCourse.get();
            newCourse.setCode(courseToBeCreated.getCode());
            newCourse.setName(courseToBeCreated.getName());
            newCourse = courseRepository.save(newCourse);
            return newCourse;
        } else {
            throw new CourseUpdateException(String.format("Course with id : %s does not exists", courseToBeCreated.getId()));
        }

    }

    public void deleteCourseById(long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            courseRepository.deleteById(id);
        } else {
            throw new CourseNotFoundException(String.format(COURSE_NOT_FOUND_EXCEPTION, id));
        }
    }

}
