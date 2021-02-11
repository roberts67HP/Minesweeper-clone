package com.roberts67HP.minesweeper;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class FieldStorage {
	
	private int x, y;
	
	private JLabel button;
	private JLabel fieldPiece;
	
	private char id;
	static short idCounter = 1;
	private Bottom bObj;
	private int location;
	
	FieldStorage(int xLoc, int yLoc, JLabel l, JLabel b, Bottom bottom, int loc, char id){
		this.x = xLoc;
		this.y = yLoc;
		this.fieldPiece = l;
		this.button = b;
		this.id = '?';
		if(bObj == null) bObj = bottom; 
		this.location = loc;
	}
	public void setIDOutside (char symb) {
		this.id = symb;
		if(idCounter == 1) {
			idCounter++;
		}
	}
	public char getID() {
		return this.id;
	}
	public int getX () {
		return this.x;
	}
	public int getY () {
		return this.y;
	}
	public int getLoc () {
		return this.location;
	}
	public JLabel getTile () {
		return this.fieldPiece;
	}
	public void setTileImage (ImageIcon image) {
		this.fieldPiece.setIcon(image);
	}
	public JLabel getButton () {
		return this.button;
	}
	public int [] determineAreaAround () {
		int XAR = bObj.getMaxX(), YmaxT = bObj.getMaxY()-1, XmaxT = XAR-1;
		if(this.x == 0 && this.y == 0) {return new int [] {1,XAR,XAR+1};}
		else if(this.x == XmaxT && this.y == YmaxT) {return new int [] {-XAR-1,-XAR,-1};}
		else if(this.x == 0 && this.y == YmaxT) {return new int [] {-XAR,-XAR+1,1};}
		else if(this.x == XmaxT && this.y == 0) {return new int [] {-1,XAR-1,XAR};}
		else if (this.x == XmaxT) {return new int [] {-XAR-1,-XAR,-1,XAR-1,XAR};}
		else if (this.y == YmaxT) {return new int [] {-XAR-1,-XAR,-XAR+1,-1,1};}
		else if (this.y == 0) {return new int [] {-1,1,XAR-1,XAR,XAR+1};}
		else if (this.x == 0) {return new int [] {-XAR,-XAR+1,1,XAR,XAR+1};}
		return new int [] {-XAR-1,-XAR,-XAR+1,-1,1,XAR-1,XAR,XAR+1};
	}
}
