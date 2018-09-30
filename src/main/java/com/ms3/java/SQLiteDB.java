package com.ms3.java;

import java.sql.*;
import java.util.ArrayList;
import java.io.File;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB {
    private String dbName = null;
    private String tableName = null;
    private DBean header;
    private File dbPath;

    /**Default constructor*/
    public SQLiteDB(){}

    /**Single Arg constructor for database*/
    public SQLiteDB(String dbName) {
        this.dbName = dbName;
        this.dbPath = new File(dbName + ".db");
        String url = "jdbc:sqlite:" + dbName;


        //Connect to database and Create new db if null
        if(!dbPath.exists()) {
            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("Connected to Database: " + this.dbName);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**Create table in defined database using header object*/
    public void setTable(String tableName, DBean header) throws Exception {
        this.tableName = tableName;
        this.header = header;

        // SQLite connection string
        String url = "jdbc:sqlite:" + dbName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS "+ tableName + "(\n"
                + header.getA() +" NOT NULL PRIMARY KEY,\n"
                + header.getB() + " NOT NULL,\n"
                + header.getC() + " NOT NULL,\n"
                + header.getD() + " NOT NULL,\n"
                + header.getE() + " NOT NULL,\n"
                + header.getF() + " NOT NULL,\n"
                + header.getG() + " NOT NULL,\n"
                + header.getH() + " NOT NULL,\n"
                + header.getI() + " NOT NULL,\n"
                + header.getJ() + " NOT NULL );";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropTable(String tableName){
        // SQLite connection string
        String url = "jdbc:sqlite:" + dbName;

        //SQL statement for dropping table
        String sql = "DROP TABLE IF EXISTS " + tableName;

        //attempt to drop table
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("Table " + tableName + " dropped.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**Create table in defined database using header object*/
    public void setDBData(ArrayList<DBean> dataIn) throws Exception {

        // SQLite connection string
        String url = "jdbc:sqlite:" + this.dbName;

        // SQL statement for inserting DBean object
        String sql = "INSERT INTO " + this.tableName + " VALUES(?,?,?,?,?,?,?,?,?,?)";

        //Insert data using preparedStatement
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             for(DBean data : dataIn){
                // insert values into table
                pstmt.setString(1, data.getA());
                pstmt.setString(2, data.getB());
                pstmt.setString(3, data.getC());
                pstmt.setString(4, data.getD());
                pstmt.setString(5, data.getE());
                pstmt.setString(6, data.getF());
                pstmt.setString(7, data.getG());
                pstmt.setString(8, data.getH());
                pstmt.setString(9, data.getI());
                pstmt.setString(10, data.getJ());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getDBData(String tableName) throws Exception {
        this.tableName = tableName;

        // SQLite connection string
        String url = "jdbc:sqlite:" + this.dbName;

        // SQL statement for inserting DBean object
        String sql = "SELECT * FROM " + this.tableName;

        //attempt to drop table
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

                //loop through result set
                while(rs.next()){
                    System.out.println(rs.getString(1) + " | " +
                            rs.getString(2) + " | " +
                            rs.getString(3) + " | " +
                            rs.getString(4) + " | " +
                            rs.getString(5) + " | " +
                            rs.getString(6) + " | " +
                            rs.getString(7) + " | " +
                            rs.getString(8) + " | " +
                            rs.getString(9) + " | " +
                            rs.getString(10)
                            );
                }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
