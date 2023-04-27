import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JFrame {
    
    private final JButton createAccountBtn;
    private final JButton signInBtn;
    createAccount createAccount = new createAccount();
    Login login = new Login();

    
    public Main(){
        createAccountBtn = new JButton("Create Account");
        signInBtn = new JButton("Sign in");
    }
    
    public void showWelcome(){
        // Set layout and add components to the frame
        setLayout(new GridLayout(5, 2));
        add(createAccountBtn);
        add(signInBtn);

        // Add action listeners to the login button
        createAccountBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount.showCreateAccount();
            }
        });
        
        signInBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                login.showLogin();
            }
        });

        // Set frame properties
        setTitle("Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Main votingApp = new Main();
        votingApp.showWelcome();
    }
}
