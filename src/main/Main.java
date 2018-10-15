package main;

import java.util.ArrayList;

public class Main{
	static int INFINITY = 99999; 
	static String result="";
public static void main(String []agrs) {
	new DrawGraphics();
}
public static void bellmanford(Graph g, int source,int end) {	
	int i, j, u, v, w;
	int tV = g.getV(); //tổng số đỉnh của đồ thị
	int tE = g.getE(); //tổng số cạnh
	//B1
	int d[] = new int[tV];  
	int p[] = new int[tV]; 
	for (i = 0; i < tV; i++) {
		d[i] = INFINITY;
		p[i] = 0;
	}
	d[source] = 0;
	//B2
	for(i = 0; i <tV-1; i++) {
		for(j = 0; j < tE; j++) {
			//get data
			u = g.getEdges().get(j).getFirstPoint();
			v = g.getEdges().get(j).getLastPoint();
			w = g.getEdges().get(j).getWeight();
			if(d[u] != INFINITY && d[v] > d[u] + w) {
				d[v] = d[u] + w;
				p[v] = u;
			}
		}
	}
   //B3
	for(i = 0; i < tE; i++) {
		u = g.getEdges().get(i).getFirstPoint();
		v = g.getEdges().get(i).getLastPoint();
		w = g.getEdges().get(i).getWeight();
		if(d[u] != INFINITY && d[v] > d[u] + w) {
			System.out.println("Đồ thị chứa chu trình âm");
			result="Đồ thị chứa chu trình âm";
			return;
		}
	}
	
	timduong(p,source,end);
	
}

private static void timduong(int[] p,int source, int end) {
	int check = 0;
	result="";
	int i =end;
	ArrayList roadlist = new ArrayList<>();
	roadlist.add(end);
	while(p[i] !=source && check < p.length+10) {
		i=p[i];
		roadlist.add(i);
		check++;
	}
	roadlist.add(source);
	if(source==end) {
		result="Điểm đầu trùng điểm cuối";
	}else if(check>p.length) {
		result="Không tồn tại đường đi từ "+(source+1)+" đến "+(end+1);
	}else {
		for(int j=roadlist.size()-1;j>0;j--) {
			int temp = (int) roadlist.get(j)+1;
			String kq =temp+" → ";
			result+=kq;
		}
		result+=(end+1)+"";	
	}
	
}

}
