package arrow;

class Token{
	
	public String value;
	public int type;
	public ArrayList<Token> children = new ArrayList<>();

	public Token(String val){
		this.value = val;
		this.type = -1;
	}

	public Token(String val, int type){
		this.value = val;
		this.type = type;
	}

	public bool hasChildren(){
		return this.children.size() > 0;
	}
}