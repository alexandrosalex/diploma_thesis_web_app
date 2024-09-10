-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS diplomadb;
USE diplomadb;
--
-- Table structure for table `applications`
--

DROP TABLE IF EXISTS applications;

CREATE TABLE applications (
  id INT NOT NULL AUTO_INCREMENT,
  student_id INT,
  subject_id INT,
  status ENUM('PENDING', 'REJECTED', 'ACCEPTED'),
  implementation_grade DOUBLE,
  report_grade DOUBLE,
  presentation_grade DOUBLE,
  final_grade DOUBLE,
  PRIMARY KEY (id),
  FOREIGN KEY (student_id) REFERENCES users (id),
  FOREIGN KEY (subject_id) REFERENCES subjects (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `professors`
--

DROP TABLE IF EXISTS professors;

CREATE TABLE professors (
  user_id INT NOT NULL,
  full_name VARCHAR(255),
  specialty VARCHAR(255),
  PRIMARY KEY (user_id),
  FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS students;

CREATE TABLE students (
  user_id INT NOT NULL,
  full_name VARCHAR(255),
  year_of_studies INT,
  current_average_grade DOUBLE,
  courses_for_graduation INT,
  PRIMARY KEY (user_id),
  FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS subjects;

CREATE TABLE subjects (
  id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255),
  objectives TEXT,
  professor_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (professor_id) REFERENCES professors (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  role ENUM('PROFESSOR', 'STUDENT'),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
