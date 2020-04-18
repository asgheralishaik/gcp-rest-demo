package com.people.restdemo.repository;

import com.people.restdemo.domain.Course;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "courses",collectionResourceRel = "courses", itemResourceRel = "courses")
public interface CourseRepository extends PagingAndSortingRepository<Course,Long> {
     Course findCoursesByCourseCode(String code);
     Course deleteCourseByCourseCode(String code);

}
