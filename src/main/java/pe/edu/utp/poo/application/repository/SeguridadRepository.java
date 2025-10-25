package pe.edu.utp.poo.application.repository;

import pe.edu.utp.poo.application.db.Conexion;
import pe.edu.utp.poo.application.model.Seguridad;
import pe.edu.utp.poo.application.model.Usuario;

import java.sql.*;
import java.util.List;

import static pe.edu.utp.poo.application.common.Constant.FRASE_SECRETA;

public class SeguridadRepository implements Repository<Seguridad> {

    public Integer insert(Seguridad usuario) throws SQLException {
        String query = """
                insert into Seguridad (usuario_id, usuario, clave, activo)
                values (?, ?, EncryptByPassPhrase(?, ?, 1, CONVERT(varbinary, convert(nvarchar, ?))), 1)
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            ps.setInt(1, usuario.getUsuario().getUsuarioId());
            ps.setString(2, usuario.getUsuarioLogin());
            ps.setString(3, FRASE_SECRETA);
            ps.setString(4, usuario.getClave());
            ps.setLong(5, usuario.getUsuario().getUsuarioId());

            Integer result = ps.executeUpdate();
            if (result == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    @Deprecated
    public boolean update(Seguridad seguridad) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Deprecated
    public List<Seguridad> list() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Deprecated
    public Seguridad findById(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Seguridad findByUsuarioId(Integer id) throws SQLException {
        Seguridad seguridad = null;
        String query = """
                select seguridad_id, usuario, activo
                from Seguridad
                where usuario_id = ?
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                seguridad = new Seguridad();
                seguridad.setSeguridadId(rs.getInt("seguridad_id"));
                seguridad.setUsuarioLogin(rs.getString("usuario"));
                seguridad.setActivo(rs.getShort("activo"));
            }
        }
        return seguridad;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String query = """
                delete from Seguridad
                where seguridad_id = ?
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setInt(1, id);

            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        }
        return false;
    }

    public Seguridad auth(String usuarioLogin, String clave) throws SQLException {
        Seguridad seguridad = null;
        String query = """
                SELECT t1.usuario_id, t2.nombres, t2.apellidos FROM Seguridad t1
                INNER JOIN Usuario t2 on t1.usuario_id = t2.usuario_id
                WHERE t1.usuario = ?
                AND CONVERT(varchar, DecryptByPassphrase(?, t1.clave, 1 , CONVERT(varbinary, convert(nvarchar, t1.usuario_id)))) = ?
                and t1.activo = 1
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, usuarioLogin);
            ps.setString(2, FRASE_SECRETA);
            ps.setString(3, clave);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuarioId(rs.getInt("usuario_id"));
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
