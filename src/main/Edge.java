package main;

public class Edge {
private int firstPoint;
private int lastPoint;
private int weight;

public Edge(int firstPoint, int lastPoint, int weight) {
	this.firstPoint = firstPoint;
	this.lastPoint = lastPoint;
	this.weight = weight;
}
public int getFirstPoint() {
	return firstPoint;
}
public void setFirstPoint(int firstPoint) {
	this.firstPoint = firstPoint;
}
public int getLastPoint() {
	return lastPoint;
}
public void setLastPoint(int lastPoint) {
	this.lastPoint = lastPoint;
}
public int getWeight() {
	return weight;
}
public void setWeight(int weight) {
	this.weight = weight;
}


}
