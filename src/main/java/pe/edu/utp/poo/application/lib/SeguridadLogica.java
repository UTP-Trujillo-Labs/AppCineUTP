package pe.edu.utp.poo.application.lib;

import pe.edu.utp.poo.application.db.Database;
import pe.edu.utp.poo.application.pojo.Usuario;

public class SeguridadLogica {
    private final Database db;
    public SeguridadLogica(Database db) {
        this.db = db;
    }

    public Usuario autenticar(String usuario, String clave) {
        return this.db.listaUsuarios().stream()
                .filter(e -> usuario.equalsIgnoreCase(e.getUsuarioAcceso()))
                .filter(e -> clave.equalsIgnoreCase(e.getClaveAcceso()))
                .findFirst().orElse(null);
    }
}
