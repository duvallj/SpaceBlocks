package design;

//import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
//import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import org.ControlHelp;

import java.net.URL;
import java.awt.Color;

import javax.swing.SwingConstants;

import java.awt.Font;

public class MainGUI extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1326716795139002372L;
	private JPanel contentPane;
	private JLabel lblcontrolBlock;
	private JButton button_4;
	private JButton[] textFields = new JButton[100];
	private BlockTypeChanger btc = new BlockTypeChanger();
	private URL[] pictures = {MainGUI.class.getResource("/design/designhelp.png"),
			MainGUI.class.getResource("/org/blocktips.png")};
	
	private int cost=0;
	private int total=500;
	
	private JLabel labelMoneyLeft;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		@SuppressWarnings("unused")
		MainGUI frame = new MainGUI();
	}

	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ShipBuildTypeChanger ch = new ShipBuildTypeChanger(btc);
		
		for(int t = 0; t < textFields.length; t++){
			textFields[t] = new JButton();
			textFields[t].setBounds(30*(t%10) + 10, 31*(int)Math.floor(t/10)+11, 20, 20);
			contentPane.add(textFields[t]);
			textFields[t].setText("0");
			textFields[t].setBackground(Color.WHITE);
			textFields[t].setForeground(Color.WHITE);
			textFields[t].addActionListener(this);
			textFields[t].addActionListener(ch);
		}
		
		
		JButton btnLoadShip = new JButton("Load Ship");
		btnLoadShip.setBounds(99, 327, 120, 23);
		btnLoadShip.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent f) {
				ShipGet g = new ShipGet();
				
				for(int y=0; y<10; y++){    		//the list will always be 10X10
					for(int x=0; x<10; x++){
						
						Color block = Color.WHITE;
						int b = g.shipList[y][x];
						String text = Integer.toString(b);
						
						if(b==1){
							block = Color.CYAN;
						} else if(b==2){
							block = Color.RED;
						} else if(b==3){
							block = Color.GRAY;
						} else if(b==4){
							block = Color.BLACK;
						} else if(b==5){
							block = Color.GREEN;
						} else if(b==6){
							block = Color.ORANGE;
						} else if(b==7){
							block = Color.YELLOW;
						}
						
						textFields[y*10+x].setText(text);
						textFields[y*10+x].setBackground(block);
						textFields[y*10+x].setForeground(block);
					}
				}
				
				parseCost();
			}
		});
		
		JButton button_0 = new JButton("0");
		button_0.setBackground(Color.WHITE);
		button_0.setForeground(Color.WHITE);
		button_0.setBounds(351, 11, 30, 30);
		button_0.setActionCommand("0");
		button_0.addActionListener(btc);
		contentPane.add(button_0);
		
		JButton button_1 = new JButton("1");
		button_1.setForeground(Color.CYAN);
		button_1.setBackground(Color.CYAN);
		button_1.setBounds(351, 41, 30, 30);
		button_1.setActionCommand("1");
		button_1.addActionListener(btc);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("2");
		button_2.setForeground(Color.RED);
		button_2.setBackground(Color.RED);
		button_2.setBounds(351, 71, 30, 30);
		button_2.setActionCommand("2");
		button_2.addActionListener(btc);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("3");
		button_3.setForeground(Color.GRAY);
		button_3.setBackground(Color.GRAY);
		button_3.setBounds(351, 101, 30, 30);
		button_3.setActionCommand("3");
		button_3.addActionListener(btc);
		contentPane.add(button_3);
		
		button_4 = new JButton("4");
		button_4.setForeground(Color.BLACK);
		button_4.setBackground(Color.BLACK);
		button_4.setBounds(351, 131, 30, 30);
		button_4.setActionCommand("4");
		button_4.addActionListener(btc);
		contentPane.add(button_4);
		
		JButton button_5 = new JButton("5");
		button_5.setForeground(Color.GREEN);
		button_5.setBackground(Color.GREEN);
		button_5.setBounds(351, 161, 30, 30);
		button_5.setActionCommand("5");
		button_5.addActionListener(btc);
		contentPane.add(button_5);
		
		JButton button_6 = new JButton("6");
		button_6.setForeground(Color.ORANGE);
		button_6.setBackground(Color.ORANGE);
		button_6.setActionCommand("6");
		button_6.setBounds(351, 191, 30, 30);
		button_6.addActionListener(btc);
		contentPane.add(button_6);
		
		JButton button_7 = new JButton("7");
		button_7.setForeground(Color.YELLOW);
		button_7.setBackground(Color.YELLOW);
		button_7.setBounds(351, 221, 30, 30);
		button_7.addActionListener(btc);
		contentPane.add(button_7);
		contentPane.add(btnLoadShip);
		
		JButton btnNewShip = new JButton("New Ship");
		btnNewShip.setBounds(359, 327, 120, 23);
		btnNewShip.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent f) {
				for(int t=0; t<100; t++){
					textFields[t].setText("0");
					textFields[t].setBackground(Color.WHITE);
					textFields[t].setForeground(Color.WHITE);
				}
				
				parseCost();
			}
		});
		contentPane.add(btnNewShip);
		
		JButton btnSaveShip = new JButton("Save Ship");
		btnSaveShip.setBounds(228, 327, 120, 23);
		btnSaveShip.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent f) {
				
				if(total-cost<0){
					JOptionPane.showMessageDialog(new JFrame(),
							"Error: ship file is not valid.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				} else{
				
				int[][] userShip = new int[10][10];
				for(int y=0; y<10; y++){
					for(int x=0; x<10; x++){
						userShip[y][x] = Integer.parseInt(textFields[y*10+x].getText());
					}
				}
				
				try {
					@SuppressWarnings("unused")
					ShipWrite w = new ShipWrite(userShip);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		});
		contentPane.add(btnSaveShip);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setBounds(0, 327, 88, 23);
		btnHelp.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent ae){
				@SuppressWarnings("unused")
				ControlHelp h = new ControlHelp(pictures);
			}
		
		});
		contentPane.add(btnHelp);
		
		JLabel lblEmptySpace = new JLabel("$0 Empty Space");
		lblEmptySpace.setBounds(391, 19, 133, 14);
		contentPane.add(lblEmptySpace);
		
		JLabel lblturningBlock = new JLabel("$10 Turning block");
		lblturningBlock.setBounds(391, 49, 133, 14);
		contentPane.add(lblturningBlock);
		
		JLabel lblthrustBlock = new JLabel("$10 Thrust block");
		lblthrustBlock.setBounds(391, 79, 133, 14);
		contentPane.add(lblthrustBlock);
		
		JLabel lblshieldBlock = new JLabel("$2 Shield block");
		lblshieldBlock.setBounds(391, 109, 133, 14);
		contentPane.add(lblshieldBlock);
		
		lblcontrolBlock = new JLabel("$0 Control block");
		lblcontrolBlock.setBounds(391, 139, 133, 14);
		contentPane.add(lblcontrolBlock);
		
		JLabel lblshootingBlock = new JLabel("$30 Shooting block");
		lblshootingBlock.setBounds(391, 169, 133, 14);
		contentPane.add(lblshootingBlock);
		
		JLabel lblhomingShotBlock = new JLabel("$50 HomShot block");
		lblhomingShotBlock.setBounds(391, 199, 133, 14);
		contentPane.add(lblhomingShotBlock);
		
		JLabel lblbatteryBlock = new JLabel("$60 Battery block");
		lblbatteryBlock.setBounds(391, 229, 133, 14);
		contentPane.add(lblbatteryBlock);
		
		JLabel lblMoney = new JLabel("Money");
		lblMoney.setBackground(new Color(127, 255, 0));
		lblMoney.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoney.setBounds(351, 254, 173, 14);
		contentPane.add(lblMoney);
		
		labelMoneyLeft = new JLabel("$500");
		labelMoneyLeft.setHorizontalAlignment(SwingConstants.CENTER);
		labelMoneyLeft.setFont(new Font("Courier New", Font.BOLD, 16));
		labelMoneyLeft.setBounds(351, 279, 173, 37);
		contentPane.add(labelMoneyLeft);
		
		setVisible(true);
	}
	
	public void parseCost(){
		
		cost = 0;
		boolean controlBlock=false;
		for(int y=0; y<10; y++){
			for(int x=0; x<10; x++){
				int block = Integer.parseInt(textFields[y*10+x].getText());
				
				if(block==4){
					controlBlock=true;
				}
				
				if(block==1){
					cost+=10;
				}
				if(block==2){
					cost+=10;
				}
				if(block==3){
					cost+=2;
				}
				if(block==5){
					cost+=30;
				}
				if(block==6){
					cost+=50;
				}
				if(block==7){
					cost+=60;
				}
			}
		}
		
		labelMoneyLeft.setText("$" + Integer.toString(total-cost));
		
		lblcontrolBlock.setVisible(!controlBlock);
		button_4.setVisible(!controlBlock);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		parseCost();
		
	}
}
