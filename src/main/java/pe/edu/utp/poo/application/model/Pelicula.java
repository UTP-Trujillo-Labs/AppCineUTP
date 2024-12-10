package pe.edu.utp.poo.application.model;

public class Pelicula {
    private Integer peliculaId;
    private String titulo;
    private String autor;
    private short duracion;
    private String genero;
    private boolean aptoParaNinos;

    public Pelicula() {}

    public Pelicula(Integer pelicula_id, String titulo, String autor, short duracion, String genero, boolean aptoParaNinos) {
        this.peliculaId = pelicula_id;
        this.titulo = titulo;
        this.autor = autor;
        this.duracion = duracion;
        this.genero = genero;
        this.aptoParaNinos = aptoParaNinos;
    }

    public int getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(Integer pelicula_id) {
        this.peliculaId = pelicula_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public short getDuracion() {
        return duracion;
    }

    public void setDuracion(short duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isAptoParaNinos() {
        return aptoParaNinos;
    }

    public void setAptoParaNinos(boolean aptoParaNinos) {
        this.aptoParaNinos = aptoParaNinos;
    }
}
