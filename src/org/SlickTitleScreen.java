package org;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
//import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import utils.ShipGet;
import utils.SlickButton;
import utils.SlickLabel;
import utils.jbox2slick;

public class SlickTitleScreen extends ShipGame{
	private static Input in;
	
	//private static int[][] p1s;
	//private static int[][] p2s;
	
	private int[] pBattleScore = new int[2];
	private int[] cBattleScore = new int[2];
	private int[] onslaughtScore = new int[2];
	private int[] collectScore = new int[2];
	
	private ShipGame game = new ShipGame("SPACEBLOCKS");
	
	private int gameType = 0;
	private int numOfEnemies = 1;
	private int gameLevel = 1;
	//private java.awt.Image background = (new ImageIcon(SlickTitleScreen.class.getResource("/org/title.png"))).getImage();
	private Image realBG;
	private SlickLabel[] slickLabels;
	private SlickButton[] slickButtons;
	
	public static void main(String[] args) throws SlickException{
		
		Sound s = new Sound("Pickup_Coin.ogg");
		s.play();
		
		refreshShips();
		
		try {
			AppGameContainer container = new AppGameContainer(new SlickTitleScreen("d"));
			container.setDisplayMode(container.getScreenWidth(), container.getScreenHeight(),false);    		//last arg = fullscreen
			ShipGame.tr = new jbox2slick(container.getScreenWidth(), container.getScreenHeight(), 240, 135);
			container.setShowFPS(false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public SlickTitleScreen(String title) {
		super("SPACEBLOCKS");
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
		if(!(game instanceof ShipTest || game instanceof ShipOnslaught || game instanceof ShipCollect || game instanceof SlickHelp)){
			renderTitle(arg1,arg0);
		} else{
			game.render(arg0, arg1);
		}
	}
	
	private void renderTitle(Graphics g, GameContainer arg0){
		final int width = (arg0.getHeight()+25)*434/228;
		
		realBG.draw(0,
					0,
					width,
					arg0.getHeight()+25
		);
		for(SlickButton s: slickButtons){
			s.draw(g);
			g.setColor(Color.black);
			s.drawText(g);
		}
		for(SlickLabel s: slickLabels){
			s.draw(g);
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		slickLabels = new SlickLabel[7];
		slickButtons = new SlickButton[9];
		in = arg0.getInput();
		game.init(arg0);
		
		Color lightblue1 = new Color(51,153,255);
		Color lightblue2 = new Color(0,102,204);
		
		jbox2slick tr = ShipGame.tr;
		
		List<int[]> coords = new ArrayList<int[]>();
		List<int[]> widths = new ArrayList<int[]>();
		List<String> texts = new ArrayList<String>();
		
		coords.add(tr.toSlick(-38, 19));
		widths.add(tr.wToSlick(76, 38));
		texts.add("PLAY");
		
		coords.add(tr.toSlick(-38, 41));
		widths.add(tr.wToSlick(76, 15));
		texts.add("LOAD SHIPS");
		
		coords.add(tr.toSlick(-19, -26));
		widths.add(tr.wToSlick(38, 11));
		texts.add("help");
		
		coords.add(tr.toSlick(19, -41));
		widths.add(tr.wToSlick(15, 15));
		texts.add("<");
		
		coords.add(tr.toSlick(98, -41));
		widths.add(tr.wToSlick(15, 15));
		texts.add(">");
		
		coords.add(tr.toSlick(71, 60));
		widths.add(tr.wToSlick(11, 11));
		texts.add("<");
		
		coords.add(tr.toSlick(101, 60));
		widths.add(tr.wToSlick(11, 11));
		texts.add(">");
		
		coords.add(tr.toSlick(71, 45));
		widths.add(tr.wToSlick(11, 11));
		texts.add("<");
		
		coords.add(tr.toSlick(101, 45));
		widths.add(tr.wToSlick(11, 11));
		texts.add(">");
		
		for(int index=0; index<slickButtons.length; index++){
			slickButtons[index] = new SlickButton(coords.get(index)[0],coords.get(index)[1],
					widths.get(index)[0],widths.get(index)[1],
					lightblue1,Color.blue,Color.cyan,lightblue2,10,texts.get(index));
		}
		
		coords = new ArrayList<int[]>();
		widths = new ArrayList<int[]>();
		texts = new ArrayList<String>();
		List<Integer> ali = new ArrayList<Integer>();
		
		coords.add(tr.toSlick(-113, 30));
		widths.add(tr.wToSlick(1, 7));
		texts.add("Player 1 Score");
		ali.add(SlickLabel.LEFT_ALIGNED);
		
		coords.add(tr.toSlick(-115, 19));
		widths.add(tr.wToSlick(1, 38));
		texts.add("0");
		ali.add(SlickLabel.LEFT_ALIGNED);
		
		coords.add(tr.toSlick(56, 30));
		widths.add(tr.wToSlick(56, 7));
		texts.add("Player 2 Score");
		ali.add(SlickLabel.RIGHT_ALIGNED);
		
		coords.add(tr.toSlick(56, 16));
		widths.add(tr.wToSlick(50, 38));
		texts.add("0");
		ali.add(SlickLabel.RIGHT_ALIGNED);
		
		coords.add(tr.toSlick(38, -45));
		widths.add(tr.wToSlick(56, 7));
		texts.add("2-Player Battle");
		ali.add(SlickLabel.CENTER_ALIGNED);
		
		coords.add(tr.toSlick(86, 60));
		widths.add(tr.wToSlick(11, 11));
		texts.add("1");
		ali.add(SlickLabel.CENTER_ALIGNED);
		
		coords.add(tr.toSlick(86, 45));
		widths.add(tr.wToSlick(11, 11));
		texts.add("1");
		ali.add(SlickLabel.CENTER_ALIGNED);
		
		for(int index=0; index<slickLabels.length; index++){
			slickLabels[index] = new SlickLabel(coords.get(index)[0], coords.get(index)[1],
					1,widths.get(index)[0],texts.get(index),ali.get(index), widths.get(index)[1]);
		}
		
		if(arg0 instanceof AppGameContainer){
			app = (AppGameContainer) arg0;
		}
		
		realBG = new Image("org/title.png");
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
		if(ShipGame.over){
			
			if(gameType == 0){
				if(game.winner == 1){
					pBattleScore[0]++;
				} else if(game.winner == 2){
					pBattleScore[1]++;
				}
			} else if(gameType == 1){
				if(game.winner == 1){
					cBattleScore[0]++;
				} else if(game.winner == 2){
					cBattleScore[1]++;
				}
			} else if(gameType == 2){
				onslaughtScore[0] += game.winner;
				onslaughtScore[1]++;
			} else if(gameType == 3){
				collectScore[0] += game.p1Orbs;
				collectScore[1] += game.p2Orbs;
			}
			
			game = new ShipGame("SPACEBLOCKS");
			game.init(arg0);
			ShipGame.over = false;
		}
		game.update(arg0, arg1);
		
		for(SlickButton s: slickButtons){
			s.update(in);
		}
		
		checkButtons(arg0);
		
		refreshLabels();
		
		if(in.isKeyPressed(Input.KEY_F) && in.isKeyDown(Input.KEY_PERIOD)){
			//arg0.setFullscreen(!arg0.isFullscreen());
		}
		
		if(in.isKeyPressed(Input.KEY_ESCAPE)){
			closeRequested();
		}
	}
	
	private void checkButtons(GameContainer arg0) throws SlickException{

		if(slickButtons[0].clicked){
			startNewGame();
			setLevel(gameLevel);
			game.init(arg0);
		}
		
		if(slickButtons[1].clicked){
			refreshShips();
		}
		
		if(slickButtons[2].clicked){
			
			Image[] pictures = {new Image("org/controlhelp.png"),
					new Image("org/battlemode.png"),
					new Image("org/onslaught.png"),
					new Image("org/collectionmode.png")
			};
			
			game = new SlickHelp("SPACEBLOCKS", pictures);
			game.init(arg0);
		}
		
		if(slickButtons[3].clicked){
			gameType--;
			if(gameType<0){
				gameType=3;
			}
			refreshLabels();
		}
		
		if(slickButtons[4].clicked){
			gameType++;
			if(gameType>3){
				gameType=0;
			}
			refreshLabels();
		}
		
		if(slickButtons[5].clicked){
			numOfEnemies--;
			if(numOfEnemies<1){
				numOfEnemies=3;
			}
			refreshLabels();
		}
		
		if(slickButtons[6].clicked){
			numOfEnemies++;
			if(numOfEnemies>3){
				numOfEnemies=1;
			}
			refreshLabels();
		}
		
		if(slickButtons[7].clicked){
			gameLevel--;
			if(gameLevel<1){
				gameLevel=7;
			}
			refreshLabels();
		}
		
		if(slickButtons[8].clicked){
			gameLevel++;
			if(gameLevel>7){
				gameLevel=1;
			}
			refreshLabels();
		}
		
		if(game instanceof SlickHelp){
			if(((SlickHelp)game).closeButton.clicked){
				game = new ShipGame("SPACEBLOCKS");
			}
		}
	}
	
	private void startNewGame(){
		if(gameType <= 1){
			game = new ShipTest(gameType * numOfEnemies);
		} else if(gameType == 2){
			game = new ShipOnslaught();
		} else if(gameType == 3){
			game = new ShipCollect();
		}
	}
	
	private void refreshLabels(){
		if(gameType == 0 || gameType == 3){
			slickButtons[5].shown=false;
			slickButtons[6].shown=false;
			slickButtons[7].shown=false;
			slickButtons[8].shown=false;
			
			slickLabels[5].shown=false;
			slickLabels[6].shown=false;
			
			if(gameType == 0){
				slickLabels[1].text = Integer.toString(pBattleScore[0]);
				slickLabels[3].text = Integer.toString(pBattleScore[1]);
				slickLabels[4].text = "2-Player Battle";
			} else{
				slickLabels[1].text = Integer.toString(collectScore[0]);
				slickLabels[3].text = Integer.toString(collectScore[1]);
				slickLabels[4].text = "Collection";
			}
		} else {
			
			slickButtons[7].shown=true;
			slickButtons[8].shown=true;
			
			slickLabels[6].shown=true;
			
			if(gameType == 1){
			
				slickButtons[5].shown=true;
				slickButtons[6].shown=true;
				
				slickLabels[5].shown=true;
				
				slickLabels[1].text = Integer.toString(cBattleScore[0]);
				slickLabels[3].text = Integer.toString(cBattleScore[1]);
				slickLabels[5].text = Integer.toString(numOfEnemies);
				slickLabels[6].text = Integer.toString(gameLevel);
				slickLabels[4].text = "1-Player Battle";
			} else{
				
				slickButtons[5].shown=false;
				slickButtons[6].shown=false;
				
				slickLabels[5].shown=false;
				
				slickLabels[1].text = Integer.toString(onslaughtScore[0]);
				slickLabels[3].text = Integer.toString(onslaughtScore[1]);
				slickLabels[6].text = Integer.toString(gameLevel);
				slickLabels[4].text = "Onslaught";
			}
			
		}
		
	}
	
	private void setLevel(int level){
		if(level ==1){
			ShipGame.level = 15000;
		} else if(level ==2){
			ShipGame.level = 7500;
		} else if(level ==3){
			ShipGame.level = 3750;
		} else if(level ==4){
			ShipGame.level = 1875;
		} else if(level ==5){
			ShipGame.level = 938;
		} else if(level ==6){
			ShipGame.level = 469;
		} else if(level ==7){		//This is faster than any human
			ShipGame.level = 200;				//It shoots 5 times a second
		}
	}
	
	private static void refreshShips(){
		ShipGet get = null;
		
		try {
			get = new ShipGet();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ShipGame.player1Ship = get.player1Ship;
		ShipGame.player2Ship = get.player2Ship;
	}
}
