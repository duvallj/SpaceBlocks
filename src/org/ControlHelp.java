package org;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class ControlHelp extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5966173264316016652L;
	private final JPanel contentPanel = new JPanel();
	
	private final URL[] pictures;
	private byte index=0;
	JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			ControlHelp dialog = new ControlHelp( new URL[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ControlHelp( URL[] pics) {
		
		pictures = pics;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(pictures[index]));
		label.setBounds(0, 0, 434, 228);
		contentPanel.add(label);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				{
					JButton leftButton = new JButton("<");
					leftButton.setActionCommand("<");
					leftButton.addActionListener(this);
					buttonPane.add(leftButton);
				}
				{
					JButton rightButton = new JButton(">");
					rightButton.setActionCommand(">");
					rightButton.addActionListener(this);
					buttonPane.add(rightButton);
				}
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();
		if(action.equals("OK") || action.equals("Cancel")){
			this.dispose();
		}
		
		if(action.equals("<")){
			nextPic(-1);
		}
		if(action.equals(">")){
			nextPic(1);
		}
		
		
	}
	
	private void nextPic(int way){
		index += way;
		
		if(index<0){
			index=(byte) (pictures.length-1);
		}
		if(index>pictures.length-1){
			index=0;
		}
		
		label.setIcon(new ImageIcon(pictures[index]));
		
	}
}
