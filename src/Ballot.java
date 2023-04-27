import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ballot extends JFrame implements ActionListener {
    
    private final JLabel candidateLabel;
    private final JButton voteButton;
    private final JButton closeBtn;
    private final JRadioButton candidate1;
    private final JRadioButton candidate2;
    private final String username;
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/voting";
    static final String USER = "root";
    static final String PASS = "";

    public Ballot(String newUsername) {
        // Create GUI Components
        candidateLabel = new JLabel("Select your preferred candidate:");
        voteButton = new JButton("Vote");
        closeBtn = new JButton("Close");
        candidate1 = new JRadioButton("Candidate 1");
        candidate2 = new JRadioButton("Candidate 2");
        username = newUsername;
    }

    @Override
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
                try {
                    recordVote("Candidate 1", username);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Ballot.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (candidate2Selected) {
                try {
                    recordVote("Candidate 2", username);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Ballot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            JOptionPane.showMessageDialog(this, "Your vote has been recorded.");

            // Disable voting button after user has voted
            voteButton.setEnabled(false);
            dispose(); // Closes Voting GUI
        }
    }

    private String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }

    private void recordVote(String candidate, String username) throws NoSuchAlgorithmException {
        String hash;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String input = username + candidate + Instant.now().getEpochSecond();
        boolean writeSuccessful = true;

        byte[] messageDigest = md.digest(input.getBytes());
        hash = convertToHex(messageDigest);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {
            // Execute a query
            System.out.println("Inserting records into the table...");
            String sql = "INSERT INTO record_vote(username, candidate) VALUES (" + "'" + username + "'" + ", " + "'" + candidate + "'" + ");";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            writeSuccessful = false;
            JOptionPane.showMessageDialog(this, "Error: \n" + e);
        }

        if (writeSuccessful) {
            String message = "Your vote hash is: " + hash;
            JOptionPane.showMessageDialog(closeBtn, message);
            dispose();
        } else {
            JOptionPane.showMessageDialog(closeBtn, "Vote Unsuccessful");
        }
    }

    public void showBallot() {
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
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
