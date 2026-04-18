package ui;

import logger.Logger;
import logger.LoggerComposite;
import logger.LoggerGame;
import world.World;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private World world;
    private GamePanel gamePanel;
    private JTextArea logArea;

    public MainFrame(World world) {
        this.world = world;
        
        this.logArea = new JTextArea(10, 20);
        this.logArea.setEditable(false);

        Logger gameLogger = new LoggerGame(this.logArea);
        world.setLogger(gameLogger);


        // 2. Pobranie kompozytu ze świata i dodanie Loggera GUI
        if (world.getLogger() instanceof LoggerComposite composite) {
            // Od tego momentu każdy log trafi i do pliku, i do okna
            composite.addLogger(new LoggerGame(this.logArea));
        }

        //window config
        setTitle("Symulator życia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //turn button - top
        JButton nextTurnBtn = new JButton("Nowa tura");
        nextTurnBtn.addActionListener(e -> {
            world.turn();
            world.getLogger().flush();
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

    private JPanel createLegendItem(String name, Color color) {
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));

        //colored squares
        JPanel colorBox = new JPanel();
        colorBox.setPreferredSize(new Dimension(15, 15));
        colorBox.setBackground(color);
        colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Obramowanie kwadracika

        itemPanel.add(colorBox);
        itemPanel.add(new JLabel("- " + name));

        return itemPanel;
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
        JLabel title = new JLabel("Legenda:");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        sidePanel.add(title);

        //adding organism to legends
        sidePanel.add(createLegendItem("Wilk", Color.DARK_GRAY));
        sidePanel.add(createLegendItem("Owca", Color.LIGHT_GRAY));

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
