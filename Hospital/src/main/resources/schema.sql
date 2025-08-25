CREATE DATABASE IF NOT EXISTS hospital_db;

CREATE USER 'mariam'@'localhost' IDENTIFIED BY '@088564';

-- Grant access to mariam for school database
GRANT ALL PRIVILEGES ON hospital_db.* TO 'mariam'@'localhost';

-- Make sure the privileges are reloaded
FLUSH PRIVILEGES;

--CREATE USER 'mariam'@'%' IDENTIFIED BY '@088564';
--GRANT ALL PRIVILEGES ON hospital_db.* TO 'mariam'@'%';
--FLUSH PRIVILEGES;