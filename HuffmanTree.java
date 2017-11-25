public class HuffmanTree{

	private Node root=null;

	public HuffmanTree(){}

	public HuffmanTree(Node _root){
		this.setRoot(_root);
	}

	public void setRoot(Node _root){this.root = _root;}
	public Node getRoot(){return this.root;}

}