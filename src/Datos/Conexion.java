
package Datos;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    private Connection conex = null;
    //Habilitar el driver o libreria de jdbc 
    public Connection conectar() { // returnar la coneccion 
        try {
            //ERRORClass.forName("com.mysql.JAVA.Driver");  jdbc=driver
            Class.forName("com.mysql.jdbc.Driver"); // pregunta que driver debe conectar
            conex = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/bddescolastigogm", "root", "");
            // coneccion a la bdd mysqql 1. bdd 2 usuario de dbb 
            //3 pssword de la bdd

//COMO HACE CONEXCCIONES EXTERNAR AL FRAMEWORK DE JAVA DEBMOS CONTROLAR
            //LOS ERROR DE EXCEPCION 
          //  System.out.println("Conexion exitosa");
        } catch (ClassNotFoundException cnex) { // invocamos el objeto que 
            //dice que no encuentra esa clase
            JOptionPane.showMessageDialog(null, "Error en el drive" + cnex.getMessage());//esta algo mal en el drive
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "Error al conectar la BDD " + sqlex.getMessage());
        }
        return conex; // para que no de error
    }
}
