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

			if(t.type/10 == 11){ // logical operators

				Value a = run(t.children.get(0));
				Value b = run(t.children.get(1));

				switch(t.value){
					case "<":  return new Value(a.value_num < b.value_num);
					case ">":  return new Value(a.value_num > b.value_num);
					case "&&": return new Value(a.value_num * b.value_num > 0);
					case "||": return new Value(a.value_num + b.value_num > 0);

					case "==": 
						if(a.typeid == b.typeid && a.typeid == 3){ // string equals
							return new Value(a.value_str.equals(b.value_str));
						}else{                                     // numeric equals
							return new Value(a.value_num == b.value_num);
						}
							
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

			else if(t.type == 122){ // operator (->), func def or ? expr

				Token func = t.children.get(1);
				Token functype = t.children.get(0).children.get(0);

				if (functype.type == 401){ // ? expr

					Value condition = run(t.children.get(0).children.get(1));

					if(condition.truthy()){
						return run( t.children.get(1) );
					}
				}

				else if (functype.type == 402){ // (while) expr


					Token condition = t.children.get(0).children.get(1);
					Value last = new Value();

					while ( run(condition).truthy() ) {

						last = run( t.children.get(1) );
					}

					return last;
				}

				else {  // func def

					Value variable = new Value(func);
					variable.value_str = t.children.get(0).children.get(1).value;

					String funcname = t.children.get(0).children.get(0).value;

					vars.put(funcname, variable);

				}

			}

			else if(t.type/10 == 13){ // (,) operator  (for now, returns the right value. needs to create a list/tuple type in the future)
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

		Scanner s = new Scanner(System.in); Value val;

		switch(funcname){

			case "print":
				val = run(args);
				if(val.typeid != 0){
					System.out.println( val );
				}else{
					System.out.println();
				}
				return new Value();

			case "print_raw":
				val = run(args);
				if(val.typeid != 0){ System.out.print( val ); }
				return new Value();

			case "input":
				System.out.print( run(args) );
				return new Value(s.nextLine());

			case "input_num":
				System.out.print( run(args) );
				return new Value((float)s.nextDouble());

			default:
				if(vars.containsKey(funcname)){
					
					Value func = vars.get(funcname);
					
					if(func.typeid == 5){
						
						//funcname : func.children[0].children[0]
						//args : func.children[0].children[1]
						
						if(!func.value_str.equals("null")){
							vars.put(func.value_str, run(args));
						}

						Value result = run( func.func );

						return result;
					}
				}
				return new Value();
		}
	}

}