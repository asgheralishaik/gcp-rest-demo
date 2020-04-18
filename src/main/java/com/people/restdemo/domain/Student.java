package com.people.restdemo.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Domain Entity for Student
 */
@Entity
@Table(name="Student")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student  implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_rut")
    @Pattern(regexp = "^(\\d{1,3}(?:\\.\\d{1,3}){2}-[\\dkK])$",message = "RUT should be in format XX.XXX.XXX-X")
    private String rut;

    @Column(name = "student_name")
    private String studentFirstName;

    @Column(name = "student_last_name")
    private String studentLastName;


    @Column(name = "student_age")
    @Min(value = 18, message = "Age should greater than 18")
    private int age;

    @Column(name = "course_code")
    @NotEmpty(message = "Please provide course code")
    @Size.List ({
            @Size(max=4, message="The course code should be  {max} characters")
    })
    private String studentCourseCode;



}
