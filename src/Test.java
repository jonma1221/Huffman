import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		String text = "";
		File file = new File("data/test.txt");
		   Scanner sc = new Scanner(file);
	        while(sc.hasNext()){
	        	text += sc.nextLine();
	        }
	        sc.close();
	      
	}

}
