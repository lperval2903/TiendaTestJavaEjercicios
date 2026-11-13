package org.iesbelen.dao;

import java.util.List;
import java.util.Optional;

import org.iesbelen.model.Usuario;

public interface UsuarioDAO {

    void create(Usuario usuario);
    List<Usuario> getAll();
    Optional<Usuario> find(int id);
    Optional<Usuario> authenticate(String username, String password);
    void update(Usuario usuario);
    void delete(int id);
}
