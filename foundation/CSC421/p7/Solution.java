/*
 *written by Raymond Elward
 *for CSC 421
 *on 3/6/2011
 *to run:
 *-javac solution.java
 *-java TestClient
 ****must be in the same folder as TestClient.java/TestClient.class
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Solution {

    static int n;
    static int R;
    static int startX;
    static int startY;
    static int endX;
    static int endY;
    static boolean[][] table;
    static int[][] value;
    static char[][] direction;
    static char[][] parent;
    static Color[][] color;

    public static void run(BufferedReader in, PrintWriter out) throws IOException {

        String input = "";

        while ((input = in.readLine()) != null) {
            if (input.isEmpty() || input.contains("\n")) {
                break;
            }
            n = Integer.parseInt(input);
            R = Integer.parseInt(in.readLine());
            initializeTable();
            for (int i = 0; i < R; i++) {
                StringTokenizer tokens = new StringTokenizer(in.readLine(), " ");
                int x = Integer.parseInt(tokens.nextToken());
                while (tokens.hasMoreTokens()) {
                    int y = Integer.parseInt(tokens.nextToken());
                    table[x][y] = true;
                }
            }
            String start = in.readLine();
            StringTokenizer startTokens = new StringTokenizer(start, " ");
            startX = Integer.parseInt(startTokens.nextToken());
            startY = Integer.parseInt(startTokens.nextToken());


            String end = in.readLine();
            StringTokenizer endTokens = new StringTokenizer(end, " ");
            endX = Integer.parseInt(endTokens.nextToken());
            endY = Integer.parseInt(endTokens.nextToken());

            solve();

            
            traceBack(out);



        }
        out.flush();
        out.println();
        out.flush();
    }

    private static void initializeTable() {
        table = new boolean[n][n];
        value = new int[n][n];
        direction = new char[n][n];
        color = new Color[n][n];
        parent = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = false;
                color[i][j] = Color.WHITE;
                parent[i][j] = 'Q';

            }
        }
    }

    private static void solve() {
        Queue<GridPos> q = new LinkedList<GridPos>();

        parent[endX][endY] = 'E';
        parent[startX][startY] = 'N';
        color[startX][startY] = Color.GREY;
        value[startX][startY] = 0;

        q.add(new GridPos(startX, startY));
        while (!q.isEmpty()) {
            GridPos curPos = q.poll();
            int row = curPos.row;
            int col = curPos.col;

            
            if (col + 1 < n && !table[row][col + 1] && color[row][col + 1] == Color.WHITE) {
                color[row][col + 1] = Color.GREY;
                value[row][col + 1] = value[row][col] + 1;
                parent[row][col + 1] = 'L';
                q.add(new GridPos(row, col + 1));
            }
            if (col - 1 >= 0 && !table[row][col - 1] && color[row][col - 1] == Color.WHITE) {
                color[row][col - 1] = Color.GREY;
                value[row][col - 1] = value[row][col] + 1;
                parent[row][col - 1] = 'R';
                q.add(new GridPos(row, col - 1));
            }
            if (row + 1 < n && !table[row + 1][col] && color[row + 1][col] == Color.WHITE) {
                color[row + 1][col] = Color.GREY;
                value[row + 1][col] = value[row][col] + 1;
                parent[row + 1][col] = 'U';
                q.add(new GridPos(row + 1, col));
            }
            if (row - 1 >= 0 && !table[row - 1][col] && color[row - 1][col] == Color.WHITE) {
                color[row - 1][col] = Color.GREY;
                value[row - 1][col] = value[row][col] + 1;
                parent[row - 1][col] = 'D';
                q.add(new GridPos(row - 1, col));
            }
            color[row][col] = Color.BLACK;
            if (row == endX && col == endY) {
                break;
            }


        }



    }

    private static void traceBack(PrintWriter out) {
        int row = endX;
        int col = endY;

        List<Character> dir = new ArrayList<Character>();

        while (true) {
            char direct = parent[row][col];
            if (direct == 'N') {
                break;
            } else if (direct == 'U') {
                dir.add('D');
                row = row - 1;
            } else if (direct == 'L') {
                dir.add('R');
                col = col - 1;
            } else if (direct == 'D') {
                dir.add('U');
                row = row + 1;
            } else if (direct == 'R') {
                dir.add('L');
                col = col + 1;
            }
        }
        Collections.reverse(dir);
        StringBuilder answer = new StringBuilder();
        for (Character s : dir) {
            answer.append(s);
        }
        
        out.println(answer.toString());
        out.flush();

    }
}

class GridPos {

    int row;
    int col;

    GridPos(int x, int y) {
        this.row = x;
        this.col = y;
    }

    public int getX() {
        return row;
    }

    public void setX(int X) {
        this.row = X;
    }

    public int getY() {
        return col;
    }

    public void setY(int Y) {
        this.col = Y;
    }
}

enum Color {

    GREY, WHITE, BLACK
}
