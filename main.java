package arrow;

public class main{
	public static void main(String[] args){

		Tokenizer t = new Tokenizer();

		System.out.println( t.parse("(3 + 5) * 15 + 7") );
	
	}
}