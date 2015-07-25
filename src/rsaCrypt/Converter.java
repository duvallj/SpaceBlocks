package rsaCrypt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Converter {
	
	static char[] ALLCHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '~', '!', '@', '#', '$', '%', '^', '&', '[', ']', '{', '}', '|', ';', ':', ',', '.', '?'};	
	
	public String toBinary( long i, int len ){
		
		String output = Long.toBinaryString(i);
		
		if(output.length()>len){ return output; }
		else{
			
			StringBuilder s = new StringBuilder();
			for( int x=0; x<len-output.length(); x++){
				s.append("0");
			}
			s.append(output);
			
			return s.toString();
			
		}
			
	}
	
	public long fromBinary( String i ){
		return Long.parseLong(i, 2);
	}
	
	public String toAll( long i ){

		long temp = i;
		if( i==0 ){ return "0"; }
		List<String> digits = new ArrayList<String>();
		
		while(temp>0){
			digits.add(String.valueOf(ALLCHARS[(int)(temp % 80)]));
			temp /= 80;
		}
		
		Collections.reverse(digits);
		
		StringBuilder output = new StringBuilder();
		for( int a=0; a<digits.size(); a++){
			output.append(digits.get(a));
		}
		
		return output.toString();
	}
	
	public long fromAll( String i ){
		
		long output = 0;
		
		List<String> reversed = new ArrayList<String>(Arrays.asList(i.split("")));
		Collections.reverse(reversed);
		
		String tALL = String.valueOf(ALLCHARS);
		
		for(int x=0; x<reversed.size(); x++){
			output += tALL.indexOf(reversed.get(x)) * Math.pow(80, x);
			reversed.set(x, "_");
		}
		
		return output;
		
	}

}
