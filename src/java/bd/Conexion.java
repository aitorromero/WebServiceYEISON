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
            try {
                connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@192.168.180.10:1521:INSLAFERRERI", "AITOR_ROMERO_CONNECTION", "aitor13aaa");
            } catch (SQLException e1) {
                try {
                    //DESDE CASA
                    connection = DriverManager.getConnection(
                            "jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:INSLAFERRERI", "AITOR_ROMERO_CONNECTION", "aitor13aaa");

                } catch (SQLException e) {
                    System.out.println(e);
                }
            }

            // connection = DriverManager.getConnection("jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:INSLAFERRERI", "PROFEA1","1234");
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
     *
     * @param loc
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public boolean insertarPosicion(Localizacion loc) throws SQLException, ParseException {
        String sql = "INSERT INTO Localizaciones (id_bus, altitud, latitud, fecha) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        String date;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mi:ss");
        stmt.setString(1, loc.getMatricula()); //stmt.setString(1, cli.getNombre);
        stmt.setDouble(2, loc.getAltitud());
        stmt.setDouble(3, loc.getLatitud());
        stmt.setString(4, loc.getfecha());

        date = loc.getfecha();
        Date data = new SimpleDateFormat("yyyy-MM-dd HH:mi:ss").parse(date);
        java.sql.Date sDate = convertUtilToSql(data);
        stmt.setDate(4, sDate);

        int res = stmt.executeUpdate();
        finalizarConexion();

        return (res == 1);
    }

    /**
     * Metodo para obtener un listado con la matricula de todos los buses.
     *
     * @return
     * @throws SQLException
     */
    public List<Bus> obtenerBuses() throws SQLException {
        ResultSet rset;
        List<Bus> lista = new ArrayList();
        String sql = "SELECT * FROM Bus";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            lista.add(new Bus(rset.getString("id_bus"), rset.getString("pass")));

        }
        finalizarConexion();
        return lista;
    }

    /**
     * Metodo para obtener un bus a partir de su matricula(id_bus)
     *
     * @param id_bus
     * @return
     * @throws SQLException
     */
    public Bus obtenerBusPor(int id_bus) throws SQLException {
        Bus bus = null;
        ResultSet rset;

        String sql = "SELECT * FROM Bus WHERE id_bus LIKE ?";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, id_bus);
        rset = stmt.executeQuery();
        while (rset.next()) {
            bus = new Bus(rset.getString("id_bus"), rset.getString("pass"));

        }
        finalizarConexion();
        return bus;

    }

    /**
     * Metodo para obtener todas las posiciones de un bus pasado por parametro
     * @param id_bus
     * @return
     * @throws SQLException 
     */
    public List<Localizacion> obtenerTodasPosiciones(String id_bus) throws SQLException {
        ResultSet rset;
        List<Localizacion> lista = new ArrayList();
        String sql = "SELECT * FROM Localizacion WHERE matricula LIKE ?";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setString(1, id_bus);
        rset = stmt.executeQuery();
        while (rset.next()) {
            lista.add(new Localizacion(rset.getDouble("latitud"), rset.getDouble("altitud"), rset.getString("fecha"), rset.getString("matricula")));

        }
        finalizarConexion();
        return lista;
    }

    /**
     * Metodo para obtener la ultima posicion de un bus pasado por parametro
     * @param id_bus
     * @return
     * @throws SQLException 
     */
    public Localizacion obtenerUltimaPosicion(String id_bus) throws SQLException {
        Localizacion loc = null;

        ResultSet rset;

        String sql = "SELECT * FROM (SELECT * FROM Posiciones WHERE matricula LIKE ? ORDER BY fecha DESC) WHERE ROWNUM = 1";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setString(1, id_bus);
        rset = stmt.executeQuery();
        while (rset.next()) {
            loc = new Localizacion(rset.getDouble("latitud"), rset.getDouble("altitud"), rset.getString("fecha"), rset.getString("id_bus"));

        }
        finalizarConexion();
        return loc;

    }

    /**
     * Metodo para obtener las 5 ultimas posiciones de un bus pasado por parametro
     * @param id
     * @return
     * @throws SQLException 
     */
    public List<Localizacion> obtenerLocalizacionesBuses(String id) throws SQLException {
        List<Localizacion> loc = new ArrayList<>();
        ResultSet rset;
        String sql = "SELECT * FROM (SELECT * FROM UBICACION WHERE matricula LIKE ? ORDER BY data DESC) WHERE ROWNUM <=5";

        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setString(1, id);
        rset = stmt.executeQuery();
        while (rset.next()) {
            loc.add(new Localizacion(rset.getDouble("altitud"), rset.getDouble("latitud"), rset.getString("fecha"), rset.getString("matricula")));
        }
        finalizarConexion();
        return loc;
    }

    /**
     * Metodo para obtener la ultima localizacion de todos los buses
     * @return
     * @throws SQLException 
     */
    public List<Localizacion> obtenerUltimasLocalizaciones() throws SQLException {
        ResultSet rset;
        List<Localizacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Localizacion WHERE (matricula, fecha) IN (SELECT matricula, MAX(fecha) FROM Localizacion GROUP BY matricula)";

        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            lista.add(new Localizacion(rset.getDouble("latitud"), rset.getDouble("altitud"), rset.getString("matricula"), rset.getString("fecha")));
        }
        finalizarConexion();
        return lista;

    }

}
