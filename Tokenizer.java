package arrow;

import java.util.HashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Tokenizer{
	
	HashMap<String, Integer> tokenType = new HashMap<>();
	ArrayList<Token> tokens = new ArrayList<>();

	public Tokenizer(){

		// 1XX : Operators
		tokenType.put("Operator+", 101);
		tokenType.put("Operator-", 102);
		tokenType.put("Operator*", 103);
		tokenType.put("Operator/", 104);

		tokenType.put("Operator<-", 105);
		tokenType.put("Operator->", 104);

		// 2XX : Literals
		tokenType.put("Literal0", 201); // number literal
		tokenType.put("LiteralS", 203); // String literal

		// 3XX : Identifier
		tokenType.put("Identifier", 300);

		// 4XX : Keywords / special
		tokenType.put("?", 401);
		tokenType.put("while", 402);

		// 5XX : Punctuation
		tokenType.put("Bracket(", 501);		
		tokenType.put("Bracket)", 502);		
		tokenType.put("Bracket[", 503);		
		tokenType.put("Bracket]", 504);		

	}

	public void parse(String expr){

		Pattern operator = Pattern.compile("^[<]?[\\+\\-\\*/][>]?");

		Pattern literal_int = Pattern.compile("^[0-9.]+");
		Pattern literal_str = Pattern.compile("^[\"].*?[\"]");

		Pattern identifier = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]?");

		while(expr.length() > 0){

			Matcher m = operator.matcher(expr);

		}

	}

}