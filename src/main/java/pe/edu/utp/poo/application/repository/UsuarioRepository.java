package pe.edu.utp.poo.application.repository;

import pe.edu.utp.poo.application.db.Conexion;
import pe.edu.utp.poo.application.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements Repository<Usuario> {
    @Override
    public Integer insert(Usuario usuario) throws SQLException {
        String query = """
                insert into Usuario (nombres, apellidos, numero_documento, rol, edad, estado)
                values (?, ?, ?, ?, ?, 1)
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getNumeroDocumento());
            ps.setString(4, usuario.getRol());
            ps.setInt(5, usuario.getEdad());

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
    public boolean update(Usuario usuario) throws SQLException {
        String query = """
                update Usuario set
                nombres = ?,
                apellidos = ?,
                numero_documento = ?,
                rol = ?,
                edad = ?
                where usuario_id = ?
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getNumeroDocumento());
            ps.setString(4, usuario.getRol());
            ps.setInt(5, usuario.getEdad());
            ps.setLong(6, usuario.getUsuarioId());

            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Usuario> list() throws SQLException{
        List<Usuario> list = new ArrayList<>();
        String query = """
                select usuario_id, nombres, apellidos, numero_documento, rol, edad, estado
                from Usuario
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuarioId(rs.getInt("usuario_id"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setNumeroDocumento(rs.getString("numero_documento"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEdad(rs.getShort("edad"));
                usuario.setEstado(rs.getShort("estado"));

                list.add(usuario);
            }
        }
        return list;
    }

    @Override
    public Usuario findById(Integer id) throws SQLException {
        Usuario usuario = null;
        String query = """
                select usuario_id, nombres, apellidos, numero_documento, rol, edad
                from Usuario
                where usuario_id = ?
                """;
        try (
                Connection conn = Conexion.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setUsuarioId(rs.getInt("usuario_id"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setNumeroDocumento(rs.getString("numero_documento"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEdad(rs.getShort("edad"));
            }
        }
        return usuario;
    }

    public boolean inhabilitar(Integer id) throws SQLException {
        String query = """
                update Usuario set
                estado = 0
                where usuario_id = ?
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

    @Override
    public boolean delete(Integer id) throws SQLException {
        String query = """
                delete from Usuario
                where usuario_id = ?
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
}
