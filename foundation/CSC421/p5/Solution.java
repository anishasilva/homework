/*
 *written by Raymond Elward
 *for CSC 421
 *on 1/26/2011
 *to run:
 *-javac solution.java
 *-java solution
 ****must be in the same folder as TestClient.java/TestClient.class
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution {

    public static void run(BufferedReader in, PrintWriter out) throws IOException {


        String line = null;
        ArrayList<Building> set = new ArrayList<Building>();
        
        StringTokenizer input;

        while ((line = in.readLine()) != null) {
            if (line.length() > 3) {
                input = new StringTokenizer(line, " ");
                set.add(new Building(Integer.parseInt(input.nextToken()),
                        Integer.parseInt(input.nextToken()),
                        Integer.parseInt(input.nextToken())));
            } else {
                try {
                    print(set, out);
                    set = new ArrayList<Building>();
                } catch (IllegalArgumentException e) {
                    break;
                }
            }
        }
        out.println();
        out.flush();
    }

    static void print(ArrayList<Building> set, PrintWriter out) {
        if (set == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Integer> result = compositeBuildings(set);

        for (Integer i : result) {
            out.println(i);
            out.flush();
        }
        out.println();
        out.flush();
    }

    static ArrayList<Integer> compositeBuildings(ArrayList<Building> set) {

        if (set.size() == 1) {
            return set.get(0).getSil();
        }

        ArrayList<Building> firstHalf = new ArrayList<Building>();
        ArrayList<Building> secondHalf = new ArrayList<Building>();

        for (int i = 0; i < set.size(); i++) {
            if (i % 2 == 0) {
                firstHalf.add(set.get(i));
            } else {
                secondHalf.add(set.get(i));
            }
        }
        ArrayList<Integer> silOne = compositeBuildings(firstHalf);
        ArrayList<Integer> silTwo = compositeBuildings(secondHalf);

        ArrayList<Integer> result = merge(silOne, silTwo);
        return result;
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

class Building {
    int position;
    int height;
    int width;
    public Building(int position, int height, int width) {
        this.position = position;
        this.height = height;
        this.width = width;
    }
    ArrayList<Integer> getSil() {
        ArrayList<Integer> returnArr = new ArrayList<Integer>();
        returnArr.add(position);
        returnArr.add(height);
        returnArr.add(position + width);
        returnArr.add(0);
        return returnArr;
    }
}
