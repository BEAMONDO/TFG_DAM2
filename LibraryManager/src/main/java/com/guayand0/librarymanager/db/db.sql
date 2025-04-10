CREATE DATABASE IF NOT EXISTS biblioteca2;
USE biblioteca2;

CREATE TABLE IF NOT EXISTS Usuarios (
    DNI VARCHAR(9) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    fecha_de_nacimiento DATE NOT NULL,
    fecha_de_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Autores (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    fecha_de_nacimiento DATE NOT NULL
);

-- Crear tabla de Categorías
CREATE TABLE IF NOT EXISTS Categorias (
    id_categoria INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

-- Crear tabla de Editoriales
CREATE TABLE IF NOT EXISTS Editoriales (
    id_editorial INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) UNIQUE NOT NULL
);

-- Crear tabla de Idiomas
CREATE TABLE IF NOT EXISTS Idiomas (
    id_idioma INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Libros (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ISBN VARCHAR(20) UNIQUE NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    autor INT NOT NULL,
    id_categoria INT,
    id_editorial INT,
    numero_paginas INT NOT NULL,
    id_idioma INT,
    anio_publicacion INT NOT NULL,
    estado ENUM('disponible', 'prestado', 'deteriorado', 'bloqueado') DEFAULT 'disponible',
    FOREIGN KEY (autor) REFERENCES Autores(ID) ON DELETE CASCADE,
    FOREIGN KEY (id_categoria) REFERENCES Categorias(id_categoria),
    FOREIGN KEY (id_editorial) REFERENCES Editoriales(id_editorial),
    FOREIGN KEY (id_idioma) REFERENCES Idiomas(id_idioma)
);

CREATE TABLE IF NOT EXISTS Prestamos (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(9) NOT NULL,
    libro INT NOT NULL,
    fecha_prestamo TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_devolucion TIMESTAMP NOT NULL,
    fecha_devolucion_real TIMESTAMP,
    multa DECIMAL(10, 2) DEFAULT 0.00
);

ALTER TABLE Usuarios CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Libros CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Autores CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Prestamos CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Categorias CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Editoriales CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Idiomas CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


ALTER TABLE Prestamos ADD CONSTRAINT prestamos_ibfk_1 FOREIGN KEY (usuario) REFERENCES Usuarios(DNI);

ALTER TABLE Prestamos ADD CONSTRAINT prestamos_ibfk_2 FOREIGN KEY (libro) REFERENCES Libros(ID);


INSERT INTO Usuarios (DNI, nombre, apellido, email, contrasena, telefono, direccion, fecha_de_nacimiento)
VALUES
('12345678A', 'Juan', 'Pérez', 'juan.perez@example.com', 'qwewe', '555-1234', 'Calle Falsa 123', '1990-01-01'),
('87654321B', 'Ana', 'López', 'ana.lopez@example.com', 'qwewe', '555-5678', 'Av. Siempreviva 742', '1985-05-15'),
('45678912C', 'Carlos', 'Gómez', 'carlos.gomez@example.com', 'qwewe', '555-8765', 'Paseo del Sol 45', '1992-03-22'),
('78912345D', 'María', 'Hernández', 'maria.hernandez@example.com', 'qwewe', '555-4321', 'Calle Luna 78', '1988-07-10'),
('32165498E', 'Lucía', 'Martínez', 'lucia.martinez@example.com', 'qwewe', '555-9876', 'Calle Estrella 90', '1995-11-30');

INSERT INTO Autores (nombre, apellido, pais, fecha_de_nacimiento)
VALUES
('Gabriel', 'García Márquez', 'Colombia', '1927-03-06'),
('Isabel', 'Allende', 'Chile', '1942-08-02'),
('Mario', 'Vargas Llosa', 'Perú', '1936-03-28'),
('Jorge Luis', 'Borges', 'Argentina', '1899-08-24'),
('Julio', 'Cortázar', 'Argentina', '1914-08-26');

INSERT INTO Categorias (nombre) VALUES ('Novela'), ('Relato');

INSERT INTO Editoriales (nombre) VALUES ('Sudamericana'), ('Plaza & Janés'), ('Seix Barral'), ('Emecé Editores');

INSERT INTO Idiomas (nombre) VALUES ('Español'), ('Inglés');

INSERT INTO Libros (ISBN, titulo, autor, id_categoria, id_editorial, numero_paginas, id_idioma, anio_publicacion)
VALUES
('978-1-56619-909-4', 'Cien años de soledad', 1, 1, 1, 471, 1, 1967),
('978-84-204-8216-3', 'La casa de los espíritus', 2, 1, 2, 368, 2, 1982),
('978-84-376-0494-7', 'La ciudad y los perros', 3, 1, 3, 408, 1, 1963),
('978-84-339-0793-8', 'Ficciones', 4, 2, 4, 174, 1, 1944),
('978-84-339-7147-2', 'Rayuela', 5, 1, 1, 736, 1, 1963);

INSERT INTO Prestamos (usuario, libro, fecha_prestamo, fecha_devolucion)
VALUES
('12345678A', 1, '2024-11-01', '2024-11-15'),
('87654321B', 2, '2024-11-05', '2024-11-20'),
('45678912C', 3, '2024-11-10', '2024-11-25'),
('78912345D', 4, '2024-11-12', '2024-11-26');
INSERT INTO Prestamos (usuario, libro, fecha_prestamo, fecha_devolucion, fecha_devolucion_real, multa)
VALUES
('32165498E', 5, '2024-11-15', '2024-11-30', '2024-12-05',
    DATEDIFF('2024-12-05', '2024-11-30') * 1.00);
INSERT INTO Prestamos (usuario, libro, fecha_prestamo, fecha_devolucion, fecha_devolucion_real)
VALUES
('45678912C', 2, '2024-11-21', '2024-11-30', '2024-11-29');

