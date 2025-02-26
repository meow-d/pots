package user_interface.components;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    public TitlePanel(String title) {
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel(title);
        Font boldFont = new Font(titleLabel.getFont().getName(), Font.BOLD, titleLabel.getFont().getSize());
        titleLabel.setFont(boldFont);
        add(titleLabel, BorderLayout.WEST);
    }
}
