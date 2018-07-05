import algorithm.AStar;
import datastructures.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Application {
    private static String start,goal;
    private static List<Graph.Edge<String>> edges = new ArrayList<>();
    private static Set<Graph.Vertex<String>> vertices = new HashSet<>();

    public static void main(String[] args)throws FileNotFoundException {
        readDataFromFile(new Scanner(new File("D:\\Учёба\\сем4\\AStar\\src\\input.txt")));
        Graph<String> graph = new Graph<>(vertices,edges);
        AStar<String> astar = new AStar<>();
        List<Graph.Edge<String>> path = astar.aStar(graph,graph.findVertex(start),graph.findVertex(goal));
        for (Graph.Edge<String> v: path){
            if(v.getFromVertex().getValue().equals(start)){
                System.out.print(start);
                System.out.print(v.getToVertex().getValue());
                continue;
            }

            System.out.print(v.getToVertex().getValue());
        }
    }
   @SuppressWarnings("unchecked")
    private static void readDataFromFile(Scanner scanner){
        start = scanner.next();
        goal  = scanner.next();
        while(scanner.hasNext()){
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
    }
}
