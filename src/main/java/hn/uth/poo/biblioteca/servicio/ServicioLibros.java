package hn.uth.poo.biblioteca.servicio;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Libro;
import java.time.Year;
import java.util.List;

public class ServicioLibros {

    private final LibroDAO libroDAO;
    private final AutorDAO autorDAO;

    public ServicioLibros(LibroDAO libroDAO, AutorDAO autorDAO) {
        this.libroDAO = libroDAO;
        this.autorDAO = autorDAO;
    }

    public void registrar(Libro libro) {
        validar(libro);
        libroDAO.insertar(libro);
    }

    public List<Libro> listar() {
        return libroDAO.listar();
    }

    public Libro buscarPorId(int id) {
        Libro libro = libroDAO.buscarPorId(id);
        if (libro == null) {
            throw new DatoInvalidoException("No existe un libro con id " + id);
        }
        return libro;
    }

    public List<Libro> listarPorAutor(int autorId) {
        if (autorDAO.buscarPorId(autorId) == null) {
            throw new DatoInvalidoException("No existe un autor con id " + autorId);
        }
        return libroDAO.listarPorAutor(autorId);
    }

    public void actualizar(Libro libro) {
        buscarPorId(libro.getId());
        validar(libro);
        libroDAO.actualizar(libro);
    }

    public void eliminar(int id) {
        buscarPorId(id);
        libroDAO.eliminar(id);
    }

    private void validar(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new DatoInvalidoException("El titulo es obligatorio");
        }
        if (libro.getIsbn() == null || libro.getIsbn().trim().isEmpty()) {
            throw new DatoInvalidoException("El ISBN es obligatorio");
        }
        int anioActual = Year.now().getValue();
        if (libro.getAnio() < 1450 || libro.getAnio() > anioActual) {
            throw new DatoInvalidoException("El anio debe estar entre 1450 y " + anioActual);
        }
        if (libro.getPrecio() < 0) {
            throw new DatoInvalidoException("El precio no puede ser negativo");
        }
        if (autorDAO.buscarPorId(libro.getAutorId()) == null) {
            throw new DatoInvalidoException("No existe un autor con id " + libro.getAutorId());
        }
        for (Libro otro : libroDAO.listar()) {
            if (otro.getIsbn().equalsIgnoreCase(libro.getIsbn().trim()) && otro.getId() != libro.getId()) {
                throw new DatoInvalidoException("Ya existe un libro con el ISBN " + libro.getIsbn());
            }
        }
    }
}
