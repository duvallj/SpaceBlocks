package org;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import design.MainGUI;
import utils.ShipGet;
import utils.jbox2slick;

import java.awt.Font;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.net.URL;

@Deprecated
public class TitleScreen implements ActionListener{

	private JFrame frame;
	
	private JLabel p1ScoreLabel;
	private JLabel p2ScoreLabel;
	private JLabel label_1;
	private JLabel label;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	private JLabel lblLevel;
	@SuppressWarnings("rawtypes")
	private JComboBox comboNumber;
	private JLabel lblNumber;
	private JLabel lblplayerBattle;
	
	private int[][] p1s;
	private int[][] p2s;
	
	private int[] pBattleScore = new int[2];
	private int[] cBattleScore = new int[2];
	private int[] onslaughtScore = new int[2];
	private int[] collectScore = new int[2];
	
	private int mode=0;
	private final JButton btnHelp = new JButton("Help");
	
	private URL[] pictures = {TitleScreen.class.getResource("/org/controlhelp.png"),
			TitleScreen.class.getResource("/org/battlemode.png"),
			TitleScreen.class.getResource("/org/onslaught.png"),
			TitleScreen.class.getResource("/org/collectionmode.png")
	};
	private JButton btnDesign;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TitleScreen window = new TitleScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TitleScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.setBounds(130, 80, 166, 44);
		btnPlay.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent f) {
				startNewGame();
			}
		});
		frame.getContentPane().add(btnPlay);
		
		btnHelp.setBounds(130, 131, 59, 23);
		btnHelp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent f) {
				@SuppressWarnings("unused")
				ControlHelp h = new ControlHelp(pictures);
			}
		});
		frame.getContentPane().add(btnHelp);
		
		JButton btnLoad = new JButton("Load Ships");
		btnLoad.setBounds(130, 50, 166, 23);
		btnLoad.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent f) {
				try {
					refreshShips();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnLoad);
		
		btnDesign = new JButton("Design");
		btnDesign.setBounds(199, 131, 97, 23);
		btnDesign.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent f) {
				@SuppressWarnings("unused")
				MainGUI n = new MainGUI();
			}
		});
		frame.getContentPane().add(btnDesign);
		
		p2ScoreLabel = new JLabel("");
		p2ScoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		p2ScoreLabel.setFont(new Font("Courier New", Font.BOLD, 38));
		p2ScoreLabel.setBounds(306, 100, 111, 51);
		frame.getContentPane().add(p2ScoreLabel);
		
		p1ScoreLabel = new JLabel("");
		p1ScoreLabel.setFont(new Font("Courier New", Font.BOLD, 38));
		p1ScoreLabel.setBounds(10, 100, 110, 51);
		frame.getContentPane().add(p1ScoreLabel);
		
		lblLevel = new JLabel("Level");
		lblLevel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLevel.setBounds(306, 50, 54, 14);
		frame.getContentPane().add(lblLevel);
		lblLevel.setVisible(false);
		
		lblNumber = new JLabel("Number");
		lblNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumber.setBounds(306, 30, 54, 14);
		frame.getContentPane().add(lblNumber);
		lblNumber.setVisible(false);
		
		lblplayerBattle = new JLabel("2-player Battle");
		lblplayerBattle.setHorizontalAlignment(SwingConstants.CENTER);
		lblplayerBattle.setBounds(278, 162, 139, 23);
		frame.getContentPane().add(lblplayerBattle);
		
		JButton buttonLeft = new JButton("<");
		buttonLeft.setBounds(278, 196, 41, 23);
		buttonLeft.setActionCommand("-1");
		buttonLeft.addActionListener(this);
		frame.getContentPane().add(buttonLeft);
		
		JButton buttonRight = new JButton(">");
		buttonRight.setBounds(376, 196, 41, 23);
		buttonRight.setActionCommand("1");
		buttonRight.addActionListener(this);
		frame.getContentPane().add(buttonRight);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7"}));
		comboBox.setBounds(370, 44, 47, 20);
		frame.getContentPane().add(comboBox);
		comboBox.setVisible(false);
		
		comboNumber = new JComboBox();
		comboNumber.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboNumber.setBounds(370, 24, 47, 20);
		frame.getContentPane().add(comboNumber);
		comboNumber.setVisible(false);
		
		JLabel lblPlayerScore = new JLabel("Player 1 Score");
		lblPlayerScore.setBounds(10, 75, 110, 14);
		frame.getContentPane().add(lblPlayerScore);
		
		label_1 = new JLabel("Player 2 Score");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(316, 75, 108, 14);
		frame.getContentPane().add(label_1);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(TitleScreen.class.getResource("/org/title.png")));
		label.setBounds(0, -20, 450, 300);
		frame.getContentPane().add(label);
		
		ShipGet get = null;
		try {
			get = new ShipGet();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p1s = get.player1Ship;
		p2s = get.player2Ship;
		
		refreshLabels();
		
	}
	
	public void startNewGame(){
		if(mode==0 || mode==1){
			startNewBattle();
		} else if(mode==2){
			startNewOnslaught();
		} else if(mode==3){
			startNewCollect();
		}
	}
	
	@SuppressWarnings("static-access")
	public void startNewBattle() {
		ShipTest sh = new ShipTest(mode * Integer.parseInt((String) comboNumber.getSelectedItem()));
		sh.player1Ship = p1s;
		sh.player2Ship = p2s;
		
		String l = (String)comboBox.getSelectedItem();
		
		if(l.equals("1")){
			sh.level = 15000;
		} else if(l.equals("2")){
			sh.level = 7500;
		} else if(l.equals("3")){
			sh.level = 3750;
		} else if(l.equals("4")){
			sh.level = 1875;
		} else if(l.equals("5")){
			sh.level = 938;
		} else if(l.equals("6")){
			sh.level = 469;
		} else if(l.equals("7")){		//This is faster than any human
			sh.level = 200;				//It shoots 5 times a second
		}
		
		try {
			AppGameContainer container = new AppGameContainer(sh);
			sh.x = container.getScreenWidth();
			sh.y = container.getScreenHeight();
			sh.tr = new jbox2slick(sh.x,sh.y,sh.x/8,sh.y/8);
			container.setDisplayMode(sh.x,sh.y,false);    		//last arg = fullscreen
			container.setForceExit(false);
			container.start();
			if(sh.winner==1){
				if(mode==0){
					pBattleScore[0]++;
				} else{
					cBattleScore[0]++;
				}
			}
			if(sh.winner==2){
				if(mode==0){
					pBattleScore[1]++;
				} else{
					cBattleScore[1]++;
				}
			}
			container.destroy();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		refreshLabels();
	}
	
	@SuppressWarnings("static-access")
	public void startNewOnslaught(){
		
		ShipOnslaught sh = new ShipOnslaught();
		sh.player1Ship = p1s;
		sh.player2Ship = p2s;
		
		String l = (String)comboBox.getSelectedItem();
		
		if(l.equals("1")){
			sh.level = 15000;
		} else if(l.equals("2")){
			sh.level = 7500;
		} else if(l.equals("3")){
			sh.level = 3750;
		} else if(l.equals("4")){
			sh.level = 1875;
		} else if(l.equals("5")){
			sh.level = 938;
		} else if(l.equals("6")){
			sh.level = 469;
		} else if(l.equals("7")){		//This is faster than any human
			sh.level = 200;				//It shoots 5 times a second
		}
		
		try {
			AppGameContainer container = new AppGameContainer(sh);
			sh.x = container.getScreenWidth();
			sh.y = container.getScreenHeight();
			sh.tr = new jbox2slick(sh.x,sh.y,sh.x/8,sh.y/8);
			container.setDisplayMode(sh.x,sh.y,false);    		//last arg = fullscreen
			container.setForceExit(false);
			container.start();
			onslaughtScore[0] += sh.winner;
			container.destroy();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		refreshLabels();
		
	}
	
	@SuppressWarnings("static-access")
	public void startNewCollect(){
		
		ShipCollect sh = new ShipCollect();
		sh.player1Ship = p1s;
		sh.player2Ship = p2s;
		
		try {
			AppGameContainer container = new AppGameContainer(sh);
			sh.x = container.getScreenWidth();
			sh.y = container.getScreenHeight();
			sh.tr = new jbox2slick(sh.x,sh.y,sh.x/8,sh.y/8);
			container.setDisplayMode(sh.x,sh.y,false);    		//last arg = fullscreen
			container.setForceExit(false);
			container.start();
			collectScore[0] += sh.p1Orbs;
			collectScore[1] += sh.p2Orbs;
			container.destroy();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		refreshLabels();
		
	}
	
	public void refreshShips() throws URISyntaxException, IOException{
		ShipGet get = new ShipGet();
		p1s = get.player1Ship;
		p2s = get.player2Ship;
	}
	
	public void refreshLabels(){
		if(mode==0){
			p1ScoreLabel.setText(Integer.toString(pBattleScore[0]));
			p2ScoreLabel.setText(Integer.toString(pBattleScore[1]));
		} else if(mode==1){
			p1ScoreLabel.setText(Integer.toString(cBattleScore[0]));
			p2ScoreLabel.setText(Integer.toString(cBattleScore[1]));
		} else if(mode==2){
			p1ScoreLabel.setText(Integer.toString(onslaughtScore[0]));
			p2ScoreLabel.setText(Integer.toString(cBattleScore[1]));	//Because I want something other than zero
		} else if(mode==3){
			p1ScoreLabel.setText(Integer.toString(collectScore[0]));
			p2ScoreLabel.setText(Integer.toString(collectScore[1]));
		}
	}
	
	public void changeMode(byte way){
		
		mode += way;
		if(mode<0){
			mode=3;
		}
		if(mode>3){
			mode=0;
		}
		if(mode==0){
			lblplayerBattle.setText("2-Player Battle");
			
			lblLevel.setVisible(false);
			comboBox.setVisible(false);
			lblNumber.setVisible(false);
			comboNumber.setVisible(false);
			
		}
		if(mode==1){
			lblplayerBattle.setText("CPU Battle");
			
			lblLevel.setVisible(true);
			comboBox.setVisible(true);
			lblNumber.setVisible(true);
			comboNumber.setVisible(true);
			
		}
		if(mode==2){
			lblplayerBattle.setText("CPU Onslaught");
			
			lblLevel.setVisible(true);
			comboBox.setVisible(true);
			lblNumber.setVisible(false);
			comboNumber.setVisible(false);
			
		}
		if(mode==3){
			
			lblplayerBattle.setText("2-Player Collection");
			
			lblLevel.setVisible(false);
			comboBox.setVisible(false);
			lblNumber.setVisible(false);
			comboNumber.setVisible(false);
			
		}
		
		refreshLabels();
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		changeMode((byte) Integer.parseInt(arg0.getActionCommand()));
		
	}
}
