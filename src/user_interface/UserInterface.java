package user_interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import backend.Backend;

public class UserInterface {
    private JFrame frame;
    private CardLayout cardLayout;

    public UserInterface() {
        initializeSystem();
        initializeUserInterface();
    }

    private void initializeSystem() {
        Backend backend = new Backend();
    }

    private void initializeUserInterface() {
        // TODO
    }
}
