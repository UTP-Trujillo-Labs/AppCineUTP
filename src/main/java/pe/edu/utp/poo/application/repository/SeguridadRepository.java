package pe.edu.utp.poo.application.repository;

import pe.edu.utp.poo.application.db.Conexion;
import pe.edu.utp.poo.application.model.Seguridad;
import pe.edu.utp.poo.application.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeguridadRepository {

    public Seguridad auth(String usuarioLogin, String clave) throws SQLException {
        Seguridad seguridad = null;
        String query = """
                SELECT t1.usuario_id, t2.nombres, t2.apellidos FROM Seguridad t1
                INNER JOIN Usuario t2 on t1.usuario_id = t2.usuario_id
                WHERE t1.usuario = ?
                AND CONVERT(varchar, DecryptByPassphrase('ESTA ESCRIPTACIÓN ES RIESGOSO USARLO EN PRODUCCIÓN!', t1.clave, 1 , CONVERT(varbinary, t1.usuario_id))) = ?
                and t1.activo = 1
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, usuarioLogin);
            ps.setString(2, clave);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuarioId(rs.getLong("usuario_id"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));

                seguridad = new Seguridad();
                seguridad.setUsuarioLogin(usuarioLogin);
                seguridad.setUsuario(usuario);
            }
        }
        return seguridad;
    }
}
