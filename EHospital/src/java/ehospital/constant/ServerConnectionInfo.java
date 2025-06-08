/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.constant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class ServerConnectionInfo {

    public static final String HOSTNAME = "212.85.25.175";
    public static final String PORT = "1433";
    public static final String DBNAME = "EHospital";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "YourStrongPassw0rd";
    public static final String CLASS_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String CONNECTION_URL = "jdbc:sqlserver://" + HOSTNAME + ":" + PORT + ";"
            + "databaseName=" + DBNAME + ";encrypt=true;trustServerCertificate=true;";
    public static final Connection CONNECTION = getConnection();

    public static Connection getConnection() {
        try {
            Class.forName(CLASS_DRIVER);
            return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
