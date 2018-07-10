package swing;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.ProxyPipe;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;
import scala.App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class AppLauncher extends JFrame
{
    private final JPanel rightPanel;
    public static JPanel leftPanel;
    public static JTextArea infoBlock = new JTextArea(25, 30);
    private String result;

    int number = 0;

    public static Graph graph;
    public static Viewer viewer;
    public static ViewPanel view;

    private AppLauncher()
    {
        super("A-star algorithm");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 600));
        setResizable(false);

        //инициализируем правую паннель
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(300, 600));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); //заполнение по вертикали

        //инициализируем левую панель
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(500, 620));
        leftPanel.add(Box.createRigidArea(new Dimension(500, 10)));
        TextLinePanel textLinePanel = new TextLinePanel();
        DrawPanel drawPanel = new DrawPanel(textLinePanel);
        drawPanel.setPreferredSize(new Dimension(500, 620));
        leftPanel.add(drawPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(500, 10)));
        leftPanel.add(textLinePanel);
        leftPanel.add(Box.createRigidArea(new Dimension(500, 10)));
        //инициализируем паннель с кнопками
        ButtonPanel buttonPanel = new ButtonPanel(this);
        buttonPanel.setPreferredSize(new Dimension(310, 90));
        buttonPanel.setBounds(550, 0, 310, 110);

        //RButtonPanel rbuttonPanel = new RButtonPanel();
       // rbuttonPanel.setPreferredSize(new Dimension(10, 20));

        infoBlock.setBounds(0, 170, 310, 20);
        infoBlock.setEnabled(false);
        infoBlock.setDisabledTextColor(Color.BLACK);
        infoBlock.setBorder(new TitledBorder("Algorithm Progress"));
        infoBlock.setLineWrap(true);
        infoBlock.setFont(new Font("Verdana", Font.BOLD, 12 ));
        infoBlock.setWrapStyleWord(true);

        JScrollPane scrollText = new JScrollPane(infoBlock, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollText.createVerticalScrollBar();

        //заполняем правую панель
        rightPanel.add(buttonPanel);
        //rightPanel.add(rbuttonPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(310, 10))); //отступ между блоками
        rightPanel.add(scrollText);
        rightPanel.add(Box.createRigidArea(new Dimension(900, 10))); //отступ от края

        graph = new SingleGraph("Graph", true, true);
        graph.addAttribute("ui.label");
        graph.addAttribute("ui.stylesheet",
                "graph{ fill-color: white; } " +
                        "node{ size: 30px, 30px;" +
                        "fill-color: gray;" +
                        "text-size: 24px;" +
                        "text-color: black;" +
                        "text-style: bold;" +
                        "shape: circle;" +
                        "size-mode: fit; }" +
                        "edge{ text-size: 24px;" +
                        "text-color: black;" +
                        "text-style: bold; }");

        graph.setStrict(false);
        graph.setAutoCreate( true );
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        view = viewer.addDefaultView(false);
        view.setBorder(new TitledBorder("Graph Image"));

       // leftPanel.add(view);

        //инициализируем основную панель
        Container container = getContentPane();

        container.revalidate();
        container.setPreferredSize(new Dimension(900, 600));
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS ));
        container.add(Box.createRigidArea(new Dimension(5, 600)));
        container.add(leftPanel);
        container.add(Box.createRigidArea(new Dimension(10, 600)));
        container.add(rightPanel);
        container.add(Box.createRigidArea(new Dimension(5, 600)));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AppLauncher();
            }
        }
        );
    }

}
