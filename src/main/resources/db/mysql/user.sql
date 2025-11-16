CREATE DATABASE IF NOT EXISTS tunehub_app;

ALTER DATABASE tunehub_app
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_general_ci;

GRANT ALL PRIVILEGES ON tunehub_app.* TO 'tunehub'@'%' IDENTIFIED BY 'tunehub_password';
