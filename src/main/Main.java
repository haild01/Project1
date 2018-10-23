package main;
import java.util.ArrayList;

public class Main{
	static int INFINITY = 99999; 
	static String result="",cost="";
public static void main(String []agrs) {
	new DrawGraphics();
}

//bellman ford algorithm
public void bellmanford(Graph g, int source,int finish) {	
	int i, j, u, v, w;
	int tV = g.getV(); //tổng số đỉnh của đồ thị
	int tE = g.getE(); //tổng số cạnh
	//B1: Khởi tạo
	int d[] = new int[tV];  
	int p[] = new int[tV]; 
	for (i = 0; i < tV; i++) {
		d[i] = INFINITY;
		p[i] = 0;
	}
	d[source] = 0;
	//B2: Tìm đường
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
   //B3: Kiểm tra chu trình âm
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
	timduong(p,source,finish,d);
	
}

//dijkstra algorithm
public  void dijkstra(int[][] graph, int source, int finish){
	 int dist[] = new int[graph.length];  // chi phí 
     int p[] = new int[graph.length];	  // đường đi
     Boolean visit[] = new Boolean[graph.length]; 
     for (int i = 0; i < graph.length; i++) { 
         dist[i] = INFINITY; 
         visit[i] = false; 
     } 
     dist[source] = 0; 
     for (int i = 0; i < graph.length-1; i++) { 
         int u = minDistance(dist, visit); 
         visit[u] = true; // đánh dấu đỉnh đã thăm
         for (int v = 0; v < graph.length; v++) 
             if (!visit[v] && graph[u][v]!=0 && dist[u] != INFINITY && dist[u]+graph[u][v] < dist[v]) {
             	dist[v] = dist[u] + graph[u][v]; 
             	p[v]= u;
             }
                 
     }
     view(p);
	timduong(p, source, finish,dist);
}
int minDistance(int dist[], Boolean visit[]) {  // tìm đỉnh gần nhất 
    int min = INFINITY, min_index=-1;
    for (int i = 0; i < visit.length; i++) 
        if (visit[i] == false && dist[i] <= min) {  // nếu đỉnh chưa thăm và kc nhỏ hơn kcnn
            min = dist[i]; 
            min_index = i; 
        } 
    return min_index; 
}

private void view(int[] graph) {
	for(int i=0;i<graph.length;i++) {
		System.out.print(graph[i]+" ");
	}
}

private void timduong(int[] p,int source, int finish,int []d) {
	int check = 0;
	result="";
	int i =finish;
	ArrayList<Object> roadlist = new ArrayList<>();
	roadlist.add(finish);
	while(p[i] !=source && check < p.length+10) {
		i=p[i];
		roadlist.add(i);
		check++;
	}
	roadlist.add(source);
	if(source==finish) {
		result="Điểm đầu trùng điểm cuối";
	}else if(check>p.length || d[finish]==INFINITY) {
		result="Không tồn tại đường đi từ "+(source+1)+" đến "+(finish+1);
	}else {
		for(int j=roadlist.size()-1;j>0;j--) {
			int temp = (int) roadlist.get(j)+1;
			String kq =temp+" → ";
			result+=kq;
		}
		result+=(finish+1)+"";	
		cost="Chi phí là: "+d[finish];
	}
}
}
