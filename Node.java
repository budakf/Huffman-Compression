public class Node{

	private Integer  frequency;
	private Character character;
	private String	code;
	private Node right;
	private Node left;

	public Node(){}

	public Node(Character _character, Integer _frequency , Node r, Node l){
		setFrequency(_frequency);
		setCharacter(_character);
		this.right = r;
		this.left = l;
	}

	public void setFrequency(Integer _frequency){ this.frequency = _frequency;}
	public void setCharacter(Character _character){ this.character = _character;}
	public void setCode(String _code){ this.code = _code;}
	public void setRight(Node _right){ this.right = _right;}
	public void setLeft(Node _left){ this.left = _left;}

	public Integer getFrequency(){ return this.frequency; }
	public Character getCharacter(){ return this.character; }
	public String getString(){ return this.code; }
	public Node getRight(){ return this.right; }
	public Node getLeft(){ return this.left; }

}