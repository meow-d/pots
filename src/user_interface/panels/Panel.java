package user_interface.panels;

import java.util.Map;
import java.awt.*;

import javax.swing.*;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import backend.Backend;
import data.BaseItem;
import user_interface.MainMenu;


abstract class Panel<T extends BaseItem> extends JPanel {
    protected JPanel panel, titleButtonPanel;
    protected JButton backButton;
    protected CustomJPanel contentPanel;
    protected Map<String, T> items;
    protected Backend backend;
    protected MainMenu parent;

    public Panel(String title, MainMenu parent, Map<String, T> items, Backend backend) {
        this.items = items;
        this.backend = backend;
        this.parent = parent;

        // Title
        setLayout(new BorderLayout());
        JPanel titlePanel = new TitlePanel(title);
        add(titlePanel, BorderLayout.NORTH);

        // Title button panel
        titleButtonPanel = new JPanel(new FlowLayout());
        titlePanel.add(titleButtonPanel, BorderLayout.EAST);

        // Back button
        backButton = new JButton("Back");
        titleButtonPanel.add(backButton, 0);

        // Content
        contentPanel = new CustomJPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        add(contentPanel, BorderLayout.CENTER);
    }
    
    
    public void backMainMenu(String menuName){
        backButton.addActionListener(e -> {
            if(menuName == null || menuName == ""){
                parent.showMainMenu();
            } else {
                parent.showPanel(menuName);
            }
        });
    };

    // abstract public void backMainMenu(String menuName);

}

class CustomJPanel extends JPanel{
    @Override
    public Component add(Component comp){
        if (comp instanceof JComponent) {
            ((JComponent) comp).setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        }

        Component addedComponent = super.add(comp);

        // Trigger layout updates
        revalidate();
        repaint();

        return addedComponent;
    }
}