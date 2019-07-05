package server;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;
import server.SimpleCapitulo;
import server.SimpleContenido;
import server.SimplePeliculaRecomendada;
import server.SimpleSerieRecomendada;
import ui.unq.edu.ar.TraiFlix.Capitulo;
import ui.unq.edu.ar.TraiFlix.Pelicula;
import ui.unq.edu.ar.TraiFlix.Serie;
import ui.unq.edu.ar.TraiFlix.Usuario;

@Accessors
@SuppressWarnings("all")
public class SimpleUsuarioSerie {
  private int id;
  
  private String titulo;
  
  private String categoria;
  
  private String clasificacion;
  
  private String creadores;
  
  private List<SimpleContenido> contenidoRelacionado;
  
  private List<SimpleCapitulo> capitulos;
  
  private int recomendaciones;
  
  private String nombreUsuario;
  
  private String nombre;
  
  private String link;
  
  private int rating;
  
  public ArrayList<SimpleCapitulo> changeCapitulosASimpleCapitulos(final List<Capitulo> capitulos) {
    ArrayList<SimpleCapitulo> chapters = new ArrayList<SimpleCapitulo>();
    for (final Capitulo c : capitulos) {
      SimpleCapitulo _simpleCapitulo = new SimpleCapitulo(c);
      chapters.add(_simpleCapitulo);
    }
    return chapters;
  }
  
  public SimpleUsuarioSerie() {
  }
  
  public SimpleUsuarioSerie(final Serie serie, final Usuario usuario) {
    this.id = serie.getCodigo();
    this.titulo = serie.getTitulo();
    this.categoria = serie.getCategoria();
    this.clasificacion = serie.getClasificacion();
    this.contenidoRelacionado = this.contenidoRelacionadoSimple(serie);
    this.capitulos = this.changeCapitulosASimpleCapitulos(serie.getCapitulos());
    this.recomendaciones = usuario.getContenidoSugeridoPorAmigos().getRecomendaciones().size();
    this.creadores = serie.getCreadores();
    this.link = serie.getCapitulos().get(0).getLinkYT();
    this.rating = serie.getRating();
    this.nombreUsuario = usuario.getNombreUsuario();
    this.nombre = usuario.getNombre();
  }
  
  public ArrayList<SimpleContenido> contenidoRelacionadoSimple(final Serie serie) {
    ArrayList<SimpleContenido> listaContenidoRelacionado = new ArrayList<SimpleContenido>();
    List<Pelicula> _contenidoRelacionadoPelicula = serie.getContenidoRelacionadoPelicula();
    for (final Pelicula contenidoPelicula : _contenidoRelacionadoPelicula) {
      {
        SimplePeliculaRecomendada contenidoActual = new SimplePeliculaRecomendada(contenidoPelicula);
        listaContenidoRelacionado.add(contenidoActual);
      }
    }
    List<Serie> _contenidoRelacionadoSerie = serie.getContenidoRelacionadoSerie();
    for (final Serie contenidoSerie : _contenidoRelacionadoSerie) {
      {
        SimpleSerieRecomendada contenidoActual = new SimpleSerieRecomendada(contenidoSerie);
        listaContenidoRelacionado.add(contenidoActual);
      }
    }
    return listaContenidoRelacionado;
  }
  
  @Pure
  public int getId() {
    return this.id;
  }
  
  public void setId(final int id) {
    this.id = id;
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
  public int getRecomendaciones() {
    return this.recomendaciones;
  }
  
  public void setRecomendaciones(final int recomendaciones) {
    this.recomendaciones = recomendaciones;
  }
  
  @Pure
  public String getNombreUsuario() {
    return this.nombreUsuario;
  }
  
  public void setNombreUsuario(final String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }
  
  @Pure
  public String getNombre() {
    return this.nombre;
  }
  
  public void setNombre(final String nombre) {
    this.nombre = nombre;
  }
  
  @Pure
  public String getLink() {
    return this.link;
  }
  
  public void setLink(final String link) {
    this.link = link;
  }
  
  @Pure
  public int getRating() {
    return this.rating;
  }
  
  public void setRating(final int rating) {
    this.rating = rating;
  }
}
