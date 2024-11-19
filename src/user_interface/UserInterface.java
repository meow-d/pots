package user_interface;

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

        // WONTFIX might not be ideal since there are a lot of pages in this project
        login = new Login(backend, this);
        mainMenu = new MainMenu(backend, this);
        mainPanel.add(login, "login");
        mainPanel.add(mainMenu, "mainMenu");

        frame.setContentPane(mainPanel);

        showPanel("login");
    }

    public void showPanel(String panelName) {
        // TODO Custom behaviour if we need it
        // if (panelName.equals("login")) {
        // }

        cardLayout.show(mainPanel, panelName);
        frame.pack();
        frame.setVisible(true);
    }
}
