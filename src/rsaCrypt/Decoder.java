package rsaCrypt;

import java.util.ArrayList;
import java.util.List;

//import rsaUtils.KeyGetter;
import rsaUtils.Num_gen;

public class Decoder {

	static int M = 0;
	static int P = 1;
	static int D = 2;
	static Converter conv = new Converter();
	//static KeyGetter getter = new KeyGetter();
	static Num_gen p = new Num_gen();
	public long[] keys;// = getter.get();
	
	public char decodeChar( long i ){
		
		char x = (char)(p.powmod(i, keys[D], keys[M]));
		return x;
		
	}
	
	public String decodeString( long[] i ){
		
		StringBuilder output = new StringBuilder(i.length);
		
		for( int x=0; x<i.length; x++){
			output.append(String.valueOf(decodeChar(i[x])));
		}
		
		return output.toString();
		
	}
	
	public String decode( String code ){
		
		String[] allCharThings = code.split("_");
		StringBuilder binaryList = new StringBuilder();
		
		for( int x=0; x<allCharThings.length; x++){
			binaryList.append(conv.toBinary(conv.fromAll(allCharThings[x]), 24));
		}
		
		String binaryFile = binaryList.toString();
		if(binaryFile.length() % 24 != 0){ return "Error: wrong number of bits"; }
		
		List<Long> decodedFirst = new ArrayList<Long>();
		
		for( int x=0; x<binaryFile.length()/24; x++){
			long theCode = conv.fromBinary(binaryFile.substring(x*24+5, (x+1)*24));
			long theKey = conv.fromBinary(binaryFile.substring(x*24, x*24+5));
			decodedFirst.add(x, theCode-theKey);
		}
		
		long decodedSec [] = new long[decodedFirst.size()];
		
		for(int i=0; i<decodedFirst.size(); i++) decodedSec[i] = decodedFirst.get(i);
		
		return decodeString(decodedSec);
		
		
	}
}
