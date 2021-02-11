package com.roberts67HP.minesweeper;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Mouse implements MouseListener{

	private JLabel button;
	private String action;
	static int corMineC;
	private Icon store;
	
	private FieldStorage storage;
	
	//mouse for bottom
	Mouse (JLabel b, String a, int t, FieldStorage s) {
		this.button = b;
		this.action = a;
		this.storage = s;
		markFunctions();
	}
	//mouse for general
	Mouse (String a){
		this.action = a;
	}
	@Override
	public void mouseClicked(MouseEvent cl) {}

	@Override
	public void mouseEntered(MouseEvent ent) {}

	@Override
	public void mouseExited(MouseEvent ex) {}

	@Override
	public void mousePressed(MouseEvent pr) {
		if(!Launcher.launcher.getGameOver()) {
			Top.smile.setIcon(Sprite.selectFace.getSprite());
			if(action.equals("click field")) {
				if(SwingUtilities.isLeftMouseButton(pr) && this.button.getIcon().hashCode() != Sprite.flag.getSprite().hashCode()) {
					//GENERAL ON CLICK
					this.storage.getButton().setIcon(Sprite.onClick.getSprite());
				} else if(SwingUtilities.isRightMouseButton(pr)) {
					if(Bottom.flagCount == 0 && this.flag) {
						this.flag = false;
						this.qstion = true;
					}
					store = this.button.getIcon();
					//SETS EMPTY BUTTON,FLAG AND QUESTION MARK ON CLICK
					if(this.flag && Bottom.flagCount != 0) this.button.setIcon(Sprite.onClickFlag.getSprite());
					else if(this.qstion) this.button.setIcon(Sprite.onClickQuestion.getSprite());
					else if(this.def) this.button.setIcon(Sprite.onClick.getSprite());
				}
			}
		}	
	}

	@Override
	public void mouseReleased(MouseEvent re) {
		if(!Launcher.launcher.getGameOver()) {
			if(action.equals("click field")) {
				if(SwingUtilities.isLeftMouseButton(re) && this.button.getIcon().hashCode() != Sprite.flag.getSprite().hashCode()) {
					
					this.storage.getButton().setIcon(Sprite.unClicked.getSprite());
					
					//AFTER THE FIRST CLICK MOVES THE MINE TO A DIFFERENT LOCATION
					moveMine();
					
					if(this.storage.getTile().getIcon().hashCode() == Sprite.mine.getSprite().hashCode()) {
						setLoseConditions();
						return;
					} else if(this.storage.getTile().getIcon().hashCode() == Sprite.clicked.getSprite().hashCode()) {
						revealNumberlessTiles(Launcher.launcher.getBottom().getFieldStorage(), this.storage);
					}
					
					removeButton(this.storage);
				} else if(SwingUtilities.isRightMouseButton(re)) {
					
					//SETS EMPTY BUTTON,FLAG AND QUESTION MARK
					markingFunctions();
					
					//CHECKS IF WINNING CONDITIONS
					if(corMineC == Bottom.mineAmount) {
						Launcher.launcher.getBottom().stopTimer();
						Top.smile.setIcon(Sprite.winFace.getSprite());
						revealTiles(Launcher.launcher.getBottom().getFieldStorage(),false);
						Launcher.launcher.setGameOver(true);
						return;
					}
				}
			}
			Top.smile.setIcon(Sprite.defaultFace.getSprite());
		}
	}
	
	private void setLoseConditions() {
		Launcher.launcher.getBottom().stopTimer();
		Top.smile.setIcon(Sprite.deadFace.getSprite());
		this.storage.setTileImage(Sprite.mineRed.getSprite());
		crossMinesAround(Launcher.launcher.getBottom().getFieldStorage());
		revealTiles(Launcher.launcher.getBottom().getFieldStorage(),true);
		removeButton(this.storage);
	}
	
	private void moveMine () {
		if(!Launcher.launcher.getClickTick()) {
			boolean ifMine = false;
			if(this.storage.getTile().getIcon().hashCode() == Sprite.mine.getSprite().hashCode()) {
				this.storage.setTileImage(Sprite.clicked.getSprite());
				ifMine = true;
			}
			Launcher.launcher.getBottom().setTileCategories(ifMine);
			Launcher.launcher.setClickTick(true);
		}
	}
	
	private FieldStorage fieldLoc;
	private FieldStorage next;
	private List <FieldStorage> revCont = new ArrayList <FieldStorage> ();
	
	private void revealNumberlessTiles (Map <Integer,FieldStorage> fieldSt, FieldStorage current) {
		int coor [];
		revCont.add(current);
		for(int j = 0;j<revCont.size();j++) {
			
			fieldLoc = revCont.get(j);
			coor = fieldLoc.determineAreaAround();
			
			for(int i = 0;i<coor.length;i++) {
				next = fieldSt.get(fieldLoc.getLoc()+coor[i]);
				if(next.getID() == '?') {
					if(next.getTile().getIcon().hashCode() == Sprite.clicked.getSprite().hashCode()) {
						next.setIDOutside('#');
						revCont.add(next);
					} else {
						next.setIDOutside('!');
					}
					removeButton(next);
				}
			}
		}
	}
	///AFTER CLICKING A MINE REVEALS ALL MINES AND DISABLES CLICKING IN FIELD
	private void revealTiles (Map <Integer,FieldStorage> fieldSt, boolean condition) {
		Launcher.launcher.setGameOver(true);
		for(int i = 0;i<fieldSt.size();i++) {
			fieldLoc = fieldSt.get(i);
			if(condition) {
				if(fieldLoc.getTile().getIcon().hashCode() == Sprite.mine.getSprite().hashCode()) {
					removeButton(fieldLoc);
				}
			} else {
				if(fieldLoc.getTile().getIcon().hashCode() != Sprite.mine.getSprite().hashCode()) {
					removeButton(fieldLoc);
				}
			}
		}
	}
	private void crossMinesAround (Map <Integer,FieldStorage> fieldSt) {
		for(int j = 0;j<fieldSt.size();j++) {
			next = fieldSt.get(j);
			if(next.getTile().getIcon().hashCode() != Sprite.mine.getSprite().hashCode() &&
					next.getButton().getIcon().hashCode() == Sprite.flag.getSprite().hashCode()) {
				next.setTileImage(Sprite.mineCrossed.getSprite());
				removeButton(next);
			}
		}
	}
	private void removeButton (FieldStorage loc) {
		loc.getButton().setVisible(false);
		Launcher.launcher.getBottom().remove(loc.getButton());
	}
	
	private boolean flag,qstion,def;
	
	private void markFunctions () {
		this.flag = true;
		this.qstion = false;
		this.def = false;
	}
	private void markingFunctions () {
		if(this.flag) {
			if(this.storage.getTile().getIcon().hashCode() == Sprite.mine.getSprite().hashCode()) {
				corMineC++;
			}
			Bottom.flagCount--;
			Launcher.launcher.getBottom().setTopNumbers(Bottom.flagCount,Top.flags1,Top.flags2,Top.flags3);
			
			this.button.setIcon(Sprite.flag.getSprite());
			
			this.flag = false;
			this.qstion = true;
		} else if (this.qstion) {
			if(Bottom.flagCount != 0 || store.hashCode() == Sprite.flag.getSprite().hashCode()) {
				Bottom.flagCount++;
				Launcher.launcher.getBottom().setTopNumbers(Bottom.flagCount,Top.flags1,Top.flags2,Top.flags3);
				if(this.storage.getTile().getIcon().hashCode() == Sprite.mine.getSprite().hashCode()) {
					corMineC--;
				}
			}
			
			this.button.setIcon(Sprite.question.getSprite());
			
			this.qstion = false;
			this.def = true;
		} else if(this.def) {
			this.button.setIcon(Sprite.unClicked.getSprite());
			
			this.def = false;
			this.flag = true;
		}
	}
}