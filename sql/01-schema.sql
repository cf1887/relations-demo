-- Create the database if not exists
CREATE DATABASE IF NOT EXISTS relations_demo DEFAULT CHARACTER
SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Switch to the used database for this application
USE relations_demo;

-- Create project table
CREATE TABLE IF NOT EXISTS project (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_project_name (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Create task table
CREATE TABLE IF NOT EXISTS task (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    project_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_task_project FOREIGN KEY (project_id) REFERENCES project(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;