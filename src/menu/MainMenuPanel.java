package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenuPanel {
    private JFrame frame;

    public void showMenu() {
        frame = new JFrame("Mine Room - Menú");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.DARK_GRAY);

        JLabel title = new JLabel("Mine Room", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(24, 12, 12, 12));
        main.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JButton start = new JButton("Iniciar");
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.setMaximumSize(new Dimension(220, 40));
        start.addActionListener((ActionEvent e) -> {
            frame.dispose();
            GameWindow gw = new GameWindow();
            gw.showGameWindow();
        });

        JButton exit = new JButton("Salir");
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.setMaximumSize(new Dimension(220, 40));
        exit.addActionListener((ActionEvent e) -> System.exit(0));

        center.add(Box.createVerticalGlue());
        center.add(start);
        center.add(Box.createVerticalStrut(14));
        center.add(exit);
        center.add(Box.createVerticalGlue());

        main.add(center, BorderLayout.CENTER);

        JLabel footer = new JLabel("Presiona para jugar", SwingConstants.CENTER);
        footer.setForeground(Color.LIGHT_GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(12, 12, 24, 12));
        main.add(footer, BorderLayout.SOUTH);

        frame.setContentPane(main);
        frame.setVisible(true);
    }
}
