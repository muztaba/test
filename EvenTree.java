import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Created by seal on 8/13/14.
 */
public class Main {
    public static void main(String[] args) {
        InputStream stream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(stream);
        PrintWriter out = new PrintWriter(outputStream);
        EvenTree solver = new EvenTree();
        solver.solve(in, out);
        out.close();
    }
}

class EvenTree {
    private int count = 0;
    private boolean[] visited, _visited;

    public void solve(InputReader in, PrintWriter out) {
        int node = in.nextInt();
        int edge = in.nextInt();
        if (node % 2 != 0) {
            out.println(0);
            return;
        }
        ArrayList[] g = new ArrayList[node + 2];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < edge; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            g[u].add(v);
            g[v].add(u);
        }
        int ans = 0;

        _visited = new boolean[node + 2];
        for (int i = 1; i <= node; i++) {
            _visited[i] = true;
            for (int j = 0; j < g[i].size(); j++) {
                count = 0;
                visited = new boolean[node + 2];
                int u = (Integer) g[i].get(j);
                if (!_visited[u]) {
                    visited[i] = true;
                    dfs(u, g);
                    if (count % 2 == 0) ans++;
                }
            }
        }
        out.println(ans);

    }

    private void dfs(int u, ArrayList[] g) {
        count += 1;
        visited[u] = true;
        for (int i = 0; i < g[u].size(); i++) {
            int v = (Integer) g[u].get(i);
            if (!visited[v]) dfs(v, g);
        }
        return;
    }
}

class InputReader {
    private BufferedReader reader;
    private StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream));
        tokenizer = null;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }
}