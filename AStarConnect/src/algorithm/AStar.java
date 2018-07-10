package algorithm;

import datastructures.Graph;
import datastructures.Graph.Vertex;
import datastructures.Graph.Edge;
import swing.AppLauncher;

import java.io.*;
import java.util.*;

public class AStar<T> {
    private boolean h;//true - general,false - discrete

    public AStar() {
        this(true);
    }

    public AStar(boolean h) {
        this.h = h;
    }

    public void setH(boolean h) {
        this.h = h;
    }

    /**
     * Find the path using the A* algorithm from start vertex to end vertex or NULL if no path exists.
     *
     * @param graph
     *          Graph to search.
     * @param start
     *          Start vertex.
     * @param goal
     *          Goal vertex.
     *
     * @return
     *          List of Edges to get from start to end or NULL if no path exists.
     */

    private Graph<T> graph;
    private Graph.Vertex<T> start;
    private Graph.Vertex<T> goal;

    private int size;
    private Set<Graph.Vertex<T>> closedSet;
    private List<Graph.Vertex<T>> openSet;
    private  Map<Graph.Vertex<T>,Graph.Vertex<T>> cameFrom;
    private Map<Graph.Vertex<T>,Integer> gScore;
    private Map<Graph.Vertex<T>,Integer> fScore;

    public void sethScore(Map<String, Integer> hScore) {
        this.hScore = hScore;
    }

    private Map<String,Integer> hScore;


    private Comparator<Graph.Vertex<T>> comparator = new Comparator<>() {
        @Override
        public int compare(Vertex<T> o1, Vertex<T> o2) {
            if (fScore.get(o1) < fScore.get(o2))
                return -1;
            if (fScore.get(o2) < fScore.get(o1))
                return 1;
            return 0;
        }
    };

    public List<Graph.Edge<T>> aStar(Graph<T> graph, Graph.Vertex<T> start, Graph.Vertex<T> goal) throws IOException {
        this.graph = graph;
        this.start = start;
        this.goal = goal;

        size = graph.getVertices().size(); // used to size data structures appropriately
        closedSet = new HashSet<>(size); // The set of nodes already evaluated.
        openSet = new ArrayList<>(size); // The set of tentative nodes to be evaluated, initially containing the start node
        openSet.add(start);
        cameFrom = new HashMap<>(size); // The map of navigated nodes.

        gScore = new HashMap<>(); // Cost from start along best known path.
        gScore.put(start, 0);

        // Estimated total cost from start to goal through y.
        fScore = new HashMap<>();
        for (Graph.Vertex<T> v : graph.getVertices())
            fScore.put(v, Integer.MAX_VALUE);
        fScore.put(start, heuristicCostEstimate(start,goal));

      //FileWriter fileOut = new FileWriter("info.txt", true);

        return null;
    }

    public List<Graph.Edge<T>> calculate(boolean full_work)
    {
        if (full_work)
        {
            graph.comments = "";
            while (!openSet.isEmpty()) {
                List<Graph.Edge<T>> tc = step();

                if (!(tc == null))
                {
                    return tc;
                }
            }
            return null;
        }
        else
        {
            graph.comments = "";

            return step();
        }
    }

    public List<Graph.Edge<T>> step()
    {
        final Graph.Vertex<T> current = openSet.get(0);
        if (current.equals(goal))
            return reconstructPath(cameFrom, goal);
        graph.comments += "Current vertex value: " + current + "\n";

        graph.comments += "List (vertex, f(x)): ";
        for (Graph.Vertex<T> v : openSet) {
            graph.comments += "(" + v.getValue() + "," + fScore.get(v).toString() + ")";
            // fileOut.append("(" + v.getValue()+ " , " + fScore.get(v).toString() + ")");
        }
        graph.comments += "\n";
        // fileOut.append("\n");
        graph.comments += "List (vertex, g(x)): ";
        //  fileOut.append("List (vertex, g(x)): ");
        for (Map.Entry entry : gScore.entrySet()) {
            graph.comments += "(" + entry.getKey().toString() + "," + entry.getValue().toString() + ")";
            //fileOut.append("(" + entry.getKey().toString() + " , " +entry.getValue().toString() + ")");
        }
        graph.comments += "\n";
        graph.comments += "\n";
        // fileOut.append("\n");
        openSet.remove(0);
        closedSet.add(current);
        for (Graph.Edge<T> edge : current.getEdges()) {
            final Graph.Vertex<T> neighbor = edge.getToVertex();
            if (closedSet.contains(neighbor))
                continue; // Ignore the neighbor which is already evaluated.

            final int tenativeGScore = gScore.get(current) + distanceBetween(current, neighbor); // length of this path.
            if (!openSet.contains(neighbor))
                openSet.add(neighbor); // Discover a new node
            else if (tenativeGScore >= gScore.get(neighbor))
                continue;

            // This path is the best until now. Record it!
            cameFrom.put(neighbor, current);
            gScore.put(neighbor, tenativeGScore);
            final int estimatedFScore = gScore.get(neighbor) + heuristicCostEstimate(neighbor, goal);
            fScore.put(neighbor, estimatedFScore);

            // fScore has changed, re-sort the list
            Collections.sort(openSet, comparator);
        }

        return null;
    }

    /**
     * Default distance is the edge cost. If there is no edge between the start and next then
     * it returns Integer.MAX_VALUE;
     */
    private int distanceBetween(Graph.Vertex<T> start, Graph.Vertex<T> next) {
        for (Edge<T> e : start.getEdges()) {
            if (e.getToVertex().equals(next))
                return e.getCost();
        }
        return Integer.MAX_VALUE;
    }


    private int heuristicCostEstimate(Graph.Vertex<T> start, Graph.Vertex<T> goal) {
        if(h) return Math.abs((int)start.getValue().toString().charAt(0) - (int)goal.getValue().toString().charAt(0));
        return hScore.get(start.getValue());//discrete

    }
    /*public Map<Graph.Vertex<T>,Integer> setGScoreforGH(String start,String goal){
        hScore = new HashMap<Graph.Vertex<T>,Integer>
    }*/
    private List<Graph.Edge<T>> reconstructPath(Map<Graph.Vertex<T>,Graph.Vertex<T>> cameFrom, Graph.Vertex<T> current) {
        final List<Graph.Edge<T>> totalPath = new ArrayList<>();

        while (current != null) {
            final Graph.Vertex<T> previous = current;
            current = cameFrom.get(current);
            if (current != null) {
                final Graph.Edge<T> edge = current.getEdge(previous);
                AppLauncher.graph.getNode(current.toString()).addAttribute("ui.style", "fill-color: blue;");
                AppLauncher.graph.getNode(previous.toString()).addAttribute("ui.style", "fill-color: blue;");
                AppLauncher.graph.getEdge(current.toString() + previous.toString() + edge.getCost()).addAttribute("ui.style", "fill-color: blue;");
                totalPath.add(edge);
            }
        }
        Collections.reverse(totalPath);
        return totalPath;
    }
}
