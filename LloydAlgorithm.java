import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by seal on 10/28/14.
 */
public class LloydAlgorithm {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			points[i] = new Point(in.nextInt(), in.nextInt());
		}
		double start = System.currentTimeMillis();
		KMeans solver = new KMeans();
		solver.solve(points, k);
		System.out.println((System.currentTimeMillis() - start) / 1000);
	}
}

class KMeans {
	Point[] kp;
	private int k;
	private double[][] dist;
	private boolean group[][];
	private boolean prevGroup[][];

	public void solve(Point[] points, int k) {
		init(points, k);
		do {
			copyArray();
			/**
			 calculate the distance between k centers and all other points
			 and keep them to the array 'dist'
			 */
			for (int i = 0; i < kp.length; i++) {
				for (int j = 0; j < points.length; j++) {
					dist[i][j] = pointDist(kp[i], points[j]);
				}
			}
			/*
			  make the boolean array 'group' false
			 */
			makeFalseGroup();
			/*
			  update the boolean array 'group' with the new center
			 */
			for (int i = 0; i < dist[0].length; i++) {
				double min = Double.MAX_VALUE;
				int j, p = 0;
				for (j = 0; j < dist.length; j++) {
					if (min > dist[j][i]) {
						p = j;
						min = dist[j][i];
					}
				}
				group[p][i] = true;
			}
			updateCenter(points);
		} while (!isMatch());
		printCluster();
	}

	public void makeFalseGroup() {
		for (boolean[] row : group) {
			Arrays.fill(row, false);
		}
	}

	public void updateCenter(Point[] p) {
		double u, v;
		u = v = 0;
		for (int i = 0; i < group.length; i++) {
			int t = 0;
			for (int j = 0; j < group[0].length; j++) {
				if (group[i][j]) {
					u += p[j].x;
					v += p[j].y;
					t++;
				}
			}
			if (t > 0) kp[i].update(u / t, v / t);
		}
	}

	public void copyArray() {
		for (int i = 0; i < group.length; i++) {
			for (int j = 0; j < group[i].length; j++) {
				prevGroup[i][j] = group[i][j];
			}
		}
	}

	public void init(Point[] p, int k) {
		int l = p.length;
		dist = new double[k][l];
		group = new boolean[k][l];
		prevGroup = new boolean[k][l];
		kp = new Point[k];
		for (int i = 0; i < kp.length; i++) {
			kp[i] = new Point(p[i].x, p[i].y);
		}
	}

	public double pointDist(Point a, Point b) {
		double x = a.x - b.x;
		double y = a.y - b.y;
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public boolean isMatch() {
		for (int i = 0; i < group.length; i++) {
			for (int j = 0; j < group[i].length; j++) {
				if (group[i][j] != prevGroup[i][j])
					return false;
			}
		}
		return true;
	}

	public void printCluster() {
		List<Integer> l;
		for (int i = 0; i < group.length; i++) {
			l = new ArrayList<>();
			for (int j = 0; j < group[0].length; j++) {
				if (group[i][j]) l.add(j + 1);
			}
			System.out.println("Cluster " + (i + 1));
			for (int p : l) System.out.print(p + " ");
			System.out.println();
			for (int p = 0; p < l.size(); p++) System.out.print("*");
			System.out.println();
		}
	}
}

class Point {
	public double x, y;

	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void update(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
