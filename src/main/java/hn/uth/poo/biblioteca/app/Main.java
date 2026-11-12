package hn.uth.poo.biblioteca.app;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.Conexion;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.dao.impl.AutorDAOImpl;
import hn.uth.poo.biblioteca.dao.impl.LibroDAOImpl;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import hn.uth.poo.biblioteca.servicio.ServicioAutores;
import hn.uth.poo.biblioteca.servicio.ServicioLibros;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static ServicioAutores servicioAutores;
    private static ServicioLibros servicioLibros;

    public static void main(String[] args) {
        try {
            Conexion.crearTablas();
        } catch (DatosException e) {
            mostrarErrorDatos(e);
            return;
        }

        AutorDAO autorDAO = new AutorDAOImpl();
        LibroDAO libroDAO = new LibroDAOImpl();
        servicioAutores = new ServicioAutores(autorDAO, libroDAO);
        servicioLibros = new ServicioLibros(libroDAO, autorDAO);

        int opcion;
        do {
            System.out.println();
            System.out.println("=== BIBLIOTECA ===");
            System.out.println("1. Autores");
            System.out.println("2. Libros");
            System.out.println("0. Salir");
            opcion = leerEntero("Opcion: ");
            if (opcion == 1) {
                menuAutores();
            } else if (opcion == 2) {
                menuLibros();
            } else if (opcion != 0) {
                System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
        System.out.println("Fin del programa");
    }

    private static void menuAutores() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- AUTORES ---");
            System.out.println("1. Crear");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por id");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            opcion = leerEntero("Opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        crearAutor();
                        break;
                    case 2:
                        listarAutores();
                        break;
                    case 3:
                        buscarAutor();
                        break;
                    case 4:
                        actualizarAutor();
                        break;
                    case 5:
                        eliminarAutor();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (DatoInvalidoException e) {
                System.out.println("Dato invalido: " + e.getMessage());
            } catch (DatosException e) {
                mostrarErrorDatos(e);
            }
        } while (opcion != 0);
    }

    private static void menuLibros() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- LIBROS ---");
            System.out.println("1. Crear");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por id");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("6. Listar libros de un autor");
            System.out.println("0. Volver");
            opcion = leerEntero("Opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        crearLibro();
                        break;
                    case 2:
                        listarLibros();
                        break;
                    case 3:
                        buscarLibro();
                        break;
                    case 4:
                        actualizarLibro();
                        break;
                    case 5:
                        eliminarLibro();
                        break;
                    case 6:
                        listarLibrosDeAutor();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (DatoInvalidoException e) {
                System.out.println("Dato invalido: " + e.getMessage());
            } catch (DatosException e) {
                mostrarErrorDatos(e);
            }
        } while (opcion != 0);
    }

    private static void crearAutor() {
        String nombre = leerTexto("Nombre: ");
        String nacionalidad = leerTexto("Nacionalidad: ");
        Autor autor = new Autor(nombre, nacionalidad);
        servicioAutores.registrar(autor);
        System.out.println("Autor registrado con id " + autor.getId());
    }

    private static void listarAutores() {
        List<Autor> autores = servicioAutores.listar();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
        }
        for (Autor autor : autores) {
            System.out.println(autor);
        }
    }

    private static void buscarAutor() {
        int id = leerEntero("Id del autor: ");
        System.out.println(servicioAutores.buscarPorId(id));
    }

    private static void actualizarAutor() {
        int id = leerEntero("Id del autor: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String nacionalidad = leerTexto("Nueva nacionalidad: ");
        servicioAutores.actualizar(new Autor(id, nombre, nacionalidad));
        System.out.println("Autor actualizado");
    }

    private static void eliminarAutor() {
        int id = leerEntero("Id del autor: ");
        servicioAutores.eliminar(id);
        System.out.println("Autor eliminado");
    }

    private static void crearLibro() {
        String isbn = leerTexto("ISBN: ");
        String titulo = leerTexto("Titulo: ");
        int anio = leerEntero("Anio: ");
        double precio = leerDecimal("Precio: ");
        int autorId = leerEntero("Id del autor: ");
        Libro libro = new Libro(isbn, titulo, anio, precio, autorId);
        servicioLibros.registrar(libro);
        System.out.println("Libro registrado con id " + libro.getId());
    }

    private static void listarLibros() {
        mostrarLibros(servicioLibros.listar());
    }

    private static void buscarLibro() {
        int id = leerEntero("Id del libro: ");
        System.out.println(servicioLibros.buscarPorId(id));
    }

    private static void actualizarLibro() {
        int id = leerEntero("Id del libro: ");
        String isbn = leerTexto("Nuevo ISBN: ");
        String titulo = leerTexto("Nuevo titulo: ");
        int anio = leerEntero("Nuevo anio: ");
        double precio = leerDecimal("Nuevo precio: ");
        int autorId = leerEntero("Id del autor: ");
        servicioLibros.actualizar(new Libro(id, isbn, titulo, anio, precio, autorId));
        System.out.println("Libro actualizado");
    }

    private static void eliminarLibro() {
        int id = leerEntero("Id del libro: ");
        servicioLibros.eliminar(id);
        System.out.println("Libro eliminado");
    }

    private static void listarLibrosDeAutor() {
        int autorId = leerEntero("Id del autor: ");
        mostrarLibros(servicioLibros.listarPorAutor(autorId));
    }

    private static void mostrarLibros(List<Libro> libros) {
        if (libros.isEmpty()) {
            System.out.println("No hay libros para mostrar");
        }
        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    private static void mostrarErrorDatos(DatosException e) {
        System.out.println("Error de base de datos: " + e.getMessage());
        if (e.getCause() != null) {
            System.out.println("Detalle: " + e.getCause().getMessage());
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero entero");
            }
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero valido");
            }
        }
    }
}
