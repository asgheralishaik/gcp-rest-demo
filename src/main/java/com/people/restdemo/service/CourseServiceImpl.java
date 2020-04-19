package com.people.restdemo.service;

import com.people.restdemo.domain.Course;
import com.people.restdemo.exception.CourseNotFoundException;
import com.people.restdemo.exception.CourseUpdateException;
import com.people.restdemo.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementation Class to implement All Course related operations
 */
@Service
@Slf4j
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
    public Course findCourseByCourseCode(String courseCode) {
        Course course = courseRepository.findCoursesByCourseCode(courseCode);
        if (course!=null) {
            return course;
        } else {
            log.error("course with course code {} not found",courseCode);
            throw new CourseNotFoundException(String.format(COURSE_NOT_FOUND_EXCEPTION, courseCode));
        }
    }

    public Course createCourse(Course courseToBeCreated) {
        Course newCourse = Course.builder().courseCode(courseToBeCreated.getCourseCode()).courseName(courseToBeCreated.getCourseName()).build();
        log.debug("course created {}",newCourse);
        newCourse = courseRepository.save(newCourse);
        return newCourse;

    }

    public Course updateCourse(Course course) {
        Course existingCourse = courseRepository.findCoursesByCourseCode(course.getCourseCode());
        if (existingCourse !=null) {
            Course newCourse = existingCourse;
            newCourse.setCourseCode(course.getCourseCode());
            newCourse.setCourseName(course.getCourseName());
            newCourse = courseRepository.save(newCourse);
            return newCourse;
        } else {
            log.error("course with course code {} not found",course.getCourseCode());
            throw new CourseUpdateException(String.format("Course with code : %s does not exists", course.getCourseCode()));
        }

    }

    public void deleteCourseByCourseCode(String code) {
        Course courseToBeDeleted = courseRepository.findCoursesByCourseCode(code);

        if (courseToBeDeleted!=null) {
            courseRepository.deleteCourseByCourseCode(code);
        } else {
            log.error("course with course code {} not found",code);
            throw new CourseNotFoundException(String.format(COURSE_NOT_FOUND_EXCEPTION, code));
        }
    }

}
