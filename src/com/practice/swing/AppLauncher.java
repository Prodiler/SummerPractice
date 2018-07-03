package com.practice.swing;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class AppLauncher extends JFrame {
   // private final Component canvas;
    private final JPanel rightPanel;
    private final JPanel leftPanel;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppLauncher().setVisible(true));
    }

    private AppLauncher() throws HeadlessException {
        super("A-star algorithm");
        setPreferredSize(new Dimension(900, 600));
       // setBounds(0, 0, 700, 400);
        setResizable(false);

        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(300, 600));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setPreferredSize(new Dimension(310, 90));
        buttonPanel.setBounds(550, 0, 310, 110);
        rightPanel.add(buttonPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(310, 10)));
        JTextArea infoBlock = new JTextArea();
        infoBlock.setPreferredSize(new Dimension(310, 300));
        infoBlock.setBounds(0, 170, 310, 300);
        infoBlock.setEnabled(false);
        infoBlock.setBorder(new TitledBorder("Algorithm Progress"));
        rightPanel.add(infoBlock);
        rightPanel.add(Box.createRigidArea(new Dimension(900, 10)));

        JPanel graphPanel = new JPanel();
        graphPanel.setPreferredSize(new Dimension(500, 500));
        graphPanel.setBorder(new TitledBorder("Graph Image"));
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(Box.createRigidArea(new Dimension(900, 10)));
        leftPanel.add(graphPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(900, 10)));


        JPanel rootPanel = new JPanel();
        rootPanel.setPreferredSize(new Dimension(900, 600));
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.X_AXIS ));
       // rootPanel.setBackground(Color.PINK);
        rootPanel.add(Box.createRigidArea(new Dimension(5, 600)));
        rootPanel.add(graphPanel);
        rootPanel.add(Box.createRigidArea(new Dimension(10, 600)));
        rootPanel.add(rightPanel);
        rootPanel.add(Box.createRigidArea(new Dimension(5, 600)));


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

    }
}
