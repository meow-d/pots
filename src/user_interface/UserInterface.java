package user_interface;

import backend.SalesReportGenerator;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import backend.Backend;

public class UserInterface {
    private JFrame frame;
    private Backend backend;
    private CardLayout cardLayout;

    private JPanel mainPanel;
    private Login login;
    private MainMenu mainMenu;

    public void init() {
        initializeSystem();
        initializeUserInterface();
    }

    private void initializeSystem() {
        backend = new Backend();
    }

    private void initializeUserInterface() {
        // Set theme
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Initialize frame
        cardLayout = new CardLayout();
        frame = new JFrame("POTS");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                backend.save();
                System.exit(0);
            }
        });
        frame.setSize(1100, 500);

        // Setup the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(cardLayout);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        login = new Login(backend, this);
        mainPanel.add(login, "login");

        frame.setContentPane(mainPanel);

        showPanel("login");
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        frame.pack();
        frame.setVisible(true);
    }

    public void login() {
        if (mainMenu != null) {
            mainPanel.remove(mainMenu);
        }
        mainMenu = new MainMenu(backend, this);
        mainPanel.add(mainMenu, "mainMenu");

        showPanel("mainMenu");
    }
}
