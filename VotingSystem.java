package SecureVotingJava;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VotingSystem extends JFrame implements ActionListener {

    private JLabel userLabel, passwordLabel, candidateLabel;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton, voteButton;
    private JRadioButton candidate1, candidate2;

    public VotingSystem() {
        // Create GUI components
        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        candidateLabel = new JLabel("Select your preferred candidate:");
        userField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        voteButton = new JButton("Vote");
        candidate1 = new JRadioButton("Candidate 1");
        candidate2 = new JRadioButton("Candidate 2");
        
        // Add radio buttons to a group to ensure only one can be selected at a time
        ButtonGroup candidates = new ButtonGroup();
        candidates.add(candidate1);
        candidates.add(candidate2);

        // Set layout and add components to the frame
        setLayout(new GridLayout(5, 2));
        add(userLabel);
        add(userField);
        add(passwordLabel);
        add(candidate1);
   
        add(loginButton);

        // Add action listeners to the buttons
        loginButton.addActionListener(this);
        voteButton.addActionListener(this);

        // Set frame properties
        setTitle("Voting System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
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
            // Enable voting button if user is authenticated
            voteButton.setEnabled(true);
        } else if (e.getSource() == voteButton) {
            // Validate user input and record vote
            boolean candidate1Selected = candidate1.isSelected();
            boolean candidate2Selected = candidate2.isSelected();
            // Validate input
            if (!candidate1Selected && !candidate2Selected) {
                JOptionPane.showMessageDialog(this, "Please select a candidate.");
                return;
            }
            // Record vote
            if (candidate1Selected) {
                recordVote("Candidate 1");
            } else {
                recordVote("Candidate 2");
            }
            JOptionPane.showMessageDialog(this, "Your vote has been recorded.");
            // Disable voting button after user has voted
            voteButton.setEnabled(false);
        }
    }
    
    private boolean authenticateUser(String username, char[] password) {
        // TODO: Implement user authentication logic
        return true; // Dummy implementation for demonstration purposes
    }
    
    private void recordVote(String candidate) {
        // TODO: Implement vote recording logic
    }
    
    public static void main(String[] args) {
        new VotingSystem();
    }
}    