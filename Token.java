package arrow;

import java.util.ArrayList;

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

	public boolean hasChildren(){
		return this.children.size() > 0;
	}

	public String toString(){
		if(this.hasChildren()){
			return "".format("(%s [%s])", this.value, this.children);	
		}
		return "".format("(%s)", this.value);
	}
}