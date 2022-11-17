package arrow;

class Operation extends Token{
	
	public Expression left;
	public Expression right;

	public Operation(String value, Expression left, Expression right){

		this.left = left;
		this.right = right;
		this.value = value;

	}

}