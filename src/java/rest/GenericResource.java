/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

/*
    Post insert
    Put insert-Update

    GET X3  AUTOBUSES
            TODAS LAS POSICIONES DE UN BUS
            ULTIMA POSICION DE UN BUS
    PUT     POSICIONES
 */
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

    /*IGUAL*/
    /**
     * Metodo para mostrar la matricula y la contraseña de todos los buses
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

    /*ESTE SI*/
 /*IGUAL*/
    /**
     * Metodo para obtener la matricula y contraseña de un bus concreto
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

    /*ESTE SI*//*PREGUNTAR*/
    /**
     * Metodo para obtener la ultima posicion de un bus
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
     * Metodo para mostrar las 5 ultimas posiciones de un solo bus
     * @param id
     * @return 
     */
    @GET
    @Path("cincoUltimas/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String mostrarUbicacionAutobus(@PathParam("id") String id) {
        List<Localizacion> loc = null;
        Conexion conexion = new Conexion();
        try {
            loc = conexion.obtenerPosiciones(id);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new Gson();
        return gson.toJson(loc);
    }

    /**
     * Metodo para insertar posiciones
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
