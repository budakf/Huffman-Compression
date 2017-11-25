import java.io.*;
import java.util.*;

public class TxtFile{

	private File file;
	private String contentsString;

	public String getContentsString(){return this.contentsString;}

	public TxtFile(String _fileName){
		this.file = new File(_fileName);
		this.contentsString = "";
	}

	public String readOperation() {
		String line;
		try( Scanner scanner = new Scanner(this.file) ){
			while (scanner.hasNextLine()){
				line = scanner.nextLine();
				//line +="\n";
				this.contentsString +=line;
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}

		return this.contentsString;
	}

	public void writeOperation() {
		try {
			FileWriter fileWriter = new FileWriter(this.file);
			fileWriter.write(this.contentsString);

		} 
		catch (IOException e) {
			System.out.println(e);
		}
	}

}