import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Elena Roncolino
 * Class that sets up the panel where the user can enter the username and password
 */

public class LoginPanel extends JPanel {
    private JPanel mainPanel, firstRowPanel, secondRowPanel, thirdRowPanel, fourthRowPanel;
    private JLabel mainLabel, usernameLabel, passwordLabel, newUserLabel, signUpLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton enterButton;

    /**
     * Constructor of the login panel.
     */

    public LoginPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        //Create title panel and its components
        mainLabel = new JLabel("CAR COLLECTION MANAGER");
        mainLabel.setFont(new Font("Arial", Font.BOLD, 40));
        Properties.setColor(mainLabel);

        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.add(mainLabel);

        //Create the panel that hosts the username label and its text field
        firstRowPanel = new JPanel();
        firstRowPanel.setOpaque(false);
        firstRowPanel.setLayout(new BoxLayout(firstRowPanel, BoxLayout.X_AXIS));

        usernameLabel = new JLabel("Username:");
        Properties.setColor(usernameLabel);
        Properties.setFont(usernameLabel);

        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(250, 30));

        firstRowPanel.add(usernameLabel);
        firstRowPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        firstRowPanel.add(usernameField);

        //Create the panel that hosts the password label and its text field
        secondRowPanel = new JPanel();
        secondRowPanel.setOpaque(false);
        secondRowPanel.setLayout(new BoxLayout(secondRowPanel, BoxLayout.X_AXIS));

        passwordLabel = new JLabel("Password:");
        Properties.setColor(passwordLabel);
        Properties.setFont(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(250, 30));

        secondRowPanel.add(passwordLabel);
        secondRowPanel.add(Box.createRigidArea(new Dimension(28, 0)));
        secondRowPanel.add(passwordField);

        //Create the panel that hosts the new user sign up option
        thirdRowPanel = new JPanel();
        thirdRowPanel.setOpaque(false);
        thirdRowPanel.setLayout(new BoxLayout(thirdRowPanel, BoxLayout.X_AXIS));

        newUserLabel = new JLabel("Are you a new user? ");
        Properties.setColor(newUserLabel);
        newUserLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        signUpLabel = new JLabel("Sign up!");
        Properties.setColor(signUpLabel);
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 20));

        signUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object[] options = {"Sign up", "Cancel"};
                UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Arial",Font.PLAIN,20)));
                int result = JOptionPane.showOptionDialog(null,
                        new SignUpPanel(), "Sign up", JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, options, null);

                //If the user wants to sign up, then the correctness of the password and of th eusername
                if (result == JOptionPane.YES_OPTION){
                    boolean canAddUser = LogInCheck.checkDataCorrectness(SignUpPanel.getUsername(), SignUpPanel.getPassword(), SignUpPanel.getConfirmedPassword());
                    if (canAddUser) {
                        User.registerUser(SignUpPanel.getUsername(), SignUpPanel.getPassword(), SignUpPanel.getImageFile());
                    }
                }
            }
        });

        thirdRowPanel.add(newUserLabel);
        thirdRowPanel.add(signUpLabel);

        //Create the panel that hosts the enter button
        fourthRowPanel = new JPanel();
        fourthRowPanel.setOpaque(false);

        enterButton = new JButton("Enter");
        enterButton.setFont(new Font("Arial", Font.PLAIN, 24));
        enterButton.setEnabled(false);
        enterButton.addActionListener(new enterListener());
        enterButton.setPreferredSize(new Dimension(150, 35));

        fourthRowPanel.add(enterButton);

        //Check if both the username and the password fields are not empty before enabling the enter button
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (usernameField.getText().length() == 0 || passwordField.getPassword().length == 0)
                    enterButton.setEnabled(false);
                else
                    enterButton.setEnabled(true);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (usernameField.getText().length() == 0 || passwordField.getPassword().length == 0)
                    enterButton.setEnabled(false);
                else
                    enterButton.setEnabled(true);
            }
        });

        //Add all to loginPanel
        add(Box.createRigidArea(new Dimension(0, 100)));
        add(mainPanel);
        add(Box.createRigidArea(new Dimension(0, 100)));
        add(firstRowPanel);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(secondRowPanel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(thirdRowPanel);
        add(Box.createRigidArea(new Dimension(0, 70)));
        add(fourthRowPanel);
    }

    /**
     * Private class that implements the listener for the enter button.
     */
    private class enterListener implements ActionListener {

        /**
         * Method that overrides the default one and calls another method to check if the inserted username and password are correct.
         *
         * @param e The event that occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            LogInCheck.checkUserExists(usernameField.getText(), new String(passwordField.getPassword()));
        }
    }
}
