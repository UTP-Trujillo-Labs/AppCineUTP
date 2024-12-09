package pe.edu.utp.poo.application.service;

import pe.edu.utp.poo.application.model.Seguridad;
import pe.edu.utp.poo.application.repository.SeguridadRepository;

import java.sql.SQLException;
import pe.edu.utp.poo.application.model.Usuario;

public class SeguridadService {
    private Usuario usuarioSession;
    private static SeguridadService seguridadSerSeguridad;
    private final SeguridadRepository seguridadRepository;

    public static SeguridadService instancia() {
        if (seguridadSerSeguridad == null) {
            seguridadSerSeguridad = new SeguridadService();
        }
        return seguridadSerSeguridad;
    }

    public SeguridadService() {
        seguridadRepository = new SeguridadRepository();
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public Long insert(Seguridad seguridad) throws SQLException {
        return seguridadRepository.insert(seguridad);
    }

    public Seguridad authenticate(String username, String password) throws SQLException {
        Seguridad seguridad = seguridadRepository.auth(username, password);
        usuarioSession = seguridad.getUsuario();
        return seguridad;
    }
}
