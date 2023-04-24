/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.votingapp;

/**
 *
 * @author Noah
 */
import static com.mycompany.votingapp.createAccount.DB_URL;
import static com.mycompany.votingapp.createAccount.PASS;
import static com.mycompany.votingapp.createAccount.USER;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    private final JLabel userLabel;
    private final JLabel passwordLabel;
    private final JTextField userField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton closeBtn;

    public Login() {
        // Create GUI components
        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        userField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        closeBtn = new JButton("Close");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == loginButton) {
            // Validate user input and authenticate user
            String username = userField.getText();
            String password = passwordField.getText();

            // Validate input
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a username and password.");
                return;
            }
            // Create user
            authenticateUser(username, password);
            dispose();
        }
    }

    private boolean validateLogin(String username, String password) {
        boolean loginValidated;
        loginValidated = Pattern.matches("^[a-zA-Z0-9]{8,}$", password) && Pattern.matches("^[a-zA-Z0-9]{1,20}$", username);
        return loginValidated;
    }

    private void authenticateUser(String username, String password) {
        boolean loginValidated = validateLogin(username, password);
        boolean readSuccessful = true;

        if (loginValidated) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {
                // Execute a query
                System.out.println("Reading records from the table...");
                String sql = "SELECT username, password FROM user_accounts WHERE username=" + "'" + username + "'" + "AND password=" + "'" + password + "';";
                stmt.executeQuery(sql);
            } catch (SQLException e) {
                readSuccessful = false;
                JOptionPane.showMessageDialog(this, "Error: \n" + e);
            }

            if (readSuccessful) {
                dispose();
                Ballot ballot = new Ballot(username);
                ballot.showBallot();
            } else {
                JOptionPane.showMessageDialog(closeBtn, "Username or password incorrect");
            }

        } else {
            JOptionPane.showMessageDialog(closeBtn, "Please enter a 8 character alphanumeric password!");
        }
    }

    public void showLogin() {
        // Set layout and add components to the frame
        setLayout(new GridLayout(5, 2));
        add(userLabel);
        add(userField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);

        // Add action listeners to the login button
        loginButton.addActionListener(this);

        // Set frame properties
        setTitle("Voting System Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
