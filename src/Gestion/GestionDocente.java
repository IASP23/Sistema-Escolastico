
package Gestion;

import Datos.Conexion;
import Datos.Docente;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GestionDocente {
    
    private Conexion conex = new Conexion();

   
    public void insertarDocente (Docente doc) {
        PreparedStatement ps = null;
        Connection con = conex.conectar();

        try {
            ps = (PreparedStatement) conex.conectar().prepareStatement("INSERT INTO tbldocente(cedula_dct,nombre_dct,direccion_dct,telefono_dct) VALUES (?,?,?,?)");
            ps.setString(1, doc.getCedula());
            ps.setString(2, doc.getNombre());
            ps.setString(3, doc.getDireccion());
            ps.setString(4, doc.getTelefono());


            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Guardado Exitosamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia Insert" + ex);

        }

    }
    
     public Docente consultarDocente(String cedula) {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;//hace consulta y se guarda aqui
        Docente dct = null;
        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM tbldocente WHERE cedula_dct=?");
            ps.setString(1, cedula);
            rs = ps.executeQuery();//ejecute la consulta
            while (rs.next()) {
                dct = new Docente (rs.getString("cedula_dct"),
                        rs.getString("nombre_dct"),
                        rs.getString("direccion_dct"),
                        rs.getString("telefono_dct"));
                       
            }
        } catch (SQLException exSql) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia "
                    + "SELECT: " + exSql.getMessage());
        }
        return dct;
    }

    public boolean eliminarDocente( String cedula) {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        boolean op = false;
        try {
            ps = (PreparedStatement) con.prepareStatement("DELETE  FROM tbldocente WHERE cedula_dct=?");
            ps.setString(1, cedula);
            ps.executeUpdate();//actualizacion de datos 
            op=true; 
            
        } catch (SQLException exSql) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia "
                    + "DELETE: " + exSql.getMessage());
        }
        return op;

    }
    
     public boolean modificarUsuario(Docente dctTmp) {
              PreparedStatement ps = null;
        Connection con = conex.conectar();

        try {
            ps = (PreparedStatement) conex.conectar().prepareStatement("UPDATE tbldocente SET nombre_dct=?, direccion_dct=? ,telefono_dct=?"
                    + "WHERE cedula_dct=?");
 
            ps.setString(1, dctTmp.getNombre());
            ps.setString(2, dctTmp.getDireccion());
            ps.setString(3, dctTmp.getTelefono());
            ps.setString(4, dctTmp.getCedula());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Docente Actualizado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la clausula WHERE" + ex);
        }
        return false;
     }
     
      public DefaultTableModel listarDocente () {
        PreparedStatement ps = null;
        Connection con = conex.conectar();
        ResultSet rs = null;
        Docente usr = null; 
        DefaultTableModel tblDocente = new DefaultTableModel(); 
        tblDocente.addColumn("Cedula:");
        tblDocente.addColumn("Nombre:");
        tblDocente.addColumn("Direccion:");
        tblDocente.addColumn("Telefono:");

        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT * FROM tbldocente");
            
            rs = ps.executeQuery();
            while (rs.next()) {
              String[] fila = { rs.getString("cedula_dct"),rs.getString("nombre_dct"),rs.getString("direccion_dct"),rs.getString("telefono_dct")};
              tblDocente.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error sql: " + ex);
    }
        return tblDocente;
}
}

    
