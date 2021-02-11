package com.roberts67HP.minesweeper;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class Menu extends JMenuBar{

	private static final long serialVersionUID = 1L;
	
	private JMenu menu;
	private JMenuItem item;
	
	public static boolean done;
	
	Menu () {
		menu = new JMenu("Game");
		menu.setFont(new Font("Ariel", Font.PLAIN, 16));
		
		addDifficulty("Beginner",Sizes.BEGINNER,new Font("Ariel", Font.PLAIN, 16));
		addDifficulty("Intermediate",Sizes.INTERMEDIATE,new Font("Ariel", Font.PLAIN, 16));
		addDifficulty("Expert",Sizes.EXPERT,new Font("Ariel", Font.PLAIN, 16));
		
		addAction("Custom",new Font("Ariel", Font.PLAIN, 16),(evt) -> {new PostCustomButton ();});
		addAction("Exit",new Font("Ariel", Font.PLAIN, 16),(evt) -> {System.exit(0);});
		add(menu);
	}
	private void addAction (String name, Font font, ActionListener act) {
		item = new JMenuItem(name);
		item.addActionListener(act);
		item.setFont(font);
		menu.add(item);
	}
	private void addDifficulty (String name, Sizes diff, Font font) {
		item = new JMenuItem(name);
		item.addActionListener(new StartNewGame(diff));
		item.setFont(font);
		menu.add(item);
	}
	class StartNewGame implements ActionListener{
		
		private Sizes difficulty;
		
		StartNewGame (Sizes diff) {
			this.difficulty = diff;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			startNewGame(difficulty);
		}
		private void startNewGame (Sizes difficulty) {
			Reset.resetAll(true);
			new Launcher(difficulty);
		}
	}
	
	class PostCustomButton extends JFrame{
		
		private static final long serialVersionUID = 1L;
		
		private JPanel panel;
		
		PostCustomButton () {
			super("Custom settings");
			try {
				String iconLoc = System.getProperty("user.dir") + "\\gfx\\Icon.png";
				super.setIconImage(ImageIO.read(new File (iconLoc)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints ();
			
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.anchor = GridBagConstraints.LINE_START;
			gbc.gridy = 0;
			createNewPanel(panel,BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
					"Difficulty"),gbc,320,130,1);
			
			gbc.anchor = GridBagConstraints.LAST_LINE_START;
			gbc.gridy = 2;
			createNewPanel(panel,BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
					"Custom"),gbc,320,60,2);
			
			gbc.gridy = 3;
			createNewPanel(panel,BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED))
					,gbc,320,40,3);
			
			setResizable(false);
			pack();
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			setVisible(true);
		}
		private void createNewPanel (JPanel panel, Border b, GridBagConstraints gbc, int w, int h, int method) {
			panel = new JPanel ();
			panel.setLayout(new GridBagLayout());
			panel.setBorder(b);
			panel.setPreferredSize(new Dimension(w,h));
			if(method == 1) addToPanel1(panel);
			else if	(method == 2) addToPanel2(panel);
			else if (method == 3) addToPanel3(panel);
			add(panel,gbc);
		}
		
		private JRadioButton begin,inter,eks,cust;
		private JLabel mine, gX, gY;
		private JTextField mines, gridX, gridY;
		private JButton cancel,start;
		private GridBagConstraints gbc = new GridBagConstraints ();
		
		private void addToPanel1 (JPanel p) {
			
			ButtonGroup bg = new ButtonGroup ();
			bg.add(begin = addRadioButtons(begin,"Beginner (10 mines and 9x9 grid)", 0,0,GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.VERTICAL));
			p.add(begin,gbc);
			bg.add(inter = addRadioButtons(inter,"Intermediate (40 mines and 16x16 grid)", 0,1,GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.VERTICAL));
			p.add(inter,gbc);
			bg.add(eks = addRadioButtons(eks,"Expert (99 mines and 31x16 grid)", 0,2,GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.VERTICAL));
			p.add(eks,gbc);
			bg.add(cust = addRadioButtons(cust,"Custom", 0,3,GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.VERTICAL));
			p.add(cust,gbc);
		}
		private void addToPanel2 (JPanel p) {
			p.add(mine = addJLabels(mine,"Mines", 0,0,GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.VERTICAL),gbc);
				p.add(mines = addTextFields(mines,"10", 0,1,GridBagConstraints.FIRST_LINE_START,
						GridBagConstraints.VERTICAL),gbc);
				
			p.add(gX = addJLabels(gX,"Width", 1,0,GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.VERTICAL),gbc);
				p.add(gridX = addTextFields(gridX,"9", 1,1,GridBagConstraints.FIRST_LINE_START,
						GridBagConstraints.VERTICAL),gbc);
				
			p.add(gY = addJLabels(gY,"Height",2,0,GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.VERTICAL),gbc);
				p.add(gridY = addTextFields(gridY,"9", 2,1,GridBagConstraints.FIRST_LINE_START,
						GridBagConstraints.VERTICAL),gbc);
			
			begin.addActionListener(new RadioButtonActions(begin.getText(),begin));	
			inter.addActionListener(new RadioButtonActions(inter.getText(),inter));
			eks.addActionListener(new RadioButtonActions(eks.getText(),eks));
			cust.addActionListener(new RadioButtonActions(cust.getText(),cust));
		}
		private void addToPanel3 (JPanel p) {
			p.add(cancel = addJButtons(cancel,"Cancel", 0,0,GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.VERTICAL,20,25, (action) -> {closeCustom();}),gbc);
			p.add(start = addJButtons(start,"Start", 1,0,GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.VERTICAL,20,20, (action) -> {
						if(begin.isSelected()) {
							new StartNewGame(Sizes.BEGINNER).startNewGame(Sizes.BEGINNER);
						} else if(inter.isSelected()) {
							new StartNewGame(Sizes.INTERMEDIATE).startNewGame(Sizes.INTERMEDIATE);
						} else if(eks.isSelected()) {
							new StartNewGame(Sizes.EXPERT).startNewGame(Sizes.EXPERT);
						} else if(cust.isSelected()) {
							customOptions();
						}
						closeCustom();
					}),gbc);
		}
		private void customOptions () {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int xText = Integer.parseInt(gridX.getText());
			int yText = Integer.parseInt(gridY.getText());
			int mText = Integer.parseInt(mines.getText());
			if(xText*16 > screenSize.getWidth() || (yText*16)-60 > screenSize.getHeight()) 
				JOptionPane.showMessageDialog(null, "Field sizes reach out of screen bounds.", 
						"Error", JOptionPane.INFORMATION_MESSAGE);
			else if (xText < 9 || yText < 2) 
				JOptionPane.showMessageDialog(null, "Field is too small.", 
					"Error", JOptionPane.INFORMATION_MESSAGE);
			else if (mText < 1 || mText > yText*xText) 
				JOptionPane.showMessageDialog(null, "Too little or too many bombs.", 
					"Error", JOptionPane.INFORMATION_MESSAGE);
			else {
				System.out.println("closed");
				Reset.resetAll(true);
				new Launcher(xText,yText,mText);	
			}
		}
		private void closeCustom () {
			setVisible(false);
			dispose();
		}
		private JRadioButton addRadioButtons (JRadioButton but, String text, int x, int y ,int anchor, int fill) {
			but = new JRadioButton(text);
			if(text.contains("Beginner")) {
				but.setSelected(true);
			}
			gbc = setAllConstraints(gbc,x, y ,anchor,fill);
			return but;
		}
		private JTextField addTextFields (JTextField field, String text, int x, int y ,int anchor, int fill) {
			field = new JTextField(text);
			field.setPreferredSize(new Dimension(50,20));
			field.setEditable(false);
			gbc = setAllConstraints(gbc,x, y ,anchor,fill);
			gbc.insets = new Insets (0,16,0,16);
			return field;
		}
		private JLabel addJLabels (JLabel label, String text, int x, int y ,int anchor, int fill) {
			label = new JLabel(text);
			gbc = setAllConstraints(gbc,x, y ,anchor,fill);
			gbc.insets = new Insets (0,17,0,17);
			return label;
		}
		private JButton addJButtons (JButton button, String text, int x, int y ,int anchor, int fill,int left,int right, ActionListener al) {
			button = new JButton(text);
			gbc = setAllConstraints(gbc,x, y ,anchor,fill);
			gbc.insets = new Insets (5,left,5,right);
			button.addActionListener(al);
			return button;
		}
		private GridBagConstraints setAllConstraints (GridBagConstraints gbc, int x, int y ,int anchor, 
				int fill) {
			gbc.anchor = anchor;
			gbc.fill = fill;
			gbc.gridx = x;
			gbc.gridy = y;
			return gbc;
		}
		class RadioButtonActions implements ActionListener{
			
			private String text;
			private JRadioButton button;
			
			RadioButtonActions(String t, JRadioButton but){
				this.text = t;
				this.button = but;
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(this.text.contains("Beginner")) {
					this.button.setSelected(true);
				}
				if(cust.isSelected()) {
					mines.setEditable(true);
					gridX.setEditable(true);
					gridY.setEditable(true);
				} else {
					mines.setEditable(false);
					gridX.setEditable(false);
					gridY.setEditable(false);
				}
			}
		}
	}
}
