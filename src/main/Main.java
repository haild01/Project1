package main;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main{
	static int INFINITY = 99999; 
	static String result="",cost="",txtNumberRoad="";
	static ArrayList<Vertex> vertexs = new ArrayList<>(); // chứa các đỉnh của đồ thị
	static ArrayList path =null;						  // chứa các đỉnh kết quả
	static ArrayList Kpath =null;						  //chứa k đường đi 
	static int[] parent =null;					          // chứa các đỉnh cha của cây khung nhỏ nhất
public static void main(String []agrs) {
	vertexs.add(new Vertex(60, 239, "1"));
	vertexs.add(new Vertex(268, 252, "2"));
	vertexs.add(new Vertex(339, 59, "3"));
	vertexs.add(new Vertex(550, 127, "4"));
	vertexs.add(new Vertex(360, 417, "5"));
	vertexs.add(new Vertex(121, 125, "6"));
	vertexs.add(new Vertex(493, 340, "7"));
	vertexs.add(new Vertex(140, 405, "8"));
	vertexs.add(new Vertex(643, 204, "9"));
	vertexs.add(new Vertex(550, 37, "10"));
	new DisplayComponent();
}

//bellman-ford algorithm
public void bellmanford(Graph g, int source,int finish) {
	clear(); // reset
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
			// có cạnh nối và khoảng cách cũ lớn hơn khoảng cách mới
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
	displayPath(p,source,finish,d);
	
}
private void displayPath(int[] p,int source, int finish,int []d) {
	int i =finish;
	ArrayList<Object> roadlist = new ArrayList<>();
	roadlist.add(finish);
	int check = 0;
	while(p[i] !=source && check < p.length+10) { // kiểm tra có tồn tại đường đi
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
		path = new ArrayList<>();		// chứa các đỉnh kết quả
		path.clear();
		for(int j=roadlist.size()-1;j>0;j--) {
			int temp = (int) roadlist.get(j)+1;
			path.add(temp-1);
			String kq =temp+" → ";
			result+=kq;
		}
		path.add(finish);
		result+=(finish+1)+"";	
		cost="Chi phí là: "+d[finish];
	}
}
//Thuật toán dijkstra
public  void dijkstra(int[][] graph, int source, int finish){
	clear();
	int check =0; // kiểm tra trọng số âm
	for(int i =0;i<graph.length;i++) {
		for(int j=0;j<graph.length;j++) {
			if(graph[i][j] <0) check++;
		}
	}
	if(check >0) {
	result ="Đồ thị chứa trọng số âm";
	}else {
		 int dist[] = new int[graph.length];  // chi phí 
	     int p[] = new int[graph.length];	  // đường đi
	     Boolean visit[] = new Boolean[graph.length]; 
	     for (int i = 0; i < graph.length; i++) { 
	         dist[i] = INFINITY; 
	         visit[i] = false; 
	     } 
	     dist[source] = 0; 
	     for (int i = 0; i < graph.length; i++) { 
	         int u = minDistance(dist, visit); 
	         visit[u] = true; // đánh dấu đỉnh đã thăm
	         for (int v = 0; v < graph.length; v++) {
	        	 // chưa thăm && có cạnh nối && có gốc && k/c u,v + trọng số < k/c đến v
	        	 if (!visit[v] && graph[u][v]!=0 && dist[u] != INFINITY && dist[u]+graph[u][v] < dist[v]) {
	              	dist[v] = dist[u] + graph[u][v]; 
	              	p[v]= u;
	              } 
	         }
	                    
	     }

	displayPath(p, source, finish,dist); // hiển thị chi tiết đường đi	
	}
	
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

// Floyd
public  void Floyd(int[][]bg,int start,int finish) {
	clear();
	int a[][] = new int[bg.length][bg.length];
	// Bước 1
	for(int i=0;i<bg.length;i++) {  
		for(int j =0;j<bg.length;j++) {
			if(i != j && bg[i][j]==0) {
				a[i][j]=INFINITY;
			}else {
				a[i][j]=bg[i][j];
			}
		}
	}
	 // Bước 2
	int p[][] = new int[a.length][a.length];
	for(int i =0;i<a.length;i++) {
		for(int j =0;j<a.length;j++) {
		 p[i][j]=-1;
		}
	}
	// Bước 3
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
	// Bước 4: hiển thị chi tiết đường đi
	displayPathFloyd(start,finish,p,a); 
}

private static void displayPathFloyd(int start, int finish,int[][]p,int[][]cost) {
	if(cost[start][finish]==INFINITY) {
		result="Không tồn tại đường đi từ "+(start+1)+" đến "+(finish+1);
	}else if(start==finish) {
	result="Điểm đầu trùng điểm cuối";	
	}else {
		ArrayList list = new ArrayList<>();
		list.add(start);
		list.add(finish);

		int k =p[start][finish];
		while(k!=-1 ) { // khởi tạo mảng cơ sở đường đi
			list.add(1, k);
			int tmp = (int)list.get(0);
			k = p[tmp][k];
		}
	
		for(int m =0;m<p.length;m++) { // cập nhật thêm các đỉnh nếu có
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
		path = new ArrayList<>();// chứa các đỉnh kết quả
		path.clear();
		Main.cost="Chi phí là: "+cost[start][finish];
		for(int i=0;i<list.size();i++) {
			int tmp =(int)list.get(i) +1;
			path.add(tmp-1);
			if(i!=list.size()-1) {
				Main.result+=tmp+" → ";
			}else {
				Main.result+=tmp;
			}
			
		}
			
	}
}

// tìm k đường đi ngắn nhất giữa 2 đỉnh
  public void findAllShortestPath(Graph g, int source,int finish) {
	  clear();
	  int i, j, u, v, w;
		int tV = g.getV(); //tổng số đỉnh của đồ thị
		int tE = g.getE(); //tổng số cạnh
		ArrayList<Node> list = new ArrayList<>();
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
				// có cạnh nối và kc cũ lớn hơn = kc mới
				if(d[u] != INFINITY && d[v] >= d[u] + w) {
					if(d[v] > d[u] + w) {
						d[v] = d[u] + w;
						p[v] = u;
					}else { // ==
						if(isInList(list, v)==false) { // nếu chưa tồn tại đường đi 
							Node node = new Node(v); //v== key check
							list.add(node);
							node.mang.add(u);		// u == value add
						}else { // nếu đã tồn tại
							int indexOfKey = getIndex(list,v); // đường đi đã tồn tại qua key
							ArrayList mang = list.get(indexOfKey).mang;
							if(issetValue(u,indexOfKey,mang)==false) { // kiểm tra đỉnh đã tồn tại trên đường đi
								list.get(indexOfKey).mang.add(u);
							}
						}
						
					}
					
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
		
		for(int t =list.size()-1;t>=0;t--) { // xóa mảng trùng trong p
			if(list.get(t).mang.size()<2) {
				list.remove(t);
			}
		}
		String s = displayKPath(source,finish,d,p,list); // Hiển thị chi tiết k đường đi
		result=s;
	
		
  }

private static int getValueOfKey(ArrayList<Node> list, int key) {
	int index = getIndex(list,key);
	int value = (int)list.get(index).mang.get(0);
	list.get(index).mang.remove(0);
	return value;
}
// Hiển thị chi tiết k đường đi
private String displayKPath(int source, int finish, int[] d, int[] p, ArrayList<Node> list) {
	int i =finish;
	int check = 0;
	while(p[i] !=source && check < p.length+10) { // kiểm tra có tồn tại đường đi
		i=p[i];
		check++;
	}
	if(source==finish) {
		return result="Điểm đầu trùng điểm cuối";
	}else if(check>p.length || d[finish]==INFINITY) {
		return result="Không tồn tại đường đi từ "+(source+1)+" đến "+(finish+1);
	}else {
			cost="Chi phí là: "+d[finish]; 
			int totalRow =1;
			for(int k =0;k<list.size();k++) {
				totalRow*=list.get(k).mang.size();
			}
			// khởi tạo mảng chứa danh sách các đường đi có thể có
			ArrayList<Node> total = new ArrayList<>();
			for(int k =0;k<totalRow;k++) {
			Node nk = new Node(k);
			total.add(nk);
			}
			int rank =1; // 
			for(int m =0;m<p.length;m++) {
			int tmp = p[m];
			if(issetKey(m,list)==false) { // không có đỉnh nào khác
				for(int k =0;k<totalRow;k++) {
					total.get(k).mang.add(tmp);
				}
			}else {
				int index =getIndex(list,m); // chỉ số của các đỉnh có đường đi khác
				int size = list.get(index).mang.size(); // số đường đi khác
				int limitAdd = totalRow /(int)Math.pow(size, rank);//giới hạn thêm 
				int jump = limitAdd; // 
				int offset =0;
				rank++;
				for(int k =0;k<totalRow;k++) {
					if(k<limitAdd) {
						int value =(int)list.get(index).mang.get(offset);
						total.get(k).mang.add(value);
					}else {
						offset++;
						limitAdd+=jump;
						if(offset==size) offset=0;
						int value =(int)list.get(index).mang.get(offset);
						total.get(k).mang.add(value);
					}	
				}	
			}	
		}

		// lấy đường đi chi tiết từ tập hợp tất cả đường đi total
		ArrayList roads = new ArrayList<>();
		for(int m =0;m<total.size();m++) {
			ArrayList Roadm = new ArrayList<>(); // tạo mảng lưu 1 đường đi
			ArrayList temp = total.get(m).mang; // lấy ra 1 đường đi
			Roadm.add(finish);
			int value = (int)temp.get(finish);
			while(value !=source) {
				Roadm.add(value);
				value = (int)temp.get(value);
			}
			Roadm.add(source);
			roads.add(Roadm);
		}
		// kiểm tra 2 đường đi có giống nhau
		for(int k =roads.size()-1;k>=0;k--) {
			ArrayList tmp1 = (ArrayList) roads.get(k);
			for(int n =0;n<k;n++) {
				ArrayList tmp2 = (ArrayList) roads.get(n);
				if(checkDuplicate(tmp1,tmp2)==true) {
					roads.remove(k);
					break;
				}
			}
		}
		Kpath = new ArrayList<>();
		Kpath.clear();
		Kpath = roads;
		String totalRoads ="<html>";
		for(int k =0;k<roads.size();k++) {
			ArrayList tmp = (ArrayList) roads.get(k);
			String s="";
			for(int m =tmp.size()-1;m>=0;m--) {
				if(m!=0) {
					int value = (int)tmp.get(m)+1;
					s+=value+" → ";
				}else {
					int value = (int)tmp.get(m)+1;
					s+=value+"<br/>";	
				}
				
			}
			totalRoads+=s;
		}
		totalRoads+="</html>";
		txtNumberRoad = "Tồn tại "+roads.size()+" đường đi ngắn nhất";
		return totalRoads;
	}
	
}



private static boolean checkDuplicate(ArrayList tmp1, ArrayList tmp2) {
	int size1 = tmp1.size();
	int size2 = tmp2.size();
	if(size1 != size2) return false;
	for(int i =0;i<size1;i++) {
		if(tmp1.get(i) != tmp2.get(i)) return false;
	}
	return true;
}

// kiểm tra tồn tại đối tượng trùng key
private static boolean issetKey(int key, ArrayList<Node> list) {  
		for(int i =0;i<list.size();i++) {
			if(list.get(i).key==key ) {
				return true;
			}
		}
	return false;
}

//kiểm tra đỉnh đã tồn tại trong đường đi
private boolean issetValue(int value, int indexOfKey, ArrayList mang) {
	for(int i =0;i<mang.size();i++) {
		if((int)mang.get(i)==value) {
			return true;
		}
	}
	return false;
}
// trả về chỉ số của đường đi đã khởi tạo qua key
private static int getIndex(ArrayList<Node> list, int key) {
	for(int i =0;i<list.size();i++) {
		if(list.get(i).key==key) return i;
	}
	return 0;
	
}

// kiểm tra đã tồn tại đường đi này trước đó qua key
  public static boolean isInList(ArrayList<Node> list,int key) {
		for(int i =0;i<list.size();i++) {
			if(list.get(i).key==key) {
				return true;
			}
		}
		return false;
	}
  
  // Bài toán cây khung nhỏ nhất
  
  //thuật toán Prim
  public static void prim(int graph[][]) { 
	  	clear();
       parent = new int[graph.length];  // lưu đỉnh cha của v
      int key[] = new int [graph.length];  // lưu chi phí 

 
      Boolean mstSet[] = new Boolean[graph.length];      // lưu các đỉnh đã chọn

      // Khởi tạo
      for (int i = 0; i < graph.length; i++) { 
          key[i] = Integer.MAX_VALUE; 
          mstSet[i] = false; 
      } 
      
      key[0] = 0;   // chi phí tới chính nó là 0   
      parent[0] = -1; // đỉnh trước 0 là -1 
      
      for (int i = 0; i < graph.length-1; i++)  { 
          int u = minKey(key, mstSet);  // lấy ra đỉnh có cạnh nhẹ nhất
          mstSet[u] = true; // đánh dấu đỉnh đã chọn
          for (int v = 0; v < graph.length; v++) {
              if (graph[u][v]!=0 && mstSet[v] == false && graph[u][v] < key[v]) { 
                  parent[v] = u; 
                  key[v] = graph[u][v]; 
              } 	
          }
      } 
    int sumCost =0;
    for(int i =0;i<key.length;i++)
    	sumCost+=key[i];
    cost="Độ dài của cây khung là: "+sumCost;
    // tập cạnh của cây khung
    for(int i =1;i<parent.length;i++) {
    	result+="("+(parent[i]+1)+","+(i+1)+")  ";
    }
  }
private static int minKey(int[] key, Boolean[] mstSet) {
    // khởi tạo giá trị
    int min = Integer.MAX_VALUE, min_index=-1; 
    for (int v = 0; v < key.length; v++) {
    	   if (mstSet[v] == false && key[v] < min) {  // đỉnh chưa chọn && < min 
               min = key[v]; 
               min_index = v; 
           } 
    }
    return min_index; 
} 
// thuật toán kruskal
public static  void Kruskal(int bg[][]) {
	clear();
	ArrayList<Edge> allEdges = new ArrayList<>();
	parent=new int[bg.length];
	for(int i=0;i<parent.length;i++)
		parent[i]=-1;
//	gán dữ liệu cho cạnh
	for(int i=0;i<bg.length;i++) {
		for(int j=0;j<bg.length;j++) {
			if(bg[i][j]!=0 && i!=j) {
				Edge edge = new Edge(i, j, bg[i][j]);
				if(!checkEdge(edge,allEdges))
				allEdges.add(edge);
			}
		}
	}
	
   	// khởi tạo hàng đợi ưu tiên đc sắp xếp theo weight
    PriorityQueue<Edge> pq = new PriorityQueue<>(allEdges.size(), 
    						 Comparator.comparingInt(o -> o.getWeight()));

    // thêm tất cả các cạnh vào hàng đợi
    for (int i = 0; i <allEdges.size() ; i++) {
        pq.add(allEdges.get(i));
    }
    	
    // tạo mảng cha đánh dấu
    int [] temp = new int[bg.length];
    for (int i = 0; i <bg.length ; i++) {
    	temp[i] = i;
    }
   
    ArrayList<Edge> mst = new ArrayList<>(); // lưu tập cạnh của CKNN

    int index = 0;
    while(index<bg.length-1){
        Edge edge = pq.remove();
        //kiểm tra cạnh được chọn có tạo chu trình
        int x_set = find(temp, edge.getFirstPoint());
        int y_set = find(temp, edge.getLastPoint());
        
        if(x_set !=y_set){
        	 // thêm vào kq 
            mst.add(edge);
            index++;
            union(temp,x_set,y_set);
        } 
    }   
  // chuyển tập cạnh thành mảng 1 chiều
 for(int i =0;i<mst.size();i++) {
    	int first =mst.get(i).getFirstPoint();
    	int last = mst.get(i).getLastPoint();
    	parent[last]=first;
    }
 // chi phí
 int sumCost =0;
 for(int i =0;i<mst.size();i++) {
	sumCost+=mst.get(i).getWeight(); 
	result+="("+(mst.get(i).getFirstPoint()+1)+","+(mst.get(i).getLastPoint()+1)+")  ";
 }

 cost="Độ dài của cây khung là: "+sumCost;
 
}
// kiểm tra cạnh đã tồn tại trong tập cạnh
private static boolean checkEdge(Edge edge, ArrayList<Edge> allEdges) {
	for(int i =0; i<allEdges.size();i++) {
		Edge tmp = allEdges.get(i);
		if(edge.getFirstPoint()==tmp.getLastPoint() && edge.getLastPoint()==tmp.getFirstPoint())
			return true;
	}
	return false;
}

public static int find(int [] temp, int vertex){
    if(temp[vertex]!=vertex)
        return find(temp, temp[vertex]);;
    return vertex;
}

public static void union(int [] temp, int x, int y){
    int x_set_parent = find(temp, x);
    int y_set_parent = find(temp, y);
    // đặt cha của y_set là x_set
    temp[y_set_parent] = x_set_parent;
}
private static void clear() {
	if(path!=null) path.clear();
	if(Kpath!=null) Kpath.clear();
	if(parent!=null) parent=null;
	result="";
	txtNumberRoad="";
}

}
