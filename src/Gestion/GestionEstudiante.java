
package Gestion;

import Datos.Conexion;
import Datos.Estudiante;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GestionEstudiante {

    private Conexion conex = new Conexion();

    public DefaultComboBoxModel consultarEstudiante() {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        DefaultComboBoxModel dcmEstudiante = new DefaultComboBoxModel();
        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM tblestudiante");
            rs = ps.executeQuery();
            while (rs.next()) {//se mueve de registro en registro
                dcmEstudiante.addElement(rs.getString("nombre_est")+"_"+rs.getString("cedula_est"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en  la sentencia SELECT: " + ex.getMessage());
        }
        return dcmEstudiante;
    }
    public void insertarEstudiante(Estudiante est) {
        PreparedStatement ps = null;
        Connection con = conex.conectar();

        try {
            ps = (PreparedStatement) conex.conectar().prepareStatement("INSERT INTO"
                    + " tblestudiante(cedula_est,nombre_est,direccion_est,telefono_est) VALUES (?,?,?,?)");
            ps.setString(1, est.getCedula());
            ps.setString(2, est.getNombre());
            ps.setString(3, est.getDireccion());
            ps.setString(4, est.getTelefono());


            ps.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia Insert" + ex);

        }

    }
    
     public Estudiante consultarEstudiante(String cedula) {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;//hace consulta y se guarda aqui
        Estudiante est = null;
        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM tblestudiante WHERE cedula_est=?");
            ps.setString(1, cedula);
            rs = ps.executeQuery();//ejecute la consulta
            while (rs.next()) {
                est = new Estudiante (rs.getString("cedula_est"),
                        rs.getString("nombre_est"),
                        rs.getString("direccion_est"),
                        rs.getString("telefono_est"));
                       
            }
        } catch (SQLException exSql) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia "
                    + "SELECT: " + exSql.getMessage());
        }
        return est;
    }

    public boolean eliminarUsuario( String cedula) {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        boolean op = false;
        try {
            ps = (PreparedStatement) con.prepareStatement("DELETE  FROM tblestudiante WHERE cedula_est=?");
            ps.setString(1, cedula);
            ps.executeUpdate();//actualizacion de datos 
            op=true; 
            
        } catch (SQLException exSql) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia "
                    + "DELETE: " + exSql.getMessage());
        }
        return op;

    }
    
     public boolean modificarUsuario(Estudiante estTmp) {
              PreparedStatement ps = null;
        Connection con = conex.conectar();

        try {
            ps = (PreparedStatement) conex.conectar().prepareStatement("UPDATE tblestudiante SET nombre_est=?, direccion_est=? ,telefono_est=?"
                    + "WHERE cedula_est=?");
 
            ps.setString(1, estTmp.getNombre());
            ps.setString(2, estTmp.getDireccion());
            ps.setString(3, estTmp.getTelefono());
            ps.setString(4, estTmp.getCedula());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario Actualizado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la clausula WHERE" + ex);
        }
        return false;
     }
     
     public DefaultTableModel listarEstudiante () {
        PreparedStatement ps = null;
        Connection con = conex.conectar();
        ResultSet rs = null;
        Estudiante usr = null; 
        DefaultTableModel tblEstudiante = new DefaultTableModel(); 
        tblEstudiante.addColumn("Cedula:");
        tblEstudiante.addColumn("Nombre:");
        tblEstudiante.addColumn("Direccion:");
        tblEstudiante.addColumn("Telefono:");

        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM tblestudiante");
            
            rs = ps.executeQuery();
            while (rs.next()) {
              String[] fila = { rs.getString("cedula_est"),rs.getString("nombre_est"),rs.getString("direccion_est"),rs.getString("telefono_est")};
              tblEstudiante.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql: " + ex);
    }
        return tblEstudiante;
}
}

