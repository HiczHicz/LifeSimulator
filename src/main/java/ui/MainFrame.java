package ui;

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

        //initializing log area
        this.logArea = new JTextArea(10, 20);
        this.logArea.setEditable(false);

        //building UI
        setupUI();

        //logger config
        setupLogger();

        //window config
        setTitle("Symulator życia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //UI setup - set Layout, buttons
    private void setupUI() {
        setLayout(new BorderLayout());

        //turn button - top
        JButton nextTurnBtn = new JButton("Nowa tura");
        nextTurnBtn.addActionListener(e -> {
            world.turn();
            if (world.getLogger() != null) {
                world.getLogger().flush();
            }
            refreshUI();
        });
        add(nextTurnBtn, BorderLayout.NORTH);

        //world map
        gamePanel = new GamePanel(world);
        add(gamePanel, BorderLayout.CENTER);

        add(createSidePanel(), BorderLayout.EAST);
    }

    //Logger setuop
    private void setupLogger() {
        //checking if there is  log composite from Main class
        if (world.getLogger() instanceof LoggerComposite composite) {
            composite.addLogger(new LoggerGame(this.logArea));
        } else {
            //if no log composite in main
            world.setLogger(new LoggerGame(this.logArea));
        }
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