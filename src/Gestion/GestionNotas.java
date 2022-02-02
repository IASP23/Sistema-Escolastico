package Gestion;

import Datos.Conexion;
import Datos.Notas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GestionNotas {

    private Conexion conex = new Conexion();

    public void insertarNotas(Notas not) {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        try {
            
            ps = (PreparedStatement) con.prepareStatement("INSERT INTO tblnotas"
                    + "(cedula_est,codigo_asi,unidad1,unidad2,unidad3) VALUES (?,?,?,?,?)");
            ps.setString(1, not.getCedula());
            ps.setString(2, not.getCodigo());
            ps.setDouble(3, not.getNota1());
            ps.setDouble(4, not.getNota2());
            ps.setDouble(5, not.getNota3());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Nota Registrada Exitosamende");
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia INSERT: " + sqlex);
        }
    }
    
    public boolean modificarUsuario(Notas esNot) {
              PreparedStatement ps = null;
        Connection con = conex.conectar();

        try {
            ps = (PreparedStatement) conex.conectar().prepareStatement("UPDATE tblnotas SET unidad1=?, unidad2=? ,unidad3=?"
                    + "WHERE cedula_est=?");
 
            ps.setDouble(1, esNot.getNota1());
            ps.setDouble(2, esNot.getNota2());
            ps.setDouble(3, esNot.getNota3());


            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario Actualizado");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la clausula WHERE" + ex);
        }
        return false;
     }
    public void modificarNotas (Notas notTmp){
        com.mysql.jdbc.Connection con = conex.conectar();
        java.sql.PreparedStatement ps = null;
        try {
            ps = (java.sql.PreparedStatement) con.prepareStatement("UPDATE tblnotas SET unidad1=?, unidad2=?, unidad3=? WHERE codigo_asi=?");
            ps.setDouble(1, notTmp.getNota1());
            ps.setDouble(2, notTmp.getNota2()); 
            ps.setDouble(3, notTmp.getNota3());
            ps.setString(4,notTmp.getCodigo());
            //ps.setString(5,notTmp.getCodigo());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estudiante Actualizado Exitosamente");
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en la clasula Where" + ex);
        }
    }
    
    public Notas consultarNotas(String cedula){
        Connection con=conex.conectar();
        PreparedStatement ps=null;
        ResultSet rs=null;
        Notas usr=null;
        
        try{
            ps=(PreparedStatement) con.prepareStatement("SELECT * FROM tblnotas WHERE cedula_est=?");
            ps.setString(1, cedula);
            rs=ps.executeQuery();
            while(rs.next()){
                usr=new Notas(rs.getString("cedula_est").trim(),
                        rs.getString("codigo_asi").trim(),
                        rs.getDouble("Unidad1"),
                        rs.getDouble("Unidad2"),
                        rs.getDouble("Unidad3"));           
            }
        }catch(SQLException exqSql){
            JOptionPane.showMessageDialog(null, "Error"+exqSql.getMessage());
        }
        return usr;
    }
        
    public DefaultTableModel consultarNotasPorEstudiante(String cedula) {
        Connection con = conex.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        DefaultTableModel dtmNotas = new DefaultTableModel();
        dtmNotas.addColumn("Asignatura");
        dtmNotas.addColumn("Unidad I");
        dtmNotas.addColumn("Unidad II");
        dtmNotas.addColumn("Unidad III");
        dtmNotas.addColumn("Promedio");
        dtmNotas.addColumn("Observación");
        try {
            ps = (PreparedStatement) con.prepareStatement("SELECT (SELECT t2.nombre_asi FROM tblasignaturas t2 WHERE t2.codigo_asi=t1.codigo_asi) as nombre_asi, t1.unidad1, t1.unidad2, t1.unidad3 FROM tblnotas t1 WHERE t1.cedula_est=?");
            ps.setString(1, cedula);
            rs = ps.executeQuery();
            while (rs.next()) {
                float promedio = (rs.getFloat("unidad1") + rs.getFloat("unidad2") + rs.getFloat("unidad3")) / 3;
                String obs = "";
                if (promedio >= 14) {
                    obs = "Aprueba";
                } else {
                    obs = "Reprueba";
                }
                String[] fila = {rs.getString("nombre_asi"), "" + rs.getFloat("unidad1"), "" + rs.getFloat("unidad2"), "" + rs.getFloat("unidad3"), "" + promedio, obs};
                dtmNotas.addRow(fila);
            }
        } catch (SQLException exSql) {
            JOptionPane.showMessageDialog(null, "Error en la sentencia SELECT: " + exSql.getMessage());
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex);
            }
        }
        return dtmNotas;
    }
}