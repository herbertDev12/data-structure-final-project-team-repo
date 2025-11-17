package menu;

import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {
    private final Runnable onResume;
    private final Runnable onExitToMenu;

    public PausePanel(Runnable onResume, Runnable onExitToMenu) {
        this.onResume = onResume;
        this.onExitToMenu = onExitToMenu;
        setOpaque(false);
        setLayout(new GridBagLayout());
        buildUI();
    }

    private void buildUI() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(8, 8, 8, 8);

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(360, 160));
        card.setBackground(new Color(20, 20, 20, 220));
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel title = new JLabel("PAUSA", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
        card.add(title, BorderLayout.NORTH);

        JPanel btns = new JPanel();
        btns.setOpaque(false);

        JButton resume = new JButton("Reanudar");
        resume.addActionListener(e -> {
            if (onResume != null) onResume.run();
        });
        JButton exit = new JButton("Salir al menú");
        exit.addActionListener(e -> {
            if (onExitToMenu != null) onExitToMenu.run();
        });

        btns.add(resume);
        btns.add(exit);
        card.add(btns, BorderLayout.SOUTH);

        add(card, c);
    }

    // pintar semitransparencia de fondo (oscurecer la pantalla)
    @Override
    protected void paintComponent(Graphics g) {
        // paint translucent dark background then child components
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 140));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }


}
