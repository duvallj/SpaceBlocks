package org;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import rsaCrypt.Decoder;
import utils.SlickButton;
import utils.SlickComboBox;
import utils.SlickLabel;
import utils.StarField;
import utils.jbox2slick;

public class SlickShipGet extends ShipGame {
	
	SlickComboBox player1box;
	SlickComboBox player2box;
	SlickLabel p1label;
	SlickLabel p2label;
	SlickButton done;
	Input in;
	
	private String[] fileNames;
	private Path[] fileOptions;

	public SlickShipGet(String title) {
		super("SPACEBLOCKS");
		// TODO Auto-generated constructor stub
	}
	
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
		arg1.setBackground(Color.black);
		StarField.render(arg1);
		player1box.render(arg1);
		player2box.render(arg1);
		
		done.draw(arg1);
		arg1.setColor(Color.black);
		done.drawText(arg1);
		arg1.setColor(Color.white);
		p1label.draw(arg1);
		p2label.draw(arg1);
		
	}

	
	public void init(GameContainer arg0) throws SlickException {
		
		File[] listOfFiles = new File(".").listFiles();
		DefaultShips defShips = null;
		try {
			defShips = new DefaultShips();
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Path[] fileOptionsLong = new Path[listOfFiles.length + defShips.ship.length];
		
		
		int y = 0;
		
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
		
		fileOptions = new Path[y];
		for(int i=0; i<y; i++){
			fileOptions[i] = fileOptionsLong[i];
		}
		
		
		
		fileNames = new String[fileOptions.length];
		
		for(int x=0; x<fileNames.length; x++){
			//System.out.println(fileOptions[x].toString());
			String[] pathSeparate =  fileOptions[x].toString().split("\\\\");
			fileNames[x] = pathSeparate[pathSeparate.length-1];
		}
		
		if(arg0 instanceof AppGameContainer){
			app = (AppGameContainer) arg0;
		}
		
		in = arg0.getInput();
		
		jbox2slick tr = ShipGame.tr;
		
		int[] pos = tr.toSlick(-90,45);
		int[] wid = tr.wToSlick(15, 67.5f);
		
		player1box = new SlickComboBox(fileNames, pos[0], pos[1], 5, wid[0], wid[1]);
		pos = tr.toSlick(7.5f,45);
		player2box = new SlickComboBox(fileNames, pos[0],pos[1],5,wid[0],wid[1]);
		
		wid = tr.wToSlick(82.5f, 7.5f);
		pos = tr.toSlick(-90, 56);
		
		p1label = new SlickLabel(pos[0],pos[1],1,wid[0],"Player 1 Ship",SlickLabel.CENTER_ALIGNED,wid[1]);
		pos = tr.toSlick(7.5f, 56);
		p2label = new SlickLabel(pos[0],pos[1],1,wid[0],"Player 2 Ship",SlickLabel.CENTER_ALIGNED,wid[1]);
		
		wid = tr.wToSlick(75, 15);
		pos = tr.toSlick(-37.5f, -45);
		
		Color lightblue1 = new Color(51,153,255);
		Color lightblue2 = new Color(0,102,204);
		
		done = new SlickButton(pos[0],pos[1],wid[0],wid[1],
				lightblue1,Color.blue,Color.cyan,lightblue2,10,
				"OK");
		
		done.shown = false;
		
	}

	
	public void update(GameContainer arg0, int arg1) throws SlickException {
		player1box.update(in);
		player2box.update(in);
		done.update(in);
		
		if(!(player1box.selectedChoice.equals("a") || player2box.selectedChoice.equals("a"))){
			done.shown = true;
		}
		
		if(done.clicked){
			getShip(1);
			getShip(2);
			over = true;
		}
	}
	
	public void getShip(int number){
		if(number==1){
			
			Path ship=null;
			
			for(int i=0; i<fileNames.length; i++){
				if(fileNames[i].equals(player1box.selectedChoice)){
					ship = fileOptions[i];
				}
			}
			
			ShipGame.player1Ship = shipFromPath(ship);
			
		}
		if(number==2){
			
			Path ship=null;
			
			for(int i=0; i<fileNames.length; i++){
				if(fileNames[i].equals(player2box.selectedChoice)){
					ship = fileOptions[i];
				}
			}
			
			ShipGame.player2Ship = shipFromPath(ship);
			
		}
	}
	
	private int[][] shipFromPath(Path p){
		
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
