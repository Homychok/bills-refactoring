-- liquibase formatted sql

-- changeset Homychok:1
CREATE TABLE student (
                         student_id  BIGINT PRIMARY KEY,
                         student_age         INTEGER NOT NULL,
                         student_name        VARCHAR(255) NOT NULL,
                         faculty_id  BIGINT REFERENCES faculty(faculty_id)
);

ALTER TABLE student ALTER COLUMN student_id ADD GENERATED BY DEFAULT AS IDENTITY;