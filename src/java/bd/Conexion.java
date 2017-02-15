package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    Post insert
    Put insert-Update

    GET X3  TODOS AUTOBUSES--NO PRESCINDIBLE--
            OBTENER 1 BUS------------(obtenerBusPor)--
            TODAS LAS POSICIONES DE UN BUS---------(obtenerPosiciones)--ORDENADAS FECHA
                DESDE ID ENCUENTRA TODAS LAS POSICIONES DE UN BUS
            ULTIMA POSICION DE UN BUS---------(obtenerUltimaPosicion)--
    PUT     POSICIONES------- (insertarPosicion)
*/
public class Conexion {

    Connection connection;

    public Conexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.180.10:1521:INSLAFERRERI", "AITOR_ROMERO_CONNECTION", "aitor13aaa");
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
    
    /*FUNCIONA, CREO*/
    public boolean insertarPosicion(Localizacion loc) throws SQLException {
        String sql = "INSERT INTO Posiciones (id_bus, altitud, latitud, fecha) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, loc.getMatricula()); //stmt.setString(1, cli.getNombre);
        stmt.setFloat(2, loc.getAltitud());
        stmt.setFloat(3, loc.getLatitud());
        stmt.setString(4, loc.getFecha());
        int res = stmt.executeUpdate();
        finalizarConexion();

        return (res == 1);
    }
    
        /*FUNCIONA, CREO*/
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

    public List<Localizacion> obtenerPosiciones() throws SQLException {
        ResultSet rset;
        List<Localizacion> lista = new ArrayList();
        String sql = "SELECT altitud, latitud, fecha FROM Posiciones WHERE id_bus = ? ORDER BY fecha DESC";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            lista.add(new Localizacion(rset.getFloat("altitud"), rset.getFloat("latitud"), rset.getString("matricula"), rset.getString("fecha")));

        }
        finalizarConexion();
        return lista;
    }

//    public Bus obtenerBusPor(int id_bus) throws SQLException {
//        Bus bus = null;
//
//        ResultSet rset;
//
//        String sql = "SELECT id_bus, passwd FROM Otobuses WHERE id_bus = ?";
//        PreparedStatement stmt = getConnection().prepareStatement(sql);
//        stmt.setInt(1, id_bus);
//        rset = stmt.executeQuery();
//        while (rset.next()) {
//            bus = new Bus(rset.getString("id_bus"), rset.getString("passwd"));
//
//        }
//        finalizarConexion();
//        return bus;
//
//    }
    


    /*public boolean actualizarCliente(Bus cli) throws SQLException {
        boolean result;
        String sql = "UPDATE Otobuses SET id_bus = ?, passwd = ? WHERE idcliente = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cli.getNombre()); //stmt.setString(1, cli.getNombre);
        stmt.setInt(2, cli.getTelefono());
        stmt.setInt(3, cli.getIdCliente());

        int res = stmt.executeUpdate();
        if (res == 0) {
            result = insertarCliente(cli);
        } else {
            result = true;
        }

        return (result);
    }*/

    /*public boolean eliminarCliente(int id_bus) throws SQLException {

        String sql = "DELETE FROM Otobuses WHERE id_bus = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id_bus);

        int res = stmt.executeUpdate();

        return (res == 1);
    }*/

}
