package com.ilyafrolov.LibraryDB;

import java.sql.*;

public class ConnectionToDB {
        static Connection con;

        static {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "root");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        static Statement postman;
        static {
            try {
                postman = con.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
