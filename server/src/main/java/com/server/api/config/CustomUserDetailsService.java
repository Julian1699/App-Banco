package com.server.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.server.api.model.Usuario;
import com.server.api.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Correo " + correo + " no encontrado."));

        // Devuelve un UserDetails sin roles
        return User.builder()
                .username(usuario.getCorreo())  // Este método aún se llama "username" por ser parte de la clase User.
                .password(usuario.getPassword())
                .authorities("")  // No asignar ninguna autoridad, ya que estamos eliminando roles
                .build();
    }
}
