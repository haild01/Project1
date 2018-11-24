package main;

import java.util.ArrayList;

public class Graph {
private int V; //số đỉnh của đồ thị
private int E; //số cạnh của đồ thị
ArrayList<Edge>  edges = new ArrayList<>();
public Graph(int v, int e, ArrayList<Edge>  edges) {
	V = v;
	E = e;
	this.edges = edges;
}
public int getV() {
	return V;
}
public void setV(int v) {
	V = v;
}
public int getE() {
	return E;
}
public void setE(int e) {
	E = e;
}
public ArrayList<Edge> getEdges() {
	return edges;
}
public void setEdges(ArrayList<Edge> edges) {
	this.edges = edges;
}

}
