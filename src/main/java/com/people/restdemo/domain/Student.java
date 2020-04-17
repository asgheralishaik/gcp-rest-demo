package com.people.restdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table(name="Student")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Pattern(regexp = "^(\\d{1,3}(?:\\.\\d{1,3}){2}-[\\dkK])$")
    private String rut;
    private String name;
    private String lastName;
    private int age;
    private char course;
}
