package server;

import com.google.common.base.Objects;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.xtext.xbase.lib.CollectionExtensions;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.uqbar.xtrest.api.annotation.Controller;
import org.uqbar.xtrest.json.JSONUtils;
import org.uqbar.xtrest.result.ResultFactory;
import server.SimpleContenido;
import server.SimplePelicula;
import server.SimplePeliculaRecomendada;
import server.SimpleSerie;
import server.SimpleSerieRecomendada;
import server.SimpleUsuarioPelicula;
import server.SimpleUsuarioSerie;
import ui.unq.edu.ar.TraiFlix.Capitulo;
import ui.unq.edu.ar.TraiFlix.Material;
import ui.unq.edu.ar.TraiFlix.Pelicula;
import ui.unq.edu.ar.TraiFlix.Serie;
import ui.unq.edu.ar.TraiFlix.TraiFlix;
import ui.unq.edu.ar.TraiFlix.Usuario;

@Controller
@SuppressWarnings("all")
public class AppController extends ResultFactory {
  @Extension
  private JSONUtils _jSONUtils = new JSONUtils();
  
  public AppController() {
  }
  
  public String argsAJson(final String args) {
    String[] pares = args.split(", ");
    Map<String, String> mr = new HashMap<String, String>();
    int i = 0;
    final String[] _converted_pares = (String[])pares;
    int limit = ((List<String>)Conversions.doWrapArray(_converted_pares)).size();
    while ((i < limit)) {
      {
        mr.put(pares[i], pares[(i + 1)]);
        int _i = i;
        i = (_i + 2);
      }
    }
    return this._jSONUtils.toJson(mr);
  }
  
  public String getErrorJson(final String message) {
    return this.argsAJson(("status, error, message, " + message));
  }
  
  public String getContenidoFavoritoDeUsuarioParaMostrar(final Usuario usuario) {
    return this._jSONUtils.toJson(this.agregarSimpleMaterialALista(usuario.getContenidoFavorito()));
  }
  
  public String getInformacionPeliculaIdconUsuario(final Pelicula pelicula, final Usuario usuario) {
    SimpleUsuarioPelicula usuarioConPeliculaParaVer = new SimpleUsuarioPelicula(pelicula, usuario);
    return this._jSONUtils.toJson(usuarioConPeliculaParaVer);
  }
  
  public String getInformacionSerieIdConUsuario(final Serie serie, final Usuario usuario) {
    SimpleUsuarioSerie usuarioConSerieParaVer = new SimpleUsuarioSerie(serie, usuario);
    return this._jSONUtils.toJson(usuarioConSerieParaVer);
  }
  
  public static String getTextoError() {
    return "Hubo un error en la consulta realizada.";
  }
  
  public static ArrayList<SimpleContenido> contenidoRelacionaSimple(final Material material) {
    ArrayList<SimpleContenido> lisContenidoRelacionado = new ArrayList<SimpleContenido>();
    String _name = material.getClass().getName();
    String _name_1 = Pelicula.class.getName();
    boolean _equals = Objects.equal(_name, _name_1);
    if (_equals) {
      List<Pelicula> _contenidoRelacionadoPelicula = ((Pelicula) material).getContenidoRelacionadoPelicula();
      for (final Pelicula contenidoPelicula : _contenidoRelacionadoPelicula) {
        {
          SimplePeliculaRecomendada contenidoActual = new SimplePeliculaRecomendada(contenidoPelicula);
          lisContenidoRelacionado.add(contenidoActual);
        }
      }
      List<Serie> _contenidoRelacionadoSerie = ((Pelicula) material).getContenidoRelacionadoSerie();
      for (final Serie contenidoSerie : _contenidoRelacionadoSerie) {
        {
          SimpleSerieRecomendada contenidoActual = new SimpleSerieRecomendada(contenidoSerie);
          lisContenidoRelacionado.add(contenidoActual);
        }
      }
    } else {
      List<Pelicula> _contenidoRelacionadoPelicula_1 = ((Serie) material).getContenidoRelacionadoPelicula();
      for (final Pelicula contenidoPelicula_1 : _contenidoRelacionadoPelicula_1) {
        {
          SimplePeliculaRecomendada contenidoActual = new SimplePeliculaRecomendada(contenidoPelicula_1);
          lisContenidoRelacionado.add(contenidoActual);
        }
      }
      List<Serie> _contenidoRelacionadoSerie_1 = ((Serie) material).getContenidoRelacionadoSerie();
      for (final Serie contenidoSerie_1 : _contenidoRelacionadoSerie_1) {
        {
          SimpleSerieRecomendada contenidoActual = new SimpleSerieRecomendada(contenidoSerie_1);
          lisContenidoRelacionado.add(contenidoActual);
        }
      }
    }
    return lisContenidoRelacionado;
  }
  
  public String validar(final String valor, final List<String> valoresAceptados) {
    try {
      String _xblockexpression = null;
      {
        boolean _contains = valoresAceptados.contains(valor);
        boolean _not = (!_contains);
        if (_not) {
          throw new Exception((valor + " no es valido"));
        }
        _xblockexpression = valor;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public Boolean marcarVistoAUnContenido(final Usuario u, final Material m) {
    boolean _xifexpression = false;
    boolean _equals = m.getClass().equals(Pelicula.class);
    if (_equals) {
      _xifexpression = u.agregarPeliculaVista(((Pelicula) m));
    } else {
      Serie serie = ((Serie) m);
      final Consumer<Capitulo> _function = new Consumer<Capitulo>() {
        public void accept(final Capitulo cap) {
          u.agregarCapituloVisto(cap);
        }
      };
      serie.getCapitulos().forEach(_function);
    }
    return Boolean.valueOf(_xifexpression);
  }
  
  public void marcarNoVistoAUnContenido(final Usuario u, final Material m) {
    boolean _equals = m.getClass().equals(Pelicula.class);
    if (_equals) {
      final Pelicula pelicula = ((Pelicula) m);
      final Function1<Pelicula, Boolean> _function = new Function1<Pelicula, Boolean>() {
        public Boolean apply(final Pelicula peli) {
          int _codigo = peli.getCodigo();
          int _codigo_1 = pelicula.getCodigo();
          return Boolean.valueOf((_codigo != _codigo_1));
        }
      };
      u.setPeliculasVistas(IterableExtensions.<Pelicula>toList(IterableExtensions.<Pelicula>filter(u.getPeliculasVistas(), _function)));
    } else {
      Serie serie = ((Serie) m);
      final Consumer<Capitulo> _function_1 = new Consumer<Capitulo>() {
        public void accept(final Capitulo cap) {
          final Function1<Capitulo, Boolean> _function = new Function1<Capitulo, Boolean>() {
            public Boolean apply(final Capitulo capVisto) {
              int _codigo = capVisto.getCodigo();
              int _codigo_1 = cap.getCodigo();
              return Boolean.valueOf((_codigo != _codigo_1));
            }
          };
          u.setCapitulosVistos(IterableExtensions.<Capitulo>toList(IterableExtensions.<Capitulo>filter(u.getCapitulosVistos(), _function)));
        }
      };
      serie.getCapitulos().forEach(_function_1);
    }
  }
  
  public ArrayList<Serie> serieRelacionadas(final TraiFlix traiFlix, final Pelicula pelicula) {
    List<Serie> _series = traiFlix.getSeries();
    ArrayList<Serie> series = new ArrayList<Serie>(_series);
    final String[] categs = pelicula.getCategoria().split(",");
    final Predicate<Serie> _function = new Predicate<Serie>() {
      public boolean test(final Serie p) {
        String[] _split = p.getCategoria().split(",");
        boolean _removeAll = CollectionExtensions.<String>removeAll(new ArrayList<String>((Collection<? extends String>)Conversions.doWrapArray(_split)), categs);
        return (!_removeAll);
      }
    };
    series.removeIf(_function);
    return series;
  }
  
  public ArrayList<Pelicula> peliculaRelacionadas(final TraiFlix traiFlix, final Pelicula pelicula) {
    List<Pelicula> _peliculas = traiFlix.getPeliculas();
    ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>(_peliculas);
    final String[] categs = pelicula.getCategoria().split(",");
    final Predicate<Pelicula> _function = new Predicate<Pelicula>() {
      public boolean test(final Pelicula p) {
        String[] _split = p.getCategoria().split(",");
        boolean _removeAll = CollectionExtensions.<String>removeAll(new ArrayList<String>((Collection<? extends String>)Conversions.doWrapArray(_split)), categs);
        return (!_removeAll);
      }
    };
    peliculas.removeIf(_function);
    final Predicate<Pelicula> _function_1 = new Predicate<Pelicula>() {
      public boolean test(final Pelicula peli) {
        int _codigo = peli.getCodigo();
        int _codigo_1 = pelicula.getCodigo();
        return (_codigo == _codigo_1);
      }
    };
    peliculas.removeIf(_function_1);
    return peliculas;
  }
  
  public ArrayList<Serie> serieRelacionadasASerie(final TraiFlix traiFlix, final Serie serie) {
    List<Serie> _series = traiFlix.getSeries();
    ArrayList<Serie> series = new ArrayList<Serie>(_series);
    final String[] categs = serie.getCategoria().split(",");
    final Predicate<Serie> _function = new Predicate<Serie>() {
      public boolean test(final Serie p) {
        String[] _split = p.getCategoria().split(",");
        boolean _removeAll = CollectionExtensions.<String>removeAll(new ArrayList<String>((Collection<? extends String>)Conversions.doWrapArray(_split)), categs);
        return (!_removeAll);
      }
    };
    series.removeIf(_function);
    final Predicate<Serie> _function_1 = new Predicate<Serie>() {
      public boolean test(final Serie s) {
        int _codigo = s.getCodigo();
        int _codigo_1 = serie.getCodigo();
        return (_codigo == _codigo_1);
      }
    };
    series.removeIf(_function_1);
    return series;
  }
  
  public ArrayList<Pelicula> peliculaRelacionadasASerie(final TraiFlix traiFlix, final Serie serie) {
    List<Pelicula> _peliculas = traiFlix.getPeliculas();
    ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>(_peliculas);
    final String[] categs = serie.getCategoria().split(",");
    final Predicate<Pelicula> _function = new Predicate<Pelicula>() {
      public boolean test(final Pelicula p) {
        String[] _split = p.getCategoria().split(",");
        boolean _removeAll = CollectionExtensions.<String>removeAll(new ArrayList<String>((Collection<? extends String>)Conversions.doWrapArray(_split)), categs);
        return (!_removeAll);
      }
    };
    peliculas.removeIf(_function);
    return peliculas;
  }
  
  public boolean corresponderCalificacion(final Usuario u, final int cantidadDeEstrellas, final String tipoContenido, final Material material) {
    boolean _xifexpression = false;
    boolean _equals = tipoContenido.equals("movie");
    if (_equals) {
      boolean _xblockexpression = false;
      {
        Pelicula pelicula = ((Pelicula) material);
        _xblockexpression = u.valorarPelicula(pelicula, Integer.valueOf(cantidadDeEstrellas));
      }
      _xifexpression = _xblockexpression;
    } else {
      boolean _xblockexpression_1 = false;
      {
        Capitulo capitulo = ((Capitulo) material);
        _xblockexpression_1 = u.valorarCapitulo(capitulo, Integer.valueOf(cantidadDeEstrellas));
      }
      _xifexpression = _xblockexpression_1;
    }
    return _xifexpression;
  }
  
  public Material buscarContenidoSegunTipoeId(final TraiFlix traiFlix, final String tipoContenido, final int idContenido) {
    try {
      String _lowerCase = tipoContenido.toLowerCase();
      boolean _matched = false;
      if (Objects.equal(_lowerCase, "movie")) {
        _matched=true;
        final Function1<Pelicula, Boolean> _function = new Function1<Pelicula, Boolean>() {
          public Boolean apply(final Pelicula p) {
            int _codigo = p.getCodigo();
            return Boolean.valueOf((_codigo == idContenido));
          }
        };
        return IterableExtensions.<Pelicula>findFirst(traiFlix.getPeliculas(), _function);
      }
      if (!_matched) {
        if (Objects.equal(_lowerCase, "serie")) {
          _matched=true;
          final Function1<Serie, Boolean> _function_1 = new Function1<Serie, Boolean>() {
            public Boolean apply(final Serie s) {
              int _codigo = s.getCodigo();
              return Boolean.valueOf((_codigo == idContenido));
            }
          };
          return IterableExtensions.<Serie>findFirst(traiFlix.getSeries(), _function_1);
        }
      }
      if (!_matched) {
        if (Objects.equal(_lowerCase, "chapter")) {
          _matched=true;
          final Function1<Capitulo, Boolean> _function_2 = new Function1<Capitulo, Boolean>() {
            public Boolean apply(final Capitulo cap) {
              int _codigo = cap.getCodigo();
              return Boolean.valueOf((_codigo == idContenido));
            }
          };
          return IterableExtensions.<Capitulo>findFirst(traiFlix.getCapitulos(), _function_2);
        }
      }
      throw new Exception((("Id:" + Integer.valueOf(idContenido)) + " no existente"));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static Usuario buscarUsuario(final TraiFlix traiFlix, final String usuario) {
    final Function1<Usuario, Boolean> _function = new Function1<Usuario, Boolean>() {
      public Boolean apply(final Usuario usuar) {
        return Boolean.valueOf(usuar.getNombreUsuario().toLowerCase().contains(usuario.toLowerCase()));
      }
    };
    return IterableExtensions.<Usuario>findFirst(traiFlix.getUsuarios(), _function);
  }
  
  public static String formatearFechaEstrenoParaMostrar(final Date fecha) {
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    return formatoFecha.format(fecha);
  }
  
  public String getAmigosDeUsuarioParaMostrar(final Usuario usuario) {
    ArrayList<String> res = new ArrayList<String>();
    List<Usuario> _amigos = usuario.getAmigos();
    for (final Usuario us : _amigos) {
      res.add(us.getNombreUsuario());
    }
    return this._jSONUtils.toJson(res);
  }
  
  public String getContenidoRecomendadoDeUsuarioParaMostrar(final Usuario usuario) {
    ArrayList<Material> recomendados = usuario.getContenidoSugeridoPorAmigos().getMaterialRecomendado();
    return this._jSONUtils.toJson(this.agregarSimpleMaterialALista(recomendados));
  }
  
  public String getContenidoMasRecomendadoDeUsuarioParaMostrar(final Usuario usuario) {
    List<Material> masRecomendados = usuario.getContenidoSugeridoPorAmigos().masRecomendadas();
    return this._jSONUtils.toJson(this.agregarSimpleMaterialALista(masRecomendados));
  }
  
  protected ArrayList<Object> agregarSimpleMaterialALista(final List<Material> listToMap) {
    ArrayList<Object> resultList = CollectionLiterals.<Object>newArrayList();
    for (final Material m : listToMap) {
      boolean _equals = m.getClass().equals(Serie.class);
      if (_equals) {
        SimpleSerie s = new SimpleSerie(((Serie) m));
        resultList.add(s);
      } else {
        SimplePelicula _simplePelicula = new SimplePelicula(((Pelicula) m));
        resultList.add(_simplePelicula);
      }
    }
    return resultList;
  }
  
  public void handle(final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
    //this.pageNotFound(baseRequest, request, response);
  }
}
