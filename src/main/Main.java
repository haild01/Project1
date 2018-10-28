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

private void view2D(int[] graph) {
	for(int i=0;i<graph.length;i++) {
		System.out.print(graph[i]+" ");
	}
}
private void view3D(int[][] graph) {
	for(int i=0;i<graph.length;i++) {
		for(int j=0;j<graph.length;j++) {
			System.out.print(graph[i][j]+" ");
		}
		System.out.println();
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
// Floyd
public  void Floyd(int[][]bg,int start,int finish) {
	int a[][] = new int[bg.length][bg.length];
	for(int i=0;i<bg.length;i++) {
		for(int j =0;j<bg.length;j++) {
			if(i != j && bg[i][j]==0) {
				a[i][j]=INFINITY;
			}else {
				a[i][j]=bg[i][j];
			}
		}
	}
	
	int p[][] = new int[a.length][a.length];
	for(int i =0;i<a.length;i++) {
		for(int j =0;j<a.length;j++) {
		 p[i][j]=-1;
		}
	}
	for(int k= 0;k<a.length;k++) {
		for(int i =0;i<a.length;i++) {
			for(int j =0;j<a.length;j++) {
				if(a[i][k]+a[k][j] < a[i][j]) {
					a[i][j]=a[i][k] + a[k][j];
					p[i][j]=k;
				}
			}
		}
	}

  findRoad(start,finish,p,a);
}

private static void findRoad(int start, int finish,int[][]p,int[][]cost) {
	Main.result="";
	if(cost[start][finish]==INFINITY) {
		result="Không tồn tại đường đi từ "+(start+1)+" đến "+(finish+1);
	}else {
		ArrayList list = new ArrayList<>();
		list.add(start);
		list.add(finish);

		int k =p[start][finish];
		while(k!=-1 ) {
			list.add(1, k);
			int tmp = (int)list.get(0);
			k = p[tmp][k];
		}
	
		for(int m =0;m<p.length;m++) {
			for(int i=list.size()-1;i>0;i--) {
				int t1=(int)list.get(i);
				int t2=(int)list.get(i-1);
				    k =p[t2][t1];
				   while(k!=-1) {
						list.add(i, k);
						int t=(int)list.get(i);
						k = p[k][t]; 
				   }
				}
		}
		int COST=cost[start][finish];
		Main.cost="Chi phí là: "+COST;
		for(int i=0;i<list.size();i++) {
			int tmp =(int)list.get(i) +1;
			if(i!=list.size()-1) {
				Main.result+=tmp+" → ";
			}else {
				Main.result+=tmp;
			}
			
		}
			
	}
	
	
}


}
