/*
written by Raymond Elward
for CSC 421
on 1/25/2011
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
        ArrayList<Integer> silOne = new ArrayList<Integer>();
        ArrayList<Integer> silTwo = new ArrayList<Integer>();
        ArrayList<Integer> result;
        boolean one = true;
        int input;
        while ((line = in.readLine()) != null) {


            if (one) {
                try {
                    input = Integer.parseInt(line);
                    silOne.add(input);
                    continue;
                } catch (NumberFormatException e) {
                    one = false;
                    continue;
                }
            } else {
                try {
                    input = Integer.parseInt(line);
                    silTwo.add(input);
                    continue;
                } catch (NumberFormatException ex) {
                    one = true;
                }
            }

            try {
                result = merge(silOne, silTwo);
            } catch (IllegalArgumentException e) {
                break;
            }
            for (Integer i : result) {
                out.println(i);
                out.flush();
            }
            out.println();
            out.flush();
            silOne = new ArrayList<Integer>();
            silTwo = new ArrayList<Integer>();
        }
        out.println();
        out.flush();


    }

    static ArrayList<Integer> merge(ArrayList<Integer> s1, ArrayList<Integer> s2) {


        ArrayList<Integer> returnList = new ArrayList<Integer>();
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException();
        }


        int pos1 = 0;
        int pos2 = 0;
        int x1 = -1;
        int y1 = -1;
        int x2 = -1;
        int y2 = -1;
        int lastYreturn = -1;
        int lastY1 = -1;
        int lastY2 = -1;
        int nextX = -1;
        int yAtX = -1;
        while (s1.size() > pos1 || s2.size() > pos2) {

            lastYreturn = yAtX;
            try {
                lastY1 = s1.get(pos1 - 1);
            } catch (Exception e) {
                lastY1 = -1;
            }
            try {
                lastY2 = s2.get(pos2 - 1);
            } catch (Exception e) {
                lastY2 = -1;
            }
            try {
                x1 = s1.get(pos1);
                y1 = s1.get(pos1 + 1);
            } catch (Exception e) {
                x1 = Integer.MAX_VALUE;
                x2 = -1;
            }
            try {
                x2 = s2.get(pos2);
                y2 = s2.get(pos2 + 1);
            } catch (Exception e) {
                x2 = Integer.MAX_VALUE;
                y2 = -1;
            }


            if (x1 < x2) {
                if (lastY2 >= y1) {
                    if (lastY1 > lastY2) {
                        nextX = x1;
                        yAtX = lastY2;
                        pos1 += 2;
                    } else {
                        pos1 += 2;
                        continue;
                    }
                } else {
                    nextX = x1;
                    yAtX = y1;
                    pos1 += 2;
                }
            } else if (x2 < x1) {
                if (lastY1 >= y2) {
                    if (lastY2 > lastY1) {
                        nextX = x2;
                        yAtX = lastY1;
                        pos2 += 2;
                    } else {
                        pos2 += 2;
                        continue;
                    }
                } else {
                    nextX = x2;
                    yAtX = y2;
                    pos2 += 2;
                }
            } else if (x1 == x2) {
                if (y1 > y2) {
                    if (lastYreturn == y1) {
                        pos1 += 2;
                        pos2 += 2;
                        continue;
                    }
                    nextX = x1;
                    yAtX = y1;
                    pos1 += 2;
                    pos2 += 2;
                } else if (y2 > y1) {
                    if (lastYreturn == y2) {
                        pos1 += 2;
                        pos2 += 2;
                        continue;
                    }
                    nextX = x2;
                    yAtX = y2;
                    pos1 += 2;
                    pos2 += 2;
                } else if (y2 == y1) {
                    nextX = x2;
                    yAtX = y2;
                    pos1 += 2;
                    pos2 += 2;
                }
            }

            returnList.add(nextX);
            returnList.add(yAtX);

        }




        return returnList;
    }
}

