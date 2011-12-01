/*
 written by Raymond Elward
 for CSC 421
 on 1/11/2011
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

            inputToParse = new StringTokenizer(line, ",");

            int count = inputToParse.countTokens();
            if (count == 0)
                continue;
            String[] set = new String[count];

            for (int i = 0; i < count; i++) {
                set[i] = (inputToParse.nextToken());
            }
            String[] returnSet = permute(set);

            for (String s : returnSet){
                out.println(s);
                out.flush();
            }
            out.print("\n");
            out.flush();
        }
        out.print("\n");
        out.flush();
    }

      private static String[] permute(String[] set) {
        if (set.length == 1) {
            return set;
        }


        String[] shortArray = new String[set.length - 1];
        int factorial = set.length;
        for (int x = set.length; x > 1; x--) {
            factorial = factorial * (x - 1);
        }
        int returnArrLength = factorial;

        String[] returnArray = new String[returnArrLength];
        
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < set.length; i++) {
            //inner for loop to make a shorter array to permute:
            for (int j = 0; j < set.length; j++){
                if (j < i)
                    shortArray[j] = set[j];
                else if (j == i)
                    continue;
                else if (j > i)
                    shortArray[j-1] = set[j];
            }

            //another inner loop to reatch the one item that was taken out to its permiated friends.
            String[] ans = permute(shortArray);
            for (String permiated : ans) {
                list.add(set[i] + "," + permiated);
            }
        }
        for (int i = 0; i < list.size(); i++){
            returnArray[i] = list.get(i);
        }

        return returnArray;
    }
      
}