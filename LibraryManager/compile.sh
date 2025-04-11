#!/bin/bash
# Ejecutar el script dentro de la carpeta del proyecto
# cd IdeaProjects/LibraryManager/
mvn clean javafx:jlink
./target/app/bin/librarymanager