package org.iesbelen.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iesbelen.model.Usuario;
import org.iesbelen.util.PasswordUtils;

public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO {

    @Override
    public synchronized void create(Usuario usuario) {
        try (Connection connection = connectDB()) {
            ensureUsuarioTable(connection);
            String sql = "INSERT INTO usuario (usuario, password, rol) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, usuario.getUsuario());
                statement.setString(2, PasswordUtils.hashPassword(usuario.getPassword()));
                statement.setString(3, usuario.getRol());
                statement.executeUpdate();
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        usuario.setIdUsuario(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException exception) {
            throw new IllegalStateException("No se pudo crear el usuario", exception);
        }
    }

    @Override
    public List<Usuario> getAll() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection connection = connectDB()) {
            ensureUsuarioTable(connection);
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT idUsuario, usuario, rol FROM usuario ORDER BY idUsuario");
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    usuarios.add(readUser(resultSet));
                }
            }
        } catch (SQLException | ClassNotFoundException exception) {
            throw new IllegalStateException("No se pudieron consultar los usuarios", exception);
        }
        return usuarios;
    }

    @Override
    public Optional<Usuario> find(int id) {
        try (Connection connection = connectDB()) {
            ensureUsuarioTable(connection);
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT idUsuario, usuario, rol FROM usuario WHERE idUsuario = ?")) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next() ? Optional.of(readUser(resultSet)) : Optional.empty();
                }
            }
        } catch (SQLException | ClassNotFoundException exception) {
            throw new IllegalStateException("No se pudo consultar el usuario", exception);
        }
    }

    @Override
    public Optional<Usuario> authenticate(String username, String password) {
        try (Connection connection = connectDB()) {
            ensureUsuarioTable(connection);
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT idUsuario, usuario, password, rol FROM usuario WHERE usuario = ?")) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next() || !PasswordUtils.verifyPassword(
                            password, resultSet.getString("password"))) {
                        return Optional.empty();
                    }
                    return Optional.of(readUser(resultSet));
                }
            }
        } catch (SQLException | ClassNotFoundException exception) {
            throw new IllegalStateException("No se pudo autenticar el usuario", exception);
        }
    }

    @Override
    public void update(Usuario usuario) {
        try (Connection connection = connectDB()) {
            ensureUsuarioTable(connection);
            String password = usuario.getPassword();
            if (password == null || password.isBlank()) {
                try (PreparedStatement statement = connection.prepareStatement(
                        "UPDATE usuario SET usuario = ?, rol = ? WHERE idUsuario = ?")) {
                    statement.setString(1, usuario.getUsuario());
                    statement.setString(2, usuario.getRol());
                    statement.setInt(3, usuario.getIdUsuario());
                    statement.executeUpdate();
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement(
                        "UPDATE usuario SET usuario = ?, password = ?, rol = ? WHERE idUsuario = ?")) {
                    statement.setString(1, usuario.getUsuario());
                    statement.setString(2, PasswordUtils.hashPassword(password));
                    statement.setString(3, usuario.getRol());
                    statement.setInt(4, usuario.getIdUsuario());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException | ClassNotFoundException exception) {
            throw new IllegalStateException("No se pudo actualizar el usuario", exception);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectDB();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM usuario WHERE idUsuario = ?")) {
            ensureUsuarioTable(connection);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException exception) {
            throw new IllegalStateException("No se pudo borrar el usuario", exception);
        }
    }

    private Usuario readUser(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultSet.getInt("idUsuario"));
        usuario.setUsuario(resultSet.getString("usuario"));
        usuario.setRol(resultSet.getString("rol"));
        return usuario;
    }

    private void ensureUsuarioTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS usuario (
                        idUsuario INT NOT NULL AUTO_INCREMENT,
                        usuario VARCHAR(45) NOT NULL,
                        password VARCHAR(128) NOT NULL,
                        rol VARCHAR(24) NOT NULL,
                        PRIMARY KEY (idUsuario),
                        CONSTRAINT chk_usuario_rol
                            CHECK (rol IN ('cliente', 'vendedor', 'administrador'))
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
                    """);
        }
    }

}
