
import java.io.*;

public class Solution {

    public static void run(BufferedReader in, PrintWriter out)
            throws IOException {
        String line = null;
        int argNum = 0;
        int wordNum = 0;
        String[] arrWord = null;
        while ((line = in.readLine()) != null) {
            try {
                argNum = Integer.parseInt(line);
                if (argNum == 0) {
                    break;
                } else {
                    wordNum = argNum;
                    arrWord = new String[wordNum];
                }
            } catch (NumberFormatException e) {
                arrWord[0] = line;
                for (int i = 1; i < argNum; i++) {
                    arrWord[i] = in.readLine();
                }

                for (int i = argNum - 1; i >= 0; i--) {
                    out.println(arrWord[i]);
                    out.flush();
                }
                out.println();
                out.flush();
            }
        }
    }
}
