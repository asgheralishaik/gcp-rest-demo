package com.people.restdemo.repository;

import com.people.restdemo.domain.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "students",collectionResourceRel = "students", itemResourceRel = "students")
public interface StudentRepository extends PagingAndSortingRepository<Student,Long> {

}
