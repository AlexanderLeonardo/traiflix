package runnable;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.uqbar.commons.model.annotations.Observable;
import ui.unq.edu.ar.TraiFlix.Capitulo;
import ui.unq.edu.ar.TraiFlix.Pelicula;
import ui.unq.edu.ar.TraiFlix.Serie;
import ui.unq.edu.ar.TraiFlix.TraiFlix;
import ui.unq.edu.ar.TraiFlix.Usuario;

@Accessors
@Observable
@SuppressWarnings("all")
public class TraiFlixFactory {
  private TraiFlix traiFlix;
  
  public TraiFlixFactory() {
    this.traiFlix = this.nuevoTraiFlix();
    this.agregarContenidoFavoritoAUsuarios();
    this.agregarAmistadEntreUsuarios();
    this.agregarRecomendacionesAUsuarios();
    this.agregarContenidoVistoAUsuarios();
    this.relacionarContenidos();
    this.traiFlix.getUsuarios().get(0).valorarPelicula(this.traiFlix.getPeliculas().get(0), Integer.valueOf(3));
    this.traiFlix.getUsuarios().get(0).valorarCapitulo(this.traiFlix.getSeries().get(0).getCapitulosTemporada(1).get(0), Integer.valueOf(4));
  }
  
  public boolean relacionarContenidos() {
    boolean _xblockexpression = false;
    {
      this.traiFlix.getSeries().get(0).getContenidoRelacionadoPelicula().add(this.traiFlix.getPeliculas().get(0));
      _xblockexpression = this.traiFlix.getPeliculas().get(0).getContenidoRelacionadoSerie().add(this.traiFlix.getSeries().get(0));
    }
    return _xblockexpression;
  }
  
  public TraiFlix nuevoTraiFlix() {
    TraiFlix traiFlix = new TraiFlix();
    this.agregarUsuarios(traiFlix);
    this.agregarPeliculas(traiFlix);
    this.agregarSeries(traiFlix);
    return traiFlix;
  }
  
  public boolean agregarAmistadEntreUsuarios() {
    boolean _xblockexpression = false;
    {
      this.traiFlix.getUsuarios().get(0).agregarAmigo(this.traiFlix.getUsuarios().get(1));
      _xblockexpression = this.traiFlix.getUsuarios().get(1).agregarAmigo(this.traiFlix.getUsuarios().get(0));
    }
    return _xblockexpression;
  }
  
  public boolean agregarContenidoFavoritoAUsuarios() {
    boolean _xblockexpression = false;
    {
      this.traiFlix.getUsuarios().get(0).getContenidoFavorito().add(this.traiFlix.getSeries().get(0));
      this.traiFlix.getUsuarios().get(0).getContenidoFavorito().add(this.traiFlix.getPeliculas().get(0));
      this.traiFlix.getUsuarios().get(0).getContenidoFavorito().add(this.traiFlix.getPeliculas().get(8));
      _xblockexpression = this.traiFlix.getUsuarios().get(1).getContenidoFavorito().add(this.traiFlix.getPeliculas().get(1));
    }
    return _xblockexpression;
  }
  
  public boolean agregarContenidoVistoAUsuarios() {
    boolean _xblockexpression = false;
    {
      this.traiFlix.getUsuarios().get(0).agregarPeliculaVista(this.traiFlix.getPeliculas().get(0));
      this.traiFlix.getUsuarios().get(0).agregarPeliculaVista(this.traiFlix.getPeliculas().get(8));
      this.traiFlix.getUsuarios().get(0).agregarPeliculaVista(this.traiFlix.getPeliculas().get(9));
      this.traiFlix.getUsuarios().get(0).agregarPeliculaVista(this.traiFlix.getPeliculas().get(10));
      _xblockexpression = this.traiFlix.getUsuarios().get(1).agregarPeliculaVista(this.traiFlix.getPeliculas().get(11));
    }
    return _xblockexpression;
  }
  
  public void agregarRecomendacionesAUsuarios() {
    Usuario u1 = this.traiFlix.getUsuarios().get(0);
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getPeliculas().get(6));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getPeliculas().get(5));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getPeliculas().get(4));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getPeliculas().get(3));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getPeliculas().get(2));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getSeries().get(1));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getSeries().get(1));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getSeries().get(2));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getSeries().get(2));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getSeries().get(3));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getSeries().get(3));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getSeries().get(4));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getSeries().get(4));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getSeries().get(0));
    u1.getContenidoSugeridoPorAmigos().recomendar(this.traiFlix.getSeries().get(0));
  }
  
  public Boolean agregarSeries(final TraiFlix flix) {
    try {
      Boolean _xblockexpression = null;
      {
        String trailer1 = "https://www.youtube.com/embed/giYeaKsXnsI";
        String trailer2 = "https://www.youtube.com/embed/0hIbFFwTKdE";
        String actoresPrincipales1 = "Emilia Clarke, Peter Dinklage, Kit Harington";
        String sDate1 = "30/09/2017";
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        String sDate2 = "03/02/2018";
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        Capitulo capitulo1 = new Capitulo(5000, "The Rains of Castamere", 1, 1, date1, 30, "David Nutter", actoresPrincipales1, trailer1);
        Capitulo capitulo2 = new Capitulo(5001, "Battle of the Bastards", 2, 1, date2, 33, "David Nutter", actoresPrincipales1, trailer2);
        Serie serie1 = new Serie(2000, "Game of Thrones", "Acción,Drama", "+18", 
          "David Benioff, D.B. Weiss");
        serie1.setLinkPortada("https://m.media-amazon.com/images/M/MV5BMjE3NTQ1NDg1Ml5BMl5BanBnXkFtZTgwNzY2NDA0MjI@._V1_UX182_CR0,0,182,268_AL_.jpg");
        serie1.agregarCapitulo(capitulo1);
        serie1.agregarCapitulo(capitulo2);
        String trailer3 = "https://www.youtube.com/embed/f5av6OqFwz0";
        String actoresPrincipales2 = "Gustaf Skarsgård, Katheryn Winnick, Alexander Ludwig";
        String sDate3 = "30/05/2014";
        Date date3 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        Capitulo capitulo3 = new Capitulo(5002, "The Last Ship", 1, 1, date3, 36, "Jeff Woolnough", actoresPrincipales2, trailer3);
        Serie serie2 = new Serie(2001, "Vikings", "Aventura", "+18", 
          "Michael Hirst");
        serie2.setLinkPortada("https://m.media-amazon.com/images/M/MV5BNDYyNzk1NzYwOF5BMl5BanBnXkFtZTgwMTQ0Nzc4MzI@._V1_UY268_CR8,0,182,268_AL_.jpg");
        serie2.agregarCapitulo(capitulo3);
        Serie serie3 = new Serie(2002, "Breaking Bad", "Drama,Thriller", "+16", "Vince Gilligan");
        serie3.setLinkPortada("https://m.media-amazon.com/images/M/MV5BZDNhNzhkNDctOTlmOS00NWNmLWEyODQtNWMxM2UzYmJiNGMyXkEyXkFqcGdeQXVyNTMxMjgxMzA@._V1_UY268_CR4,0,182,268_AL_.jpg");
        Date _parse = new SimpleDateFormat("dd/MM/yyyy").parse("17/02/2008");
        Capitulo cap1Serie3 = new Capitulo(5006, "Cancer Man", 1, 1, _parse, 
          48, "Jim McKay", "Bryan Cranston, Anna Gunn, Aaron Paul", "https://www.youtube.com/embed/HhesaQXLuRY");
        serie3.agregarCapitulo(cap1Serie3);
        Serie serie4 = new Serie(2003, "La maldición de Hill House", "Drama,Fantasía,Horror", "+16", "Mike Flanagan");
        serie4.setLinkPortada("https://m.media-amazon.com/images/M/MV5BMTU4NzA4MDEwNF5BMl5BanBnXkFtZTgwMTQxODYzNjM@._V1_UX182_CR0,0,182,268_AL_.jpg");
        Date _parse_1 = new SimpleDateFormat("dd/MM/yyyy").parse("12/10/2018");
        Capitulo cap1Serie4 = new Capitulo(5004, "Steven ve un fantasma", 1, 1, _parse_1, 
          60, "Mike Flanagan", "Michiel Huisman, Carla Gugino, Timothy Hutton", "yt");
        cap1Serie4.setLinkYT("https://www.youtube.com/embed/G9OzG53VwIk");
        Date _parse_2 = new SimpleDateFormat("dd/MM/yyyy").parse("12/10/2018");
        Capitulo cap2Serie4 = new Capitulo(5005, "Ataúd abierto", 1, 2, _parse_2, 
          60, "Mike Flanagan", "Michiel Huisman, Carla Gugino, Timothy Hutton", "https://www.youtube.com/embed/Ce0RQq-rjPk");
        serie4.agregarCapitulo(cap1Serie4);
        serie4.agregarCapitulo(cap2Serie4);
        Serie serie5 = new Serie(2004, "Los Simpson", "Animación,Comedia", "ATP", "James L. Brooks, Matt Groening");
        serie5.setLinkPortada("https://m.media-amazon.com/images/M/MV5BYjFkMTlkYWUtZWFhNy00M2FmLThiOTYtYTRiYjVlZWYxNmJkXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_UX182_CR0,0,182,268_AL_.jpg");
        Date _parse_3 = new SimpleDateFormat("dd/MM/yyyy").parse("14/01/1990");
        Capitulo cap1Serie5 = new Capitulo(5007, "Bart the Genius", 1, 1, _parse_3, 
          30, "David Silverman", "Dan Castellaneta, Julie Kavner, Nancy Cartwright", "https://www.youtube.com/embed/pzdnq1zFvOY");
        serie5.agregarCapitulo(cap1Serie5);
        Serie serie6 = new Serie(2005, "Dexter", "Crimen,Drama", "ATP", "James Manos Jr.");
        serie6.setLinkPortada("https://m.media-amazon.com/images/M/MV5BMTM5MjkwMTI0MV5BMl5BanBnXkFtZTcwODQwMTc0OQ@@._V1_UY268_CR7,0,182,268_AL_.jpg");
        Date _parse_4 = new SimpleDateFormat("dd/MM/yyyy").parse("01/10/2006");
        Capitulo cap1Serie6 = new Capitulo(5008, "Dexter", 1, 1, _parse_4, 
          53, "Michael Cuesta", "Michael C. Hall, Julie Benz, Jennifer Carpenter", "https://www.youtube.com/embed/x9aGJeL_BRc");
        serie6.agregarCapitulo(cap1Serie6);
        Serie serie7 = new Serie(2006, "The Walking Dead", "Drama,Horror", "+13", "Robert Kirkman");
        serie7.setLinkPortada("https://vignette.wikia.nocookie.net/thewalkingdead/images/3/39/The_Walking_Dead.jpg/revision/latest?cb=20111109184928&path-prefix=es");
        Date _parse_5 = new SimpleDateFormat("dd/MM/yyyy").parse("31/10/2010");
        Capitulo cap1Serie7 = new Capitulo(5009, "Days Gone Bye", 1, 1, _parse_5, 50, "Frank Darabont", "Jon Bernthal , Andrew Lincoln", "https://www.youtube.com/embed/ZA21VAgd6L4");
        serie7.agregarCapitulo(cap1Serie7);
        flix.agregarSerie(serie1);
        flix.agregarSerie(serie2);
        flix.agregarSerie(serie3);
        flix.agregarSerie(serie4);
        flix.agregarSerie(serie5);
        flix.agregarSerie(serie6);
        _xblockexpression = flix.agregarSerie(serie7);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public Boolean agregarPeliculas(final TraiFlix flix) {
    Boolean _xblockexpression = null;
    {
      LocalDate _now = LocalDate.now();
      Pelicula pelicula1 = new Pelicula(1000, "A Clockwork Orange", "Drama", "+18", _now, 124, 
        "Stanley Kubrick", "Malcolm McDowell, Patrick Magee, Michael Bates", 
        "https://www.youtube.com/embed/SPRzm8ibDQ8");
      pelicula1.setLinkPortada("https://m.media-amazon.com/images/M/MV5BMTY3MjM1Mzc4N15BMl5BanBnXkFtZTgwODM0NzAxMDE@._V1_UX182_CR0,0,182,268_AL_.jpg");
      LocalDate _now_1 = LocalDate.now();
      Pelicula pelicula2 = new Pelicula(1001, "Jurassic Park", "Ciencia Ficción", "ATP", _now_1, 140, 
        "Steven Spielberg", "Sam Neill, Laura Dern, Jeff Goldblum, Richard Attenborough", 
        "https://www.youtube.com/embed/lc0UehYemQA");
      pelicula2.setLinkPortada("https://m.media-amazon.com/images/M/MV5BMjM2MDgxMDg0Nl5BMl5BanBnXkFtZTgwNTM2OTM5NDE@._V1_UX182_CR0,0,182,268_AL_.jpg");
      LocalDate _now_2 = LocalDate.now();
      Pelicula pelicula3 = new Pelicula(1002, "Jurassic Word", "Ciencia Ficción", "ATP", _now_2, 124, 
        "Colin Trevorrow", "Chris Pratt, Bryce Dallas Howard, Nick Robinson, Vincent D’Onofrio", 
        "https://www.youtube.com/embed/RFinNxS5KN4");
      pelicula3.setLinkPortada("https://m.media-amazon.com/images/M/MV5BNzQ3OTY4NjAtNzM5OS00N2ZhLWJlOWUtYzYwZjNmOWRiMzcyXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UX182_CR0,0,182,268_AL_.jpg");
      LocalDate _parse = LocalDate.parse("2011-11-11");
      Pelicula p4 = new Pelicula(1003, "Rio", "Animación,Aventura,Comedia", "ATP", _parse, 90, 
        "Carlos Saldanha", "Jesse Eisenberg, Anne Hathaway", "https://www.youtube.com/embed/P1GRO31ve5Q");
      p4.setLinkPortada("https://m.media-amazon.com/images/M/MV5BMTU2MDY3MzAzMl5BMl5BanBnXkFtZTcwMTg0NjM5NA@@._V1_UX182_CR0,0,182,268_AL_.jpg");
      LocalDate _parse_1 = LocalDate.parse("2000-11-11");
      Pelicula p5 = new Pelicula(1004, "Naúfrago", "Aventura,Drama,Romance", "+16", _parse_1, 153, 
        "William Broyles Jr.", "Tom Hanks, Helen Hunt", "https://www.youtube.com/embed/4tVklCz2jcI");
      p5.setLinkPortada("https://m.media-amazon.com/images/M/MV5BN2Y5ZTU4YjctMDRmMC00MTg4LWE1M2MtMjk4MzVmOTE4YjkzXkEyXkFqcGdeQXVyNTc1NTQxODI@._V1_UX182_CR0,0,182,268_AL_.jpg");
      LocalDate _parse_2 = LocalDate.parse("1980-11-11");
      Pelicula p6 = new Pelicula(1005, "El Resplandor", "Drama,Horror", "+18", _parse_2, 143, 
        "Stanley Kubrick", "Jack Nicholson, Shelley Duvall", "https://www.youtube.com/embed/3b726feAhdU");
      p6.setLinkPortada("https://m.media-amazon.com/images/M/MV5BZWFlYmY2MGEtZjVkYS00YzU4LTg0YjQtYzY1ZGE3NTA5NGQxXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UX182_CR0,0,182,268_AL_.jpg");
      LocalDate _parse_3 = LocalDate.parse("2004-11-11");
      Pelicula p7 = new Pelicula(1006, "The notebook", "Drama,Romance", "+13", _parse_3, 132, 
        "Nick Cassavetes", "Gena Rowlands, James Garner", "https://www.youtube.com/embed/FC6biTjEyZw");
      p7.setLinkPortada("https://m.media-amazon.com/images/M/MV5BMTk3OTM5Njg5M15BMl5BanBnXkFtZTYwMzA0ODI3._V1_UX182_CR0,0,182,268_AL_.jpg");
      LocalDate _parse_4 = LocalDate.parse("2002-11-11");
      Pelicula p8 = new Pelicula(1007, "Atrápame Si Puedes", "Biográfico,Crimen,Drama", "+13", _parse_4, 132, 
        "Steven Spielberg", "Leonardo DiCaprio, Tom Hanks", "https://www.youtube.com/embed/71rDQ7z4eFg");
      p8.setLinkPortada("https://m.media-amazon.com/images/M/MV5BMTY5MzYzNjc5NV5BMl5BanBnXkFtZTYwNTUyNTc2._V1_UX182_CR0,0,182,268_AL_.jpg");
      LocalDate _parse_5 = LocalDate.parse("2017-11-11");
      Pelicula p9 = new Pelicula(1008, "Coco", "Animación,Aventura,Comedia", "ATP", _parse_5, 85, 
        "Lee Unkrich, Adrian Molina", "Anthony Gonzalez, Gael García Bernal", "https://www.youtube.com/embed/awzWdtCezDo");
      p9.setLinkPortada("https://m.media-amazon.com/images/M/MV5BYjQ5NjM0Y2YtNjZkNC00ZDhkLWJjMWItN2QyNzFkMDE3ZjAxXkEyXkFqcGdeQXVyODIxMzk5NjA@._V1_UY268_CR3,0,182,268_AL_.jpg");
      LocalDate _parse_6 = LocalDate.parse("2015-11-11");
      Pelicula p10 = new Pelicula(1009, "El Renacido", "Acción,Aventura,Biográfico", "+16", _parse_6, 153, 
        "Alejandro G. Iñárritu", "Leonardo DiCaprio, Tom Hardy", "https://www.youtube.com/embed/QRfj1VCg16Y");
      p10.setLinkPortada("https://m.media-amazon.com/images/M/MV5BY2FmODc2N2QtYmY3MS00YTMwLWI2NGYtZWRmYWVkNjFjZmI0XkEyXkFqcGdeQXVyNTMxMjgxMzA@._V1_UX182_CR0,0,182,268_AL_.jpg");
      LocalDate _parse_7 = LocalDate.parse("2018-02-11");
      Pelicula p11 = new Pelicula(1010, "Perdida", "Crimen,Drama", "+16", _parse_7, 103, 
        "Alejandro Montiel", " Mara Alberto, Luisana Lopilato, Carlos Alcantára", "https://www.youtube.com/embed/lp9t3DXcqXo");
      p11.setLinkPortada("https://m.media-amazon.com/images/M/MV5BMTBmY2U4YmItZDdlYi00ZTM2LWE0NDQtMmE2ZDM1MTlhZWE0XkEyXkFqcGdeQXVyMjQ3NzUxOTM@._V1_UY268_CR3,0,182,268_AL_.jpg");
      LocalDate _parse_8 = LocalDate.parse("1996-02-11");
      Pelicula p12 = new Pelicula(1011, "Matilda", "Comedia,Fantasía", "ATP", _parse_8, 98, 
        "Danny DeVito", "Danny DeVito, Rhea Perlman, Mara Wilson", "https://www.youtube.com/embed/mPvuVAUEETg");
      p12.setLinkPortada("https://m.media-amazon.com/images/M/MV5BZTA4MmI5YzgtOTU1Yy00NGVjLTgyMGQtNjNlMDY2YWVlZmYyL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UX182_CR0,0,182,268_AL_.jpg");
      flix.agregarPelicula(pelicula1);
      flix.agregarPelicula(pelicula2);
      flix.agregarPelicula(pelicula3);
      flix.agregarPelicula(p4);
      flix.agregarPelicula(p5);
      flix.agregarPelicula(p6);
      flix.agregarPelicula(p7);
      flix.agregarPelicula(p8);
      flix.agregarPelicula(p9);
      flix.agregarPelicula(p10);
      flix.agregarPelicula(p11);
      _xblockexpression = flix.agregarPelicula(p12);
    }
    return _xblockexpression;
  }
  
  public boolean agregarUsuarios(final TraiFlix flix) {
    boolean _xblockexpression = false;
    {
      LocalDate _now = LocalDate.now();
      LocalDate _parse = LocalDate.parse("2000-01-01");
      Usuario usuario1 = new Usuario(1, "al84", "Antonio Lamas", _now, _parse);
      LocalDate _now_1 = LocalDate.now();
      LocalDate _parse_1 = LocalDate.parse("2000-06-08");
      Usuario usuario2 = new Usuario(2, "lb2003", "Lorenzo Banderas", _now_1, _parse_1);
      flix.agregarUsuario(usuario1);
      _xblockexpression = flix.agregarUsuario(usuario2);
    }
    return _xblockexpression;
  }
  
  @Pure
  public TraiFlix getTraiFlix() {
    return this.traiFlix;
  }
  
  public void setTraiFlix(final TraiFlix traiFlix) {
    this.traiFlix = traiFlix;
  }
}
