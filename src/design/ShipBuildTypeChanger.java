package design;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ShipBuildTypeChanger implements ActionListener{
	
	public BlockTypeChanger btc;   		//this is so I can get the type
	
	public ShipBuildTypeChanger(BlockTypeChanger b){
		btc=b;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Color block = Color.WHITE;
		String text = Integer.toString(btc.type);
		
		if(btc.type==1){
			block = Color.CYAN;
		} else if(btc.type==2){
			block = Color.RED;
		} else if(btc.type==3){
			block = Color.GRAY;
		} else if(btc.type==4){
			block = Color.BLACK;
		} else if(btc.type==5){
			block = Color.GREEN;
		} else if(btc.type==6){
			block = Color.ORANGE;
		} else if(btc.type==7){
			block = Color.YELLOW;
		}
		
		((JButton)arg0.getSource()).setText(text);
		((JButton)arg0.getSource()).setBackground(block);
		((JButton)arg0.getSource()).setForeground(block);
		
	}

}
