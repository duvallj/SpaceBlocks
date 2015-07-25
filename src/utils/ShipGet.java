package utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.DefaultShips;

import rsaCrypt.Decoder;

@SuppressWarnings("serial")
public class ShipGet extends JPanel{
	
	private Path player1;
	private Path player2;
	public int[][] player1Ship;
	public int[][] player2Ship;
	
	public ShipGet() throws URISyntaxException, IOException{
		
		File[] listOfFiles = new File(".").listFiles();
		DefaultShips defShips = new DefaultShips();
		Path[] fileOptionsLong = new Path[listOfFiles.length + defShips.ship.length];
		
		
		int y = 0;
		JFrame frame = new JFrame("Ship selector");
		
		for(int x=0; x<listOfFiles.length; x++){
			if(listOfFiles[x].getName().endsWith(".ship") && listOfFiles[x].isFile()){
				fileOptionsLong[y] = Paths.get(listOfFiles[x].getName());
				y++;
			}
		}
		
		for(Path p: defShips.ship){
			fileOptionsLong[y] = p;
			y++;
		}
		
		Path[] fileOptions = new Path[y];
		for(int i=0; i<y; i++){
			fileOptions[i] = fileOptionsLong[i];
		}
		
		
		
		String[] fileNames = new String[fileOptions.length];
		
		for(int x=0; x<fileNames.length; x++){
			System.out.println(fileOptions[x].toString());
			String[] pathSeparate =  fileOptions[x].toString().split("\\\\");
			fileNames[x] = pathSeparate[pathSeparate.length-1];
		}
		
		String player1s = (String)JOptionPane.showInputDialog(
                frame,
                "Choose Player 1's ship file:",
                "Player 1 ship",
                JOptionPane.PLAIN_MESSAGE,
                null,
                fileNames, "");
		
		String player2s = (String)JOptionPane.showInputDialog(
                frame,
                "Choose Player 2's ship file:",
                "Player 2 ship",
                JOptionPane.PLAIN_MESSAGE,
                null,
                fileNames, "");
		
		if(player1s == null || player2s == null){
			System.exit(0);
		}
		
		for(int i=0; i<fileNames.length; i++){
			if(fileNames[i].equals(player1s)){
				player1 = fileOptions[i];
			}
			if(fileNames[i].equals(player2s)){
				player2 = fileOptions[i];
			}
		}
		
		player1Ship = shipFromPath(player1);
		player2Ship = shipFromPath(player2);
		
		//defShips.fs.close(); //uncomment to put in jar
		
	}
	
	public static void main(String[] args) throws URISyntaxException, IOException{
		
		@SuppressWarnings("unused")
		ShipGet shipGet = new ShipGet();
		
	}
	
	public int[][] shipFromPath(Path p){
		
		int[][] ship = new int[10][10];
		
		List<String> shipInListString = null;

		
		try {
			shipInListString = Files.readAllLines(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		String[] dataSeparate = shipInListString.toArray(new String[shipInListString.size()]);
		String[] keysInString = dataSeparate[0].split("\\;");
		
		long[] keys = new long[3];
		for(byte x=0; x<3; x++){
			keys[x] = Long.parseLong(keysInString[x]);
		}
		
		Decoder d = new Decoder();
		d.keys = keys;
		
		String[] shipData = d.decode(dataSeparate[1]).split("\\;");
		String[][] allSeparate = new String[10][10];
		
		for(int x=0; x<10; x++){
			allSeparate[x] = shipData[x].split("\\.");
		}
		
		for(byte x=0; x<10; x++){
			for(byte y=0; y<10; y++){
				ship[x][y] = Integer.parseInt(allSeparate[x][y]);
			}
		}
		
		return ship;
	}
		
}