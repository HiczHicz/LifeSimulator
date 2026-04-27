package ui;

import logger.Logger;
import logger.LoggerGame;
import organism.Human;
import organism.Organism;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class MainFrame extends JFrame {
    private World world;
    private GamePanel gamePanel;
    private JTextArea logArea;
    private int displayedTurnIndex = 0;
    private JLabel turnLabel = new JLabel("Tura: 0");
    private JButton prevBtn;
    private JButton nextBtn;
    private JLabel abilityLabel;


    //auto mode
    private Timer autoTimer;
    private JSlider speedSlider;
    private JButton autoBtn;
    private boolean isRunning = false;

    public MainFrame(World world) {
        this.world = world;

        setPreferredSize(new Dimension(800, 600)); //window size
        setMinimumSize(new Dimension(800, 600));
        setMaximumSize(new Dimension(1200, 900));

        //initializing log area
        this.logArea = new JTextArea(10, 20);
        this.logArea.setEditable(false);

        this.logArea.setLineWrap(true);      //wrapping text to next line
        this.logArea.setWrapStyleWord(true); //making whole word go to next line if needed

        this.logArea.setFocusable(false); //blocks clicking on log area

        //logger config
        setupLogger();

        //building UI
        setupUI();

        //turn 0 logs
        displayedTurnIndex = 0;
        updateLogDisplay();

        //key config
        InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getRootPane().getActionMap();

        //defining directions
        int[] keys = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
        String[] names = {"moveUp", "moveDown", "moveLeft", "moveRight"};

        for (int i = 0; i < keys.length; i++) {
            final int keyCode = keys[i];
            im.put(KeyStroke.getKeyStroke(keyCode, 0), names[i]);
            am.put(names[i], new AbstractAction() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    handleHumanMove(keyCode);
                }
            });
        }

        //defining h key for holocaust ability
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0), "activateAbility");
        am.put("activateAbility", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                for (Organism o : world.getOrganisms()) {
                    if (o instanceof Human h) {
                        h.activateAbility();
                        executeSingleTurn();
                        break;
                    }
                }
            }
        });


        //window config
        setTitle("Life Symulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //handling human moving
    private void handleHumanMove(int keyCode) {
        boolean humanFound = false;
        for (Organism o : world.getOrganisms()) {
            if (o instanceof Human h) {
                h.setNextDirection(keyCode);
                humanFound = true;
                break;
            }
        }

        if (humanFound) {
            executeSingleTurn(); //executing turn right after clicking
        }
    }

    //Logger setup
    private void setupLogger() {
        LoggerGame lg = new LoggerGame(this.logArea);
        world.setGameLogger(lg);

        for (Organism o : world.getOrganisms()) {
            world.log(Logger.Level.INFO, "Organism added: " + o.toString());
        }
    }


    //UI setup - set Layout, buttons
    private void setupUI() {
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();

        File savesDir = new File("saves");
        if (!savesDir.exists()) {
            savesDir.mkdir(); //creates folder if doesnt exist
        }

        // save button
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(savesDir);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text files (*.txt)", "txt"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                world.saveToFile(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        topPanel.add(saveBtn);


        // load button
        JButton loadBtn = new JButton("Load");
        loadBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(savesDir);
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                world.loadFromFile(fileChooser.getSelectedFile().getAbsolutePath());

                LoggerGame lg = (LoggerGame) world.getGameLogger();
                displayedTurnIndex = 0;

                updateLogDisplay(); //update
                refreshUI();

            }
        });
        topPanel.add(loadBtn);

        //turn button - top
        JButton nextTurnBtn = new JButton("New turn");
        nextTurnBtn.addActionListener(e -> executeSingleTurn());
        topPanel.add(nextTurnBtn);

        speedSlider = new JSlider(JSlider.HORIZONTAL, 100, 1100, 600);
        speedSlider.setInverted(true); //the less value, the faster the speed
        speedSlider.addChangeListener(e -> {
            if (autoTimer != null) {
                autoTimer.setDelay(speedSlider.getValue());
            }
            //logging - only when slider is released, not while dragging
            if (!speedSlider.getValueIsAdjusting()) {
                world.log(Logger.Level.INFO, "Auto Mode speed changed: " + speedSlider.getValue() + "ms");
                if (world.getFileLogger() != null) world.getFileLogger().flush();
            }
        });
        topPanel.add(new JLabel("Speed:"));
        topPanel.add(speedSlider);

        //auto button
        autoBtn = new JButton("Start Auto");
        autoBtn.addActionListener(e -> toggleAuto());
        topPanel.add(autoBtn);

        //adding both buttons
        add(topPanel, BorderLayout.NORTH);

        autoTimer = new Timer(speedSlider.getValue(), e -> executeSingleTurn());

        //world map
        gamePanel = new GamePanel(world);
        add(gamePanel, BorderLayout.CENTER);

        add(createSidePanel(), BorderLayout.EAST);
    }

    //auto
    private void executeSingleTurn() {
        world.turn();
        //in game logs
        LoggerGame lg = (LoggerGame) world.getGameLogger();
        displayedTurnIndex = lg.getHistorySize() - 1;
        updateLogDisplay();

        //file logs
        if (world.getFileLogger() != null) world.getFileLogger().flush();
        //if (world.getGameLogger() != null) world.getGameLogger().flush();

        refreshUI();

        //game over - to do in the future
//        if (checkGameOver()) {
//            stopAuto();
//        }
    }

    private void toggleAuto() {
        if (isRunning) {
            stopAuto();
        } else {
            startAuto();
        }
    }

    private void startAuto() {
        isRunning = true;
        autoBtn.setText("Stop Auto");

        //logging
        world.log(Logger.Level.INFO, "Auto Mode on, speed: " + speedSlider.getValue() + "ms");
        if (world.getFileLogger() != null) world.getFileLogger().flush();

        autoTimer.start();
    }

    private void stopAuto() {
        isRunning = false;
        autoBtn.setText("Start Auto");

        //logging
        world.log(Logger.Level.INFO, "Auto Mode off");
        if (world.getFileLogger() != null) world.getFileLogger().flush();

        autoTimer.stop();
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

        //legend
        JLabel title = new JLabel("Legend:");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        sidePanel.add(title);

        //adding organism to legends
        sidePanel.add(createLegendItem("Wolf", Color.DARK_GRAY));
        sidePanel.add(createLegendItem("Sheep", Color.LIGHT_GRAY));
        sidePanel.add(createLegendItem("Fox", Color.ORANGE));
        sidePanel.add(createLegendItem("Turtle", Color.GREEN));
        sidePanel.add(createLegendItem("Antilope", Color.ORANGE.darker()));

        sidePanel.add(new JSeparator());

        //special ability
        abilityLabel = new JLabel("Holocaust: Ready");
        abilityLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        abilityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(abilityLabel);

        sidePanel.add(new JSeparator());

        //navigation bar over the logs
        JPanel navPanel = new JPanel(new FlowLayout());
        prevBtn = new JButton("<");
        nextBtn = new JButton(">");

        navPanel.add(prevBtn);
        navPanel.add(turnLabel);
        navPanel.add(nextBtn);

        sidePanel.add(navPanel);

        //logarea
        JScrollPane scrollPane = new JScrollPane(logArea);
        sidePanel.add(scrollPane);

        //button actions
        prevBtn.addActionListener(e -> navigateHistory(-1));
        nextBtn.addActionListener(e -> navigateHistory(1));

        return sidePanel;
    }

    private void navigateHistory(int offset) {
        LoggerGame lg = (LoggerGame) world.getGameLogger();
        int newIndex = displayedTurnIndex + offset;

        if (newIndex >= 0 && newIndex < lg.getHistorySize()) {
            displayedTurnIndex = newIndex;
            updateLogDisplay();
        }
    }

    private void updateLogDisplay() {
        LoggerGame lg = (LoggerGame) world.getGameLogger();

        logArea.setText(lg.getTurnLog(displayedTurnIndex));

        if (displayedTurnIndex == lg.getHistorySize() - 1) {
            turnLabel.setText("Turn: " + world.getTurnCounter());
        } else {
            turnLabel.setText("Turn: (History) " + displayedTurnIndex);
        }

        //turning arrows on and off
        prevBtn.setEnabled(displayedTurnIndex > 0);
        nextBtn.setEnabled(displayedTurnIndex < lg.getHistorySize() - 1);

        //automatic scrolling to the top
        logArea.setCaretPosition(0);

        for (Organism o : world.getOrganisms()) {
            if (o instanceof Human h) {
                if (h.getAbilityDuration() > 0) {
                    abilityLabel.setText("Holocaust ACTIVE: (" + h.getAbilityDuration() + ")");
                    abilityLabel.setForeground(Color.RED);
                } else if (h.getAbilityCooldown() > 0) {
                    abilityLabel.setText("Reneval: " + h.getAbilityCooldown() + " turns");
                    abilityLabel.setForeground(Color.BLACK);
                } else {
                    abilityLabel.setText("Holocaust: Ready (H)");
                    abilityLabel.setForeground(new Color(0, 150, 0)); //darkgreen
                }
                break;
            }
        }
    }


    public void refreshUI() {
        gamePanel.repaint();
    }
}