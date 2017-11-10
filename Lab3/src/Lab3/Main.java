package Lab3;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame {

    private JPanel panelMain;

    private static JTextField loginField;
    private static JPasswordField passwordField;
    private static JLabel loginLabel;
    private static JLabel passwordLabel;
    private static JButton okButton;
    private static JButton cancelButton;
    private static JLabel okImage;
    private static JLabel wrongImage;

    private static Map<String, String> loginMap;

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->{
            setupDummyLoginVec();
            setUpView();
        });
    }

    private Main() {
        panelMain = new JPanel();
        loginField = new JTextField(10);
        passwordField = new JPasswordField(12);
        loginLabel = new JLabel("Login:");
        passwordLabel = new JLabel("Password: ");
        okButton = new JButton("Log in");
        cancelButton = new JButton("cancel");

        ImageIcon imageIcon = new ImageIcon("tick.png");
        okImage = new JLabel(imageIcon);

        ImageIcon imageIcon2 = new ImageIcon("close.png");
        wrongImage = new JLabel(imageIcon2);

        okButton.addActionListener(actionEvent -> {
            if(login()) {
                okImage.setVisible(true);
                wrongImage.setVisible(false);
                new PaintCanvas().run();
            }
            else {
                okImage.setVisible(false);
                wrongImage.setVisible(true);
            }
        });

        cancelButton.addActionListener(actionEvent -> System.exit(0));
    }

    private static void setUpView() {
        JFrame frame = new JFrame("Logowanie");
        frame.setContentPane(new Main().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(270, 200);
        frame.setLocationRelativeTo(null);

        Container contentPane = frame.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        contentPane.add(loginField);
        contentPane.add(loginLabel);
        contentPane.add(okImage);
        contentPane.add(wrongImage);

        okImage.setVisible(false);
        wrongImage.setVisible(false);

        layout.putConstraint(SpringLayout.WEST, loginLabel, 15, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, loginLabel, 15, SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, loginField, 50, SpringLayout.EAST, loginLabel);
        layout.putConstraint(SpringLayout.NORTH, loginField, 15, SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, okImage, 10, SpringLayout.EAST, loginField);
        layout.putConstraint(SpringLayout.NORTH, loginField, 15, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, wrongImage, 10, SpringLayout.EAST, loginField);
        layout.putConstraint(SpringLayout.NORTH, loginField, 15, SpringLayout.NORTH, contentPane);

        contentPane.add(passwordField);
        contentPane.add(passwordLabel);

        layout.putConstraint(SpringLayout.WEST, passwordLabel, 15, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, passwordLabel, 45, SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, passwordField, 15, SpringLayout.EAST, passwordLabel);
        layout.putConstraint(SpringLayout.NORTH, passwordField, 45, SpringLayout.NORTH, contentPane);

        contentPane.add(okButton);
        contentPane.add(cancelButton);

        layout.putConstraint(SpringLayout.WEST, okButton, 45, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, okButton, 75, SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, cancelButton, 15, SpringLayout.EAST, okButton);
        layout.putConstraint(SpringLayout.NORTH, cancelButton, 75, SpringLayout.NORTH, contentPane);

        frame.setVisible(true);
    }

    private static void setupDummyLoginVec(){
        loginMap = new HashMap();

        loginMap.put("sdsd", "passw1");
        loginMap.put("xdxd", "passw2");
        loginMap.put("user", "password");
    }

    private static boolean login(){

        if (loginField.getText().isEmpty() || passwordField.getPassword().length == 0){
            return false;
        }

        String login = loginField.getText();
        String password = new String(passwordField.getPassword());

        return loginMap.get(login).equals(password);
    }
}
