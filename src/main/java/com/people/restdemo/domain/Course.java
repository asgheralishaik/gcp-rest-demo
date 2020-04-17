package com.people.restdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Course")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code")
    @NotEmpty(message = "Please provide course code")
    @Size.List ({
            @Size(max=4, message="The course code should be  {max} characters")
    })
    private String code;

    @Column(name = "course_name")
    @NotEmpty(message = "Please provide course name")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
