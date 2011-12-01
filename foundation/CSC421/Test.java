import java.io.*;

public class Test
{
    public static void main(String[] args) throws Exception
    {
        BufferedReader in = new BufferedReader(new FileReader("in.txt"));
        PrintWriter out = new PrintWriter(new FileWriter("out.txt"));

        Solution.run(in, out);

        in.close();
        out.close();
    }
}
