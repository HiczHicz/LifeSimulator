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
    private String[] plants = {"Trawa", "Mlecz", "Guarana", "Jagody"}; //CHANGE TO ENGLISH!

    public AddOrganismDialog(JFrame parent, World world, int x, int y) {
        super(parent, "Add to (" + x + "," + y + ")", true);
        this.world = world;
        this.targetX = x;
        this.targetY = y;

        contentPanel = new JPanel(new CardLayout());

        // --- EKRAN 1: Wybór kategorii ---
        JPanel categoryPanel = new JPanel(new FlowLayout());
        JButton animalBtn = new JButton("Animal");
        JButton plantBtn = new JButton("Plant");

        categoryPanel.add(new JLabel("What to add?"));
        categoryPanel.add(animalBtn);
        categoryPanel.add(plantBtn);

        // --- EKRAN 2: Wybór gatunku ---
        JPanel selectionPanel = new JPanel(new FlowLayout());
        organismCombo = new JComboBox<>();
        JButton confirmBtn = new JButton("Add");


        //  PRZYCISK: Cofnij
        JButton backBtn = new JButton("Back");

        selectionPanel.add(organismCombo);
        selectionPanel.add(confirmBtn);
        selectionPanel.add(backBtn);

        contentPanel.add(categoryPanel, "CATEGORY");
        contentPanel.add(selectionPanel, "SELECTION");

        // Logika przycisków
        animalBtn.addActionListener(e -> showSelection(animals));
        plantBtn.addActionListener(e -> showSelection(plants));

        // LOGIKA PRZYCISKU COFNIJ
        backBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout) contentPanel.getLayout();
            cl.show(contentPanel, "CATEGORY");
            pack(); // Ponowne dopasowanie rozmiaru okna
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
        pack(); // Dopasuj rozmiar do nowej zawartości
    }

    private void addOrganism(String type) {
        Organism newOrg = switch (type) {
            case "Wolf" -> new Wolf(targetX, targetY, world);
            case "Sheep" -> new Sheep(targetX, targetY, world);
            case "Fox" -> new Fox(targetX, targetY, world);
            case "Turtle" -> new Turtle(targetX, targetY, world);
            case "Antilope" -> new Antilope(targetX, targetY, world);

            default -> null;
        };

        if (newOrg != null) {
            world.addOrganism(newOrg);
            ((MainFrame) getOwner()).refreshUI();
        }
    }
}