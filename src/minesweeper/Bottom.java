package com.roberts67HP.minesweeper;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

public class Bottom extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private int x, y;
	private JLabel button, field;
	private int butArrange [][];
	
	static int mineAmount;
	static int flagCount = 0; 
	private int mineAround = 0;
	
	private FieldStorage current;
	private FieldStorage check;
	
	private Map <Integer, FieldStorage> fieldSt = new HashMap <Integer, FieldStorage> ();
	
	public Map <Integer, FieldStorage> getFieldStorage(){
		return fieldSt;
	}
	public int getMaxX () {
		return this.x;
	}
	public int getMaxY () {
		return this.y;
	}
	
	public static boolean done;
	
	Bottom(int xField, int yField, int mineA){
		this.x = xField;
		this.y = yField;
		mineAmount = mineA;
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), 
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
		setBackground(Color.LIGHT_GRAY);
		setField(false);
	}
	private void setField(boolean postSetup) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.butArrange = new int [y][x];
		char id = '?';
		Random rand = new Random ();
		/*
		 * ADDS TILES, RANDOMISES MINES AND ADDS BUTTONS
		 */
		for(int i = 0, t = 0;i<butArrange.length;i++) {
			gbc.gridy += i;
			for(int j = 0;j<butArrange[i].length;j++, t++) {
				gbc.gridx += j;
				
				button = new JLabel ();
				button.setIcon(Sprite.unClicked.getSprite());
				add(button,gbc);
				
				int which = rand.nextInt(100);
				
				field = new JLabel ();
				
				//MINE RANDOMISER PART 1
				if(which <= 80) field.setIcon(Sprite.clicked.getSprite());
				else {
					flagCount++;
					field.setIcon(Sprite.mine.getSprite());
					id = 'm';
				}
				
				fieldSt.put(t, new FieldStorage(j,i,field,button, this,t,id));
				button.addMouseListener(new Mouse(button,"click field",t, fieldSt.get(t)));
				add(field,gbc);
				id = '?';
			}
			gbc.gridx = 0;
		}
		///MINE RANDOMISER PART 2
		if(flagCount!=mineAmount) {
			for(int m = 0;flagCount!=mineAmount;m++) {
				if(m==fieldSt.size()) m = 0;
				current = fieldSt.get(m);
				int result = rand.nextInt(100);
				if(flagCount<mineAmount && current.getTile().getIcon().hashCode() == Sprite.clicked.getSprite().hashCode()) {
					if(result <= 80) {
						current.getTile().setIcon(Sprite.mine.getSprite());
						current.setIDOutside('m');
						flagCount++;
					}
				} else if (flagCount>mineAmount && current.getTile().getIcon().hashCode() == Sprite.mine.getSprite().hashCode()) {
					if(result <= 30) {
						current.getTile().setIcon(Sprite.clicked.getSprite());
						current.setIDOutside('?');
						flagCount--;
					}
				}
			}
		}
		setTopNumbers(flagCount,Top.flags1,Top.flags2,Top.flags3);
		setTopNumbers(0,Top.time1,Top.time2,Top.time3);
	}
	public void setTileCategories (boolean erMineSet) {
		/*
		 * SETS NUMBERS ON THE FIELDS AND SPLITS SAFE FIELDS FOR ACCURATE REVEALING OF SAFE TILES
		 */
		while (erMineSet) {
			current = fieldSt.get((new Random ()).nextInt(fieldSt.size()));
			if(!(current.getTile().getIcon().hashCode() == Sprite.mine.getSprite().hashCode())) {
				current.setTileImage(Sprite.mine.getSprite());
				erMineSet = false;
			}
		}
		for(int t = 0;t<fieldSt.size();t++) {
			current = fieldSt.get(t);
			if(current.getTile().getIcon().hashCode() != Sprite.mine.getSprite().hashCode()) {
				int Cor [] = current.determineAreaAround();
				findMineCount(Cor,t);
				addNumber();
			}
		}	
		setTimer();
	}
	public void setTopNumbers (int count, JLabel one, JLabel two, JLabel three) {
		String flags = turnNumbersIntoStrings(count);
		one.setIcon(setNumber(flags.substring(flags.length()-3,flags.length()-2)));
		two.setIcon(setNumber(flags.substring(flags.length()-2,flags.length()-1)));
		three.setIcon(setNumber(flags.substring(flags.length()-1,flags.length())));
	}
	
	private Thread timerThread;
	private Timer timer;
	
	public void stopTimer() {
		if(timer != null) {
			this.timer.stop();
			try {
				this.timerThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	
	private int fin;
	private long nowMillis;
	
	public void setTimer () {
		long createdMillis = System.currentTimeMillis();
		this.timer = new Timer (100, (act) -> {
			this.timerThread = new Thread (new Runnable() {
				@Override
				public void run() {
					nowMillis = System.currentTimeMillis();
			        fin = (int)((nowMillis - createdMillis) / 1000);
			        if(fin>999) {
			        	System.out.println("happen");
			        	stopTimer();
			        }
			        setTopNumbers(fin,Top.time1,Top.time2,Top.time3);
				}
			});
			this.timerThread.start();
		});
		this.timer.start();
	}
	///find image for Top class number
	private ImageIcon setNumber (String num) {
		for(int i = 0;i<Sprite.topNums.size();i++) {
			if(num.equals(Integer.toString(i))) {
				return Sprite.topNums.get(i).getSprite();
			}
		}
		return null;
	}
	///converts current number of flags to set proper image
	private String turnNumbersIntoStrings (int number) {
		String numSt = Integer.toString(number);
		if(numSt.length()<3) {
			for(int i = 0;i<=3-numSt.length();i++) {
				numSt = numSt.replaceFirst("", "0");
			}
		}
		return numSt;
	}
	///detects mines around each field
	private void findMineCount (int cor [],int t) {
		for(int f = 0;f<cor.length;f++) {
			check = fieldSt.get(t+cor[f]);
			if(check.getTile().getIcon().hashCode() == Sprite.mine.getSprite().hashCode()) {
				mineAround++;
				continue;
			}
		}
	}
	///sets the appropriate image for each field in the Bottom class
	private void addNumber () {
		for(int i = 0;i<Sprite.botNums.size();i++) {
			if(mineAround == i+1) {
				current.getTile().setIcon(Sprite.botNums.get(i).getSprite());
			}
		}
		mineAround = 0;
	}
}