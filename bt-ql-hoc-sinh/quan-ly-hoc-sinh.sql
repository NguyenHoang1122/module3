CREATE DATABASE QuanLyHocSinh;
USE QuanLyHocSinh;

select * from students;
select * from `groups`;
select * from Subject_student ;
select * from Subjects ;


CREATE TABLE Students(
StudentId INT AUTO_INCREMENT PRIMARY KEY,
StudentName VARCHAR(255) NOT NULL,
Gender INT NOT NULL,
Email VARCHAR(255),
Phone VARCHAR(255),
Group_id INT,
FOREIGN KEY (Group_id) REFERENCES `Groups`(Id)
);

CREATE TABLE `Groups`(
Id INT PRIMARY KEY,
GroupName VARCHAR(255)
);

CREATE TABLE Subjects(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(255)
);
 CREATE TABLE Subject_student(
 id INT PRIMARY KEY AUTO_INCREMENT,
 Student_id INT,
 Subject_id INT,
 FOREIGN KEY (Student_id) REFERENCES Students(StudentId),
 FOREIGN KEY (Subject_id) REFERENCES Subjects(id)
 );
 
 SELECT s.StudentId, s.StudentName, s.Gender, s.Email, s.Phone, g.Id AS group_id, g.GroupName AS group_name, 
        GROUP_CONCAT(sub.`name` SEPARATOR ', ') AS subject_names
        FROM students s 
        JOIN `groups` g ON s.group_id = g.id 
        LEFT JOIN subject_student subs ON s.StudentId = subs.Student_id 
        LEFT JOIN subjects sub ON subs.subject_id = sub.id
        GROUP BY s.StudentId;