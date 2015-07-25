package rsaUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class KeyGetter {
	public long[] get(){
		Path file = Paths.get("secret.rsaC");
		
		try {
		    Files.createFile(file);
		    
		    Num_gen rsaGenerator = new Num_gen();
			long[] keys = rsaGenerator.gen();
			
			PrintWriter outKeys = new PrintWriter("secret.rsaC");
			outKeys.print(keys[0]);
			outKeys.print(";");
			outKeys.print(keys[1]);
			outKeys.print(";");
			outKeys.print(keys[2]);
			
			outKeys.close();
			
			return keys;
		} catch (FileAlreadyExistsException ex) {
			List<String> keysInListString = null;
			try {
				keysInListString = Files.readAllLines(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String[] keysSeparate = keysInListString.toArray(new String[keysInListString.size()])[0].split(";");
			
			long[] keys = new long[3];
			for(byte x=0; x<3; x++){
				keys[x] = Long.parseLong(keysSeparate[x]);
			}
			
			return keys;
		
		} catch (IOException ex) {
		    System.err.format("createFile error: %s%n", ex);
		}
		return null;
	}

}
