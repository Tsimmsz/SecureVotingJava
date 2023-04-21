package SecureVotingJava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {

    private JLabel userLabel, passwordLabel;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login() {
        // Create GUI components
        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        userField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

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

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == loginButton) {
            // Validate user input and authenticate user
            String username = userField.getText();
            char[] password = passwordField.getPassword();
            
            // Validate input
            if (username.isEmpty() || password.length == 0) {
                JOptionPane.showMessageDialog(this, "Please enter a username and password.");
                return;
            }
            // Authenticate user
            boolean authenticated = authenticateUser(username, password);
            if (!authenticated) {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
                return;
            }
            // Create Ballot
            Ballot ballot = new Ballot();
        }
    }

    private void authenticateUser(String username, char[] password) {
        Boolean validated;

        if (Pattern.matches("^[a-zA-Z0-9]{12,}$", password) && Pattern.matches("^[a-zA-Z0-9]{12,}$", username)){
            validated = true;
        } else {
            validated = false;
        }

        if (validated){
            try {
                Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/voting",
                    "root", "root");

                PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("SELECT username, password FROM user_accounts WHERE username=? AND password=?);");

                st.setString(1, username);
                st.setString(2, password);

                ResultSet rs = st.executeQuery();
                
                if (rs.next()) {
                    dispose();
                    Ballot ballot = new Ballot();
                    login.setTitle("Voting App");
                    login.setVisible(true);
                    JOptionPane.showMessageDialog(btnNewButton, "Login Successful");
                } else {
                    JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
                }   
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(btnNewButton, "Please enter a 12 character alphanumeric password!");
        }
    } 
}
