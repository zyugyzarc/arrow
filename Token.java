package arrow;

class Token{
	
	public String value;
	public int type;

	public Token(String val){
		this.value = val;
		this.type = -1;
	}

	public Token(String val, int type){
		this.value = val;
		this.type = type;
	}
}