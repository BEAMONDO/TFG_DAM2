CREATE DATABASE IF NOT EXISTS library;
USE library;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `Autores` (
       `ID` int NOT NULL,
       `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `apellido` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `pais` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `fecha_de_nacimiento` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `Autores` (`ID`, `nombre`, `apellido`, `pais`, `fecha_de_nacimiento`) VALUES
       (1, 'Gabriel', 'García Márquez', 'Colombia', '1927-03-06'),
       (2, 'Mario', 'Vargas Llosa', 'Perú', '1936-03-28'),
       (3, 'Jorge Luis', 'Borges', 'Argentina', '1899-08-24'),
       (4, 'Julio', 'Cortázar', 'Argentina', '1914-08-26'),
       (5, 'Isabel', 'Allende', 'Chile', '1942-08-02');

CREATE TABLE `Categorias` (
       `id_categoria` int NOT NULL,
       `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `Categorias` (`id_categoria`, `nombre`) VALUES
       (3, 'Biografía'),
       (4, 'Ciencia Ficción'),
       (1, 'Novela'),
       (2, 'Relato');

CREATE TABLE `Editoriales` (
        `id_editorial` int NOT NULL,
        `nombre` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `Editoriales` (`id_editorial`, `nombre`) VALUES
       (1, 'Edelvives'),
       (2, 'Emecé Editores'),
       (3, 'Plaza & Janés'),
       (4, 'Seix Barral'),
       (5, 'Sudamericana');

CREATE TABLE `Idiomas` (
       `id_idioma` int NOT NULL,
       `nombre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `Idiomas` (`id_idioma`, `nombre`) VALUES
       (3, 'Español'),
       (2, 'Francés'),
       (1, 'Inglés');

CREATE TABLE `Libros` (
       `ISBN` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `titulo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `autor` int DEFAULT NULL,
       `id_categoria` int DEFAULT NULL,
       `id_editorial` int DEFAULT NULL,
       `numero_paginas` int NOT NULL,
       `id_idioma` int DEFAULT NULL,
       `anio_publicacion` int NOT NULL,
       `estado` enum('disponible','prestado','deteriorado','bloqueado') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'disponible'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `Libros` (`ISBN`, `titulo`, `autor`, `id_categoria`, `id_editorial`, `numero_paginas`, `id_idioma`, `anio_publicacion`, `estado`) VALUES
       ('978-1-56619-909-4', 'Cien Años De Soledad', 1, 1, 5, 417, 3, 1967, 'disponible'),
       ('978-84-204-8216-3', 'La Casa De Los Espíritus', 5, 1, 3, 448, 3, 1982, 'disponible'),
       ('978-84-322-1055-6', 'Rayuela', 4, 1, 4, 736, 3, 1963, 'disponible'),
       ('978-84-376-0493-0', 'Ficciones', 3, 2, 2, 174, 3, 1944, 'disponible'),
       ('978-84-376-0494-7', 'La Ciudad Y Los Perros', 2, 1, 4, 384, 3, 1963, 'disponible');

CREATE TABLE `Prestamos` (
       `ID` int NOT NULL,
       `usuario` varchar(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `libro` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `fecha_prestamo` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       `fecha_devolucion` timestamp NOT NULL,
       `fecha_devolucion_real` timestamp NULL DEFAULT NULL,
       `multa` decimal(10,2) DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Usuarios` (
       `DNI` varchar(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `apellidos` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `contrasena` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `telefono` varchar(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `direccion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
       `fecha_de_nacimiento` date NOT NULL,
       `fecha_de_registro` datetime DEFAULT CURRENT_TIMESTAMP,
       `permiso` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Usuario',
       `sexo` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `Usuarios` (`DNI`, `nombre`, `apellidos`, `email`, `contrasena`, `telefono`, `direccion`, `fecha_de_nacimiento`, `fecha_de_registro`, `permiso`, `sexo`) VALUES
    ('12345678Z', 'Admin', 'Admin', 'admin@admin.es', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', '000000000', 'Admin', '2000-01-01', '2000-01-01 00:00:00', 'Administrador', 'Otro');

ALTER TABLE `Autores`
    ADD PRIMARY KEY (`ID`);

ALTER TABLE `Categorias`
    ADD PRIMARY KEY (`id_categoria`),
    ADD UNIQUE KEY `nombre` (`nombre`);

ALTER TABLE `Editoriales`
    ADD PRIMARY KEY (`id_editorial`),
    ADD UNIQUE KEY `nombre` (`nombre`);

ALTER TABLE `Idiomas`
    ADD PRIMARY KEY (`id_idioma`),
    ADD UNIQUE KEY `nombre` (`nombre`);

ALTER TABLE `Libros`
    ADD PRIMARY KEY (`ISBN`),
    ADD KEY `autor` (`autor`),
    ADD KEY `id_categoria` (`id_categoria`),
    ADD KEY `id_editorial` (`id_editorial`),
    ADD KEY `id_idioma` (`id_idioma`);

ALTER TABLE `Prestamos`
    ADD PRIMARY KEY (`ID`),
    ADD KEY `prestamos_ibfk_1` (`usuario`),
    ADD KEY `prestamos_ibfk_2` (`libro`);

ALTER TABLE `Usuarios`
    ADD PRIMARY KEY (`DNI`),
    ADD UNIQUE KEY `email` (`email`);

ALTER TABLE `Autores`
    MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

ALTER TABLE `Categorias`
    MODIFY `id_categoria` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `Editoriales`
    MODIFY `id_editorial` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

ALTER TABLE `Idiomas`
    MODIFY `id_idioma` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `Prestamos`
    MODIFY `ID` int NOT NULL AUTO_INCREMENT;

ALTER TABLE `Libros`
    ADD CONSTRAINT `Libros_ibfk_1` FOREIGN KEY (`autor`) REFERENCES `Autores` (`ID`) ON DELETE SET NULL,
    ADD CONSTRAINT `Libros_ibfk_2` FOREIGN KEY (`id_categoria`) REFERENCES `Categorias` (`id_categoria`) ON DELETE SET NULL,
    ADD CONSTRAINT `Libros_ibfk_3` FOREIGN KEY (`id_editorial`) REFERENCES `Editoriales` (`id_editorial`) ON DELETE SET NULL,
    ADD CONSTRAINT `Libros_ibfk_4` FOREIGN KEY (`id_idioma`) REFERENCES `Idiomas` (`id_idioma`) ON DELETE SET NULL;

ALTER TABLE `Prestamos`
    ADD CONSTRAINT `Prestamos_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `Usuarios` (`DNI`),
    ADD CONSTRAINT `Prestamos_ibfk_2` FOREIGN KEY (`libro`) REFERENCES `Libros` (`ISBN`);
COMMIT;
