SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS Course cascade ;
DROP TABLE IF EXISTS Student cascade ;

CREATE TABLE Course
(
    course_code CHAR(4) PRIMARY KEY,
    course_name VARCHAR(250) NOT NULL

);

CREATE TABLE Student
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    student_rut       VARCHAR(255) NOT NULL,
    student_name      VARCHAR(255),
    student_last_name VARCHAR(255),
    student_age       TINYINT,
    course_code       CHAR(4),
    FOREIGN KEY (course_code) REFERENCES Course (course_code)
);

SET FOREIGN_KEY_CHECKS = 1;