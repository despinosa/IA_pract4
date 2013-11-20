/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jonathan
 */
public class SQLSentencia {
    public static void insertar(String csv) throws FileNotFoundException{
        ArrayList<String> cabeceras= new ArrayList<String>();
        Scanner linea= new Scanner(new File(csv));
        Scanner dato= new Scanner(linea.nextLine());
        Conexion conex = new Conexion();
        int registros=0;
        dato.useDelimiter(",");
        while(dato.hasNext()){
            cabeceras.add(dato.next());
        }
        String nombre=crearTabla(cabeceras);
        String aux_csv=csv.replace("\\", "/");
        String aux_query="LOAD DATA LOCAL INFILE '" + aux_csv + "' INTO TABLE " + nombre + " FIELDS TERMINATED BY ',' LINES TERMINATED BY '\\n' IGNORE 1 LINES";
        try{
            conex.conectar();
            registros=conex.getCon().createStatement().executeUpdate(aux_query);
            conex.getCon().close();
        }catch(Exception e){
            conex.cerrar();
            System.out.println(e);
        }
        //LOAD DATA LOCAL INFILE ‘ruta/fichero.csv‘ INTO TABLE ‘nombre-tabla‘ FIELDS TERMINATED BY ‘,‘ LINES TERMINATED BY ‘\n‘;
        
    }
    public static String crearTabla(ArrayList<String> cabe){
        Conexion conex= new Conexion();
        String nombreTabla="";
        //select count(*) from information_schema.tables where table_schema = 'BD';
        try{
            conex.conectar();
            ResultSet rs=conex.getCon().createStatement().executeQuery("select count(*) from information_schema.tables where table_schema = '"+conex.getDb()+"'");
            while(rs.next()){
                nombreTabla="tabla" + rs.getInt("count(*)") + "(";
            }
            conex.getCon().close();
        }catch(Exception e){
            conex.cerrar();
            System.out.println(e);
        }
        String query="CREATE TABLE " + nombreTabla;
        
        for(int i=0; i<cabe.size(); i++){
            String aux=cabe.get(i) + " varchar(100) null,";
            query+=aux;
        }
        query=query.substring(0, query.length()-1) + ")";
        try{
            conex.conectar();
            conex.getCon().createStatement().executeUpdate(query);
            conex.getCon().close();
        }catch(Exception e){
            conex.cerrar();
            System.out.println(e);
        }
        return nombreTabla.substring(0, nombreTabla.length()-1);
    }
    
    public static ArrayList<String> llenaCombo(){
        ArrayList<String> res= new ArrayList<String>();
        Conexion conex= new Conexion();
        try{
            conex.conectar();
            ResultSet rs=conex.getCon().createStatement().executeQuery("show tables");
            while(rs.next()){
                res.add(rs.getString("Tables_in_"+conex.getDb()));
            }
            conex.getCon().close();
        }catch(Exception e){
            conex.cerrar();
            System.out.println(e);
        }
        return res;
    }
    
    public static DefaultTableModel llenaTabla(String tabla){
        DefaultTableModel res= new DefaultTableModel();
        Conexion conex= new Conexion();
        int filas=0, cont=0, cols=0;
        try{
            conex.conectar();
            ResultSet rs=conex.getCon().createStatement().executeQuery("SELECT * FROM " + tabla);
            ResultSet rsNum=conex.getCon().createStatement().executeQuery("SELECT count(*) FROM " + tabla);
            while(rsNum.next()){
                filas=rsNum.getInt(1);
            }
            ResultSetMetaData rsmd=rs.getMetaData();
            res.setColumnCount(0);
            cols=rsmd.getColumnCount();
            for(int i=1; i<=cols; i++){
                res.addColumn(rsmd.getColumnName(i));
            }
            res.setNumRows(filas);
            while(rs.next()){
                for(int w=1; w<=cols; w++){
                    res.setValueAt(rs.getString(w), cont, w-1);
                }
                cont++;
            }
            conex.getCon().close();
        }catch(Exception e){
            conex.cerrar();
            System.out.println(e);
        }
        return res;
    }
    
    public static Hashtable<String,String[]> tablaActualHash(DefaultTableModel tabla){
        Hashtable<String,String[]> tablaActual=new Hashtable<String, String[]>();
        for(int k=0; k<tabla.getRowCount(); k++){
            String[] aux= new String[tabla.getColumnCount()];
            for(int j=0; j<aux.length; j++){
                aux[j]=tabla.getValueAt(k, j).toString();
            }
            tablaActual.put(String.valueOf(k), aux);
        }
        return tablaActual;
    }
}