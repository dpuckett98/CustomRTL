import java.io.FileWriter;
import java.io.IOException;

public class Util {
	
	public static void ASSERT(boolean test, String onFail) {
		if (!test) {
			System.out.println(onFail);
		}
	}
	
	public static void writeToFile(String fileName, String value) {
		try {
			FileWriter file = new FileWriter(fileName);
			//file.createNewFile();
			file.write(value);
			file.close();
		} catch (IOException e) {
			System.out.println("ERROR writing to file: " + fileName);
			e.printStackTrace();
		}
	}
	
}