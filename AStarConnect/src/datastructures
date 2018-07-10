package datastructures;

import java.util.*;
import org.graphstream.graph.*;

import javax.management.Attribute;


public class Graph<T> {

    private List<Vertex<T>> allVertices = new ArrayList<Vertex<T>>();
    private List<Edge<T>> allEdges = new ArrayList<Edge<T>>();
    public String comments;

    public Graph() {
    }


    public org.graphstream.graph.Graph getVisualization() {
        org.graphstream.graph.Graph graphPic = new org.graphstream.graph.implementations.SingleGraph("graphPic", true, true);
        graphPic.addAttribute("ui.label");
        graphPic.addAttribute("ui.stylesheet",
                "graph{ fill-color: white; } " +
                        "node{ size: 30px, 30px;" +
                        "fill-color: gray;" +
                        "text-size: 16px;" +
                        "text-color: black;" +
                        "text-style: bold;" +
                        "shape: circle;" +
                        "size-mode: fit; }" +
                        "edge{ text-size: 16px;" +
                        "text-color: black;" +
                        "text-style: bold; }");
        for (Vertex<T> vertex: allVertices) {
            String name = vertex.value.toString();
            Node node = graphPic.addNode(name);
            node.addAttribute("ui.label", name);
        }
        for (Edge<T> edge: allEdges) {
            org.graphstream.graph.Edge edg = graphPic.addEdge(edge.from.value.toString() + edge.to.value.toString() + edge.getCost(), edge.from.value.toString(), edge.to.value.toString(), true);
            edg.addAttribute("ui.label", edge.cost);
        }

        return graphPic;
    }


    /**
     * Creates a datastructures.Graph from the vertices and edges.
     *
     * NOTE: Duplicate vertices and edges ARE allowed.
     * NOTE: Copies the vertex and edge objects but does NOT store the Collection parameters itself.
     *
     * @param vertices Collection of vertices
     * @param edges Collection of edges
     */

    public Graph(Collection<Vertex<T>> vertices, Collection<Edge<T>> edges) {
        this.allVertices.addAll(vertices);
        this.allEdges.addAll(edges);
    }

    public List<Vertex<T>> getVertices() {
        return allVertices;
    }

    public Vertex<T> getVertex(Vertex<T> v) {
        for (Vertex<T> b : allVertices) {
            if (b.equals(v))
                return b;
        }
        return null;
    }
    public Vertex<T> findVertex(T v) {
        for (Vertex<T> b : allVertices) {
            if (b.getValue().equals(v))
                return b;
        }
        return null;
    }
    public List<Edge<T>> getEdges() {
        return allEdges;
    }

    public static class Vertex<T>  {

        private T value = null;
        private List<Edge<T>> edges = new ArrayList<Edge<T>>();

        @SuppressWarnings("unchecked")
        public Vertex(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void addEdge(Edge<T> e) {
            edges.add(e);
        }

        public List<Edge<T>> getEdges() {
            return edges;
        }

        public Edge<T> getEdge(Vertex<T> v) {
            for (Edge<T> e : edges) {
                if (e.to.equals(v))
                    return e;
            }
            return null;
        }

        @Override
        public int hashCode() {
            final int code = this.value.hashCode() + this.edges.size();
            return 31 * code;
        }

        @Override
        public boolean equals(Object v1) {
            if (!(v1 instanceof Vertex))
                return false;

            final Vertex<T> v = (Vertex<T>) v1;

            final boolean edgesSizeEquals = this.edges.size() == v.edges.size();
            if (!edgesSizeEquals)
                return false;

            final boolean valuesEquals = this.value.equals(v.value);
            if (!valuesEquals)
                return false;

            final Iterator<Edge<T>> iter1 = this.edges.iterator();
            final Iterator<Edge<T>> iter2 = v.edges.iterator();
            while (iter1.hasNext() && iter2.hasNext()) {
                // Only checking the cost
                final Edge<T> e1 = iter1.next();
                final Edge<T> e2 = iter2.next();
                if (e1.cost != e2.cost)
                    return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return this.getValue().toString();
        }
    }

    public static class Edge<T>  {

        private Vertex<T> from = null;
        private Vertex<T> to = null;
        private int cost = 0;

        public Edge(int cost, Vertex<T> from, Vertex<T> to) {
            if (from == null || to == null)
                throw (new NullPointerException("Both 'to' and 'from' vertices need to be non-NULL."));

            this.cost = cost;
            this.from = from;
            this.to = to;
        }

        public int getCost() {
            return cost;
        }

        public void setFrom(Vertex<T> from) {
            this.from = from;
        }

        public void setTo(Vertex<T> to) {
            this.to = to;
        }

        public Vertex<T> getFromVertex() {
            return from;
        }

        public Vertex<T> getToVertex() {
            return to;
        }

        @Override
        public int hashCode() {
            final int cost = (this.cost * (this.getFromVertex().hashCode() * this.getToVertex().hashCode()));
            return 31 * cost;
        }

        @Override
        public boolean equals(Object e1) {
            if (!(e1 instanceof Edge))
                return false;

            final Edge<T> e = (Edge<T>) e1;

            final boolean costs = this.cost == e.cost;
            if (!costs)
                return false;

            final boolean from = this.from.equals(e.from);
            if (!from)
                return false;

            final boolean to = this.to.equals(e.to);
            if (!to)
                return false;

            return true;
        }

    }

}
