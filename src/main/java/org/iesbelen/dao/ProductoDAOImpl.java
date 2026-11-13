package org.iesbelen.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iesbelen.model.Producto;

public class ProductoDAOImpl extends AbstractDAOImpl implements ProductoDAO{

    /**
     * Inserta en base de datos el nuevo producto, actualizando el id en el bean producto.
     */
    @Override
    public synchronized void create(Producto producto) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectDB();
            ensureProductoTable(conn);


            //1 alternativas comentadas:
            //ps = conn.prepareStatement("INSERT INTO fabricantes (nombre) VALUES (?)", new String[] {"codigo"});
            //Ver también, AbstractDAOImpl.executeInsert ...
            //Columna fabricante.codigo es clave primaria auto_increment, por ese motivo se omite de la sentencia SQL INSERT siguiente.
            String sql;
            if (producto.getCodigoFabricante() > 0) {
                sql = "INSERT INTO producto (nombre, codigo_fabricante) VALUES (?, ?)";
                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, producto.getNombre());
                ps.setInt(2, producto.getCodigoFabricante());
            } else {
                sql = "INSERT INTO producto (nombre) VALUES (?)";
                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, producto.getNombre());
            }

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de producto con 0 filas insertadas.");

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next())
                    producto.setCodigo(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, null);
        }

    }

    /**
     * Devuelve lista con todos loa productos.
     */
    @Override
    public List<Producto> getAll() {

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Producto> listProd = new ArrayList<>();

        try {
            conn = connectDB();
            ensureProductoTable(conn);

            // Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
            s = conn.createStatement();

            rs = s.executeQuery("SELECT codigo, nombre FROM producto");
            while (rs.next()) {
                Producto prod = new Producto();
                int idx = 1;
                prod.setCodigo(rs.getInt(idx++));
                prod.setNombre(rs.getString(idx++));
                listProd.add(prod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listProd;

    }

    private void ensureProductoTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS producto (
                        codigo INT NOT NULL AUTO_INCREMENT,
                        nombre VARCHAR(255) NOT NULL,
                        codigo_fabricante INT NULL,
                        PRIMARY KEY (codigo)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
                    """);
        }
    }

    /**
     * Devuelve Optional de producto con el ID dado.
     */
    @Override
    public Optional<Producto> find(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement(
                    "SELECT codigo, nombre, codigo_fabricante FROM producto WHERE codigo = ?");

            int idx =  1;
            ps.setInt(idx, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Producto prod = new Producto();
                idx = 1;
                prod.setCodigo(rs.getInt(idx++));
                prod.setNombre(rs.getString(idx++));
                prod.setCodigoFabricante(rs.getInt(idx));

                return Optional.of(prod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return Optional.empty();

    }
    /**
     * Actualiza producto con campos del bean producto según ID del mismo.
     */
    @Override
    public void update(Producto producto) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("UPDATE producto SET nombre = ?  WHERE codigo = ?");
            int idx = 1;
            ps.setString(idx++, producto.getNombre());
            ps.setInt(idx, producto.getCodigo());

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de producto con 0 registros actualizados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

    }

    /**
     * Borra producto con ID proporcionado.
     */
    @Override
    public void delete(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("DELETE FROM producto WHERE codigo = ?");
            int idx = 1;
            ps.setInt(idx, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de producto con 0 registros eliminados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

    }

    /**
     * Devuelve el número de productos con el ID de fabricante proporcionado.
     */
    @Override
    public Optional<Integer> getCountProductos(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT COUNT(*) FROM producto WHERE codigo_fabricante = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return Optional.empty();
    }
}
