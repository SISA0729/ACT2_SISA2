package com.example.act2_sisa2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;

public class HelloController {

    @FXML
    private TextField usuario;

    @FXML
    private PasswordField contraseña;

    @FXML
    private Label mensaje;

    public HelloController() {
        crearUsuarioDAM2();
        crearBaseDeDatos();
    }

    @FXML
    protected void onLoginButtonClick() {
        String user = usuario.getText();
        String password = contraseña.getText();

        if (validarLogin(user, password)) {
            mensaje.setText("HAS ACCEDIDO A LA BASE DE DATOS");
        } else {
            mensaje.setText("USUARIO O CONTRASEÑA INCORRECTOS");
        }
    }

    private boolean validarLogin(String user, String password) {
        String url = "jdbc:mysql://localhost:3306/login_db";
        String dbUser = "DAM2";  // Cambiado a DAM2
        String dbPassword = "DAM2";  // Cambiado a DAM2

        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);

            java.sql.ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            mensaje.setText("ERROR DE CONEXIÓN A LA BASE DE DATOS");
            return false;
        }
    }

    private void crearBaseDeDatos() {
        String url = "jdbc:mysql://localhost:3306/";
        String dbUser = "DAM2";
        String dbPassword = "DAM2";

        String createDBQuery = "CREATE DATABASE IF NOT EXISTS login_db";
        String useDBQuery = "USE login_db";
        String createTableQuery = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "username VARCHAR(50) NOT NULL,"
                + "password VARCHAR(50) NOT NULL)";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createDBQuery);
            statement.executeUpdate(useDBQuery);
            statement.executeUpdate(createTableQuery);

            System.out.println("Base de datos y tabla 'usuarios' creadas exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            mensaje.setText("ERROR AL CREAR LA BASE DE DATOS O LA TABLA");
        }
    }

    // Método para crear el usuario DAM2 y concederle permisos, usando root
    private void crearUsuarioDAM2() {
        String url = "jdbc:mysql://localhost:3306/";
        String dbUser = "root";
        String dbPassword = "";

        String createUserQuery = "CREATE USER IF NOT EXISTS 'DAM2'@'localhost' IDENTIFIED BY 'DAM2'";
        String grantPrivilegesQuery = "GRANT ALL PRIVILEGES ON login_db.* TO 'DAM2'@'localhost'";
        String flushPrivilegesQuery = "FLUSH PRIVILEGES";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createUserQuery);
            statement.executeUpdate(grantPrivilegesQuery);
            statement.executeUpdate(flushPrivilegesQuery);

            System.out.println("Usuario DAM2 creado con éxito y permisos concedidos.");

        } catch (SQLException e) {
            e.printStackTrace();
            mensaje.setText("ERROR AL CREAR EL USUARIO 'DAM2' O CONCEDER PERMISOS");
        }
    }

    public void borrarTablaUsuarios() {
        String url = "jdbc:mysql://localhost:3306/login_db";
        String dbUser = "DAM2";
        String dbPassword = "DAM2";

        String dropTableQuery = "DROP TABLE IF EXISTS usuarios";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(dropTableQuery);
            System.out.println("Tabla 'usuarios' eliminada exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            mensaje.setText("ERROR AL ELIMINAR LA TABLA 'USUARIOS'");
        }
    }
}
