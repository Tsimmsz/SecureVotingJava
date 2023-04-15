package SecureVotingJava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ballot extends JFrame implements ActionListener{
    private JLabel candidateLabel;
    private JButton voteButton;
    private JRadioButton candidate1, candidate2;
    
    public Ballot() {
        // Create GUI Components
        candidateLabel = new JLabel("Select your preferred candidate:");
        voteButton = new JButton("Vote");
        candidate1 = new JRadioButton("Candidate 1");
        candidate2 = new JRadioButton("Candidate 2");

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
                recordVote("Candidate 1");
            } else if(candidate2Selected) {
                recordVote("Candidate 2");
            }
            JOptionPane.showMessageDialog(this, "Your vote has been recorded.");
            // Disable voting button after user has voted
            voteButton.setEnabled(false);
        }
    }
    private void recordVote(String candidate) {
        // TODO: Implement vote recording logic
        return;
    }
}