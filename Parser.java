package arrow;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser{

	HashMap<Integer, Integer> order = new HashMap<>();

	public Parser(Tokenizer tokenizer){

		order.put(tokenizer.tokenType.get("Operator,"), 0);
		order.put(tokenizer.tokenType.get("Operator->"), 1);
		order.put(tokenizer.tokenType.get("Operator<-"), 2);

		order.put(tokenizer.tokenType.get("Operator=="), 11);
		order.put(tokenizer.tokenType.get("Operator>" ), 12);
		order.put(tokenizer.tokenType.get("Operator<" ), 13);
		order.put(tokenizer.tokenType.get("Operator&&"), 14);
		order.put(tokenizer.tokenType.get("Operator||"), 14);

		order.put(tokenizer.tokenType.get("Operator-"), 21);
		order.put(tokenizer.tokenType.get("Operator+"), 22);
		order.put(tokenizer.tokenType.get("Operator*"), 23);
		order.put(tokenizer.tokenType.get("Operator/"), 24);

	}

	public Token parse(ArrayList<Token> tokens){

		for(Token t : tokens){

			if( t.type/100 == 5 && t.hasChildren()){ // CTX type
				Token children = this.parse(t.children);
				t.children.clear();
				t.children.add(children);
			}
		}

		while(tokens.size() > 1){

			int tokenPos = -1;
			int maxToken = -1;

			for(int i = tokens.size()-1; i >= 0 ; i--){
				
				Token t = tokens.get(i);

				if( !t.hasChildren() && order.containsKey(t.type) && order.get(t.type) > maxToken ){
					tokenPos = i;
					maxToken = order.get(t.type);
				}
			}

			// all operations are binary operators

			tokens.get(tokenPos).children.add(tokens.get(tokenPos - 1));
			tokens.get(tokenPos).children.add(tokens.get(tokenPos + 1));

			tokens.remove(tokenPos + 1);
			tokens.remove(tokenPos - 1);

		}

		return tokens.get(0);
	}

}