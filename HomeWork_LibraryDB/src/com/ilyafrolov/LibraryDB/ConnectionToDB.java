package com.ilyafrolov.LibraryDB;

import java.sql.*;
import java.util.List;

public class ConnectionToDB{
    public static Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static {
        try {
            DataSourceUtil connectionPool = DataSourceUtil.create(URL, USER, PASSWORD);
            con = connectionPool.getConnection();
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
