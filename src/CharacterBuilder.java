/*
 * 	@author Nick Charles Martin
 * 			CIS 2151 Summer 2016
 * 			Prof. John Baugh
 * 			Capstone Project
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class CharacterBuilder extends JFrame
{

	private final int WINDOW_WIDTH = 740;
	private final int WINDOW_HEIGHT = 680;
	private ButtonGroup btngender;
	private String[] raceList = {"Warrior", "Barbarian", "Mage", "Thief", "Priest"};
	private JComboBox<?> raceBox;
	private JSlider intel;
	private JSlider dex;
	private JSlider wisdom;
	private JSlider str;
	private JTextField name;
	private JLabel nameLabel;
	private JFileChooser saveSelect;
	private File selectedFile;
	private PrintWriter outputFile;
	private int status;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu optionsMenu;
	private JMenuItem open; 
	private JMenuItem save;
	private JMenuItem reset;
	private JMenuItem exit;
	private JLabel lblPointsLeft;
	private String selectedRace; //Didn't use.
	private Scanner inputFile;
	//private File del;
		
	JRadioButton male = new JRadioButton("Male", true);
	JRadioButton female = new JRadioButton("Female");
	JRadioButton herma = new JRadioButton("Hermaphrodite");
	
	public CharacterBuilder()
	{
	
	setTitle("Character Creation");
	setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLayout(new GridLayout(2, 1));
	
	menuBar = new JMenuBar();
	fileMenu = new JMenu("File");
	optionsMenu = new JMenu("Option");
	
	open = new JMenuItem("Open a saved character.");
	save = new JMenuItem("Save a character.");
	reset = new JMenuItem("Reset All");
	exit = new JMenuItem("Exit");
	
	fileMenu.add(open);
	fileMenu.add(save);
	
	optionsMenu.add(reset);
	optionsMenu.add(exit);
	
	menuBar.add(fileMenu);
	menuBar.add(optionsMenu);
	
	setJMenuBar(menuBar);
	
	JLabel gender = new JLabel("Gender");
	JLabel races = new JLabel("Races");
	JLabel intelLabel = new JLabel("Intelligence");
	JLabel dexLabel = new JLabel("Dexterity");
	JLabel strLabel = new JLabel("Strength");
	JLabel wisLabel = new JLabel("Wisdom");
	
	
	btngender = new ButtonGroup();	
	btngender.add(male);
	btngender.add(female);
	btngender.add(herma);
	
	JPanel panel1 = new JPanel(new FlowLayout());
	JPanel panel2 = new JPanel(new GridLayout(2, 1));
		
	JPanel panelGen = new JPanel(new GridLayout(4, 1));
	JPanel panelRaceBox = new JPanel();
	JPanel panelSlider = new JPanel();
	
	panelGen.add(gender);
	panelGen.add(male);
	panelGen.add(female);
	panelGen.add(herma);
		
	raceBox = new JComboBox<Object>(raceList);
		
	panelRaceBox.add(races);
	panelRaceBox.add(raceBox);
			
	intel = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
	dex = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
	str = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
	wisdom = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
	
	intel.setMajorTickSpacing(20);
	dex.setMajorTickSpacing(20);
	str.setMajorTickSpacing(20);
	wisdom.setMajorTickSpacing(20);
	
	intel.setMinorTickSpacing(5);
	dex.setMinorTickSpacing(5);
	str.setMinorTickSpacing(5);
	wisdom.setMinorTickSpacing(5);
	
	intel.setPaintTicks(true);
	dex.setPaintTicks(true);
	str.setPaintTicks(true);
	wisdom.setPaintTicks(true);
	
	intel.setPaintLabels(true);
	dex.setPaintLabels(true);
	str.setPaintLabels(true);
	wisdom.setPaintLabels(true);
	
	lblPointsLeft = new JLabel("Remaining: 100");
	panelSlider.setLayout(new GridLayout(9, 1));
	
	panelSlider.add(lblPointsLeft);
	panelSlider.add(intelLabel);
	panelSlider.add(intel);
	panelSlider.add(dexLabel);
	panelSlider.add(dex);
	panelSlider.add(strLabel);
	panelSlider.add(str);
	panelSlider.add(wisLabel);
	panelSlider.add(wisdom);
	
	panel1.add(panelGen);
	panel1.add(panelRaceBox);
	panel1.add(panelSlider);
	
	nameLabel = new JLabel("Please enter Characters name: "); 
	name = new JTextField(10);
		
	panel2.add(nameLabel);
	panel2.add(name);
		
	add(panel1);
	add(panel2);
	
	requestFocus();
	pack();
	setVisible(true);
	setResizable(false);
		
	//male.addActionListener(new RadioButtonListener());
	//female.addActionListener(new RadioButtonListener());
	//herma.addActionListener(new RadioButtonListener());
	//raceBox.addActionListener(new ComboBoxListener());
	open.addActionListener(new MenuListener());
	save.addActionListener(new MenuListener());
	exit.addActionListener(new ExitListener());
	intel.addChangeListener(new SkillListener());
	str.addChangeListener(new SkillListener());
	wisdom.addChangeListener(new SkillListener());
	dex.addChangeListener(new SkillListener());
	reset.addActionListener(new ResetListener());
	raceBox.addActionListener(new RaceListener());
	
	
	
	}
	
	public static void main(String[] args) throws IOException
	{
		new CharacterBuilder();
	}
	
	public class MenuListener implements ActionListener 
	{
				
		@Override
		public void actionPerformed(ActionEvent e) 
		{
						
			if (e.getSource() == open)
			{
				saveSelect = new JFileChooser();
				status = saveSelect.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION)
				{
					selectedFile = saveSelect.getSelectedFile();
					int result;
					try 
					{
						inputFile = new Scanner(selectedFile);
						
					} 
					catch (FileNotFoundException e1) 
					{
						
						e1.printStackTrace();
					};
					
					while (inputFile.hasNext())
					{
						String line = inputFile.nextLine();
						name.setText(line);
						line = inputFile.nextLine();
						
						if (line.equals("Male"))
						{
							male.setSelected(true);
						}
					
						else if (line.equals("Female"))
						{
							female.setSelected(true);
						}
					
						else 
						{
							herma.setSelected(true);								
						}						
						
						line = inputFile.nextLine();
						raceBox.setSelectedItem(line);
						line = inputFile.nextLine();
						result = Integer.parseInt(line);
						intel.setValue(result);
						line = inputFile.nextLine();
						result = Integer.parseInt(line);
						dex.setValue(result);
						line = inputFile.nextLine();
						result = Integer.parseInt(line);
						str.setValue(result);
						line = inputFile.nextLine();
						result = Integer.parseInt(line);
						wisdom.setValue(result);
						
					}
					
					inputFile.close();
					
				}
				
					
			}
			
			else if (e.getSource() == save)
			{
				
				
				String charName = name.getText();
				
				try 
				{
					//del = new File(System.getProperty("user.dir/*.player"));
					//System.out.println(del);
					//del.delete();
					outputFile = new PrintWriter(charName +".player");
					
				} 
				catch (FileNotFoundException e1) 
				
				{
					
					e1.printStackTrace();
					
				}
				
				if (status == JFileChooser.APPROVE_OPTION)
				{
					
												
					outputFile.println(charName);
					
					if(male.isSelected())
					{
						outputFile.println("Male");
					}
					else if(female.isSelected())
					{
						outputFile.println("Female");
					}
					else
					{
						outputFile.println("Hermaphrodite");
					}
										
					outputFile.println(raceBox.getSelectedItem());
					outputFile.println(intel.getValue());
					outputFile.println(dex.getValue());
					outputFile.println(str.getValue());
					outputFile.println(wisdom.getValue());
					outputFile.close();
					
				}
				
			}

		}

	}
	
	public class ExitListener implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			System.exit(0);

		}

	}
	
	public class SkillListener implements javax.swing.event.ChangeListener 
	{

		@Override
		public void stateChanged(ChangeEvent ev) 
		{
			JSlider currentSlider = (JSlider)ev.getSource();
			
			int intelUpdate, dexUpdate, wisUpdate, strUpdate;
			
			intelUpdate = intel.getValue();
			dexUpdate = dex.getValue();
			wisUpdate = wisdom.getValue();
			strUpdate = str.getValue();
			
			int pointsLeft = 100 - intelUpdate - dexUpdate - wisUpdate - strUpdate;
			int pointsToSpend = 0;
			
			if (pointsLeft < 0)
			{
				if(currentSlider == intel)
				{
					pointsToSpend = 100 - dexUpdate - wisUpdate - strUpdate;	
				}
				else if(currentSlider == dex)
				{
					pointsToSpend = 100 - intelUpdate - wisUpdate - strUpdate;	
				}
				else if(currentSlider == wisdom)
				{
					pointsToSpend = 100 - dexUpdate - intelUpdate - strUpdate;		
				}
				else //slider is str
				{
					pointsToSpend = 100 - dexUpdate - wisUpdate - intelUpdate;		
				}//end if-else-if
			
				currentSlider.setValue(pointsToSpend);
			}//end pointsLeft < 0
			
			intelUpdate = intel.getValue();
			dexUpdate = dex.getValue();
			wisUpdate = wisdom.getValue();
			strUpdate = str.getValue();
			pointsLeft = 100 - intelUpdate - dexUpdate - wisUpdate - strUpdate;
				
			lblPointsLeft.setText("Remaining: " + pointsLeft);

		}//end stateChanged method

	}
		
	public class ResetListener implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent ev) 
		{
			intel.setValue(0);
			dex.setValue(0);
			wisdom.setValue(0);
			str.setValue(0);
			name.setText(null);
			male.setSelected(true);
			raceBox.setSelectedItem(raceList[0]);

		}

	}
	
	public class RaceListener implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			selectedRace = (String) raceBox.getSelectedItem(); //Didn't use

		}

	}
	
}
