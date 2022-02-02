
package Gestion;

import Datos.Asignatura;
import Datos.Conexion;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GestionAsignatura {
    
    private Conexion conex = new Conexion();

    public DefaultComboBoxModel consultarAsignatura() {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        DefaultComboBoxModel dcmAsignaturas = new DefaultComboBoxModel();
        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM tblasignaturas");
            rs = ps.executeQuery();
            while (rs.next()) {//se mueve de registro en registro
                dcmAsignaturas.addElement(rs.getString("nombre_asi")+"_"+rs.getString("codigo_asi"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en  la sentencia SELECT: " + ex.getMessage());
        }
        return dcmAsignaturas;
    }
    

    public void insertarAsignatura(Asignatura asg) {
        PreparedStatement ps = null;
        Connection con = conex.conectar();

        try {
            ps = (PreparedStatement) conex.conectar().prepareStatement("INSERT INTO tblasignaturas(codigo_asi,nombre_asi,descripcion_asi) VALUES(?,?,?)");

            ps.setString(1, asg.getCodigo());
            ps.setString(2, asg.getNombre());
            ps.setString(3, asg.getDescripcion());
        
            ps.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia XD" + ex);

        }

    }
 
    public Asignatura consultarAsiganatura(String nombre) {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;//hace consulta y se guarda aqui
        Asignatura asi = null;
        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM tblasignaturas WHERE nombre_asi=?");
            ps.setString(1, nombre);//# de interrogantes       
            rs = ps.executeQuery();//ejecute la consulta
            while (rs.next()) {
                asi = new Asignatura(rs.getString("codigo_asi"),
                        rs.getString("nombre_asi"),
                        rs.getString("descripcion_asi"));
                        
            }
        } catch (SQLException exSql) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia "
                    + "SELECT: " + exSql.getMessage());
        }
        return asi;
    }

    public boolean eliminarAsignatura( String nombre) {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        boolean op = false;
        try {
            ps = (PreparedStatement) con.prepareStatement("DELETE  FROM tblasignaturas WHERE nombre_asi=?");
            ps.setString(1, nombre);
            ps.executeUpdate();//actualizacion de datos 
            op=true; 
            
        } catch (SQLException exSql) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia "
                    + "DELETE: " + exSql.getMessage());
        }
        return op;

    }
     public boolean modificarUsuario(Asignatura asgTmp) {
              PreparedStatement ps = null;
        Connection con = conex.conectar();

        try {
            ps = (PreparedStatement) conex.conectar().prepareStatement("UPDATE tblasignaturas SET codigo_asi=?, descripcion_asi=?"
                    + "WHERE nombre_asi=?");
 
            ps.setString(1, asgTmp.getCodigo());
            ps.setString(2, asgTmp.getDescripcion());
            ps.setString(3, asgTmp.getNombre());
            ;

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Asignatura Actualizada");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la clausula WHERE" + ex);
        }
        return false;
     }
     
      public DefaultTableModel listarAsignatura () {
        PreparedStatement ps = null;
        Connection con = conex.conectar();
        ResultSet rs = null;
        Asignatura usr = null; 
        DefaultTableModel tblAsignatura = new DefaultTableModel(); 
        tblAsignatura.addColumn("Codigo:");
        tblAsignatura.addColumn("Nombre:");
        tblAsignatura.addColumn("Descripcion:");
       

        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM tblasignaturas");
            
            rs = ps.executeQuery();
            while (rs.next()) {
              String[] fila = { rs.getString("codigo_asi"),rs.getString("nombre_asi"),rs.getString("descripcion_asi")};
              tblAsignatura.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql: " + ex);
    }
        return tblAsignatura;
}
}
