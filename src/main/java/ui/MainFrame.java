package ui;

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

        //initializing log area
        this.logArea = new JTextArea(10, 20);
        this.logArea.setEditable(false);

        //logger config
        setupLogger();

        //building UI
        setupUI();

        //window config
        setTitle("Symulator życia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //Logger setup
    private void setupLogger() {
        world.setGameLogger(new LoggerGame(this.logArea));
    }

    //UI setup - set Layout, buttons
    private void setupUI() {
        setLayout(new BorderLayout());

        //turn button - top
        JButton nextTurnBtn = new JButton("Nowa tura");
        nextTurnBtn.addActionListener(e -> {
            world.turn();

            //flushing logs
            if (world.getFileLogger() != null) world.getFileLogger().flush();
            if (world.getGameLogger() != null) world.getGameLogger().flush();

            refreshUI();
        });
        add(nextTurnBtn, BorderLayout.NORTH);

        //world map
        gamePanel = new GamePanel(world);
        add(gamePanel, BorderLayout.CENTER);

        add(createSidePanel(), BorderLayout.EAST);
    }

    //legend setup
    private JPanel createLegendItem(String name, Color color) {
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        JPanel colorBox = new JPanel();
        colorBox.setPreferredSize(new Dimension(15, 15));
        colorBox.setBackground(color);
        colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
        sidePanel.add(new JLabel("Autor: hiczhicz"));
        sidePanel.add(new JSeparator());

        //legend
        JLabel title = new JLabel("Legenda:");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        sidePanel.add(title);

        //adding organism to legends
        sidePanel.add(createLegendItem("Wilk", Color.DARK_GRAY));
        sidePanel.add(createLegendItem("Owca", Color.LIGHT_GRAY));

        sidePanel.add(new JSeparator());

        //using existing logArea
        sidePanel.add(new JLabel("Podsumowanie tury:"));
        JScrollPane scrollPane = new JScrollPane(logArea);
        sidePanel.add(scrollPane);

        return sidePanel;
    }


    public void refreshUI() {
        gamePanel.repaint();
    }
}