/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author EL PAPITO
 */
public class Bus {
    
    private String pass;
    private String id_bus;
      
    public Bus(String pass, String id_bus) {
        this.pass = pass;
        this.id_bus = id_bus;
    }

    public Bus() {
    }

    public String getIdBus() {
        return id_bus;
    }

    public String getPass() {
        return pass;
    }
    
}
