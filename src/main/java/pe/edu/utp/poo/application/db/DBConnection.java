package pe.edu.utp.poo.application.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static pe.edu.utp.poo.application.common.Constant.SHORT_LOCALDATETIME_FORMAT;
import pe.edu.utp.poo.application.common.Util;

/**
AWS DB
url: utp-student-db.cuolbk5i0mmv.us-east-1.rds.amazonaws.com
port: 1433
instance: utp-student-db
superadmin: utpadmin
pass: Utp2024&.
 * @author manuelguarniz
 */
public class DBConnection {
    private final String url = "jdbc:sqlserver://%s:%s;encrypt=false;databaseName=%s;user=%s;password=%s";
//    private final String url = "jdbc:sqlserver://%s:%s;encrypt=true;databaseName=%s;user=%s;password=%s";
    private PreparedStatement statement;
    private String query;
    private Connection db;

    private static DBConnection instance;
    public DBConnection() {
    }
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    public DBConnection connect() throws SQLException {
        String urlConnect = String.format(url,
            "db-utp-labs.cuolbk5i0mmv.us-east-1.rds.amazonaws.com",
            1433,
            "db_cine",
            "utpadmin",
            "Utp2024&.");
        this.db = DriverManager.getConnection(urlConnect);
        return instance;
    }
    
    public DBConnection query(String query) throws SQLException {
        this.query = query;
        statement = db.prepareCall(query);
        return instance;
    }
    
    public DBConnection params(Object... params) throws SQLException {
        if (this.query.contains("?")) {
            int index = 0;
            for (Object param : params) {
                index++;
                switch (param.getClass().getName()) {
                    case "java.lang.Integer" -> statement.setInt(index, Integer.parseInt(String.valueOf(param)));
                    case "java.lang.Double" -> statement.setDouble(index, Double.parseDouble(String.valueOf(param)));
                    case "java.time.LocalDateTime" -> statement.setString(index, Util.dateToString((LocalDateTime) param));
                    case "java.time.LocalDate" -> statement.setString(index, Util.dateToString((LocalDateTime) param, SHORT_LOCALDATETIME_FORMAT));
                    default -> statement.setString(index, (String) param);
                }
            }
        }
        return instance;
    }
    
    public ArrayList<Map<String, Object>> get() throws SQLException {
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();
        System.out.println("query: " + statement);
        
        ResultSetMetaData metadata = resultSet.getMetaData();
        
        int countColumns = metadata.getColumnCount();
        String[] columnas = new String[countColumns];
        
        for (int index = 0; index < countColumns; index++) {
            columnas[index] = metadata.getColumnName(index + 1);
        }

        while (resultSet.next()) {
            Map<String, Object> fila = new HashMap<>();
                        
            for (int index = 0; index < countColumns; index++) {
                fila.put(columnas[index], resultSet.getObject(index + 1));
            }
            data.add(fila);
        }
        return data;
    }
    
    public Integer save() throws SQLException {
        int count = statement.executeUpdate();
        return count;
    }
}
