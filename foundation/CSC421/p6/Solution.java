/*
 *written by Raymond Elward
 *for CSC 421
 *on 2/17/2011
 *to run:
 *-javac solution.java
 *-java TestClient
 ****must be in the same folder as TestClient.java/TestClient.class
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution {

    static int n;
    static double[][] I;//input table
    static double[][][] M;//table with best cost
    static int[][][] S;//table tracing info back.
    static String answer;
    static boolean ans;
    static int ansNum;
    static int kBreak;

    public static void run(BufferedReader in, PrintWriter out) throws IOException {

        String input = "";
        int count = 0;
        while ((input = in.readLine()) != null) {
            if (input.isEmpty() || input.contains("\n")) {
                break;
            }
            n = Integer.parseInt(input);
            createInputTable(in);
            fillTable();
            count++;
            System.out.println(count);

            //printToConsole();
            printAnswer(out);

        }
        out.println();
    }

    private static void createInputTable(BufferedReader in) throws IOException {
        I = new double[n][n];
        for (int i = 0; i < n; i++) {
            String line = in.readLine();
            StringTokenizer lineBreak = new StringTokenizer(line, " ");
            for (int j = 0; j < n; j++) {
                I[i][j] = Double.parseDouble(lineBreak.nextToken());
            }
        }
    }
    private static void fillTable() {
        M = new double[n][n][n];
        S = new int[n][n][n];
        ans = false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                M[0][i][j] = I[i][j];
            }
        }
        fill:
        for (int k = 1; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    double value = 0;
                    int sValue = -1;
                    for (int x = 0; x < n; x++) {
                        double temp = M[k - 1][i][x] * I[x][j];

                        if (temp > value) {
                            value = temp;
                            S[k][i][j] = x + 1;
                        }
                    }
                    M[k][i][j] = value;
                    if (i == j && value > 1.01) {
                        //System.out.println("Answer:" + value);
                        ansNum = i + 1;
                        kBreak = k;
                        ans = true;
                        break fill;

                    }
                }
            }
        }
    }

    private static void printAnswer(PrintWriter out) {
        if (ans == false) {
            out.println("none");
            return;
        }
        Stack<Integer> list = new Stack<Integer>();
        list.add(ansNum);
        int temp, yLookup = ansNum -1;
        while (kBreak > 0) {
            temp = S[kBreak][ansNum - 1][yLookup];
            list.add(temp);
            kBreak--;
            yLookup = temp - 1;
        }
        list.add(ansNum);
        ArrayList<Integer> list2 = new ArrayList<Integer>();

        while (!list.empty()){
            list2.add(list.pop());
        }
        String blah = "";
        for (Integer i : list2){
            blah += (i + " ");
        }

        out.println(blah);

    }
}
