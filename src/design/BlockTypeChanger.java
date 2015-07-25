package design;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockTypeChanger implements ActionListener{
	
	public int type=0;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		type = Integer.parseInt(arg0.getActionCommand());
		
	}

}
