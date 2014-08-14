/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Brownie
 */
import java.sql.*;
import java.util.Properties;
import javax.swing.JOptionPane;

public class MyConnectionSybase {

    public String user, pass;
//    public String db = "desarrollo";
//    public String url = "jdbc:sybase:Tds://kanki.uacam.mx:6100/"+db;
    public String db = "beta";
    public String url = "jdbc:sybase:Tds://kanki.uacam.mx:6200/"+db;
    Connection conn = null;
    
    public MyConnectionSybase(){
        
    }
    
    public Connection Conectar(String user, String pass){
        try {
        // *****************************************************************
        // En las siguientes líneas remueva el comentario de la línea
        // 'Class.forName' según la versión de jConnect que vaya a usar:
        // *****************************************************************

        // Para usar jConnect 4.2:
        //Class.forName("com.sybase.jdbc.SybDriver");

        // Para usar jConnect 5.2:
        //Class.forName("com.sybase.jdbc2.jdbc.SybDriver");

        // Para usar jConnect 6.0:
        Class.forName("com.sybase.jdbc3.jdbc.SybDriver");

        // *****************************************************************
        // En las siguientes líneas se definen las propiedades de la
        // conexión. Reemplace:
        // <user>: Usuario en la base de datos
        // <passwd>: Contraseña
        // Puede incluir otras propiedades, como CHARSET, HOSTNAME,
        // APPLICATIONNAME, etc.
        // *****************************************************************
        Properties props = new Properties();
        props.put("user", user);
        props.put("password", pass);

        // *****************************************************************
        // En la siguiente línea se define el URL (dirección) del servidor
        // de base de datos. Reemplace:
        // <host>: Dirección IP o nombre de la máquina donde corre la base de datos
        // <port>: Puerto del servidor de base de datos
        // *****************************************************************
//        String url = "jdbc:sybase:Tds:kanki.uacam.mx:6100";
        String url = "jdbc:sybase:Tds:kanki.uacam.mx:6200";
        // *****************************************************************
        // En la siguiente línea se establece la conexión, usando la
        // dirección y propiedades previamente definidas:
        // *****************************************************************
        conn = DriverManager.getConnection(url, props);

        // *****************************************************************
        // En las siguientes líneas se crea y ejecuta la sentencia SQL. La
        // ejecución crea también un conjunto resultado.
        // *****************************************************************
        // *****************************************************************
        // En las siguientes líneas se procesa el conjunto resultado, fila
        // por fila:
        // *****************************************************************
        // *****************************************************************
        // Finalmente, se liberan los recursos y se cierra la conexión:
        // *****************************************************************
    } catch ( Exception e ) {
        JOptionPane.showMessageDialog(null,"Usuario y/o contraseña incorrectos");
        e.printStackTrace();
    }
        return conn;
    }
    

    
    
    public static void main( String[] args ) {

    
    } // main

} // MyConnection class

