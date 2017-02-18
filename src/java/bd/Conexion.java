package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    Post insert
    Put insert-Update

    GET X3  AUTOBUSES
            OBTENER 1 BUS------------(obtenerBusPor)--
            TODAS LAS POSICIONES DE UN BUS---------(obtenerPosiciones)
            ULTIMA POSICION DE UN BUS---------(obtenerUltimaPosicion)--
    PUT     POSICIONES------- (insertarPosicion)
 */
public class Conexion {

    Connection connection;

    public Conexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //DESDE CLASE
            //connection = DriverManager.getConnection(
            //        "jdbc:oracle:thin:@192.168.180.10:1521:INSLAFERRERI", "AITOR_ROMERO_CONNECTION", "aitor13aaa");
            //DESDE CASA
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:INSLAFERRERI", "AITOR_ROMERO_CONNECTION", "aitor13aaa");
            // connection = DriverManager.getConnection("jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:INSLAFERRERI", "PROFEA1","1234");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public void finalizarConexion() throws SQLException {
        connection.close();
    }

    private java.sql.Date convertUtilToSql(java.util.Date uDate) {

        java.sql.Date sDate = new java.sql.Date(uDate.getTime());

        return sDate;
    }

    /**
     * Metodo para insertar una nueva posicion a un bus
     * @param loc
     * @return
     * @throws SQLException
     * @throws ParseException 
     */
    public boolean insertarPosicion(Localizacion loc) throws SQLException, ParseException {
        String sql = "INSERT INTO Localizaciones (id_bus, altitud, latitud, fecha) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        String date;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        stmt.setString(1, loc.getMatricula()); //stmt.setString(1, cli.getNombre);
        stmt.setDouble(2, loc.getAltitud());
        stmt.setDouble(3, loc.getLatitud());
        stmt.setString(4, loc.getfecha());

        date = loc.getfecha();
        Date data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        java.sql.Date sDate = convertUtilToSql(data);
        stmt.setDate(4, sDate);

        int res = stmt.executeUpdate();
        finalizarConexion();

        return (res == 1);
    }
    /**
     * Metodo para obtener un listado con la matricula de todos los buses.
     * @return
     * @throws SQLException 
     */
    public List<Bus> obtenerBuses() throws SQLException {
        ResultSet rset;
        List<Bus> lista = new ArrayList();
        String sql = "SELECT matricula FROM Bus";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            lista.add(new Bus(rset.getString("matricula"), rset.getString("password")));

        }
        finalizarConexion();
        return lista;
    }
    
    /**
     * Metodo para obtener un bus a partir de su matricula(id_bus)
     * @param id_bus
     * @return
     * @throws SQLException 
     */
    public Bus obtenerBusPor(int id_bus) throws SQLException {
        Bus bus = null;

        ResultSet rset;

        String sql = "SELECT * FROM Bus WHERE id_bus = ?";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, id_bus);
        rset = stmt.executeQuery();
        while (rset.next()) {
            bus = new Bus(rset.getString("id_bus"), rset.getString("passwd"));

        }
        finalizarConexion();
        return bus;

    }
    /**
     * Metodo para obtener las 5 ultimas posiciones de un solo bus.
     * Este metodo se usara para el mapa.
     * @param id_bus
     * @return
     * @throws SQLException 
     */
    public List<Localizacion> obtenerPosiciones(String id_bus) throws SQLException {
        ResultSet rset;
        List<Localizacion> lista = new ArrayList();
        String sql = "SELECT * FROM (SELECT * FROM Localizacion WHERE matricula LIKE ? ORDER BY fecha DESC) WHERE ROWNUM <=5";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            lista.add(new Localizacion(rset.getFloat("altitud"), rset.getFloat("latitud"), rset.getString("id_bus"), rset.getString("fecha")));

        }
        finalizarConexion();
        return lista;
    }

    /*PREGUNTAR*/
    public Localizacion obtenerUltimaPosicion(int id_bus) throws SQLException {
        Localizacion loc = null;

        ResultSet rset;

        String sql = "SELECT * FROM (SELECT * FROM Posiciones WHERE id_bus = ? ORDER BY fecha DESC) WHERE ROWNUM = 1";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, id_bus);
        rset = stmt.executeQuery();
        while (rset.next()) {
            loc = new Localizacion(rset.getFloat("latitud"), rset.getFloat("altitud"), rset.getString("fecha"), rset.getString("id_bus"));

        }
        finalizarConexion();
        return loc;

    }

}
