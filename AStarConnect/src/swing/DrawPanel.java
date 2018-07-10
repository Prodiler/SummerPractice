package swing;

import datastructures.Graph;
import org.graphstream.algorithm.generator.IncompleteGridGenerator;
import scala.util.parsing.combinator.testing.Str;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DrawPanel extends JPanel {
    private static ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    private  ArrayList<Connection> connections = new ArrayList<Connection>();
    public static ArrayList<Edge> edgesList = new ArrayList<>();
    private Vertex bufferNodeFrom;
    private Vertex bufferNodeTo;
    private final int rad = 10;
    private boolean haveFirstNode = false;
    private boolean isSelected = false;

    public static void setInputHeuristic(boolean inputHeuristic) {
        DrawPanel.inputHeuristic = inputHeuristic;
    }

    public static boolean inputHeuristic = false;


     public static HashSet<Graph.Vertex<String>> takeVertices(){
        HashSet<Graph.Vertex<String>> vs = new HashSet<>();
        for (Vertex v:vertices){
            vs.add(new Graph.Vertex<>(v.nodeName));
        }
        return vs;
    }

    public static ArrayList<Graph.Edge<String>> takeEdges (){
        ArrayList<Graph.Edge<String>> es = new ArrayList<>();
        for (Edge e:edgesList){
            es.add(new Graph.Edge<>(e.weight,new Graph.Vertex<>(e.from),new Graph.Vertex<>(e.to)));
        }
        return es;
    }
    public static HashMap<String,Integer> takeHScore(){
        HashMap<String,Integer> he = new HashMap<>();
        for(Vertex v: vertices){
            he.put(v.nodeName,v.heuristicValue);
        }
        return he;
    }

    public static String getStartNode() {
        return startNode;
    }

    public static String getGoalNode() {
        return goalNode;
    }

    public static String startNode;
    public static String goalNode;
    //private Vertex startV;
    //private Vertex finishV;

    boolean isAdded = false;


    public DrawPanel(TextLinePanel textLinePanel) {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    haveFirstNode = false;
                    isSelected = false;
                    String nodeName = textLinePanel.nameInput.getText();
                    boolean vertexNotExist = true;
                    for (Vertex node: vertices) {
                        if (nodeName.equals(node.nodeName)) {
                            vertexNotExist = false;
                            break;
                        }
                    }
                    if (vertexNotExist) {
                        if (inputHeuristic) {
                            String heuristic = textLinePanel.heuristicInput.getText();
                            int heuristicValue = 0;
                            if (nodeName.equals("")) {
                                JOptionPane.showMessageDialog(null, "Input string name!");
                            }
                            else {
                                if (heuristic.equals("")) {
                                    JOptionPane.showMessageDialog(null, "Input the value of heuristic function!");
                                }
                                else {
                                    heuristicValue = Integer.parseInt(heuristic);
                                    textLinePanel.nameInput.setText("");
                                    textLinePanel.heuristicInput.setText("");
                                    vertices.add(new Vertex(new Point(e.getX() - rad, e.getY() - rad), nodeName, heuristicValue));
                                    repaint();

                                }
                            }
                        }
                        else {
                            if (nodeName.equals("")) {
                                JOptionPane.showMessageDialog(null, "Input string name!");
                            }
                            else  {
                                textLinePanel.nameInput.setText("");
                                vertices.add(new Vertex(new Point(e.getX() - rad, e.getY() - rad), nodeName, 0));
                                repaint();
                            }
                        }

                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Vertex already exists!");
                    }
                }
                else if (SwingUtilities.isRightMouseButton(e)){
                    if (!haveFirstNode){
                        int xCoord = e.getX() - 10;
                        int yCoord = e.getY() - 10;
                        boolean isOk = false;
                        for (Vertex node: vertices) {
                            if (Math.pow((double)(node.center.x - xCoord), 2)
                                    + Math.pow((double)(node.center.y - yCoord), 2) <= Math.pow((double)rad + 5, 2)) {
                                isOk = true;
                                bufferNodeFrom = node;
                                haveFirstNode = true;
                                isSelected = true;
                                repaint();
                                break;
                            }
                        }
                        if (isOk) {
                            //everything is ok :^)
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "There is no vertex!");
                            haveFirstNode = false;
                        }
                    }
                    else {
                        int xCoord = e.getX() - 10;
                        int yCoord = e.getY() - 10;
                        boolean isOk = false;
                        for (Vertex node: vertices) {
                            if (Math.pow((double)(node.center.x - xCoord), 2)
                                    + Math.pow((double)(node.center.y - yCoord), 2) <= Math.pow((double)rad + 5, 2)) {
                                isOk = true;
                                bufferNodeTo = node;
                                break;
                            }
                        }
                        if (isOk) {
                            isSelected = false;
                            haveFirstNode = false;
                            boolean edgeNotExist = true;
                            for (Edge edge: edgesList) {
                                if ((bufferNodeFrom.nodeName.equals(edge.from) && bufferNodeTo.nodeName.equals(edge.to)) ||
                                        (bufferNodeTo.nodeName.equals(edge.from) && bufferNodeFrom.nodeName.equals(edge.to))) edgeNotExist = false;
                            }
                            if (edgeNotExist) {

                                if (bufferNodeFrom == bufferNodeTo) {
                                    ArrayList<Edge> ggwp = new ArrayList<>();
                                    for (Edge edge: edgesList) {
                                        if (bufferNodeFrom.nodeName.equals(edge.from) || bufferNodeFrom.nodeName.equals(edge.to)) {
                                            ggwp.add(edge);
                                        }
                                    }
                                    for (Edge edgeToDel: ggwp) {
                                        edgesList.remove(edgeToDel);
                                    }
                                    vertices.remove(bufferNodeFrom);

                                    ArrayList<Connection> ggwp1 = new ArrayList<>();
                                    for (Connection line: connections) {
                                        if (bufferNodeFrom.center == line.from || bufferNodeFrom.center == line.to) {
                                            ggwp1.add(line);
                                        }
                                    }
                                    for (Connection lineToDel: ggwp1) {
                                        connections.remove(lineToDel);
                                    }

                                    repaint();
                                }
                                else {
                                    String weight = JOptionPane.showInputDialog("Input edge weight: ","0");
                                    edgesList.add(new Edge(bufferNodeFrom.nodeName, bufferNodeTo.nodeName, Integer.parseInt(weight)));
                                    connections.add(new Connection(bufferNodeFrom.center, bufferNodeTo.center, weight));
                                    haveFirstNode = false;
                                    repaint();
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Edge already exists!");
                            }

                        }
                        else {
                            JOptionPane.showMessageDialog(null, "There is no vertex!");
                            haveFirstNode = false;
                            isSelected = false;
                        }
                    }
                }
                else if (SwingUtilities.isMiddleMouseButton(e)) {
                    startNode = JOptionPane.showInputDialog(null, "Input start vertex: ");
                    boolean isStartExist = false;
                    for (Vertex node: vertices) {
                        if (node.nodeName.equals(startNode)) {
                            isStartExist = true;
                           // startV = node;
                            break;
                        }
                    }
                    if (!isStartExist) {
                        JOptionPane.showMessageDialog(null, "Start: vertex doesn't exist");
                    }
                    else {
                        goalNode = JOptionPane.showInputDialog(null, "Input finish vertex: ");
                        boolean isFinishExist = false;
                        for (Vertex node1: vertices) {

                            if (node1.nodeName.equals(goalNode)) {
                                isFinishExist = true;
                                //finishV = node1;
                                break;
                            }
                        }
                        if (!isFinishExist) {
                            JOptionPane.showMessageDialog(null, "Finish: vertex doesn't exist");
                        }
                        else {
                            isAdded = true;
                            repaint();
                        }
                    }

                }
            }
        });
    }


    public void clearAll() {
        vertices.clear();
        connections.clear();
        edgesList.clear();
        startNode = "";
        goalNode = "";
        isSelected = false;
        isAdded = false;
        inputHeuristic = false;
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.LIGHT_GRAY);

        for (Vertex node : vertices) {
            if (node.nodeName.equals(startNode) & isAdded) {
                g2.setColor(Color.GREEN);
                g2.fillOval(node.center.x, node.center.y, 2*rad, 2*rad);

            }
            else if (node.nodeName.equals(goalNode) & isAdded) {
                g2.setColor(Color.RED);
                g2.fillOval(node.center.x, node.center.y, 2*rad, 2*rad);
            }
            else {
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillOval(node.center.x, node.center.y, 2*rad, 2*rad);
            }
        }
        g2.setColor(Color.MAGENTA);
        if (isSelected) {
            g2.fillOval(bufferNodeFrom.center.x, bufferNodeFrom.center.y, 2*rad, 2*rad);
        }

        g2.setColor(Color.LIGHT_GRAY);
        for (Connection arrow: connections) {
            g2.drawLine(arrow.from.x + 10, arrow.from.y + 10, arrow.to.x + 10, arrow.to.y + 10);
            double  beta = Math.atan2(arrow.from.y - arrow.to.y,
                                       arrow.to.x - arrow.from.x); //{ArcTan2 ищет арктангенс от x/y что бы неопределенностей не
            //  возникало типа деления на ноль}
            double alpha = Math.PI/15;// {угол между основной осью стрелки и рисочки в конце}
            int r1 = 20; //{длинна риски}

            int x2 =(int) Math.round(arrow.to.x - r1*Math.cos(beta + alpha));
            int y2 =(int)Math.round(arrow.to.y + r1*Math.sin(beta + alpha));
            //g2d.drawLine(x1,y1,x2,y2);
            Polygon a = new Polygon();
            a.addPoint(x2 + 10, y2 +10);
            x2 =(int) Math.round(arrow.to.x - r1*Math.cos(beta - alpha));
            y2 =(int)Math.round(arrow.to.y + r1*Math.sin(beta - alpha));
            //g2d.drawLine(x1,y1,x2,y2);
            a.addPoint(x2 + 10, y2 + 10);
            a.addPoint(arrow.to.x + 10, arrow.to.y +10);
            g2.drawPolygon(a);
            g2.fillPolygon(a);
        }

        g2.setColor(Color.MAGENTA);
        for (Connection arrow: connections) {
            g2.drawString(arrow.weight, (float)((arrow.from.x + arrow.to.x)/2 + 10), (float)((arrow.from.y + arrow.to.y)/2 + 10));
        }

        if (inputHeuristic) {
            g2.setColor(Color.MAGENTA);
            for (Vertex node: vertices) {
                g2.drawString(node.nodeName + " / " + node.heuristicValue, node.center.x , node.center.y );
            }
        }
        else {
            g2.setColor(Color.MAGENTA);
            for (Vertex node: vertices) {
                g2.drawString(node.nodeName, node.center.x , node.center.y );
            }

        }



    }
}

class Vertex {
    public Point center;
    public String nodeName;
    public int heuristicValue;

    public Vertex(Point point, String name, int hValue) {
        this.center = point;
        this.nodeName = name;
        this.heuristicValue = hValue;
    }
}



class Edge {
    public String from;
    public String to;
    public int weight;

    public Edge(String from, String to, int value) {
        this.from = from;
        this.to = to;
        this.weight = value;
    }
}

class Connection {
    public Point from;
    public Point to;
    public String weight;

    public Connection(Point from, Point to, String weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}