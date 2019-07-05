package server;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;
import server.AppController;
import server.SimpleCapitulo;
import server.SimpleContenido;
import ui.unq.edu.ar.TraiFlix.Material;
import ui.unq.edu.ar.TraiFlix.Serie;

@Accessors
@SuppressWarnings("all")
public class SimpleSerie extends Material {
  private int id;
  
  private String type;
  
  private String titulo;
  
  private String categoria;
  
  private String clasificacion;
  
  private String creadores;
  
  private int temporadas;
  
  private int rating;
  
  private List<SimpleContenido> contenidoRelacionado;
  
  private List<SimpleCapitulo> capitulos;
  
  private String portada;
  
  public SimpleSerie() {
  }
  
  public SimpleSerie(final Serie serie) {
    this.id = serie.getCodigo();
    this.type = "serie";
    this.titulo = serie.getTitulo();
    this.categoria = serie.getCategoria();
    this.clasificacion = serie.getClasificacion();
    this.creadores = serie.getCreadores();
    this.temporadas = serie.getTemporadas().size();
    this.rating = serie.getRating();
    this.contenidoRelacionado = AppController.contenidoRelacionaSimple(serie);
    ArrayList<SimpleCapitulo> _arrayList = new ArrayList<SimpleCapitulo>();
    this.capitulos = _arrayList;
    this.portada = serie.getLinkPortada();
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
  public String getCategoria() {
    return this.categoria;
  }
  
  public void setCategoria(final String categoria) {
    this.categoria = categoria;
  }
  
  @Pure
  public String getClasificacion() {
    return this.clasificacion;
  }
  
  public void setClasificacion(final String clasificacion) {
    this.clasificacion = clasificacion;
  }
  
  @Pure
  public String getCreadores() {
    return this.creadores;
  }
  
  public void setCreadores(final String creadores) {
    this.creadores = creadores;
  }
  
  @Pure
  public int getTemporadas() {
    return this.temporadas;
  }
  
  public void setTemporadas(final int temporadas) {
    this.temporadas = temporadas;
  }
  
  @Pure
  public int getRating() {
    return this.rating;
  }
  
  public void setRating(final int rating) {
    this.rating = rating;
  }
  
  @Pure
  public List<SimpleContenido> getContenidoRelacionado() {
    return this.contenidoRelacionado;
  }
  
  public void setContenidoRelacionado(final List<SimpleContenido> contenidoRelacionado) {
    this.contenidoRelacionado = contenidoRelacionado;
  }
  
  @Pure
  public List<SimpleCapitulo> getCapitulos() {
    return this.capitulos;
  }
  
  public void setCapitulos(final List<SimpleCapitulo> capitulos) {
    this.capitulos = capitulos;
  }
  
  @Pure
  public String getPortada() {
    return this.portada;
  }
  
  public void setPortada(final String portada) {
    this.portada = portada;
  }
}
