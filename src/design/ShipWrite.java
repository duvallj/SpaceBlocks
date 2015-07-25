package design;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import rsaCrypt.Decoder;
import rsaCrypt.Encoder;
import rsaUtils.Num_gen;

public class ShipWrite extends JPanel{
	
	//private JFrame frame;
	
	private static final long serialVersionUID = 3007964357815614585L;

	public ShipWrite(int[][] ship) throws FileNotFoundException{
		
		JFrame frame = new JFrame("Ship writer");
		
		String shipName = (String)JOptionPane.showInputDialog(
                frame,
                "Choose the ship file:",
                "Ship file selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null, 
                "");
		
		writeShip(ship, shipName);
	
	}
	
	public void writeShip(int[][] ship, String shipName) throws FileNotFoundException{
		
		for(byte y=0; y<10; y++){
			for(byte x=0; x<10; x++){
				int data = ship[y][x];
				if(data<0 || data>7){
					ship[y][x]= 0;
				}
			}
		}
		
		//if(!checkShip(ship)){
			/*JOptionPane.showMessageDialog(frame,
					"Error: ship file is not valid.",
					"Error",
					JOptionPane.ERROR_MESSAGE);*/
		//} else{
		
		
		char[] shipInCharList = new char[200];
		int c = 0;
		
		for(int y=0; y<10; y++){ 			//again, always 10x10
			for(int x=0; x<10; x++){
				shipInCharList[c] = Integer.toString(ship[y][x]).charAt(0); //ints are always length 1
				c++;
				shipInCharList[c] = '.';
				c++;
			}
			shipInCharList[c-1] = ';';

		}
		
		try {
		    Files.createFile(Paths.get(shipName + ".ship"));
		} catch (IOException ex) {
			//nothing, just keeps regular file
		}
		
		Encoder e = new Encoder();
		Num_gen n = new Num_gen();
		
		long[] keys = n.gen();
		
		PrintWriter printWriter = new PrintWriter(shipName + ".ship");
		printWriter.print(keys[0]);
		printWriter.print(";");
		printWriter.print(keys[1]);
		printWriter.print(";");
		printWriter.println(keys[2]);
		
		e.keys = keys;
		
		printWriter.print(e.encode(String.valueOf(shipInCharList)));	
		printWriter.close();
		
		try{
			(new ShipCheckSave()).check(shipName);
		} catch (java.lang.ArrayIndexOutOfBoundsException ex){
			System.out.println("caught error");
			writeShip(ship, shipName);
		}
		//}
		
	}
	
	public boolean checkShip(int[][] shipFile){
		
		int thrust = 0;
		int turn = 0;
		int control = 0;
		int shoot = 0;
		
		for(byte y=0; y<10; y++){
			for(byte x=0; x<10; x++){
				int type = shipFile[y][x];
				
				if(type==1){turn++;}
				if(type==2){thrust++;}
				if(type==4){control++;}
				if(type==5){shoot++;}
			}
		}
		
		if(thrust>15){
			return false;
		}
		if(turn>15){
			return false;
		}
		if(shoot>5){
			return false;
		}
		if(control!=1){
			return false;
		}
		
		return true;
		
	}
}

class ShipCheckSave{
	
	//if no errors are thrown, code continues
	
	public void check(String filename){
		int[][] ship = new int[10][10];
		
		List<String> shipInListString = null;
		
		
		try {
			shipInListString = Files.readAllLines(Paths.get(filename+".ship"));
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
	}
	
}
