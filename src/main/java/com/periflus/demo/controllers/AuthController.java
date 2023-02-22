package com.periflus.demo.controllers;

import com.periflus.demo.dao.UsuarioDao;
import com.periflus.demo.models.Usuario;
import com.periflus.demo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
@RestController
public class AuthController {
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private JWTUtil jwtUtil;
    @RequestMapping(value="api/login",method= RequestMethod.POST )
    public String login(@RequestBody Usuario usuario){
        Usuario usuarioLogueado = usuarioDao.obtenerUsuarioPorCreedenciales(usuario);
        if (usuarioLogueado != null) {
            String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuario.getEmail());
            return tokenJwt;
        }
        return "FAIL";

    }
}
