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

/**
 *
 * @author Lluis_2
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

    public boolean insertarBus(Bus bus) throws SQLException {
        String sql = "INSERT INTO Otobuses (id_bus, passwd) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, bus.getIdBus()); //stmt.setString(1, cli.getNombre);
        stmt.setString(2, bus.getPass());
        int res = stmt.executeUpdate();
        finalizarConexion();

        return (res == 1);
    }

    public List<Bus> obtenerBuses() throws SQLException {
        ResultSet rset;
        List<Bus> lista = new ArrayList();
        String sql = "SELECT id_bus, passwd FROM Otobuses";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        rset = stmt.executeQuery();
        while (rset.next()) {
            lista.add(new Bus(rset.getString("id_bus"), rset.getString("passwd")));

        }
        finalizarConexion();
        return lista;
    }

    public Bus obtenerBus(int id_bus) throws SQLException {
        Bus bus = null;

        ResultSet rset;

        String sql = "SELECT id_bus, passwd FROM Otobuses WHERE id_bus = ?";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1, id_bus);
        rset = stmt.executeQuery();
        while (rset.next()) {
            bus = new Bus(rset.getString("id_bus"), rset.getString("passwd"));

        }
        finalizarConexion();
        return bus;

    }

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

    public boolean eliminarCliente(int id_bus) throws SQLException {

        String sql = "DELETE FROM Otobuses WHERE id_bus = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id_bus);

        int res = stmt.executeUpdate();

        return (res == 1);
    }

}
