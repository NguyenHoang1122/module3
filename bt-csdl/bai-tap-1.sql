CREATE DATABASE ClinicManagement;

USE ClinicManagement;

CREATE TABLE Doctors(
Doctor_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
Name VARCHAR(255),
Specialty_id INT,
Email VARCHAR(255),
Phone VARCHAR(10),
Experience INT,
FOREIGN KEY (Specialty_id) REFERENCES Specialtes(id)
);

CREATE TABLE Specialtes(
id INT PRIMARY KEY AUTO_INCREMENT, 
name VARCHAR(255)
);

CREATE TABLE Patients(
Patient_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
Name VARCHAR(255),
Age INT,
Gender VARCHAR(255),
Phone VARCHAR(10),
Address VARCHAR(255),
MedicalHistory VARCHAR(255)
);

CREATE TABLE Appointments(
Prescription_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
Doctor_id INT,
Patient_id INT
);

CREATE TABLE Invoices(
Invoices_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
Payment_date DATE,
Payment_method VARCHAR(255),
Total_amount INT,
Patient_id INT,
FOREIGN KEY (Patient_id) REFERENCES Patients(Patient_id)
);

CREATE TABLE Prescriptions(
Prescription_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
Instructions VARCHAR(255),
Doctor_id INT,
Patient_id INT,
FOREIGN KEY (Doctor_id) REFERENCES Doctors(Doctor_id),
FOREIGN KEY (Patient_id) REFERENCES Patients(Patient_id)
);

CREATE TABLE Medicines(
Medicine_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
Name VARCHAR(255),
Indication TEXT,
Unit ENUM('viên', 'chai', 'hộp'),
Price DECIMAL(10,2)
);

CREATE TABLE Prescription_detail(
Dosage VARCHAR(255),
Prescription_id INT,
Medicine_id INT,
PRIMARY KEY(Prescription_id,Medicine_id),
FOREIGN KEY (Prescription_id) REFERENCES Prescriptions(Prescription_id),
FOREIGN KEY (Medicine_id) REFERENCES Medicines(Medicine_id)
);