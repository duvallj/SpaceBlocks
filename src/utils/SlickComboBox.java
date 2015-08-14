package utils;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class SlickComboBox {
	
	private List<SlickButton> shownChoices = new ArrayList<SlickButton>();
	private List<String> allChoices = new ArrayList<String>();
	private SlickButton upButton;
	private SlickButton downButton;
	private int numOfRows;
	private int rowHeight;
	private int rowWidth;
	private int pages;
	private int index=0;
	
	private int x;
	private int y;
	
	public String selectedChoice = "a";
	
	public SlickComboBox(String[] listOfChoices){
		for(int i=0; i<listOfChoices.length; i++){
			allChoices.add(listOfChoices[i]);
		}
		
		numOfRows=5;
		rowHeight=100;
		rowWidth=500;
		
		x=0;
		y=0;
		
		pages = Math.floorDiv(listOfChoices.length, numOfRows) + 1;
		
		initButtons();
	}
	
	public SlickComboBox(String[] listOfChoices, int xpos, int ypos, int rn, int rh, int rw){
		for(int i=0; i<listOfChoices.length; i++){
			allChoices.add(listOfChoices[i]);
			System.out.println(listOfChoices[i]);
		}
		
		numOfRows=rn;
		rowHeight=rh;
		rowWidth=rw;
		
		x=xpos;
		y=ypos;
		
		pages = Math.floorDiv(listOfChoices.length, numOfRows) + 1;
		
		initButtons();
	}
	
	public void update(Input in){
		for(SlickButton b: shownChoices){
			b.update(in);
			if(b.clicked){
				selectedChoice=b.text;
			}
			if(selectedChoice.equals(b.text)){
				b.selected=true;
			}
		}
		upButton.update(in);
		downButton.update(in);
		
		if(upButton.clicked){
			
			index--;
			if(index<0){
				index=pages-1;
			}
			refreshButtons();
		}
		
		if(downButton.clicked){
			index++;
			if(index>pages-1){
				index=0;
			}
			refreshButtons();
		}
	}
	
	public void render(Graphics g){
		for(SlickButton b: shownChoices){
			b.draw(g);
			if(selectedChoice.equals(b.text) || b.selected){
				g.setColor(Color.black);
			} else{
				g.setColor(Color.black);
			}
			b.drawText(g);
		}
		upButton.draw(g);
		downButton.draw(g);
		
		g.setColor(Color.black);
		
		upButton.drawText(g);
		downButton.drawText(g);
	}
	
	private void refreshButtons(){
		for(int i=0; i<numOfRows; i++){
			SlickButton b = shownChoices.get(i);
			int pos = (numOfRows*index+i)%allChoices.size();
			b.text = allChoices.get(pos);
		}
	}
	
	private void initButtons(){
		
		for(int i=0; i<numOfRows; i++){
			
			shownChoices.add( new SlickButton(x, y+i*rowHeight, rowWidth, rowHeight, 
					Color.cyan, Color.black, Color.white, Color.black, 2, 
					allChoices.get(i)
			));
			
		}
		
		Color lightblue1 = new Color(51,153,255);
		Color lightblue2 = new Color(0,102,204);
		
		upButton = new SlickButton(x+rowWidth, y, rowHeight,rowHeight,
				lightblue1,Color.blue,Color.cyan,lightblue2, 10,
				"^");
		downButton = new SlickButton(x+rowWidth, y+(numOfRows-1)*rowHeight, rowHeight,rowHeight,
				lightblue1,Color.blue,Color.cyan,lightblue2, 10,
				"V");
		
		refreshButtons();
	}
	
}
