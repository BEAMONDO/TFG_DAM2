CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

CREATE TABLE IF NOT EXISTS Usuarios (
    DNI VARCHAR(9) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(15) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    fecha_de_nacimiento DATE NOT NULL,
    fecha_de_registro DATE DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS Libros (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ISBN VARCHAR(20) UNIQUE NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    editorial VARCHAR(100) NOT NULL,
    numero_paginas INT NOT NULL,
    idioma VARCHAR(50) NOT NULL,
    anio_publicacion INT NOT NULL,
    estado ENUM('disponible', 'prestado', 'deteriorado', 'bloqueado')  NOT NULL DEFAULT 'disponible'
);

CREATE TABLE IF NOT EXISTS Prestamos (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(9) NOT NULL,
    libro INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE NOT NULL,
    fecha_devolucion_real DATE,
    multa DECIMAL(10, 2) DEFAULT 0.00
);

ALTER TABLE Usuarios CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Libros CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Autores CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Prestamos CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

ALTER TABLE Prestamos
ADD CONSTRAINT prestamos_ibfk_1 FOREIGN KEY (usuario) REFERENCES Usuarios(DNI) ON DELETE CASCADE;

ALTER TABLE Prestamos
ADD CONSTRAINT prestamos_ibfk_2 FOREIGN KEY (libro) REFERENCES Libros(ID) ON DELETE CASCADE;

INSERT INTO Usuarios (DNI, nombre, apellido, email, telefono, direccion, fecha_de_nacimiento)
VALUES
('12345678A', 'Juan', 'Pérez', 'juan.perez@example.com', '555-1234', 'Calle Falsa 123', '1990-01-01'),
('87654321B', 'Ana', 'López', 'ana.lopez@example.com', '555-5678', 'Av. Siempreviva 742', '1985-05-15'),
('45678912C', 'Carlos', 'Gómez', 'carlos.gomez@example.com', '555-8765', 'Paseo del Sol 45', '1992-03-22'),
('78912345D', 'María', 'Hernández', 'maria.hernandez@example.com', '555-4321', 'Calle Luna 78', '1988-07-10'),
('32165498E', 'Lucía', 'Martínez', 'lucia.martinez@example.com', '555-9876', 'Calle Estrella 90', '1995-11-30');

INSERT INTO Libros (ISBN, titulo, autor, categoria, editorial, numero_paginas, idioma, anio_publicacion)
VALUES
('978-1-56619-909-4', 'Cien años de soledad', "Gabriel García Márquez", 'Novela', 'Sudamericana', 471, 'Español', 1967),
('978-84-204-8216-3', 'La casa de los espíritus', "Isabel Allende", 'Novela', 'Plaza & Janés', 368, 'Inglés', 1982),
('978-84-376-0494-7', 'La ciudad y los perros', "Mario Vargas Llosa", 'Novela', 'Seix Barral', 408, 'Español', 1963),
('978-84-339-0793-8', 'Ficciones', "Jorge Luis Borges", 'Relato', 'Emecé Editores', 174, 'Español', 1944),
('978-84-339-7147-2', 'Rayuela', "Julio Cortázar", 'Novela', 'Sudamericana', 736, 'Español', 1963);

INSERT INTO Prestamos (usuario, libro, fecha_prestamo, fecha_devolucion)
VALUES
('12345678A', 1, '2024-11-01', '2024-11-15'),
('87654321B', 2, '2024-11-05', '2024-11-20'),
('45678912C', 3, '2024-11-10', '2024-11-25'),
('78912345D', 4, '2024-11-12', '2024-11-26'),
('32165498E', 5, '2024-11-15', '2024-11-30');
