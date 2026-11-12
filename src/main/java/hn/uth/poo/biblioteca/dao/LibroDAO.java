package hn.uth.poo.biblioteca.dao;

import hn.uth.poo.biblioteca.modelo.Libro;
import java.util.List;

public interface LibroDAO {

    void insertar(Libro libro);

    Libro buscarPorId(int id);

    List<Libro> listar();

    void actualizar(Libro libro);

    void eliminar(int id);

    List<Libro> listarPorAutor(int autorId);
}
