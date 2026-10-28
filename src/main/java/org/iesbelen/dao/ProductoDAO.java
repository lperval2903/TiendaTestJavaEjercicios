package org.iesbelen.dao;

import java.util.List;
import java.util.Optional;

import org.iesbelen.model.Producto;

public interface ProductoDAO {

        public void create(Producto fabricante);
        public List<Producto> getAll();
        public Optional<Producto> find(int id);
        public void update(Producto fabricante);
        public void delete(int id);

        // Ampliación CRUD obligatoria:
        public Optional<Integer> getCountProductos(int id);

}