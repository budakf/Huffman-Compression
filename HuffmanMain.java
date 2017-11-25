import java.io.*;
import java.util.*;
import java.nio.*;


public class HuffmanMain{

	private String txtFileName;
	private String huffFileName;
	private Map <Character, Integer> frequency = new LinkedHashMap <Character, Integer>();
	private HuffmanTree tree = new HuffmanTree();
	private Map<Character, String> charCode = new HashMap<Character, String>();

	public void setTree(Node rootOfTree){ this.tree.setRoot(rootOfTree);}
	public void setCharCode(){ this.charCode = generateCodes(this.tree.getRoot() ); }

	public Map <Character, Integer> getFrequency(){return this.frequency;}
	public HuffmanTree getTree(){ return this.tree;}
	public Map<Character, String> getCharCode(){ return this.charCode;}

	HuffmanMain(){}

	public void calculateFreq(String str){

		for (char i : str.toCharArray()){
			if(this.frequency.containsKey(i)){
				this.frequency.put(i, frequency.get(i)+1);
			}
			else{
				this.frequency.put(i, 1);
			}
		}
	}	

	public void printFrequency(){ // Sonuçları görebilmek için

		for(Map.Entry <Character, Integer> c : this.frequency.entrySet()){
			System.out.println(c.getKey()+ ": "+ c.getValue());
		}
	}

	public void printCharCode(){ // Sonuçları görebilmek için

		for(Map.Entry <Character, String> c : this.charCode.entrySet()){
			System.out.println(c.getKey()+ ": "+ c.getValue());
		}
	}

	public void sortFrequencyByValues(){

		Set< Map.Entry<Character, Integer> > mapEntries = this.frequency.entrySet();
		List < Map.Entry<Character, Integer> > list = new LinkedList< Map.Entry<Character, Integer> >(mapEntries);

		Collections.sort(list, new Comparator<Map.Entry<Character,Integer>>(){

			@Override
			public int compare(Map.Entry<Character, Integer> element1, Map.Entry<Character, Integer> element2){
				return element1.getValue().compareTo(element2.getValue());
			}
		});

		this.frequency.clear();

		for (Map.Entry<Character, Integer> entries : list){
			frequency.put(entries.getKey(),entries.getValue());
		} 
	}

	public void sortListByValues(List < Map.Entry<Character, Integer> > list){

		Collections.sort(list, new Comparator<Map.Entry<Character,Integer>>(){

			@Override
			public int compare(Map.Entry<Character, Integer> element1, Map.Entry<Character, Integer> element2){
				return element1.getValue().compareTo(element2.getValue());
			}
		});
	}

	public Node buildTree(){
		final Queue< Node > nodeQueue = createNode();

		while(nodeQueue.size()>1){
			final Node n1 = nodeQueue.remove();
			final Node n2 = nodeQueue.remove();
			Node node = new Node('*', n1.getFrequency() + n2.getFrequency(), n1, n2 );
			nodeQueue.add(node);		
		}
		return nodeQueue.remove(); // this node is root
	}

	private static class HuffManComparator implements Comparator<Node> {	// PriorityQueue için
        @Override
        public int compare(Node n1, Node n2) {
            return n1.getFrequency() - n2.getFrequency();
        }
    }

	public Queue< Node > createNode(){
		final Queue< Node > queue = new PriorityQueue<Node>(11, new HuffManComparator() );
		for (Map.Entry<Character, Integer> entry : this.frequency.entrySet()) {
            queue.add(new Node(entry.getKey(), entry.getValue(), null, null));
        }
        return queue;
	}

	public Map<Character, String> generateCodes(Node root) {
       final Map<Character, String> charCode = new HashMap<Character, String>();
       doGenerateCode(root, charCode, "");
       return charCode;
    }

    public void doGenerateCode(Node node, Map<Character, String> charCode, String s) {

        if (node.getLeft() == null && node.getRight() == null) {
            charCode.put(node.getCharacter(), s);
            return;
        }    
        doGenerateCode(node.getLeft(), charCode, s + '0');
        doGenerateCode(node.getRight(), charCode, s + '1' );
    }

    public BitSet stringToBitSet(String encoded){
		BitSet bitSet = new BitSet(encoded.length());
		int bitcounter = 0;
		for(Character c : encoded.toCharArray()) {
		    if(c.equals('1')) {
		        bitSet.set(bitcounter);
		    }
		    bitcounter++;
		}

		return bitSet;
	}


	public BitSet encodeMessage(Map <Character, String> charCode, String message){

		StringBuilder strBuilder = new StringBuilder();

		for(char c : message.toCharArray()){
			strBuilder.append(charCode.get(c));
		}

		String temp = strBuilder.toString();
		//System.out.println(temp);
		return stringToBitSet(temp);
	}

	public String bitSetToString(BitSet bitSet){
		String binaryString = "";
		for(int i = 0; i < bitSet.length(); i++) {
		    if(bitSet.get(i)) {
		        binaryString += "1";
		    } else {
		        binaryString += "0";
		    }
		}
		return binaryString;
	}

	public String takeMessage(String binaryString){
		String message = "";
		String temp = "";
		for(char c : binaryString.toCharArray()){
			temp+=c;
			for (Map.Entry<Character, String> entry : this.charCode.entrySet()) {
            	if (entry.getValue().equals(temp)) {
               		message +=entry.getKey();
               		temp = "";
            	}
        	}
		}
		return message;
	}

	public String decodeMesage(BitSet bitSet){
		
		String temp = bitSetToString(bitSet);
		String message = takeMessage(temp); 
		return message;

	}

	public static void main(String [] args) throws IOException{
		
		String fileNameR = "a.txt";
		String fileNameW = "a.huff";
		String contentsOfFile="";
		HuffmanMain hf = new HuffmanMain();
		TxtFile  r;
		HuffFile w;
		try{
			r = new TxtFile(fileNameR);
			contentsOfFile = r.readOperation();
		}
		catch(Exception e){
			System.out.println(e);
		}

		hf.calculateFreq(contentsOfFile);
		hf.sortFrequencyByValues();
		hf.setTree(hf.buildTree());
		Node root = hf.getTree().getRoot();
		//hf.printFrequency();
		hf.setCharCode();
		//hf.printCharCode();

		BitSet encodedMessage = hf.encodeMessage(hf.getCharCode(), contentsOfFile);
		System.out.println("encoded Message: "+encodedMessage);

		try{
			w = new HuffFile(fileNameW, encodedMessage);
			w.writeOperation();
		}
		catch(Exception e){
			System.out.println(e);
		}

		BitSet received = new BitSet();
		
		try{
			w = new HuffFile(fileNameW);
			w.readOperation();
			received = w.getContentsBits();
			
		}
		catch(Exception e){
			System.out.println(e);
		}

		String finalMessage = hf.decodeMesage(received);
		System.out.println("decoded Mesage: "+finalMessage);

	}

}