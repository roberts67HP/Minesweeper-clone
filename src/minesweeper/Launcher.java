package com.roberts67HP.minesweeper;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

enum Sizes {
	
	BEGINNER(9,9,10), INTERMEDIATE(16,16,40), EXPERT(31,16,99);
	
	private int xSize, ySize;
	private int mines;
	private int fieldWidth;
	
	Sizes(int x, int y, int m){
		this.xSize = x;
		this.ySize = y;
		this.mines = m;
		this.fieldWidth = xSize*32;
	}
	public int getX () {
		return this.xSize;
	}
	public int getY () {
		return this.ySize;
	}
	public int getMines () {
		return this.mines;
	}
	public int getFieldWidth () {
		return this.fieldWidth;
	}
}

public class Launcher {
	
	public static JFrame frame;
	
	public static Launcher launcher;
	private Top top;
	private Bottom bottom;
	private Menu menu;
	public Sizes dif;
	
	private int x,y,m,width;
	
	public int getXCustom () {
		return this.x;
	}
	public int getYCustom () {
		return this.y;
	}
	public int getMinesCustom () {
		return this.m;
	}
	public int getWidthCustom () {
		return this.width;
	}
	
	private boolean setInCenter = false;
	private boolean firstClick = false;
	private boolean gameOver = false;
	private static boolean firstOpen = false;
	
	public boolean getClickTick () {return firstClick;}
	public void setClickTick (boolean which) {firstClick = which;}
	
	public boolean getGameOver () {return gameOver;}
	public void setGameOver (boolean which) {gameOver = which;}
	
	public Sizes getDif() {return dif;}
	public Menu getMenu() {return this.menu;}
	public Top getTop() {return this.top;}
	public Bottom getBottom() {return this.bottom;}
	
	public static void main(String [] args) {
		Sprite.putNumbersInMap();
		frame = new JFrame("Minesweeper (Java clone)");
		new Launcher(Sizes.BEGINNER);
	}
	Launcher(Sizes d){
		launcher = this;
		dif = d;
		setOrResetFrame(dif.getX(),dif.getY(),dif.getMines(),dif.getFieldWidth());
	}
	Launcher(int xCor, int yCor, int mines){
		launcher = this;
		dif = null;
		this.x = xCor;
		this.y = yCor;
		this.m = mines;
		this.width = xCor*32;
		setOrResetFrame(xCor,yCor,mines,xCor*32);
	}
	
	private Thread thread, thread2, thread3;
	
	public void setOrResetFrame (int x, int y,int mineAmount,int allWidth) {
		
		Menu.done = false;
		Top.done = false;
		Bottom.done = false;
		
		frame.setLayout(new GridBagLayout());
		try {
			String iconLoc = System.getProperty("user.dir") + "\\gfx\\Icon.png";
			frame.setIconImage(ImageIO.read(new File(iconLoc)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.LINE_START;
				menu = new Menu ();
				frame.add(menu,gbc);
				frame.setJMenuBar(menu);
				Menu.done = true;
			}
		});
		thread.start();
		
		thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.gridy = 1;
				top = new Top(allWidth);
				frame.add(top,gbc);
				Top.done = true;
			}
		});
		thread2.start();
		
		thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridy = 2;
				bottom = new Bottom(x,y,mineAmount);
				frame.add(bottom,gbc);
				Bottom.done = true;
			}
		});
		thread3.start();
		try {
			do {
				if(Menu.done) {
					thread.join();
					if(Top.done) {
						thread2.join();
						if(Bottom.done) {
							thread3.join();
						}
					}
				}
				System.out.print("");
			} while (!Bottom.done);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		frame.setResizable(false);
		frame.addMouseListener(new Mouse("general"));
		frame.pack();
		if(!setInCenter) {
			setInCenter = true;
			frame.setLocationRelativeTo(null);
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		if(!firstOpen) {
			System.out.println("Everything has been loaded.");
			System.out.println("Enjoy this game :)");
			firstOpen = true;
		}
	}
}
