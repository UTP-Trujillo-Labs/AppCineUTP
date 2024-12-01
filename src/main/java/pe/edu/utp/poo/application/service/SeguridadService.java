package pe.edu.utp.poo.application.service;

import pe.edu.utp.poo.application.model.Seguridad;
import pe.edu.utp.poo.application.repository.SeguridadRepository;

public class SeguridadService {
    private final SeguridadRepository seguridadRepository;
    public SeguridadService() {
        seguridadRepository = new SeguridadRepository();
    }
    public Seguridad authenticate(String username, String password) {
        return seguridadRepository.auth(username, password);
    }
}
