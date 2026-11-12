package hn.uth.poo.biblioteca.dao;

import hn.uth.poo.biblioteca.excepciones.DatosException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public static Connection getConexion() {
        try {
            Connection con = DriverManager.getConnection(URL);
            try (Statement st = con.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON");
            }
            return con;
        } catch (SQLException e) {
            throw new DatosException("No se pudo abrir la conexion", e);
        }
    }

    public static void crearTablas() {
        String autores = "CREATE TABLE IF NOT EXISTS autores ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT NOT NULL, "
                + "nacionalidad TEXT)";
        String libros = "CREATE TABLE IF NOT EXISTS libros ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "isbn TEXT NOT NULL UNIQUE, "
                + "titulo TEXT NOT NULL, "
                + "anio INTEGER, "
                + "precio REAL NOT NULL, "
                + "autor_id INTEGER NOT NULL REFERENCES autores(id))";
        try (Connection con = getConexion();
             Statement st = con.createStatement()) {
            st.execute(autores);
            st.execute(libros);
        } catch (SQLException e) {
            throw new DatosException("No se pudieron crear las tablas", e);
        }
    }
}
