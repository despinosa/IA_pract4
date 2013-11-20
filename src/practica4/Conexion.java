/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4;

import java.sql.*;

/**
 *
 * @author Jonathan
 */
public class Conexion {
    private Connection con;
    private String db;
    private String user;
    private String pass;
    private String url;
    
    public Conexion(){
        con=null;
        db="ID3_KB";
        user="AI_app";
        pass="11235";
        url="jdbc:mysql://localhost:3306/" + db;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }
    
    public void cerrar(){
        try{
            if(!this.con.isClosed()){
                this.con.close();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            setCon(DriverManager.getConnection(this.url,this.user, this.pass));
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
