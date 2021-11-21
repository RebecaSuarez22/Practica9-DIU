/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica9_diu;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnect{
    private final String user, password;
    private Connection con;
    private DatabaseMetaData md;
    private ResultSet rs;
    
    public DBConnect(String user, String password){
        this.user = user;
        this.password = password;
        System.out.println(user + password);
    }
    
    public void connectDB() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://i7-lab5.dis.ulpgc.es/DIU_BD?useSSL=true",
                user,
                password);
        md = con.getMetaData();
    }
    
    public ArrayList<String> getTables() throws SQLException{
        ArrayList<String> tables = new ArrayList<>();
        String[] types = {"TABLE"};
        rs = md.getTables(null, null, "%", types);
        
        while (rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }
    
    public ArrayList<String> getColumns(String[] tables) throws SQLException{
        ArrayList<String> columns = new ArrayList<>();
        for (String table : tables) {
            ResultSet rs2 = md.getColumns(null, null, table, null);
            while (rs2.next()) {
                String column = (table + "." + rs2.getString("COLUMN_NAME"));
                columns.add(column.toString());
            }
        }
        return columns;
    }
    
    public void closeDB() throws SQLException{
        con.close();
    }
}
