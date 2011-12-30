import java.io.*;

public class OutputStream
{
public static void main(String[] args)
{
  try {
	FileWriter fileWriter = new FileWriter("testOutput.txt");
	BufferedWriter writeBuffer = new BufferedWriter(fileWriter);

	writeBuffer.write("testing the write buffer\n");
	writeBuffer.write("Another line testing the buffer\n");
	writeBuffer.write("Call me Ishmael\n");

	writeBuffer.close();
	fileWriter.close();

  } catch (Exception e) { }

} //end main
} //end class OutputStream