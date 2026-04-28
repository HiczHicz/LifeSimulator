package ui;

import organism.*;
import world.World;

import javax.swing.*;
import java.awt.*;

public class AddOrganismDialog extends JDialog {
    private World world;
    private int targetX, targetY;

    private JPanel contentPanel;
    private JComboBox<String> organismCombo;
    private String[] animals = {"Wolf", "Sheep", "Fox", "Turtle", "Antilope"};
    private String[] plants = {"Grass", "Dandelion", "Guarana", "WolfBerries"};

    public AddOrganismDialog(JFrame parent, World world, int x, int y) {
        super(parent, "Add to (" + x + "," + y + ")", true);
        this.world = world;
        this.targetX = x;
        this.targetY = y;

        contentPanel = new JPanel(new CardLayout());

        // --- SCREEN 1: choose category ---
        JPanel categoryPanel = new JPanel(new FlowLayout());
        JButton animalBtn = new JButton("Animal");
        JButton plantBtn = new JButton("Plant");

        categoryPanel.add(new JLabel("What to add?"));
        categoryPanel.add(animalBtn);
        categoryPanel.add(plantBtn);

        // --- SCREEN 2: choose organism ---
        JPanel selectionPanel = new JPanel(new FlowLayout());
        organismCombo = new JComboBox<>();
        JButton confirmBtn = new JButton("Add");


        // back button
        JButton backBtn = new JButton("Back");

        selectionPanel.add(organismCombo);
        selectionPanel.add(confirmBtn);
        selectionPanel.add(backBtn);

        contentPanel.add(categoryPanel, "CATEGORY");
        contentPanel.add(selectionPanel, "SELECTION");

        //button logic
        animalBtn.addActionListener(e -> showSelection(animals));
        plantBtn.addActionListener(e -> showSelection(plants));

        //back button logic
        backBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, "CATEGORY");
            pack(); //ajdusting size
        });

        confirmBtn.addActionListener(e -> {
            String selected = (String) organismCombo.getSelectedItem();
            addOrganism(selected);
            dispose();
        });

        add(contentPanel);
        pack();
        setLocationRelativeTo(parent);
    }

    private void showSelection(String[] list) {
        organismCombo.setModel(new DefaultComboBoxModel<>(list));
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "SELECTION");
        pack(); //adjusting size
    }

    private void addOrganism(String type) {
        Organism newOrg = switch (type) {
            case "Wolf" -> new Wolf(targetX, targetY, world);
            case "Sheep" -> new Sheep(targetX, targetY, world);
            case "Fox" -> new Fox(targetX, targetY, world);
            case "Turtle" -> new Turtle(targetX, targetY, world);
            case "Antilope" -> new Antilope(targetX, targetY, world);
            case "Grass" -> new Grass(targetX, targetY, world);
            case "Dandelion" -> new Dandelion(targetX, targetY, world);
            case "Guarana" -> new Guarana(targetX, targetY, world);
            case "WolfBerries" -> new WolfBerries(targetX, targetY, world);


            default -> null;
        };

        if (newOrg != null) {
            world.addOrganism(newOrg);
            ((MainFrame) getOwner()).refreshUI();
        }
    }
}