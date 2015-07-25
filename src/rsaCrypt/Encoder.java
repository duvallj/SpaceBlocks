package rsaCrypt;

//import rsaUtils.KeyGetter;
import rsaUtils.Num_gen;

public class Encoder {
	
	static int M = 0;
	static int P = 1;
	static int D = 2;
	static Converter conv = new Converter();
	//static KeyGetter getter = new KeyGetter();
	static Num_gen p = new Num_gen();
	public long[] keys;

	public long encodeChar( char c ){
		
		long x = p.powmod((long)c, keys[P], keys[M]);
		return x;
		
	}

	public long[] encodeString( String s ){
		
		char[] chars = s.toCharArray();
		long[] output = new long[s.length()];
		
		for(int x=0; x<chars.length; x++){
			
			output[x] = encodeChar(chars[x]);
			
		}
		
		return output;
		
	}
	
	public String encode( String s ){
		
		long code [] = encodeString(s);
		long keyList [] = new long[code.length];
		
		for(int x=0; x<code.length; x++){
			
			keyList[x] = (long)(Math.random()*32);
			code[x] += keyList[x];
			
		}
		
		StringBuilder output = new StringBuilder();
		
		for( int x=0; x<code.length; x++){
			
			StringBuilder binary = new StringBuilder(conv.toBinary(keyList[x], 5));
			binary.append(conv.toBinary(code[x], 19));
			output.append(conv.toAll(conv.fromBinary(binary.toString())));
			output.append("_");
			
		}
		
		output.deleteCharAt(output.length()-1);
		return output.toString();
		
	}

}
