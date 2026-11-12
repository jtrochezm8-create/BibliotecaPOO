Biblioteca - CRUD con DAO y SQLite

* Nombre: Josue Alejandro Trochez Mendez
* Numero de cuenta: 202330010407

Descripcion

Proyecto Maven en Java que administra autores y libros (un autor tiene muchos libros).
Usa interfaces DAO, JDBC con SQLite y una separacion por capas: modelo, dao, servicio y presentacion (menu de consola).

Requisitos

* JDK 11 o superior
* Maven (incluido en NetBeans)
* Conexion a internet la primera vez, para que Maven descargue el driver `sqlite-jdbc`

Como ejecutar

1. Abrir el proyecto en NetBeans (File > Open Project).
2. Clic derecho sobre el proyecto > Clean and Build.
3. Ejecutar con Run Project (clase principal: `hn.uth.poo.biblioteca.app.Main`).
4. Usar el menu que aparece en la ventana Output.

La base de datos biblioteca.db se crea sola en la carpeta del proyecto al ejecutar.
El script biblioteca.sql tiene las mismas tablas por si se quieren crear a mano.

Por consola con Maven:

```
mvn compile exec:java -Dexec.mainClass=hn.uth.poo.biblioteca.app.Main
```

## Estructura de paquetes

* `hn.uth.poo.biblioteca.modelo` - Autor, Libro
* `hn.uth.poo.biblioteca.excepciones` - DatosException, DatoInvalidoException
* `hn.uth.poo.biblioteca.dao` - Conexion, AutorDAO, LibroDAO
* `hn.uth.poo.biblioteca.dao.impl` - AutorDAOImpl, LibroDAOImpl
* `hn.uth.poo.biblioteca.servicio` - ServicioAutores, ServicioLibros
* `hn.uth.poo.biblioteca.app` - Main

## Reglas de negocio

* Nombre del autor y titulo del libro son obligatorios.
* El ISBN no se puede repetir.
* El anio debe estar entre 1450 y el anio actual.
* El precio debe ser mayor o igual a 0.
* Un libro solo se registra si su autor existe.
* No se puede eliminar un autor que tiene libros.

## Evidencias

Las capturas estan en la carpeta "evidencias".

Autores

* Crear:
* Listar:
* Buscar por id:
* Actualizar:
* Eliminar:

Libros

* Crear:
* Listar:
* Buscar por id:
* Actualizar:
* Eliminar:
* Listar libros de un autor:

Reglas de negocio rechazando datos

* ISBN repetido:
* Eliminar un autor con libros:

