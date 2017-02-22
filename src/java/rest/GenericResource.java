/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import bd.Bus;
import bd.Conexion;
import bd.Localizacion;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author EL PAPITO
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Metodo para mostrar la matricula y la contraseña de todos los buses. El 
     * resultado de la consulta lo convertimos a Json.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listarBuses() {

        Conexion conexion = new Conexion();
        List<Bus> lista = null;
        try {
            lista = conexion.obtenerBuses();

        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        return gson.toJson(lista);
    }

    /**
     * Metodo para obtener la matricula y contraseña de un bus concreto .El 
     * resultado de la consulta lo convertimos a Json.
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String mostrarBus(@PathParam("id") int id) {
        Bus bus = null;
        Conexion conexion = new Conexion();
        try {
            bus = conexion.obtenerBusPor(id);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        return gson.toJson(bus);
    }

    /**
     * Metodo para obtener la ultima posicion de un bus. Cuando este metodo se
     * ejecuta nos devuelve la ultima posicion de un bus en cuestion como un 
     * String que transformamos en Json.
     *
     * @param id
     * @return
     */
    @GET
    @Path("ultima/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerUltimaPosicion(@PathParam("id") String id) {
        Localizacion loc = null;
        Conexion conexion = new Conexion();
        try {
            loc = conexion.obtenerUltimaPosicion(id);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();

        return gson.toJson(loc);
    }

    /**
     * Metodo para mostrar las 5 ultimas posiciones de un solo bus. Cuando este
     * metodo se ejecuta nos devuelve una lista con las 5 ultimas localizaciones
     * de un bus que pasaremos a Json.
     * @param id
     * @return
     */
    @GET
    @Path("cincoUltimas/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String mostrarLocalizacionAutobus(@PathParam("id") String id) {
        List<Localizacion> loc = null;
        Conexion conexion = new Conexion();
        try {
            loc = conexion.obtenerLocalizacionesBuses(id);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();
        return gson.toJson(loc);
    }
    
    /**
     * Metodo para obtener la ultima localizacion de todos los buses. Cuando este
     * metodo se ejecuta nos recupera una lista con la ultima posicion de todos
     * los buses que pasamos a Json.
     * @return 
     */
    @GET
    @Path("ultimaPosicionTodos")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerUltimasLocalizaciones() {
        List<Localizacion> ubi = null;
        Conexion conexion = new Conexion();
        try {
            ubi = conexion.obtenerUltimasLocalizaciones();
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();
        return ubi.isEmpty() ? gson.toJson(false) : gson.toJson(ubi);
    }

    /**
     * Metodo para insertar posiciones
     *
     * @param posicion
     * @return
     * @throws ParseException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean insertarPosicion(String posicion) throws ParseException {
        Conexion conexion = new Conexion();
        Gson gson = new Gson();
        Localizacion loc;
        loc = gson.fromJson(posicion, Localizacion.class);
        boolean result = true;
        try {
            conexion.insertarPosicion(loc);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }

}
