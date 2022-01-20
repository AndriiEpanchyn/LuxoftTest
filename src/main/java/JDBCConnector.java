package main.java;


import java.sql.*;

public class JDBCConnector {

    //private final String driverName = "com.mysql.jdbc.Driver";
    private final String driverName = "com.mysql.cj.jdbc.Driver";
    private final String connectionString = "jdbc:mysql://localhost/textstatistics";
    private final String user="root";
    private final String password="111111";
    Connection connection;

    public void insert(String sql) throws SQLException {
        try {
            Class.forName(driverName);
             connection = DriverManager.getConnection(connectionString, user, password);
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public ResultSet select(String sql) throws SQLException {
        ResultSet result=null;
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(connectionString, user, password);
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            result =stmt.executeQuery(sql);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
