package ui;

import world.World;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private World world;
    private GamePanel gamePanel;
    private JTextArea logArea;

    public MainFrame(World world) {
        this.world = world;

        //window config
        setTitle("Symulator życia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //turn button - top
        JButton nextTurnBtn = new JButton("Nowa tura");
        nextTurnBtn.addActionListener(e -> {
            world.turn();
            refreshUI();
        });
        add(nextTurnBtn, BorderLayout.NORTH);

        //world map
        gamePanel = new GamePanel(world);
        add(gamePanel, BorderLayout.CENTER);

        //info&legend positioning
        add(createSidePanel(), BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(250, 0));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //creator
        sidePanel.add(new JLabel("Autor: \thiczhicz"));
        sidePanel.add(new JSeparator());

        //legend
        sidePanel.add(new JLabel("<html><br><b>Legenda:</b><br>" +
                "<font color='red'>W</font> - Wilk<br>" +
                "<font color='blue'>O</font> - Owca<br>" +
                "<font color='green'>T</font> - Trawa</html>"));

        sidePanel.add(new JSeparator());

        //logs of whats happening on screen
        logArea = new JTextArea(10, 20);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        sidePanel.add(new JLabel("Podsumowanie tury:"));
        sidePanel.add(scrollPane);

        return sidePanel;
    }

    public void refreshUI() {
        gamePanel.repaint();
        //update logs
        logArea.setText("Nowa tura wykonana...");
    }
}
