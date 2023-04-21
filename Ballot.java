package SecureVotingJava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;  

public class Ballot extends JFrame implements ActionListener {
    private JLabel candidateLabel;
    private JButton voteButton;
    private JRadioButton candidate1, candidate2;
    private String username;
    
    public Ballot(String newUsername) {
        // Create GUI Components
        candidateLabel = new JLabel("Select your preferred candidate:");
        voteButton = new JButton("Vote");
        candidate1 = new JRadioButton("Candidate 1");
        candidate2 = new JRadioButton("Candidate 2");
        username = newUsername;

        // Add radio buttons to a group to ensure only one can be selected at a time
        ButtonGroup candidates = new ButtonGroup();
        candidates.add(candidate1);
        candidates.add(candidate2);

        // Set layout and add components to the frame
        setLayout(new GridLayout(5, 1));
        add(candidateLabel);
        add(candidate1);
        add(candidate2);
        add(voteButton);

        // Add action listener to vote button
        voteButton.addActionListener(this);

        // Set frame properties
        setTitle("Voting Sysyem Ballot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == voteButton) {
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
                recordVote("Candidate 1", username);
            } else if(candidate2Selected) {
                recordVote("Candidate 2", username);
            }
            JOptionPane.showMessageDialog(this, "Your vote has been recorded.");
            
            // Disable voting button after user has voted
            voteButton.setEnabled(false);
            dispose(); // Closes Voting GUI
        }
    }
    private void recordVote(String candidate, String username) {
        // TODO create vote hash
        try {
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/voting",
                "root", "root");

            PreparedStatement st = (PreparedStatement) connection
                .prepareStatement("INSERT INTO record_vote (username, candidate) VALUES (username=?, candidate=?);");

            st.setString(1, username);
            st.setString(2, candidate);

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
        return;
    }
}