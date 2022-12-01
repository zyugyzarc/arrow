package arrow;

/**

 Arrow

 By Sanjay Sankaran <sanjay.sankaran@bellevuecollege.edu>

 A dynamically typed interpreter, made in java.
 for further details, check the [documentation](doc.md.pdf)

**/

import java.util.ArrayList;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

public class main{
	public static void main(String[] args) throws IOException{

		Tokenizer t = new Tokenizer();
		Parser p = new Parser(t);
		Interpreter i = new Interpreter();

		String filename = args[0];
		String source = Files.readString( Path.of(filename) );

		ArrayList<Token> tokens = t.parse( source );
		Token tree = p.parse(tokens);

		//tree.printTree(0);

		Value result = i.run(tree);

		//System.out.println( tokens );
		//System.out.println( tree );
		//System.out.println( result );
	
	}
}