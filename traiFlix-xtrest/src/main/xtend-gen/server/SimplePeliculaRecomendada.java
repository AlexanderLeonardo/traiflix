package server;

import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;
import server.SimpleContenido;
import ui.unq.edu.ar.TraiFlix.Pelicula;

@Accessors
@SuppressWarnings("all")
public class SimplePeliculaRecomendada extends SimpleContenido {
  private int id;
  
  private String type;
  
  private String titulo;
  
  private String fechaEstreno;
  
  public SimplePeliculaRecomendada(final Pelicula pelicula) {
    this.id = pelicula.getCodigo();
    this.type = "movie";
    this.titulo = pelicula.getTitulo();
    this.fechaEstreno = pelicula.getFechaDeEstreno().toString();
  }
  
  @Pure
  public int getId() {
    return this.id;
  }
  
  public void setId(final int id) {
    this.id = id;
  }
  
  @Pure
  public String getType() {
    return this.type;
  }
  
  public void setType(final String type) {
    this.type = type;
  }
  
  @Pure
  public String getTitulo() {
    return this.titulo;
  }
  
  public void setTitulo(final String titulo) {
    this.titulo = titulo;
  }
  
  @Pure
  public String getFechaEstreno() {
    return this.fechaEstreno;
  }
  
  public void setFechaEstreno(final String fechaEstreno) {
    this.fechaEstreno = fechaEstreno;
  }
}
