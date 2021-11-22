import java.io.*;
import java.util.*; 
public class Mountain {
	static long[] areas;
	public static void main(String[]args) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader("mountains.in"));
		StringTokenizer s = new StringTokenizer(br.readLine());
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("mountains.out")));
		int n = Integer.parseInt(s.nextToken());
		Point[] points = new Point[n];
		for(int i = 0; i < n; i++) {
			s = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(s.nextToken());
			int y = Integer.parseInt(s.nextToken());
			points[i] = new Point(x, y);
		}
		Point[][] trianglePoints = new Point[n][3];
		for(int i = 0; i < n; i++) {
			trianglePoints[i][0] = points[i];
			trianglePoints[i][1] = new Point(points[i].x - points[i].y, 0);
			trianglePoints[i][2] = new Point(points[i].x + points[i].y, 0);
		}
		int count = 0;
		boolean containsPoint = false;
		for(int i = 0; i < n; i++) {
			containsPoint = false;
			for(int j = 0; j < n && !containsPoint; j++) {
					if(i!=j && containsPoint(points[j],points[i]))
						containsPoint = true;
					if(j == n - 1 && !containsPoint) 
						count++;
			}
		}
		out.println(count);
		out.close();
	}
	static boolean containsPoint(Point point, Point point2) {
		if (point2.y <= point2.x + (point.y - point.x) && point2.y <= (-1 * point2.x) + (point.y + point.x))
			return true;
		return false;
	}
	
	static long areaOfTriangle(Point[] triangle) {
		long area = (long) (0.5 * Math.abs((triangle[0].x - triangle[2].x) * (triangle[1].y - triangle[0].y) - (triangle[0].x - triangle[1].x) * 
				(triangle[2].y - triangle[0].y)));
		return area;
	}

	static class Point {
		long x, y;
		Point(long x, long y) {
			this.x = x;
			this.y = y;
		}
		
		public String toString() {
			return "(" + x + ", " + y + " )";
		}
	}
}