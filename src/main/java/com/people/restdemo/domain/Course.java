package com.people.restdemo.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Domain entity for course
 */
@Entity
@Table(name = "Course")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Course  {

    @Id
    @Column(name = "course_code")
    @NotEmpty(message = "Please provide course code")
    @Size.List ({
            @Size(max=4, message="The course code should be  {max} characters")
    })
    private String courseCode;

    @Column(name = "course_name")
    @NotEmpty(message = "Please provide course name")
    private String courseName;
}
