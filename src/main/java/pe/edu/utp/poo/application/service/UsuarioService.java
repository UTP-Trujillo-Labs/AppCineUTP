package pe.edu.utp.poo.application.service;

import pe.edu.utp.poo.application.model.Seguridad;
import pe.edu.utp.poo.application.model.Usuario;
import pe.edu.utp.poo.application.repository.SeguridadRepository;
import pe.edu.utp.poo.application.repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final SeguridadRepository seguridadRepository;
    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
        this.seguridadRepository = new SeguridadRepository();
    }
    public List<Usuario> findAll() throws SQLException {
        return this.usuarioRepository.list();
    }
    public Usuario findById(Long id) throws SQLException {
        return this.usuarioRepository.findById(id);
    }
    public boolean deleteById(Long id) throws SQLException {
        Usuario usuario = this.usuarioRepository.findById(id);
        if (usuario != null) {
            Seguridad seguridad = this.seguridadRepository.findByUsuarioId(id);
            if (seguridad != null) {
                this.seguridadRepository.delete(seguridad.getSeguridadId());
            }
            return this.usuarioRepository.delete(id);
        }
        return false;
    }
    public Usuario save(Usuario usuario) throws SQLException {
        if (usuario.getUsuarioId() == null) {
            Long id = this.usuarioRepository.insert(usuario);

            if (id != null) {
                usuario.setUsuarioId(id);
                String usuarioLogin = usuario.getNombres().toLowerCase().charAt(0) + usuario.getApellidos().toLowerCase().split(" ")[0];

                Seguridad seguridad = new Seguridad();
                seguridad.setUsuarioLogin(usuarioLogin);
                seguridad.setActivo((short) 1);
                seguridad.setClave(usuario.getNumeroDocumento());
                seguridad.setUsuario(usuario);
                Long idSeguridad = this.seguridadRepository.insert(seguridad);

                usuario.setUsuarioId(id);

                return usuario;
            }
        } else {
            boolean result = this.usuarioRepository.update(usuario);
            if (result) {
                return usuario;
            }
        }
        return null;
    }
}
