package swing;

import algorithm.AStar;
import datastructures.Graph;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import scala.App;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class ButtonPanel extends JPanel implements ActionListener
{

    public final JButton startButton = createButton("Start", 10, 50);
    public final JButton stepButton = createButton("Step", 170, 50);
    public final JButton fileButton = createButton("Upload from file", 90, 15);

    public static boolean isGraphicGH = true;
    public static boolean isFileGH = true;
    public static boolean isFile = false;

    private AppLauncher appLauncher = null;

    private  Graph<String> graph;
    private String resultPath = "";
    private File file = null;

    int line = 0;

    public ButtonPanel(AppLauncher appLauncher) {
        super(null);

        this.appLauncher = appLauncher;

        startButton.addActionListener(this);
        stepButton.addActionListener(this);
        fileButton.addActionListener(this);

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

    public void addStartButtonListener(MouseListener listener) {
        startButton.addMouseListener(listener);
    }

    public void addFileButtonListener(MouseListener listener) {
        fileButton.addMouseListener(listener);
    }

    public void addStepButtonListener(MouseListener listener) {
        stepButton.addMouseListener(listener);
    }


    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource().equals(startButton))
        {
            if(isFile){
            if (!(file == null))
            {
               readFileAndStartAlgorithm();


                AppLauncher.infoBlock.setText("");
                AppLauncher.infoBlock.append(graph.comments);
                AppLauncher.infoBlock.append("The minimum path is: " + resultPath);
            }
            else
            {
                AppLauncher.infoBlock.setText("");
                AppLauncher.infoBlock.append("Graph was not initialized");
            }


            } else {
                AppLauncher.graph.clear();

                AppLauncher.graph.addAttribute("ui.label");
                AppLauncher.graph.addAttribute("ui.stylesheet",
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


                readGraphicsandStartAlgorithm();

                AppLauncher.infoBlock.setText("");
                AppLauncher.infoBlock.append(graph.comments);
                AppLauncher.infoBlock.append("The minimum path is: " + resultPath);
            }
            line = 0;
        }
        else if (ae.getSource().equals(stepButton))
        {
            if(isFile) {
                if (!(file == null)) {
                    readFileAndStartAlgorithm();

                    if (line == 0) {
                        AppLauncher.infoBlock.setText("");
                    }

                    if (line == graph.comments.split("\n\n").length) {
                        AppLauncher.infoBlock.append("The minimum path is: " + resultPath);
                        line++;
                    } else if (line < graph.comments.split("\n\n").length) {
                        AppLauncher.infoBlock.append(graph.comments.split("\n\n")[line] + "\n\n");

                        //for (int j = 0; j <= graph.comments.split("\n")[line].split(" ")[graph.comments.split("\n").length - 1].split(" , ").length; j += 2)
                        //{
                        //AppLauncher.graph.getNode(graph.comments.split("\n")[line].split(" ")[graph.comments.split("\n").length - 1].split(" , ")[j].charAt(1)).addAttribute("ui.style", "fill-color: yellow;");
                        //System.out.println(graph.comments.split("\n")[line].split(" ")[graph.comments.split("\n").length - 1].split(" , ")[j].charAt(1));
                        //}

                        line++;
                    }
                } else {
                    AppLauncher.infoBlock.setText("");
                    AppLauncher.infoBlock.append("Graph was not initialized");
                }
            }else{
                resultPath = "";
                readGraphicsandStartAlgorithm();

                if (line == 0) {
                    AppLauncher.infoBlock.setText("");
                }

                if (line == graph.comments.split("\n\n").length) {
                    AppLauncher.infoBlock.append("The minimum path is: " + resultPath);
                    line++;
                } else if (line < graph.comments.split("\n\n").length) {
                    AppLauncher.infoBlock.append(graph.comments.split("\n\n")[line] + "\n\n");

                    //for (int j = 0; j <= graph.comments.split("\n")[line].split(" ")[graph.comments.split("\n").length - 1].split(" , ").length; j += 2)
                    //{
                    //AppLauncher.graph.getNode(graph.comments.split("\n")[line].split(" ")[graph.comments.split("\n").length - 1].split(" , ")[j].charAt(1)).addAttribute("ui.style", "fill-color: yellow;");
                    //System.out.println(graph.comments.split("\n")[line].split(" ")[graph.comments.split("\n").length - 1].split(" , ")[j].charAt(1));
                    //}

                    line++;
                }
            }
        }
        else if (ae.getSource().equals(fileButton))
        {

            AppLauncher.viewer.disableAutoLayout();
            isFile = true;
            ActionListener buttonlistener =  new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser file_chooser = new JFileChooser();
                    file_chooser.setDialogTitle("Choose File");
                    file_chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                    int result = file_chooser.showOpenDialog(null);

                    if (result == JFileChooser.APPROVE_OPTION)
                    {
                        file = file_chooser.getSelectedFile();
                        FileReader file_reader = null;

                        AppLauncher.graph.clear();

                        AppLauncher.graph.addAttribute("ui.label");
                        AppLauncher.graph.addAttribute("ui.stylesheet",
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

                        try
                        {
                            file_reader = new FileReader(file);
                            BufferedReader br = new BufferedReader(file_reader);
                            String line = br.readLine();
                            String start = line.split(" ")[0];
                            String goal = line.split(" ")[1];

                            line = br.readLine();

                            while(!(line.equals("heuristic:")))
                            {
                                AppLauncher.graph.addEdge(line.replace(" ", ""), line.split(" ")[0], line.split(" ")[1], true);
                                AppLauncher.graph.getNode(line.split(" ")[0]).addAttribute("ui.label", line.split(" ")[0]);
                                AppLauncher.graph.getNode(line.split(" ")[1]).addAttribute("ui.label", line.split(" ")[1]);
                                AppLauncher.graph.getEdge(line.replace(" ", "")).addAttribute("ui.label", line.split(" ")[2]);
                                line = br.readLine();
                            }

                            AppLauncher.graph.getNode(start).addAttribute("ui.style", "fill-color: green;");
                            AppLauncher.graph.getNode(goal).addAttribute("ui.style", "fill-color: red;");
                        }
                        catch (FileNotFoundException s)
                        {
                            s.printStackTrace();
                        }
                        catch (IOException m)
                        {
                            m.printStackTrace();
                        }

                        AppLauncher.viewer.enableAutoLayout();
                        AppLauncher.view = AppLauncher.viewer.addDefaultView(false);
                        AppLauncher.view.setBorder(new TitledBorder("Graph Image"));
                        AppLauncher.leftPanel.removeAll();
                        AppLauncher.leftPanel.add(Box.createRigidArea(new Dimension(500, 10)));
                        AppLauncher.leftPanel.add(AppLauncher.view);
                        AppLauncher.leftPanel.add(Box.createRigidArea(new Dimension(500, 10)));
                        AppLauncher.leftPanel.add(Box.createRigidArea(new Dimension(500, 40)));
                        AppLauncher.leftPanel.updateUI();
                        line = 0;
                        AppLauncher.infoBlock.setText("");
                    }
                }
            };
            ActionListener h1listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isFileGH = true;
                }
            };
            ActionListener h2listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isFileGH = false;
                }
            };
            RadioButtonJFrame rad = new RadioButtonJFrame(buttonlistener,h1listener,h2listener);
            rad.setVisible(true);
        }

    }

    private void readFileAndStartAlgorithm(){
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Graph.Edge<String>> edges = new ArrayList<>();
        Set<Graph.Vertex<String>> vertices = new HashSet<>();

        String start = scanner.next();
        String goal  = scanner.next();

        while(!scanner.hasNext("heuristic:")){
            String from = scanner.next();
            String to = scanner.next();
            int weight = scanner.nextInt();
            Graph.Vertex<String> v1 = new Graph.Vertex(from);
            Graph.Vertex<String> v2 = new Graph.Vertex(to);
            edges.add(new Graph.Edge<>(weight,v1,v2));
            if (!vertices.contains(v1)){
                vertices.add(v1);
            }

            if (!vertices.contains(v2)){
                vertices.add(v2);
            }
        }
        for (Graph.Vertex<String> v: vertices){
            for(Graph.Edge<String> e: edges){
                if(v.getValue().equals(e.getFromVertex().getValue())){
                    if(!v.getEdges().contains(e))v.addEdge(e);
                }
            }
        }
        for (Graph.Edge<String> e: edges){
            for (Graph.Vertex<String> v: vertices){
                if(e.getToVertex().getValue().equals(v.getValue())){
                    if(!e.getToVertex().equals(v)) e.setTo(v);
                }
                if(e.getFromVertex().getValue().equals(v.getValue())){
                    if(!e.getFromVertex().equals(v)) e.setFrom(v);
                }
            }
        }

        graph = new Graph<>(vertices,edges);
        resultPath = "";

        AStar<String> astar = new AStar<>();
        List<Graph.Edge<String>> path = new ArrayList<>();

        if (!isFileGH){
            Map<String,Integer> hScore = new HashMap<>();
            String value;
            Integer heuristic;
            value = scanner.next();
            while(scanner.hasNext()){
                value = scanner.next();
                heuristic = scanner.nextInt();
                hScore.put(value,heuristic);
            }
            astar.setH(false);
            astar.sethScore(hScore);
        }else astar.setH(true);

        try {
            astar.aStar(graph,graph.findVertex(start),graph.findVertex(goal));
            path = astar.calculate(true);
        }
        catch(NullPointerException e)
        {
            //System.out.println("File contains incorrect data!");
            JOptionPane.showMessageDialog(null, "File contains incorrect data");
            System.exit(1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(path == null) {
            //System.out.println("Path from <"+ start + "> to <" + goal + "> doesn't exist");
            JOptionPane.showMessageDialog(null, "Path from <"+ start + "> to <" + goal + "> doesn't exist");
        }
        else {
            for (Graph.Edge<String> v : path) {
                if (v.getFromVertex().getValue().equals(start)) {
                    resultPath += start;
                    resultPath += v.getToVertex().getValue();
                    continue;
                }
                resultPath += v.getToVertex().getValue();
            }
        }

    }

    private void readGraphicsandStartAlgorithm(){

        List<Graph.Edge<String>> edges = DrawPanel.takeEdges();
        Set<Graph.Vertex<String>> vertices = DrawPanel.takeVertices();

        for (Graph.Vertex<String> v: vertices){
            for(Graph.Edge<String> e: edges){
                if(v.getValue().equals(e.getFromVertex().getValue())){
                    if(!v.getEdges().contains(e))v.addEdge(e);
                }
            }
        }
        for (Graph.Edge<String> e: edges){
            for (Graph.Vertex<String> v: vertices){
                if(e.getToVertex().getValue().equals(v.getValue())){
                    if(!e.getToVertex().equals(v)) e.setTo(v);
                }
                if(e.getFromVertex().getValue().equals(v.getValue())){
                    if(!e.getFromVertex().equals(v)) e.setFrom(v);
                }
            }
        }
        graph = new Graph<>(vertices,edges);
        AStar<String> astar = new AStar<>();
        List<Graph.Edge<String>> path = new ArrayList<>();
        String start = DrawPanel.getStartNode();
        String goal = DrawPanel.getGoalNode();

// TODO
        AppLauncher.graph.clear();

        AppLauncher.graph.addAttribute("ui.label");
        AppLauncher.graph.addAttribute("ui.stylesheet",
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
        AppLauncher.graph = graph.getVisualization();
        AppLauncher.viewer = AppLauncher.graph.display();
        AppLauncher.viewer.enableAutoLayout();
        AppLauncher.view = AppLauncher.viewer.addDefaultView(false);
        AppLauncher.view.setBorder(new TitledBorder("Graph Image"));
        AppLauncher.leftPanel.removeAll();
        AppLauncher.leftPanel.add(Box.createRigidArea(new Dimension(500, 10)));
        AppLauncher.leftPanel.add(AppLauncher.view);
        AppLauncher.leftPanel.add(Box.createRigidArea(new Dimension(500, 10)));
        AppLauncher.leftPanel.add(Box.createRigidArea(new Dimension(500, 40)));
        AppLauncher.leftPanel.updateUI();


        if(isGraphicGH){
            astar.setH(true);
            DrawPanel.setInputHeuristic(false);
        }
        else {
            Map<String,Integer> hScore = DrawPanel.takeHScore();
            DrawPanel.setInputHeuristic(true);
            astar.setH(false);
            astar.sethScore(hScore);
        }

        try {
            astar.aStar(graph,graph.findVertex(start),graph.findVertex(goal));
            path = astar.calculate(true);
        }
        catch(NullPointerException e)
        {
           // System.out.println("File contains incorrect data!");
            JOptionPane.showMessageDialog(null, "File contains incorrect data");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(path == null) {
            //System.out.println("Path from <"+ start + "> to <" + goal + "> doesn't exist");
            JOptionPane.showMessageDialog(null, "Path from <"+ start + "> to <" + goal + "> doesn't exist");
        }
        else {
            for (Graph.Edge<String> v : path) {
                if (v.getFromVertex().getValue().equals(start)) {
                    resultPath += start;
                    resultPath += v.getToVertex().getValue();
                    continue;
                }
                resultPath += v.getToVertex().getValue();
            }
        }

    }

}
