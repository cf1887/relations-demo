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

-- Create tag table
CREATE TABLE IF NOT EXISTS tag (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_tag_name (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Create join table for many-to-many relation between task and tag
CREATE TABLE IF NOT EXISTS task_tag (
    task_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (task_id, tag_id),
    KEY idx_tasktag_task (task_id),
    KEY idx_tasktag_tag (tag_id),
    CONSTRAINT fk_tasktag_task FOREIGN KEY (task_id) REFERENCES task(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_tasktag_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Create comment table
CREATE TABLE IF NOT EXISTS comment (
    id BIGINT NOT NULL AUTO_INCREMENT,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    task_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    KEY idx_comment_task (task_id),
    CONSTRAINT fk_comment_task FOREIGN KEY (task_id) REFERENCES task(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Create user table
CREATE TABLE IF NOT EXISTS user_account (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_email (email)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- Create user_profile table
CREATE TABLE IF NOT EXISTS user_profile (
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_profile_user (user_id),
    CONSTRAINT fk_profile_user FOREIGN KEY (user_id) REFERENCES user_account(id) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;