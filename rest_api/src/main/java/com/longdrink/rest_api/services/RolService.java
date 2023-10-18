package com.longdrink.rest_api.services;

import com.longdrink.rest_api.dao.IRolDAO;
import com.longdrink.rest_api.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {
    @Autowired
    private IRolDAO rolDAO;

    public List<Rol> listarRoles(){ return (List<Rol>) rolDAO.findAll(); }

    public Rol guardar(Rol r){
        r.setCodRol(0L);
        return rolDAO.save(r);
    }

    public Rol actualizar(Rol r){
        return rolDAO.save(r);
    }

}
