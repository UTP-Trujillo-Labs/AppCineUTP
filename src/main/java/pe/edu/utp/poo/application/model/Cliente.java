package pe.edu.utp.poo.application.model;

public class Cliente extends Persona {
    private int clienteId;

    public Cliente() {}

    public Cliente(String nombres, String apellidos, String numeroDocumento) {
        super(nombres, apellidos, numeroDocumento);
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
}
