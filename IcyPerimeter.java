import java.io.*;
import java.util.*; 
public class IcyPerimeter {
	static int a = 0, p = 0, area, per, total; 
	static String[][] matrix; 
	static boolean[][] visited; 
	public static void main(String[] args) throws IOException {
		Scanner s = new Scanner(new File("perimeter.in"));
        PrintWriter pw = new PrintWriter(new File("perimeter.out"));
        int N = s.nextInt(), t = 0; 
        matrix = new String[N][N]; 
        ArrayList<int[]> coords = new ArrayList<int[]>(); 
        s.nextLine(); 
        for (int i = 0; i < N; i ++)
        	matrix[i] = s.nextLine().split("");
        visited = new boolean[N][N]; 
        for (int i = 0; i < N; i ++)
        	for (int j = 0; j < N; j ++)
        		if (matrix[i][j].equals("#")) {
        			coords.add(new int[] {i, j}); 
        			t ++; 
        		}
        for (int[] arr : coords) {
        	int i = arr[0], j = arr[1]; 
        		if (!visited[i][j] && matrix[i][j].equals("#")) {
        			area = per = 0; 
        			dfs(i, j, N); 
        		}
        		if (total == t) 
        			break; 
        }
        pw.println(a + " " + p);
        s.close();
        pw.close();
	}
	public static void dfs(int i, int j, int N) {
		visited[i][j] = true;
		area += 1; 
		total ++; 
		int addP = 4; 
		if (i < N - 1) 
			if(matrix[i + 1][j].equals("#")) 
				addP --; 
		if (i > 0)
			if (matrix[i - 1][j].equals("#")) 
				addP --; 
		if (j < N - 1)
			if (matrix[i][j + 1].equals("#")) 
				addP --; 
		if (j > 0)
			if (matrix[i][j - 1].equals("#")) 
				addP --; 
		per += addP; 
		if (area >= a) {
			if (a == area)
				p = Math.min(per, p); 
			else 
				p = per; 
			a = area;
		}
		if (i < N - 1) 
			if(matrix[i + 1][j].equals("#") && !visited[i + 1][j]) 
				dfs(i + 1, j, N);
		if (i > 0)
			if (matrix[i - 1][j].equals("#") && !visited[i - 1][j]) 
				dfs(i - 1, j, N);
		if (j < N - 1)
			if (matrix[i][j + 1].equals("#") && !visited[i][j + 1]) 
				dfs(i, j + 1, N);
		if (j > 0)
			if (matrix[i][j - 1].equals("#") && !visited[i][j - 1]) 
				dfs(i, j - 1, N);
	}
}
