package hn.uth.poo.biblioteca.dao;

import hn.uth.poo.biblioteca.modelo.Autor;
import java.util.List;

public interface AutorDAO {

    void insertar(Autor autor);

    Autor buscarPorId(int id);

    List<Autor> listar();

    void actualizar(Autor autor);

    void eliminar(int id);
}
