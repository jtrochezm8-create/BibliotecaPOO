package hn.uth.poo.biblioteca.servicio;

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Autor;
import java.util.List;

public class ServicioAutores {

    private final AutorDAO autorDAO;
    private final LibroDAO libroDAO;

    public ServicioAutores(AutorDAO autorDAO, LibroDAO libroDAO) {
        this.autorDAO = autorDAO;
        this.libroDAO = libroDAO;
    }

    public void registrar(Autor autor) {
        validar(autor);
        autorDAO.insertar(autor);
    }

    public List<Autor> listar() {
        return autorDAO.listar();
    }

    public Autor buscarPorId(int id) {
        Autor autor = autorDAO.buscarPorId(id);
        if (autor == null) {
            throw new DatoInvalidoException("No existe un autor con id " + id);
        }
        return autor;
    }

    public void actualizar(Autor autor) {
        buscarPorId(autor.getId());
        validar(autor);
        autorDAO.actualizar(autor);
    }

    public void eliminar(int id) {
        buscarPorId(id);
        if (!libroDAO.listarPorAutor(id).isEmpty()) {
            throw new DatoInvalidoException("No se puede eliminar el autor porque tiene libros registrados");
        }
        autorDAO.eliminar(id);
    }

    private void validar(Autor autor) {
        if (autor.getNombre() == null || autor.getNombre().trim().isEmpty()) {
            throw new DatoInvalidoException("El nombre del autor es obligatorio");
        }
    }
}
