package server;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.uqbar.xtrest.api.Result;
import org.uqbar.xtrest.api.annotation.Body;
import org.uqbar.xtrest.api.annotation.Controller;
import org.uqbar.xtrest.api.annotation.Get;
import org.uqbar.xtrest.api.annotation.Post;
import org.uqbar.xtrest.api.annotation.Put;
import org.uqbar.xtrest.http.ContentType;
import org.uqbar.xtrest.json.JSONUtils;
import org.uqbar.xtrest.result.ResultFactory;
import server.AppController;
import server.SimplePelicula;
import server.SimpleSerie;
import ui.unq.edu.ar.TraiFlix.Material;
import ui.unq.edu.ar.TraiFlix.Pelicula;
import ui.unq.edu.ar.TraiFlix.Serie;
import ui.unq.edu.ar.TraiFlix.TraiFlix;
import ui.unq.edu.ar.TraiFlix.Usuario;

/**
 * Servidor RESTful implementado con XtRest.
 */
@Controller
@SuppressWarnings("all")
public class RestfulServer extends ResultFactory {
  @Extension
  private JSONUtils _jSONUtils = new JSONUtils();
  
  private TraiFlix traiFlix;
  
  private AppController appController;
  
  public RestfulServer(final TraiFlix traiFlix) {
    this.traiFlix = traiFlix;
    AppController _appController = new AppController();
    this.appController = _appController;
  }
  
  @Get("/categories")
  public Result getCategories(final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    List<String> res = this.traiFlix.getCategorias();
    Collections.<String>sort(res, String.CASE_INSENSITIVE_ORDER);
    return ResultFactory.ok(this._jSONUtils.toJson(res));
  }
  
  @Get("/content/:category")
  public Result getContenidoDeCategoria(final String category, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    ArrayList<Object> contenidos = new ArrayList<Object>();
    String categoria = category.toLowerCase().substring(0, 3);
    List<Pelicula> _peliculas = this.traiFlix.getPeliculas();
    for (final Pelicula p : _peliculas) {
      boolean _contains = p.getCategoria().toLowerCase().contains(categoria);
      if (_contains) {
        SimplePelicula _simplePelicula = new SimplePelicula(p);
        contenidos.add(_simplePelicula);
      }
    }
    List<Serie> _series = this.traiFlix.getSeries();
    for (final Serie s : _series) {
      boolean _contains_1 = s.getCategoria().toLowerCase().contains(categoria);
      if (_contains_1) {
        SimpleSerie _simpleSerie = new SimpleSerie(s);
        contenidos.add(_simpleSerie);
      }
    }
    return ResultFactory.ok(this._jSONUtils.toJson(contenidos));
  }
  
  @Post("/auth")
  public Result validarUsuario(@Body final String body, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    final String nombreUsuario = this._jSONUtils.getPropertyValue(body, "username");
    final Usuario usuario = AppController.buscarUsuario(this.traiFlix, nombreUsuario);
    if ((usuario == null)) {
      return ResultFactory.badRequest(this.appController.argsAJson("status, error, message, Usuario Invalido"));
    } else {
      return ResultFactory.ok("{ \"status\": \"ok\",\r\n\t\t\t\"message\": \"¡Listo!\" }");
    }
  }
  
  @Get("/:username/favs")
  public Result getContenidoFavoritoDeUsuario(final String username, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    final Usuario usuario = AppController.buscarUsuario(this.traiFlix, username);
    if ((usuario == null)) {
      return ResultFactory.badRequest(this.appController.argsAJson("status, error, message, Usuario Invalido"));
    } else {
      return ResultFactory.ok(this.appController.getContenidoFavoritoDeUsuarioParaMostrar(usuario));
    }
  }
  
  @Get("/:username/recommended")
  public Result getContenidoRecomendadoDeUsuario(final String username, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    final Usuario usuario = AppController.buscarUsuario(this.traiFlix, username);
    if ((usuario == null)) {
      return ResultFactory.badRequest(this.appController.argsAJson("status, error, message, Usuario Invalido"));
    } else {
      return ResultFactory.ok(this.appController.getContenidoRecomendadoDeUsuarioParaMostrar(usuario));
    }
  }
  
  @Get("/:username/mostrecommended")
  public Result getContenidoMasRecomendadoDeUsuario(final String username, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    final Usuario usuario = AppController.buscarUsuario(this.traiFlix, username);
    if ((usuario == null)) {
      return ResultFactory.badRequest(this.appController.argsAJson("status, error, message, Usuario Invalido"));
    } else {
      return ResultFactory.ok(this.appController.getContenidoMasRecomendadoDeUsuarioParaMostrar(usuario));
    }
  }
  
  @Post("/recommend/:type/:id")
  public Result generarRecomendacionDeUnUsuarioAOtro(@Body final String body, final String type, final String id, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    try {
      final String nombreUserFrom = this._jSONUtils.getPropertyValue(body, "userfrom");
      final String nombreUserTo = this._jSONUtils.getPropertyValue(body, "userto");
      final Integer codigo = new Integer(id);
      Usuario userFrom = AppController.buscarUsuario(this.traiFlix, nombreUserFrom);
      Usuario userTo = AppController.buscarUsuario(this.traiFlix, nombreUserTo);
      String nombreContenido = "";
      boolean _equals = type.equals("serie");
      if (_equals) {
        final Function1<Serie, Boolean> _function = new Function1<Serie, Boolean>() {
          public Boolean apply(final Serie serie) {
            int _codigo = serie.getCodigo();
            return Boolean.valueOf((_codigo == (codigo).intValue()));
          }
        };
        userFrom.sugerirContenido(IterableExtensions.<Serie>findFirst(this.traiFlix.getSeries(), _function), userTo);
        final Function1<Serie, Boolean> _function_1 = new Function1<Serie, Boolean>() {
          public Boolean apply(final Serie serie) {
            int _codigo = serie.getCodigo();
            return Boolean.valueOf((_codigo == (codigo).intValue()));
          }
        };
        nombreContenido = IterableExtensions.<Serie>findFirst(this.traiFlix.getSeries(), _function_1).getTitulo();
      } else {
        final Function1<Pelicula, Boolean> _function_2 = new Function1<Pelicula, Boolean>() {
          public Boolean apply(final Pelicula pelicula) {
            int _codigo = pelicula.getCodigo();
            return Boolean.valueOf(Objects.equal(Integer.valueOf(_codigo), id));
          }
        };
        userFrom.sugerirContenido(IterableExtensions.<Pelicula>findFirst(this.traiFlix.getPeliculas(), _function_2), userTo);
        final Function1<Pelicula, Boolean> _function_3 = new Function1<Pelicula, Boolean>() {
          public Boolean apply(final Pelicula pelicula) {
            int _codigo = pelicula.getCodigo();
            return Boolean.valueOf((_codigo == (codigo).intValue()));
          }
        };
        nombreContenido = IterableExtensions.<Pelicula>findFirst(this.traiFlix.getPeliculas(), _function_3).getTitulo();
      }
      String msgResultado = "%s le recomendo %s a %s";
      msgResultado = String.format(msgResultado, userFrom.getNombre(), nombreContenido, userTo.getNombre());
      return ResultFactory.ok(this.appController.argsAJson(("status, ok, message, " + msgResultado)));
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        String _textoError = AppController.getTextoError();
        String _plus = ("status, error, message, " + _textoError);
        return ResultFactory.badRequest(this.appController.argsAJson(_plus));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Get("/:username/movie/:id")
  public Result getMovie(final String username, final String id, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    try {
      final Integer codigoId = new Integer(id);
      final Usuario usuario = AppController.buscarUsuario(this.traiFlix, username);
      final Function1<Pelicula, Boolean> _function = new Function1<Pelicula, Boolean>() {
        public Boolean apply(final Pelicula pelicula) {
          int _codigo = pelicula.getCodigo();
          return Boolean.valueOf((_codigo == (codigoId).intValue()));
        }
      };
      Pelicula pelicula = IterableExtensions.<Pelicula>findFirst(this.traiFlix.getPeliculas(), _function);
      pelicula.setContenidoRelacionadoPelicula(this.appController.peliculaRelacionadas(this.traiFlix, pelicula));
      pelicula.setContenidoRelacionadoSerie(this.appController.serieRelacionadas(this.traiFlix, pelicula));
      return ResultFactory.ok(this.appController.getInformacionPeliculaIdconUsuario(pelicula, usuario));
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        return ResultFactory.badRequest(this.appController.getErrorJson("No se encontro la pelicula"));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Put("/:username/fav/:type/:id")
  public Result asignarFavoritos(@Body final String body, final String username, final String type, final String id, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    try {
      Usuario usuario = AppController.buscarUsuario(this.traiFlix, username);
      String tipoDeContenido = this.appController.validar(type, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("movie", "serie")));
      Material contenido = this.appController.buscarContenidoSegunTipoeId(this.traiFlix, tipoDeContenido, Integer.parseInt(id));
      boolean esFavorito = this.appController.validar(this._jSONUtils.getPropertyValue(body, "value"), Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("true", "false"))).equals("true");
      String asignacion = "";
      if (esFavorito) {
        usuario.agregarAFavoritos(contenido);
        asignacion = "agrego a favoritos";
      } else {
        usuario.quitarDeFavoritos(contenido);
        asignacion = "saco de sus favoritos";
      }
      String msg = "El usuario %s %s a %s";
      msg = String.format(msg, usuario.getNombre(), asignacion, contenido.getTitulo());
      return ResultFactory.ok(this.appController.argsAJson(("status, ok, message, " + msg)));
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        return ResultFactory.badRequest(this.appController.getErrorJson(e.getMessage()));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Get("/:username/watched/movies")
  public Result getContenidoVisto(final String username, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    Usuario usuario = AppController.buscarUsuario(this.traiFlix, username);
    if ((usuario == null)) {
      return ResultFactory.badRequest(this.appController.argsAJson("status, error, message, Usuario Invalido"));
    } else {
      final Function1<Pelicula, SimplePelicula> _function = new Function1<Pelicula, SimplePelicula>() {
        public SimplePelicula apply(final Pelicula p) {
          return new SimplePelicula(p);
        }
      };
      return ResultFactory.ok(this._jSONUtils.toJson(ListExtensions.<Pelicula, SimplePelicula>map(usuario.getPeliculasVistas(), _function)));
    }
  }
  
  @Put("/:username/watched/:type/:id")
  public Result marcarVisto(@Body final String body, final String username, final String type, final String id, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    try {
      Usuario usuario = AppController.buscarUsuario(this.traiFlix, username);
      String tipoDeContenido = this.appController.validar(type, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("movie", "serie")));
      Material contenido = this.appController.buscarContenidoSegunTipoeId(this.traiFlix, tipoDeContenido, Integer.parseInt(id));
      boolean visto = this.appController.validar(this._jSONUtils.getPropertyValue(body, "value"), Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("true", "false"))).equals("true");
      String asignacion = "";
      if (visto) {
        this.appController.marcarVistoAUnContenido(usuario, contenido);
        asignacion = "agrego a su contenido visto";
      } else {
        this.appController.marcarNoVistoAUnContenido(usuario, contenido);
        asignacion = "elimino de su contenido visto";
      }
      String msg = "El usuario %s %s a %s";
      msg = String.format(msg, usuario.getNombre(), asignacion, contenido.getTitulo());
      return ResultFactory.ok(this.appController.argsAJson(("status, ok, message, " + msg)));
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        return ResultFactory.badRequest("");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Put("/:username/rating/:type/:id")
  public Result calificarContenido(@Body final String body, final String username, final String type, final String id, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    try {
      Usuario usuario = AppController.buscarUsuario(this.traiFlix, username);
      String tipoDeContenido = this.appController.validar(type, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("movie", "chapter")));
      Material contenido = this.appController.buscarContenidoSegunTipoeId(this.traiFlix, tipoDeContenido, Integer.parseInt(id));
      int rating = Integer.parseInt(this.appController.validar(this._jSONUtils.getPropertyValue(body, "stars"), Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("1", "2", "3", "4", "5"))));
      this.appController.corresponderCalificacion(usuario, rating, type, contenido);
      String msg = "El usuario %s califico con %s estrellas a %s";
      msg = String.format(msg, usuario.getNombre(), Integer.valueOf(rating), contenido.getTitulo());
      return ResultFactory.ok(this.appController.argsAJson(("status, ok, message, " + msg)));
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        return ResultFactory.badRequest(this.appController.getErrorJson(e.getMessage()));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Post("/search")
  public Result buscarContenidos(@Body final String body, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    try {
      final String textoABuscar = this._jSONUtils.getPropertyValue(body, "pattern").toLowerCase();
      ArrayList<Object> contenidos = new ArrayList<Object>();
      List<Pelicula> _peliculas = this.traiFlix.getPeliculas();
      for (final Pelicula p : _peliculas) {
        boolean _contains = p.getTitulo().toLowerCase().contains(textoABuscar);
        if (_contains) {
          SimplePelicula _simplePelicula = new SimplePelicula(p);
          contenidos.add(_simplePelicula);
        }
      }
      List<Serie> _series = this.traiFlix.getSeries();
      for (final Serie s : _series) {
        boolean _contains_1 = s.getTitulo().toLowerCase().contains(textoABuscar);
        if (_contains_1) {
          SimpleSerie _simpleSerie = new SimpleSerie(s);
          contenidos.add(_simpleSerie);
        }
      }
      return ResultFactory.ok(this._jSONUtils.toJson(contenidos));
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        String _textoError = AppController.getTextoError();
        String _plus = ("status, error, message, " + _textoError);
        return ResultFactory.badRequest(this.appController.argsAJson(_plus));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Get("/:username/serie/:id")
  public Result getSerie(final String username, final String id, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    try {
      final Integer codigoId = new Integer(id);
      final Usuario usuario = AppController.buscarUsuario(this.traiFlix, username);
      final Function1<Serie, Boolean> _function = new Function1<Serie, Boolean>() {
        public Boolean apply(final Serie serie) {
          int _codigo = serie.getCodigo();
          return Boolean.valueOf((_codigo == (codigoId).intValue()));
        }
      };
      Serie serie = IterableExtensions.<Serie>findFirst(this.traiFlix.getSeries(), _function);
      serie.setContenidoRelacionadoPelicula(this.appController.peliculaRelacionadasASerie(this.traiFlix, serie));
      serie.setContenidoRelacionadoSerie(this.appController.serieRelacionadasASerie(this.traiFlix, serie));
      return ResultFactory.ok(this.appController.getInformacionSerieIdConUsuario(serie, usuario));
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        return ResultFactory.badRequest(this.appController.getErrorJson("No se encontro la serie"));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Get("/:username/vio/:type/:id")
  public Result vioContenido(final String username, final String type, final String id, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    response.setContentType(ContentType.APPLICATION_JSON);
    try {
      final Integer codigoId = new Integer(id);
      final Usuario usuario = AppController.buscarUsuario(this.traiFlix, username);
      boolean visto = false;
      boolean _equals = type.equals("movie");
      if (_equals) {
        final Function1<Pelicula, Boolean> _function = new Function1<Pelicula, Boolean>() {
          public Boolean apply(final Pelicula peli) {
            int _codigo = peli.getCodigo();
            return Boolean.valueOf((_codigo == (codigoId).intValue()));
          }
        };
        boolean _isEmpty = IterableExtensions.isEmpty(IterableExtensions.<Pelicula>filter(usuario.getPeliculasVistas(), _function));
        boolean _not = (!_isEmpty);
        visto = _not;
      } else {
        final Function1<Serie, Boolean> _function_1 = new Function1<Serie, Boolean>() {
          public Boolean apply(final Serie serie) {
            int _codigo = serie.getCodigo();
            return Boolean.valueOf((_codigo == (codigoId).intValue()));
          }
        };
        boolean _isEmpty_1 = IterableExtensions.isEmpty(IterableExtensions.<Serie>filter(this.traiFlix.seriesVistasDeFormaCompletaPor(usuario), _function_1));
        boolean _not_1 = (!_isEmpty_1);
        visto = _not_1;
      }
      String _string = Boolean.valueOf(visto).toString();
      String _plus = ("visto, " + _string);
      return ResultFactory.ok(this.appController.argsAJson(_plus));
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        String _textoError = AppController.getTextoError();
        String _plus_1 = ("status, error, message, " + _textoError);
        return ResultFactory.badRequest(this.appController.argsAJson(_plus_1));
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Get("/:username/amigos")
  public Result getAmigosDeUsuario(final String username, final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    final Usuario usuario = AppController.buscarUsuario(this.traiFlix, username);
    if ((usuario == null)) {
      return ResultFactory.badRequest(this.appController.argsAJson("status, error, message, Usuario Invalido"));
    }
    return ResultFactory.ok(this.appController.getAmigosDeUsuarioParaMostrar(usuario));
  }
  
  @Get("/content/movies")
  public Result getPeliculas(final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    final ArrayList<SimplePelicula> resp = new ArrayList<SimplePelicula>();
    final Consumer<Pelicula> _function = new Consumer<Pelicula>() {
      public void accept(final Pelicula p) {
        SimplePelicula _simplePelicula = new SimplePelicula(p);
        resp.add(_simplePelicula);
      }
    };
    this.traiFlix.getPeliculas().forEach(_function);
    return ResultFactory.ok(this._jSONUtils.toJson(resp));
  }
  
  @Get("/content/series")
  public Result getSeries(final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) {
    response.setContentType(ContentType.APPLICATION_JSON);
    final ArrayList<SimpleSerie> resp = new ArrayList<SimpleSerie>();
    final Consumer<Serie> _function = new Consumer<Serie>() {
      public void accept(final Serie s) {
        SimpleSerie _simpleSerie = new SimpleSerie(s);
        resp.add(_simpleSerie);
      }
    };
    this.traiFlix.getSeries().forEach(_function);
    return ResultFactory.ok(this._jSONUtils.toJson(resp));
  }
  
  public void handle(final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
    {
    	Matcher matcher = 
    		Pattern.compile("/categories").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = getCategories(target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/content/movies").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = getPeliculas(target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/content/series").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = getSeries(target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/auth").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Post") && matcher.matches()) {
    		// take parameters from request
    		String body = readBodyAsString(request);
    		
    		// take variables from url
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = validarUsuario(body, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/search").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Post") && matcher.matches()) {
    		// take parameters from request
    		String body = readBodyAsString(request);
    		
    		// take variables from url
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = buscarContenidos(body, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/content/(\\w+)").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		String category = matcher.group(1);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = getContenidoDeCategoria(category, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/(\\w+)/favs").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		String username = matcher.group(1);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = getContenidoFavoritoDeUsuario(username, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/(\\w+)/recommended").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		String username = matcher.group(1);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = getContenidoRecomendadoDeUsuario(username, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/(\\w+)/mostrecommended").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		String username = matcher.group(1);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = getContenidoMasRecomendadoDeUsuario(username, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/(\\w+)/watched/movies").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		String username = matcher.group(1);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = getContenidoVisto(username, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/(\\w+)/amigos").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		String username = matcher.group(1);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = getAmigosDeUsuario(username, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/(\\w+)/movie/(\\w+)").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		String username = matcher.group(1);
    		String id = matcher.group(2);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = getMovie(username, id, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/(\\w+)/serie/(\\w+)").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		String username = matcher.group(1);
    		String id = matcher.group(2);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = getSerie(username, id, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/recommend/(\\w+)/(\\w+)").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Post") && matcher.matches()) {
    		// take parameters from request
    		String body = readBodyAsString(request);
    		
    		// take variables from url
    		String type = matcher.group(1);
    		String id = matcher.group(2);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = generarRecomendacionDeUnUsuarioAOtro(body, type, id, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/(\\w+)/vio/(\\w+)/(\\w+)").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Get") && matcher.matches()) {
    		// take parameters from request
    		
    		// take variables from url
    		String username = matcher.group(1);
    		String type = matcher.group(2);
    		String id = matcher.group(3);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = vioContenido(username, type, id, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/(\\w+)/fav/(\\w+)/(\\w+)").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Put") && matcher.matches()) {
    		// take parameters from request
    		String body = readBodyAsString(request);
    		
    		// take variables from url
    		String username = matcher.group(1);
    		String type = matcher.group(2);
    		String id = matcher.group(3);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = asignarFavoritos(body, username, type, id, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/(\\w+)/watched/(\\w+)/(\\w+)").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Put") && matcher.matches()) {
    		// take parameters from request
    		String body = readBodyAsString(request);
    		
    		// take variables from url
    		String username = matcher.group(1);
    		String type = matcher.group(2);
    		String id = matcher.group(3);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = marcarVisto(body, username, type, id, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    {
    	Matcher matcher = 
    		Pattern.compile("/(\\w+)/rating/(\\w+)/(\\w+)").matcher(target);
    	if (request.getMethod().equalsIgnoreCase("Put") && matcher.matches()) {
    		// take parameters from request
    		String body = readBodyAsString(request);
    		
    		// take variables from url
    		String username = matcher.group(1);
    		String type = matcher.group(2);
    		String id = matcher.group(3);
    		
            // set default content type (it can be overridden during next call)
            response.setContentType("application/json");
    		
    	    Result result = calificarContenido(body, username, type, id, target, baseRequest, request, response);
    	    result.process(response);
    	    
    		response.addHeader("Access-Control-Allow-Origin", "*");
    	    baseRequest.setHandled(true);
    	    return;
    	}
    }
    //this.pageNotFound(baseRequest, request, response);
  }
}
