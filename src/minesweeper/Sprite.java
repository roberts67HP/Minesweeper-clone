package com.roberts67HP.minesweeper;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Sprite {
	
	public static Sprite unClicked = new Sprite(0,0,16,16,Sheet.field);
	public static Sprite clicked = new Sprite(1,0,16,16,Sheet.field);
	public static Sprite oneCl = new Sprite(2,0,16,16,Sheet.field);
	public static Sprite twoCl = new Sprite(3,0,16,16,Sheet.field);
	public static Sprite threeCl = new Sprite(4,0,16,16,Sheet.field);
	public static Sprite fourCl = new Sprite(5,0,16,16,Sheet.field);
	public static Sprite fiveCl = new Sprite(6,0,16,16,Sheet.field);
	public static Sprite sixCl = new Sprite(7,0,16,16,Sheet.field);
	public static Sprite sevenCl = new Sprite(8,0,16,16,Sheet.field);
	public static Sprite eightCl = new Sprite(9,0,16,16,Sheet.field);
	public static Sprite flag = new Sprite(10,0,16,16,Sheet.field);
	public static Sprite question = new Sprite(11,0,16,16,Sheet.field);
	public static Sprite mine = new Sprite(12,0,16,16,Sheet.field);
	public static Sprite mineRed = new Sprite(13,0,16,16,Sheet.field);
	public static Sprite mineCrossed = new Sprite(14,0,16,16,Sheet.field);
	
	public static Sprite onClick = new Sprite(15,0,16,16,Sheet.field);
	public static Sprite onClickFlag = new Sprite(16,0,16,16,Sheet.field);
	public static Sprite onClickQuestion = new Sprite(17,0,16,16,Sheet.field);
	
	public static Sprite defaultFace = new Sprite(0,0,26,26,Sheet.emotions);
	public static Sprite selectFace = new Sprite(1,0,26,26,Sheet.emotions);
	public static Sprite winFace = new Sprite(0,1,26,26,Sheet.emotions);
	public static Sprite deadFace = new Sprite(1,1,26,26,Sheet.emotions);
	
	public static Sprite defaultClicked = new Sprite(0,2,26,26,Sheet.emotions);
	public static Sprite winClicked = new Sprite(0,3,26,26,Sheet.emotions);
	public static Sprite deadClicked = new Sprite(1,3,26,26,Sheet.emotions);
	
	public static Sprite you_fucking_idiot = new Sprite(0,0,265,170,Sheet.idiot);
	
	public static void putNumbersInMap () {
		topNums.put(0,new Sprite(0,0,13,23,Sheet.numbers));
		topNums.put(1,new Sprite(1,0,13,23,Sheet.numbers));
		topNums.put(2,new Sprite(2,0,13,23,Sheet.numbers));
		topNums.put(3,new Sprite(3,0,13,23,Sheet.numbers));
		topNums.put(4,new Sprite(4,0,13,23,Sheet.numbers));
		topNums.put(5,new Sprite(5,0,13,23,Sheet.numbers));
		topNums.put(6,new Sprite(6,0,13,23,Sheet.numbers));
		topNums.put(7,new Sprite(7,0,13,23,Sheet.numbers));
		topNums.put(8,new Sprite(8,0,13,23,Sheet.numbers));
		topNums.put(9,new Sprite(9,0,13,23,Sheet.numbers));
		
		botNums.put(0,new Sprite(2,0,16,16,Sheet.field));
		botNums.put(1,new Sprite(3,0,16,16,Sheet.field));
		botNums.put(2,new Sprite(4,0,16,16,Sheet.field));
		botNums.put(3,new Sprite(5,0,16,16,Sheet.field));
		botNums.put(4,new Sprite(6,0,16,16,Sheet.field));
		botNums.put(5,new Sprite(7,0,16,16,Sheet.field));
		botNums.put(6,new Sprite(8,0,16,16,Sheet.field));
		botNums.put(7,new Sprite(9,0,16,16,Sheet.field));
	}
	
	public static Map <Integer,Sprite> topNums = new HashMap <Integer,Sprite> ();
	public static Map <Integer,Sprite> botNums = new HashMap <Integer,Sprite> ();
	private int xLoc, yLoc, width, height;
	ImageIcon sprite;
	
	
	Sprite(int x,int y, int w, int h, Sheet sh){
		this.xLoc = x*w;
		this.yLoc = y*h;
		this.width = w;
		this.height = h;
		
		BufferedImage cut = sh.getSheetImage().getSubimage(xLoc, yLoc, width, height);
		
		int largerX = cut.getWidth()*2, largerY = cut.getHeight()*2;
		Image scaled = cut.getScaledInstance(largerX, largerY, Image.SCALE_DEFAULT);
		BufferedImage sc = new BufferedImage (largerX,largerY,BufferedImage.TYPE_INT_ARGB);
		Graphics g = sc.getGraphics();
		g.drawImage(scaled,0,0,null);
		g.dispose();
		this.sprite = new ImageIcon(sc);
	}
	public ImageIcon getSprite() {
		return this.sprite;
	}
}
class Sheet {
	
	static Sheet field = new Sheet ("\\gfx\\Field.png");
	static Sheet emotions = new Sheet ("\\gfx\\Emotions.png");
	static Sheet numbers = new Sheet ("\\gfx\\Numbers.png");
	static Sheet idiot = new Sheet ("\\gfx\\screen_too_big.png");
	
	private BufferedImage image;
	
	Sheet(String path){
		try {
			String loc = System.getProperty("user.dir") + path;
			this.image = ImageIO.read(new File(loc));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	BufferedImage getSheetImage() {
		return this.image;
	}
}
