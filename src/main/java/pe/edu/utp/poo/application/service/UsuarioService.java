package pe.edu.utp.poo.application.service;

import pe.edu.utp.poo.application.model.Usuario;
import pe.edu.utp.poo.application.repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }
    public List<Usuario> findAll() throws SQLException {
        return this.usuarioRepository.list();
    }
}
