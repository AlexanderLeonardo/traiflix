package server;

import java.util.List;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;
import server.AppController;
import server.SimpleContenido;
import ui.unq.edu.ar.TraiFlix.Pelicula;

@Accessors
@SuppressWarnings("all")
public class SimplePelicula {
  private int id;
  
  private String type;
  
  private String titulo;
  
  private String categoria;
  
  private String clasificacion;
  
  private String fechaDeEstreno;
  
  private int duracion;
  
  private String directores;
  
  private String actores;
  
  private String link;
  
  private String portada;
  
  private int ranking;
  
  private List<SimpleContenido> contenidoRelacionado;
  
  public SimplePelicula() {
  }
  
  public SimplePelicula(final Pelicula pelicula) {
    this.id = pelicula.getCodigo();
    this.type = "movie";
    this.titulo = pelicula.getTitulo();
    this.categoria = pelicula.getCategoria();
    this.clasificacion = pelicula.getClasificacion();
    this.fechaDeEstreno = pelicula.getFechaDeEstreno().toString();
    this.duracion = pelicula.getDuracion();
    this.directores = pelicula.getDirectores();
    this.actores = pelicula.getActores();
    this.link = pelicula.getLinkYT();
    this.portada = pelicula.getLinkPortada();
    this.ranking = pelicula.getRating();
    this.contenidoRelacionado = AppController.contenidoRelacionaSimple(pelicula);
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
  public String getFechaDeEstreno() {
    return this.fechaDeEstreno;
  }
  
  public void setFechaDeEstreno(final String fechaDeEstreno) {
    this.fechaDeEstreno = fechaDeEstreno;
  }
  
  @Pure
  public int getDuracion() {
    return this.duracion;
  }
  
  public void setDuracion(final int duracion) {
    this.duracion = duracion;
  }
  
  @Pure
  public String getDirectores() {
    return this.directores;
  }
  
  public void setDirectores(final String directores) {
    this.directores = directores;
  }
  
  @Pure
  public String getActores() {
    return this.actores;
  }
  
  public void setActores(final String actores) {
    this.actores = actores;
  }
  
  @Pure
  public String getLink() {
    return this.link;
  }
  
  public void setLink(final String link) {
    this.link = link;
  }
  
  @Pure
  public String getPortada() {
    return this.portada;
  }
  
  public void setPortada(final String portada) {
    this.portada = portada;
  }
  
  @Pure
  public int getRanking() {
    return this.ranking;
  }
  
  public void setRanking(final int ranking) {
    this.ranking = ranking;
  }
  
  @Pure
  public List<SimpleContenido> getContenidoRelacionado() {
    return this.contenidoRelacionado;
  }
  
  public void setContenidoRelacionado(final List<SimpleContenido> contenidoRelacionado) {
    this.contenidoRelacionado = contenidoRelacionado;
  }
}
