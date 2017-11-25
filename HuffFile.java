import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class HuffFile{

	private String file_name;
	private BitSet contentsBits;

	public BitSet getContentsBits(){return this.contentsBits;}

	public HuffFile(String p_fileName, BitSet p_contents){
		this.file_name = new String(p_fileName);
		this.contentsBits = new BitSet();
		this.contentsBits = p_contents;
	}

	public HuffFile(String p_fileName){
		this.file_name = new String(p_fileName);
	}

	public void writeOperation() {
		try{
			byte[] bytes = this.contentsBits.toByteArray();
			Path path = Paths.get("./"+this.file_name);
			Files.write(path, bytes);
		}
		catch(FileNotFoundException e){
			System.out.println(e);
		}
		catch(IOException e){
			System.out.println(e);
		}
	}

	
	public void readOperation() {
		try{
			Path path = Paths.get("./"+this.file_name);
	    	byte[] encodedMessage = Files.readAllBytes(path);
	    	this.contentsBits = BitSet.valueOf(encodedMessage);
		}
		catch(IOException e){
			System.out.println(e);
		}
	}


}