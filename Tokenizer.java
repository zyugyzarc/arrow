package arrow;

import java.util.HashMap;
import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Tokenizer{
	
	public HashMap<String, Integer> tokenType = new HashMap<>();

	HashMap<String, String> tokenRegex = new HashMap<>();
	ArrayList<Token> tokens = new ArrayList<>();

	public Tokenizer(){

		// 1XX : Operators

		// 10X : Arithematic operators
		tokenType.put("Operator+", 101);
		tokenType.put("Operator-", 102);
		tokenType.put("Operator*", 103);
		tokenType.put("Operator/", 104);

		// 11X : Logical Operators
		tokenType.put("Operator==", 111);
		tokenType.put("Operator>" , 112);
		tokenType.put("Operator<" , 113);
		tokenType.put("Operator&&", 114);
		tokenType.put("Operator||", 114);


		// 12X : Special operators
		tokenType.put("Operator<-", 121);
		tokenType.put("Operator->", 122);

		tokenType.put("Operator,", 131);

		tokenRegex.put("Operator+", "[+]");
		tokenRegex.put("Operator-", "[-]");
		tokenRegex.put("Operator*", "[*]");
		tokenRegex.put("Operator/", "[/]");

		tokenRegex.put("Operator<-", "<-");
		tokenRegex.put("Operator->", "->");

		tokenRegex.put("Operator==", "==");
		tokenRegex.put("Operator<",  "<");
		tokenRegex.put("Operator>",  ">");
		tokenRegex.put("Operator&&", "&&");
		tokenRegex.put("Operator||", "\\|\\|");

		tokenRegex.put("Operator,", "[,]");

		// 2XX : Literals
		tokenType.put("Literal0", 201); // number literal
		tokenType.put("LiteralS", 203); // String literal
		tokenType.put("LiteralN", 204); // NULL literal

		tokenRegex.put("Literal0", "[0-9.]+");
		tokenRegex.put("LiteralS", "\".*?\"");
		tokenRegex.put("LiteralN", "(NULL)?(null)?");

		// 3XX : Identifier
		tokenType.put("Identifier", 300);

		tokenRegex.put("Identifier", "[a-zA-Z_][a-zA-Z0-9_]*");

		// 4XX : Keywords / special
		tokenType.put("?", 401);
		tokenType.put("while", 402);

		tokenRegex.put("?", "[?]");
		tokenRegex.put("while", "while");

		// 5XX : Contexts
		tokenType.put("CTX()", 501);
		tokenType.put("CTX[]", 502);
		tokenType.put("CTX{}", 503);

		tokenRegex.put("CTX()", "CTX()");
		tokenRegex.put("CTX[]", "CTX[]");
		tokenRegex.put("CTX{}", "CTX{}");

		// 6XX : Punctuation tokens - to be discarded
		tokenType.put("discardS", 601);
		tokenType.put("discardT", 602);
		tokenType.put("discardN", 603);

		tokenRegex.put("discardS", " ");
		tokenRegex.put("discardT", "\\t");
		tokenRegex.put("discardN", "\\n");

	}

	int getToken(String pattern, String expr){

		if(pattern.startsWith("CTX")){

			char op = pattern.charAt(3);
			char cl = pattern.charAt(4);

			int count = 0;
			int i = 0;

			do{

				if(expr.charAt(i) == op){
					count++;
				}
				else if(expr.charAt(i) == cl){
					count--;
				}

				if(count <= 0){
					return i>0 ? i+1 : 0;
				}

				i++;

			}while(i < expr.length());

		}

		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(expr);

		if(m.find() && m.start() == 0){
			return m.end();
		}
		else{
			return 0;
		}

	}

	public ArrayList<Token> parse(String expr){

		ArrayList<Token> tokens = new ArrayList<>();

		while(expr.length() > 0){
		
			String token;
			int tokenStart = 0;

			for(String key : tokenRegex.keySet()){

				tokenStart = getToken( tokenRegex.get(key), expr );

				if(tokenStart > 0){

					token = expr.substring(0, tokenStart);
					expr  = expr.substring(tokenStart, expr.length());

					if(!key.startsWith("discard")){

						if(key.equals("LiteralS")){

							tokens.add(
								new Token(token.substring(1, token.length()-1), tokenType.get(key))
							);

						}
						else{
							
							tokens.add(
								new Token(token, tokenType.get(key))
							);
						}

					}

					if(key.startsWith("CTX")){

						tokens.get(tokens.size()-1).value = key;
						tokens.get(tokens.size()-1).children = this.parse(token.substring(1, token.length()-1));

					}

					break;
				}

			}

			if(tokenStart == 0){
				System.out.println("Unknown token at " + expr);
			}

		}

		return tokens;

	}

}