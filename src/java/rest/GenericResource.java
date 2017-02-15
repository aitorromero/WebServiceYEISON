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
     * Retrieves representation of an instance of rest.GenericResource
     *
     * @return an instance of java.lang.String
     */
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String listarClientes() {
//
//        Conexion conexion = new Conexion();
//        List<Bus> lista = null;
//        try {
//            lista = conexion.obtenerBuses();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Gson gson = new Gson();
//
//        return gson.toJson(lista);
//    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    /*@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean insertarBus (String cli) {
         Conexion conexion = new Conexion();
         Gson gson = new Gson();
         Bus cliente;
         cliente = gson.fromJson(cli, Bus.class);
         boolean result = true;
        try {
          
            conexion.actualizarBus(cliente);
        } catch (SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            result = false;             
        }
        return result;
    }*/
 /*ESTE SI*/
//    @GET
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String mostrarBus(@PathParam("id") int id) {
//        Bus bus = null;
//        Conexion conexion = new Conexion();
//        try {
//            bus = conexion.obtenerBusPor(id);
//        } catch (SQLException ex) {
//            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Gson gson = new Gson();
//
//        return gson.toJson(bus);
//    }

    /*ESTE SI*/
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerUltimaPosicion(@PathParam("id") int id) {
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

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public boolean actualizarBus(String cli) {
//        Conexion conexion = new Conexion();
//        Gson gson = new Gson();
//        Bus cliente;
//        cliente = gson.fromJson(cli, Bus.class);
//        boolean result = true;
//        try {
//
//            conexion.insertarBus(cliente);
//        } catch (SQLException ex) {
//            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
//            result = false;
//
//        }
//        return result;
//    }
//
//    @DELETE
//    @Path("/delete/{id}")
//
//    public boolean eliminarBus(@PathParam("id") int id) {
//        Conexion conexion = new Conexion();
//        boolean result = true;
//
//        try {
//
//            conexion.eliminarCliente(id);
//        } catch (SQLException ex) {
//            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
//            result = false;
//
//        }
//        return result;
//    }
}
