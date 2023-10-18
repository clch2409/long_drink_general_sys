package com.longdrink.rest_api.dao;

import com.longdrink.rest_api.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IUsuarioDAO extends CrudRepository<Usuario,Long> {
    @Query("SELECT U FROM Usuario U WHERE U.activo = true")
    List<Usuario> findAllByActivo();

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    Optional<Usuario> findByEmail(String email);
}
