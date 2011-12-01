/*
 written by Raymond Elward
 for CSC 421
 on 1/13/2011
 to run:
 -javac solution.java
 -java solution
 ****must be in the same folder as TestClient.java/TestClient.class
 */
import java.io.*;
import java.util.*;

public class Solution {

    public static void run(BufferedReader in, PrintWriter out)
            throws IOException {

        String line = null;
        StringTokenizer inputToParse = null;

        while ((line = in.readLine()) != null) {

            inputToParse = new StringTokenizer(line, " ");
            if (inputToParse.countTokens() == 0)
                break;
            String post = postOrder(inputToParse.nextToken(), inputToParse.nextToken());
            
            out.println(post);
            out.flush();
        }

        out.print("\n");
        out.flush();

    }

    public static String postOrder(String preOrder, String inOrder) {
        if (preOrder.length() == 1) {
            return preOrder;
        }

        String root = Character.toString(preOrder.charAt(0));

        int rootPosition = findRootPosition(root, inOrder);


        String leftTreeIn = inOrder.substring(0, rootPosition);
        String rightTreeIn = inOrder.substring(rootPosition + 1);

        String leftTreePre = preOrder.substring(1, leftTreeIn.length() + 1);
        String rightTreePre = preOrder.substring(leftTreeIn.length() + 1);


        String postOrder = "";
        if (leftTreePre.length() > 0 && rightTreePre.length() > 0) {
            postOrder = postOrder(leftTreePre, leftTreeIn) + postOrder(rightTreePre, rightTreeIn) + root;
        } else if (leftTreePre.length() < 1 && rightTreePre.length() > 0) {
            postOrder = postOrder(rightTreePre, rightTreeIn) + root;
        } else if (leftTreePre.length() > 0 && rightTreePre.length() < 1) {
            postOrder = postOrder(leftTreePre, leftTreeIn) + root;
        }

        return postOrder;
    }

    public static int findRootPosition(String root, String inOrder) {
        int position = 0;
        for (int i = 0; i < inOrder.length(); i++) {

            if (root.equals(inOrder.substring(i, i + 1))) {
                position = i;
                break;
            }
        }
        return position;
    }
}
