package arrow;

class Literal extends Token{
	
	public boolean isNum = false;
	public double numValue;

	public Literal(String val){
		
		super(val);			

		try{
			
			this.numValue = Double.parseDouble(val);
			this.isNum = true;

		}catch(NumberFormatException e){
				
			this.isNum = false;

		}

		
	}
}