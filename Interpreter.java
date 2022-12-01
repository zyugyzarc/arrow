package arrow;

import java.util.HashMap;
import java.util.Scanner;

public class Interpreter{

	HashMap<String, Value> vars = new HashMap<>();

	public Value run(Token t){
		
		if(t.type/100 == 2){ // literal types
			return Value.parseValue(t);
		}

		if(t.type/100 == 1){ // operators

			if(t.type/10 == 10){ // arithematic operators

				Value a = run(t.children.get(0));
				Value b = run(t.children.get(1));

				if(a.typeid == b.typeid && a.typeid == 1){ // arithematic operators
					switch(t.value){
						case "+": return new Value(a.value_num + b.value_num);
						case "-": return new Value(a.value_num - b.value_num);
						case "*": return new Value(a.value_num * b.value_num);
						case "/": return new Value(a.value_num / b.value_num);
					}
				}
				else{
					return new Value(a.toString() + b.toString());
				}
			}

			else if(t.type == 121){ // special operator (<-)

				Token l = t.children.get(0);
				Token r = t.children.get(1);

				if(l.type/100 == 3){ // identifier, assign variable
					vars.put(l.value, run(r));
				}
				else if(l.type == 501 || l.type == 502){ // function call

					if(l.children.size() == 1 && l.children.get(0).type/100 == 3){
						return function_call(l.children.get(0).value, r);
					}
				}

			}

			else if(t.type == 122){ // operator (->), func def

				Token func = t.children.get(1);
				vars.put(t.children.get(0).children.get(0).value, new Value(func));

			}

			else if(t.type/10 == 13){ // , operator
				Token l = t.children.get(0);
				Token r = t.children.get(1);

				run(l);
				return run(r);
			}
		}

		if(t.type/100 == 5){ // CTX types
			//System.out.println(t.children.size());	
			return run(t.children.get(0));
		}

		if(t.type/100 == 3){ // Identifier

			return vars.get(t.value);

		}

		return new Value();

	}

	Value function_call(String funcname, Token args){

		switch(funcname){

			case "print":
				System.out.println( run(args) );
				return new Value();

			case "input":
				System.out.print( run(args) );
				Scanner s = new Scanner(System.in);
				return new Value(s.nextLine());

			default:
				if(vars.containsKey(funcname)){
					
					Value func = vars.get(funcname);
					
					if(func.typeid == 5){
						//funcname : func.children[0].children[0]
						//args : func.children[0].children[1]
						run( func.func );
					}
				}
				return new Value();
		}
	}

}