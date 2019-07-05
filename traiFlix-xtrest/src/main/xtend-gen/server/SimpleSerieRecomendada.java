package server;

import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;
import server.SimpleContenido;
import ui.unq.edu.ar.TraiFlix.Serie;

@Accessors
@SuppressWarnings("all")
public class SimpleSerieRecomendada extends SimpleContenido {
  private int id;
  
  private String type;
  
  private String titulo;
  
  public SimpleSerieRecomendada() {
  }
  
  public SimpleSerieRecomendada(final Serie serie) {
    this.id = serie.getCodigo();
    this.type = "serie";
    this.titulo = serie.getTitulo();
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
}
