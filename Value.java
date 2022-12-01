package arrow;

import java.util.ArrayList;
import java.util.Arrays;

class Value{
	
	public int typeid = 0;
	/*
		TypeID | Type

		   0     Null/None
		   1     Number (Float)
		   2     Boolean
		   3     String
		   4     List/Array   (Not Implemented yet)
		   5     function
	*/

	public float value_num;
	public String value_str;
	public ArrayList<Value> value_arr;
	public Token func;

	public Value(){
		this.typeid = 0;
	}

	public Value(float f){
		this.typeid = 1;
		this.value_num = f;
	}

	public Value(int f){
		this.typeid = 1;
		this.value_num = (float)f;
	}

	public Value(boolean f){
		this.typeid = 1;
		this.value_num = f?1f:0f;
	}

	public Value(String s){
		this.typeid = 3;
		this.value_str = s;
	}

	public Value(Value... list){
		this.typeid = 4;
		value_arr = new ArrayList<Value>(Arrays.asList(list));
	}

	public Value(Token t){
		this.typeid = 5;
		func = t; 
	}

	public void list(){
		this.typeid = 4;
		value_arr = new ArrayList<Value>();
	}

	public String toString(){
		switch(this.typeid){
			case 0: return "null";
			case 1: return ""+ this.value_num;
			case 2: return this.value_num==0?"false":"true";
			case 3: return this.value_str;
			case 4: return ""+ this.value_arr;
		}
		return "INVALID TYPE";
	}

	public static Value parseValue(Token t){
		if(t.type == 201){ return new Value(Float.parseFloat(t.value)); } // number type
		if(t.type == 203){ return new Value(t.value); } // string type
		return new Value();
	}
}