/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkpridwan;

import java.sql.*;

public class Koneksi {
  	public static void Koneksi()  {
  	}
  	
	public Connection bukaKoneksi() throws SQLException  {
        Connection con = null;
     	try {
		    Class.forName("com.mysql.jdbc.Driver");
      	    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_apkinwha","root","");
		    return con;
		}
    	catch (SQLException se) {
      	    System.out.println("No Connection Open");
      	    return null;
    	}
        catch (Exception ex) {
      	   System.out.println("Cound not open connection");
      	   return null;
    	}
  	}
}