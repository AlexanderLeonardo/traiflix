package server;

import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;
import server.AppController;
import ui.unq.edu.ar.TraiFlix.Capitulo;

@Accessors
@SuppressWarnings("all")
public class SimpleCapitulo {
  private int id;
  
  private String type;
  
  private int nroTemporada;
  
  private int nroCapitulo;
  
  private String fechaDeEstreno;
  
  private int duracion;
  
  private String directores;
  
  private String actoresPrincipales;
  
  private String linkYT;
  
  private int rating;
  
  private String titulo;
  
  public SimpleCapitulo() {
  }
  
  public SimpleCapitulo(final Capitulo capitulo) {
    this.id = capitulo.getCodigo();
    this.type = "Capitulo";
    this.nroTemporada = capitulo.getNroTemporada();
    this.nroCapitulo = capitulo.getNroCapitulo();
    this.fechaDeEstreno = AppController.formatearFechaEstrenoParaMostrar(capitulo.getFechaEstreno());
    this.duracion = capitulo.getDuracion();
    this.directores = capitulo.getDirectores();
    this.actoresPrincipales = capitulo.getActoresPrincipales();
    this.linkYT = capitulo.getLinkYT();
    this.rating = capitulo.getRating();
    this.titulo = capitulo.getTitulo();
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
  public int getNroTemporada() {
    return this.nroTemporada;
  }
  
  public void setNroTemporada(final int nroTemporada) {
    this.nroTemporada = nroTemporada;
  }
  
  @Pure
  public int getNroCapitulo() {
    return this.nroCapitulo;
  }
  
  public void setNroCapitulo(final int nroCapitulo) {
    this.nroCapitulo = nroCapitulo;
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
  public String getActoresPrincipales() {
    return this.actoresPrincipales;
  }
  
  public void setActoresPrincipales(final String actoresPrincipales) {
    this.actoresPrincipales = actoresPrincipales;
  }
  
  @Pure
  public String getLinkYT() {
    return this.linkYT;
  }
  
  public void setLinkYT(final String linkYT) {
    this.linkYT = linkYT;
  }
  
  @Pure
  public int getRating() {
    return this.rating;
  }
  
  public void setRating(final int rating) {
    this.rating = rating;
  }
  
  @Pure
  public String getTitulo() {
    return this.titulo;
  }
  
  public void setTitulo(final String titulo) {
    this.titulo = titulo;
  }
}
