package com.roberts67HP.minesweeper;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Reset implements MouseListener{
	
	private JLabel smile;
	
	Reset(JLabel l){
		this.smile = l;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent pr) {
		if(SwingUtilities.isLeftMouseButton(pr)) {
			if(smile.getIcon().hashCode() == Sprite.defaultFace.getSprite().hashCode()) {
				smile.setIcon(Sprite.defaultClicked.getSprite());
			} else if (smile.getIcon().hashCode() == Sprite.winFace.getSprite().hashCode()) {
				smile.setIcon(Sprite.winClicked.getSprite());
			} else if (smile.getIcon().hashCode() == Sprite.deadFace.getSprite().hashCode()) {
				smile.setIcon(Sprite.deadClicked.getSprite());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent re) {
		if(SwingUtilities.isLeftMouseButton(re)) {
			if(smile.getIcon().hashCode() == Sprite.defaultClicked.getSprite().hashCode()) {
				smile.setIcon(Sprite.defaultFace.getSprite());
			} else if (smile.getIcon().hashCode() == Sprite.winClicked.getSprite().hashCode()) {
				smile.setIcon(Sprite.winFace.getSprite());
			} else if (smile.getIcon().hashCode() == Sprite.deadClicked.getSprite().hashCode()) {
				smile.setIcon(Sprite.deadFace.getSprite());
			}
			resetAll(false);
		}
	}
	public static void resetAll (boolean menu) {
		Launcher.launcher.getBottom().stopTimer();
		FieldStorage.idCounter = 1;
		Bottom.flagCount = 0;
		Mouse.corMineC = 0;
		Launcher.launcher.setClickTick(false);
		Launcher.launcher.setGameOver(false);
		Launcher.launcher.getBottom().getFieldStorage().clear();
		Launcher.frame.remove(Launcher.launcher.getMenu());
		Launcher.frame.remove(Launcher.launcher.getTop());
		Launcher.frame.remove(Launcher.launcher.getBottom());
		if(!menu) {
			if(!(Launcher.launcher.getDif() == null)) {
				Launcher.launcher.setOrResetFrame(Launcher.launcher.getDif().getX(),Launcher.launcher.getDif().getY(),
						  Launcher.launcher.getDif().getMines(),Launcher.launcher.getDif().getFieldWidth());
			} else {
				Launcher.launcher.setOrResetFrame(Launcher.launcher.getXCustom(),Launcher.launcher.getYCustom(),
						Launcher.launcher.getMinesCustom(),Launcher.launcher.getWidthCustom());
			}
		}
	}
}
