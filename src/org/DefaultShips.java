package org;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL; //comment to put in jar
//import java.nio.file.FileSystem; //uncomment to put in jar
//import java.nio.file.FileSystems; //uncomment to put in jar
import java.nio.file.Path;
import java.nio.file.Paths; //comment to put in jar
//import java.util.HashMap; //uncomment to put in jar
//import java.util.Map; //uncomment to put in jar

public class DefaultShips {
	
	public String[] shipName = {"circle",
								"fast",
								"mini",
								"slow",
								"square",
								"U"
	};
	public Path[] ship = new Path[shipName.length];
	//uncomment to put in jar public final FileSystem fs;
	
	public DefaultShips() throws URISyntaxException, IOException{
		
		//uncomment to put in jar
		
		/*final Map<String, String> env = new HashMap<>();
		final String[] array = DefaultShips.class.getResource("/utils/circle.ship").toString().split("!");
		fs = FileSystems.newFileSystem(URI.create(array[0]), env);*/ 

		for(int s=0; s<shipName.length; s++){
			ship[s] = gf("/utils/" + shipName[s] + ".ship");
		}
		
		
	}
	private Path gf(String name) throws URISyntaxException, IOException{
		URL s1 = DefaultShips.class.getResource(name); //comment to put in jar
		URI s2 = s1.toURI();//comment to put in jar
		Path s3 = Paths.get(s2); //comment to put in jar fs.getPath(name);
		
		return s3;
	}
	
}
