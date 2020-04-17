DROP TABLE IF EXISTS Course;

CREATE TABLE Course (
                               id INT AUTO_INCREMENT  PRIMARY KEY,
                               course_code CHAR(4) NOT NULL,
                               course_name VARCHAR(250) NOT NULL

);