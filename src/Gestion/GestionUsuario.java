package Gestion;

import Datos.Conexion;
import Datos.Usuario;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GestionUsuario {

    private Conexion conex = new Conexion();

    public void insertarUsuario(Usuario usr) {
        PreparedStatement ps = null;
        Connection con = conex.conectar();

        try {
            ps = (PreparedStatement) conex.conectar().prepareStatement("INSERT INTO tblusuarios(cedula_usr,nombre_usr,login_usr,password_usr,rol_usr) VALUES (?,?,?,?,?)");
            ps.setString(1, usr.getCedula());
            ps.setString(2, usr.getNombre());
            ps.setString(3, usr.getLogin());
            ps.setString(4, usr.getPassword());
            ps.setString(5, usr.getRol());

            ps.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia Insert" + ex);
        }
    }
    public boolean verificarUsuario(String login, String password) {
        PreparedStatement ps = null;
        Connection con = conex.conectar();
        ResultSet rs = null;
        int contador = 0;
        boolean encontro;
        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM tblusuarios WHERE login_usr= ?"
                    + " AND password_usr=?");
            ps.setString(1, login);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                contador++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql: " + ex);

        }
        if (contador >= 1) {
            encontro = true;
        } else {
            encontro = false;
        }
        return encontro;
    }

    public Usuario consultarUsuario(String cedula) {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;//hace consulta y se guarda aqui
        Usuario usr = null;
        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM tblusuarios WHERE cedula_usr=?");
            ps.setString(1, cedula);
            rs = ps.executeQuery();//ejecute la consulta
            while (rs.next()) {
                usr = new Usuario(rs.getString("cedula_usr"),
                        rs.getString("nombre_usr"),
                        rs.getString("login_usr"),
                        rs.getString("password_usr"),
                        rs.getString("rol_usr"));
            }
        } catch (SQLException exSql) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia "
                    + "SELECT: " + exSql.getMessage());
        }
        return usr;
    }

    public boolean eliminarUsuario( String cedula) {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        boolean op = false;
        try {
            ps = (PreparedStatement) con.prepareStatement("DELETE  FROM tblusuarios WHERE cedula_usr=?");
            ps.setString(1, cedula);
            ps.executeUpdate();//actualizacion de datos 
            op=true; 
            
        } catch (SQLException exSql) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia "
                    + "DELETE: " + exSql.getMessage());
        }
        return op;

    }
    
     public boolean modificarUsuario(Usuario usrTmp) {
              PreparedStatement ps = null;
        Connection con = conex.conectar();

        try {
            ps = (PreparedStatement) conex.conectar().prepareStatement("UPDATE tblusuarios SET "
                    + "nombre_usr=?, login_usr=? ,password_usr=?, rol_usr=?"
                    + "WHERE cedula_usr=?");
 
            ps.setString(1, usrTmp.getNombre());
            ps.setString(2, usrTmp.getLogin());
            ps.setString(3, usrTmp.getPassword());
            ps.setString(4, usrTmp.getRol());
            ps.setString(5, usrTmp.getCedula());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario Actualizado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la clausula WHERE" + ex);
        }
        return false;
     }
     public DefaultTableModel listarUsuario () {
        PreparedStatement ps = null;
        Connection con = conex.conectar();
        ResultSet rs = null;
        Usuario usr = null; 
        DefaultTableModel dtnUsuario = new DefaultTableModel(); 
        dtnUsuario.addColumn("Cedula:");
        dtnUsuario.addColumn("Nombre:");
        dtnUsuario.addColumn("Login:");
        dtnUsuario.addColumn("Password:");
        dtnUsuario.addColumn("Rol:");
        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM tblusuarios");
            
            rs = ps.executeQuery();
            while (rs.next()) {
              String[] fila = { rs.getString("cedula_usr"),rs.getString("nombre_usr"),rs.getString("login_usr"),rs.getString("password_usr"),rs.getString("rol_usr")};
              dtnUsuario.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql: " + ex);

        }
        return dtnUsuario;
    
    }

}