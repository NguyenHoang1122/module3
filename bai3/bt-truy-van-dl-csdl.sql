
USE QuanLySinhVien;

SELECT * FROM Student
WHERE LOWER(StudentName) LIKE 'h%';

SELECT * FROM Class
WHERE MONTH(StartDate) = 12;

SELECT * FROM `Subject`
WHERE Credit BETWEEN 3 AND 5;

SELECT * FROM student;

UPDATE Student
SET ClassId = 2
WHERE StudentName = 'Hung' AND StudentId = 1;

SELECT s.StudentName, sub.SubName, m.Mark
FROM Mark m
JOIN Student s ON m.StudentID = s.StudentID
JOIN `Subject` sub ON m.SubId = sub.SubId
ORDER BY m.Mark DESC,s.StudentName;