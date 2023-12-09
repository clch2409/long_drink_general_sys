package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.IUsuarioDAO;
import com.longdrink.rest_api.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioDAO usuarioDAO;

    public List<Usuario> listarUsuarios(){ return (List<Usuario>) usuarioDAO.findAll(); }

    public List<Usuario> listarActivos(){ return usuarioDAO.findAllByActivo(); }

    public Usuario getPorUsername(String nombreUsuario){
        try{
            Usuario u = usuarioDAO.findByNombreUsuario(nombreUsuario).get();
            return u;
        }
        catch(Exception ex){ return null; }
    }

    public Usuario getPorEmail(String email){
        return usuarioDAO.findByEmail(email).orElse(null);
    }

    public Usuario getPorCod(Long cod){
        try{
            Usuario u = usuarioDAO.findById(cod).get();
            return u;
        }
        catch(Exception ex){ return null; }
    }

    public Usuario guardar(Usuario u){
        u.setCodUsuario(0L);
        return usuarioDAO.save(u);
    }

    public Usuario actualizar(Usuario u){
        return usuarioDAO.save(u);
    }

    public boolean eliminar(Long cod){
        try{
            Usuario usuario = usuarioDAO.findById(cod).get();
            if(usuario.isActivo()){
                usuario.setActivo(false);
                usuarioDAO.save(usuario);
            }
        }
        catch(Exception ex){ return false; }
        return false;
    }
}
