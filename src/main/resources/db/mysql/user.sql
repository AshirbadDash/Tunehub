CREATE DATABASE IF NOT EXISTS bloomwave_app;
    DEFAULT CHARACTER SET utf8mb4
      DEFAULT COLLATE utf8mb4_general_ci;

ALTER DATABASE bloomwave_app
      DEFAULT CHARACTER SET utf8mb4
        DEFAULT COLLATE utf8mb4_general_ci;

GRANT ALL PRIVILEGES ON bloomwave_app.* TO 'bloomwave'@'%' IDENTIFIED BY 'bloomwave_password';
