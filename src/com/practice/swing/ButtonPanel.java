package com.practice.swing;

import javax.swing.*;

 public class ButtonPanel extends JPanel {
    private final JButton startButton = createButton("Start", 10, 50);
    private final JButton stepButton = createButton("Step", 170, 50);
    private final JButton fileButton = createButton("Upload from file", 90, 15);

    public ButtonPanel() {
        super(null);
        add(startButton);
        add(stepButton);
        add(fileButton);
    }

    private JButton createButton(String text, int x, int y) {
        JButton createdButton = new JButton(text);
        createdButton.setBounds(x, y, 150, 30);
        createdButton.setFocusPainted(false);
        createdButton.setBorderPainted(true);
        return createdButton;
    }

}
