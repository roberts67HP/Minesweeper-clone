package com.roberts67HP.minesweeper;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class Top extends JPanel{

	private static final long serialVersionUID = 1L;
	
	static JLabel flags1,flags2,flags3, 
				  time1,time2,time3;
	static JLabel smile;
	
	public static boolean done;
	
	Top(int fieldWidth){
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), 
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
		setBackground(Color.LIGHT_GRAY);
		
		//FLAG COUNTER
		
			gbc.fill = GridBagConstraints.WEST;
			gbc.ipady = 20;
			flags1 = setTopClassNumbers(gbc,flags1,5,0);
			flags2 = setTopClassNumbers(gbc,flags2,0,0);
			flags3 = setTopClassNumbers(gbc,flags3,0,fieldWidth/2-109);
				
		//SMILE IN CENTER
			smile = new JLabel();
			smile.setIcon(Sprite.defaultFace.getSprite());
			smile.setBorder(null);
			smile.addMouseListener(new Reset (smile));//resets game
			gbc.fill = GridBagConstraints.CENTER;
			gbc.ipady = 0;
			gbc.insets = new Insets (0,0,0,0);
			add(smile,gbc);
		
		//TIME LIMIT
			
			gbc.fill = GridBagConstraints.EAST;
			gbc.ipady = 20;

			time1 = setTopClassNumbers(gbc,time1,fieldWidth/2-109,0);
			time2 = setTopClassNumbers(gbc,time2,0,0);
			time3 = setTopClassNumbers(gbc,time3,0,5);
	}
	private JLabel setTopClassNumbers (GridBagConstraints gbc,JLabel number, int gap1, int gap2) {
		gbc.insets = new Insets (0,gap1,0,gap2);
		number = new JLabel ();
		number.setIcon(Sprite.topNums.get(0).getSprite());
		add(number,gbc);
		return number;
	}
}
