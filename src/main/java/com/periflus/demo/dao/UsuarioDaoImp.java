package com.periflus.demo.dao;

import com.periflus.demo.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public class UsuarioDaoImp  implements UsuarioDao{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }
    @Override
    public Usuario obtenerUsuarioPorCreedenciales(Usuario usuario){
        String query = "FROM Usuario where email = :email  and password = :password";
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter( "email", usuario.getEmail())
                .getResultList();
        if (lista.isEmpty()){
            return null;
        }
        String passwordHashed = lista.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, usuario.getPassword())){
            return lista.get(0);
        }
        return null;
    }
}
