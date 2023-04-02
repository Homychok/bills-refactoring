-- liquibase formatted sql

-- changeset Homychok:1
CREATE TABLE faculty(
                        faculty_id  BIGINT PRIMARY KEY,
                        faculty_color       VARCHAR(255) NOT NULL,
                        faculty_name        VARCHAR(255) UNIQUE NOT NULL
);

ALTER TABLE faculty ALTER COLUMN faculty_id ADD GENERATED BY DEFAULT AS IDENTITY;