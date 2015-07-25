package design;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import rsaCrypt.Decoder;

@SuppressWarnings("serial")
public class ShipGet extends JPanel{
	
	private String shipFile;
	public int[][] shipList;
	
	public ShipGet(){
		
		File[] listOfFiles = new File(".").listFiles();
		String[] fileOptionsLong = new String[listOfFiles.length];
		int y = 0;
		JFrame frame = new JFrame("Ship selector");
		
		for(int x=0; x<listOfFiles.length; x++){
			if(listOfFiles[x].getName().endsWith(".ship") && listOfFiles[x].isFile()){
				fileOptionsLong[y] = listOfFiles[x].getName();
				y++;
			}
		}
		
		String[] fileOptions = new String[y];
		for(int i=0; i<y; i++){
			fileOptions[i] = fileOptionsLong[i];
		}
		
		shipFile = (String)JOptionPane.showInputDialog(
                frame,
                "Choose the ship file:",
                "Ship file selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                fileOptions, "");
		
		
		shipList = getPlayer(1);
		
	}
	
	public int[][] getPlayer(int player){
		
		int[][] ship = new int[10][10];
		
		List<String> shipInListString = null;
		
		
		try {
			shipInListString = Files.readAllLines(Paths.get(shipFile));
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
	
	public static void main(String[] args){
		
		@SuppressWarnings("unused")
		ShipGet shipGet = new ShipGet();
		
	}
		
}
