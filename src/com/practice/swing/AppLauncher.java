package com.practice.swing;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

        //инициализируем правую паннель
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(300, 600));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); //заполнение по вертикали

        //инициализируем паннель с кнопками
        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setPreferredSize(new Dimension(310, 90));
        buttonPanel.setBounds(550, 0, 310, 110);

        //инициализируем текстовый блок
        JTextArea infoBlock = new JTextArea(25, 30);
        //infoBlock.setPreferredSize(new Dimension(290, 300));
        infoBlock.setBounds(0, 170, 310, 20);
        infoBlock.setEnabled(true);
        infoBlock.setBorder(new TitledBorder("Algorithm Progress"));
        infoBlock.setLineWrap(true);
        infoBlock.setWrapStyleWord(true);
        JScrollPane scrollText = new JScrollPane(infoBlock, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollText.createVerticalScrollBar();

        //заполняем правую панель
        rightPanel.add(buttonPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(310, 10))); //отступ между блоками
        rightPanel.add(scrollText);
        rightPanel.add(Box.createRigidArea(new Dimension(900, 10))); //отступ от края

        //захардкодим граф для примера
        // TODO: реализовать работу с графами через отдельный класс
        Graph graph = new SingleGraph("Example", true, true);
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("AB", "A", "B", true);
        graph.addEdge("BC", "B", "C", true);
        graph.addEdge("AC", "A", "C", true);
        graph.addEdge("DB", "D", "B", true);

        //для вывода графа
        Viewer graphViewer = graph.display();
        View viewOfGraph = graphViewer.addDefaultView(false);
        ((ViewPanel)viewOfGraph).setBorder(new TitledBorder("Graph Image"));


        //SelectionPanel selectInputPanel = new SelectionPanel();
        //инициализируем левую панель
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(500, 620));
        leftPanel.add(Box.createRigidArea(new Dimension(500, 10)));
        leftPanel.add((JPanel)viewOfGraph); //подрубаем граф
        leftPanel.add(Box.createRigidArea(new Dimension(500, 10)));
        leftPanel.addMouseListener(new MouseL());

        //инициализируем основную панель
        JPanel rootPanel = new JPanel();
        rootPanel.setPreferredSize(new Dimension(900, 600));
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.X_AXIS ));
        rootPanel.add(Box.createRigidArea(new Dimension(5, 600)));
        rootPanel.add(leftPanel);
        rootPanel.add(Box.createRigidArea(new Dimension(10, 600)));
        rootPanel.add(rightPanel);
        rootPanel.add(Box.createRigidArea(new Dimension(5, 600)));


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);

    }
}


class MouseL implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        JFrame frame = new JFrame("Input");

        // prompt the user to enter
        String value = JOptionPane.showInputDialog(frame, "Value of heuristic function: ");
        // get the user's input. note that if they press Cancel, 'name' will be null
       // System.out.printf("The user's name is '%s'.\n", );
       //System.exit(0);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

/*class StartApplication implements Runnable {

    @Override
    public void run() {
        JFrame mainFrame = new JFrame("A-star algorithm");

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new FlowLayout());
        ButtonPanel buttons = new ButtonPanel();
        buttons.setPreferredSize(new Dimension(420, 30));
        rootPanel.add(buttons);

        JPanel rootPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.setColor(Color.PINK);
                g.fillRect(20, 20, 300, 300);
            }
        };

        rootPanel.setPreferredSize(new Dimension(700, 400));

        mainFrame.setContentPane(rootPanel);
        mainFrame.setPreferredSize(new Dimension(700, 400));
        mainFrame.pack();

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

    }
} */

