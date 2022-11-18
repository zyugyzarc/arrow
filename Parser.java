package arrow;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser{

	HashMap<String, Integer> order = new HashMap<>();

	public Parser(){

		order.put("Operator->", 1);
		order.put("Operator<-", 2);

		order.put("Operator-", 11);
		order.put("Operator+", 12);
		order.put("Operator*", 13);
		order.put("Operator/", 14);

		order.put("CTX()", 21);
		order.put("CTX[]", 22);
		order.put("CTX{}", 23);

	}

	public Token parse(ArrayList<Token> tokens){

		return null;

	}

}