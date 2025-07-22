show databases;

/* tao moi csdl */
create database my_db;

use my_db;

/* tao bang */

create table staffs(
id int primary key auto_increment,
fName nvarchar(255) not null,
lName nvarchar(255) not null,
email nvarchar(255) unique not null,
phone nvarchar(11) unique not null,
address nvarchar(255) null
);

/* xoa bang */
DROP TABLE staffs;

/* them 1 ban ghi */
INSERT INTO staffs(fName, lName, email, phone, address)
VALUE ('Pham', 'Nam', 'nam@gmail.com', '09402758492', 'HN');

INSERT INTO staffs(fName, lName, email, phone, address)
VALUE ('Pham', 'Khanh', 'khanh@gmail.com', '09447568492', 'HN');

/* lay du lieu */
SELECT * FROM staffs;

/* lay du lieu dieu kien */

SELECT * FROM staffs
WHERE id = 1;

/* Xoa 1 ban ghi theo id */
DELETE FROM staffs WHERE id = 1;

/* Cap nhap */

UPDATE staffs SET  fName = "Hoang" WHERE id = 2;

/* Cap nhat bang */

ALTER TABLE staffs
	add column salary int default 1000;
    
ALTER TABLE staffs
	drop column salary;

rename table my_staffs to staffs;

alter table staffs
rename column address1 to address;