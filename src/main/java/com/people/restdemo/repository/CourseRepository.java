package com.people.restdemo.repository;

import com.people.restdemo.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "courses",collectionResourceRel = "courses", itemResourceRel = "courses")
public interface CourseRepository extends PagingAndSortingRepository<Course,Long> {
     List<Course> findCoursesByCode(char code);
}
