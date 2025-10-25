package pe.edu.utp.poo.application.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private final static String host = "db-utp-labs.cuolbk5i0mmv.us-east-1.rds.amazonaws.com";
    private final static String database = "db_cine";
    private final static String url = "jdbc:sqlserver://" + host + ":1433;encrypt=false;databaseName=" + database;
    private final static String user = "utpadmin";
    private final static String pass = "Utp2024&.";

    public static Connection getInstance() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
