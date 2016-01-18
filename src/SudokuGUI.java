import java.awt.*;

import javax.swing.*;
import javax.swing.text.html.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.font.*;
import java.awt.event.*;
import java.awt.*;

import javax.swing.border.*;
import java.beans.*;
import java.io.*;
import java.util.*;

public class SudokuGUI extends JDialog implements MouseListener, ActionListener, ItemListener{
 

	private static final long serialVersionUID = 1L;
	final static String MainMenu = "Main Menu";
    final static String SudokuGame = "Sudoku Game";
    final static String Instructions = "Instructions";
    final static String GameStatistics = "Game Statistics";
    final static String Options = "Options";
    //Layout
    JTabbedPane tab = new JTabbedPane();
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JPanel panel5;
    JPanel sudoku_panel;
    JPanel side_panel;
    JPanel statistics_panel;
    //Main Menu Buttons
    JButton start_button;
    JButton easy_button;
    JButton medium_button;
    JButton difficult_button;
    JButton impossible_button;
    JButton statistics_button;
    JButton how_button;
    JButton options_button;
    JButton exit_button;
    private static SudokuGUI sample;
    public static JFrame frame;
    private int x_value, y_value;
    private Color medium = Color.decode("#33CCFF");
    private GridBagConstraints constraint = new GridBagConstraints();
    private int start_pressed = 0;
    private int option_pressed = 0;
    private int instruction_pressed = 0;
    public int[][] master_grid;
    //Sudoku Buttons
    JButton change_level;
    JButton undo_button;
    JButton reset_button;
    JButton validate_answer;
    JButton give_up;
    JDialog dialog;
    //variables for Sudoku page
    int i=0;
    int add_index = 0;
    JButton game_piece;
    //gets the array of the given numbers read in for the current Sudoku game from the SudokuRun class
    int[] placement_matrix = SudokuRun.init(); 
    //Dialog Boxes
    JOptionPane tryAgain;
    int X = -1;
    int Y = -1;
    //set options for textFields
    JTextField textField1;
    JTextField textField2;
    JTextField textField3;
    JTextField textField4;
    
    //Color
    public Color given = Color.black;
    public Color input = Color.black;
     
    String file;
	String toFile="";
	String player; /////player name from import file
	String playerName = "";
    Integer wins =0;
    Integer losses =0;
    ArrayList<String> play = new ArrayList<String>();
    ArrayList<Integer> wi = new ArrayList<Integer>();
    ArrayList<Integer> los = new ArrayList<Integer>();
    
    JMenuItem[] items; 
    JPopupMenu popup; 
    Point mouse = new Point(0,0);
	private int column;
	private int row;
	ArrayList<JButton> sources = new ArrayList<JButton>(81);
	Object current_source;
	private JButton new_game;
	private int times;
	private int statistics_pressed;
 
    public static void main(String[] args){
 
        createAndShowGUI();
 
    }
 
    private static void createAndShowGUI(){
 
        frame = new JFrame("Sudoku");
        sample = new SudokuGUI();
 
        sample.addComponentToMainMenu(frame.getContentPane());
 
        frame.setSize(900,600);
        //blocks maximizing 
        frame.setResizable(false);
        frame.setVisible(true);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
    }
    
    public ImageIcon resizeImage(ImageIcon img, int length, int width){
    	  Image get_img = img.getImage();
        Image new_img = get_img.getScaledInstance(length, width, Image.SCALE_SMOOTH);
        ImageIcon new_icon = new ImageIcon(new_img);
        return new_icon;
    }   
    
    public void addComponentToMainMenu(Container pane){
   
        panel1 = new JPanel();
        panel1.setBackground(medium);
        
        
    	  panel1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //set up style...gridBagLayout and create a way to make constraint to the buttons
        panel1.setLayout(new GridBagLayout());
       
        //natural height, maximum width
        constraint.fill = GridBagConstraints.BOTH;
        
        JPanel start_panel = new JPanel();
        start_panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //set up style...gridBagLayout and create a way to make constraint to the buttons
        start_panel.setLayout(new GridBagLayout());
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 0.5;
        constraint.weighty = 0.5;
        start_panel.setBackground(Color.black);
        start_panel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        start_panel.setPreferredSize(new Dimension(260, 260));
        constraint.gridx = 0;//(in section 0 of the grid)column 1
        constraint.gridy = 0;//top row
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.insets = new Insets(4,2,4,2);//padding-spacing between buttons and edges
        panel1.add(start_panel, constraint);
        ImageIcon start_jpg = new ImageIcon("start.jpg");
        ImageIcon start_icon = resizeImage(start_jpg, 160, 160);
        start_button = new JButton(start_icon);
        start_button.setPreferredSize(new Dimension(160, 160));
        constraint.anchor = GridBagConstraints.CENTER;
        constraint.fill = GridBagConstraints.NONE;
        start_panel.add(start_button, constraint);
        JLabel start_label = new JLabel("START");
        Font curFont = start_label.getFont();
        start_label.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 20));
        start_label.setForeground(Color.white);
        constraint.anchor = GridBagConstraints.PAGE_START;
        start_panel.add(start_label, constraint);
        start_button.addActionListener(this);
        
        JPanel level_panel = new JPanel();
        level_panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //set up style...gridBagLayout and create a way to make constraint to the buttons
        level_panel.setLayout(new GridBagLayout());
        constraint.fill = GridBagConstraints.BOTH;
        constraint.anchor = GridBagConstraints.CENTER;
        constraint.weightx = 0.5;
        constraint.weighty = 0.5;
        level_panel.setSize(new Dimension(260, 260));
        level_panel.setBackground(Color.black);
        level_panel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        constraint.gridx = 1;//column 2
        constraint.gridy = 0;//top row
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        panel1.add(level_panel, constraint);
        ImageIcon easy_jpg = new ImageIcon("easy.jpg");
        ImageIcon easy_icon = resizeImage(easy_jpg, 100, 100);
        easy_button = new JButton(easy_icon);
        easy_button.setPreferredSize(new Dimension(100, 100));
        ImageIcon medium_jpg = new ImageIcon("medium.jpg");
        ImageIcon medium_icon = resizeImage(medium_jpg, 100, 100);
        medium_button = new JButton(medium_icon);
        medium_button.setPreferredSize(new Dimension(100, 100));
        ImageIcon difficult_jpg = new ImageIcon("more_difficult.jpg");
        ImageIcon difficult_icon = resizeImage(difficult_jpg, 100, 100);
        difficult_button = new JButton(difficult_icon);
        difficult_button.setPreferredSize(new Dimension(100, 100));
        ImageIcon impossible_jpg = new ImageIcon("impossible.jpg");
        ImageIcon impossible_icon = resizeImage(impossible_jpg, 100, 100);
        impossible_button = new JButton(impossible_icon);
        impossible_button.setPreferredSize(new Dimension(100, 100));
        constraint.gridx = 0;//column 2
        constraint.gridy = 1;
        constraint.fill = GridBagConstraints.NONE;
        level_panel.add(easy_button, constraint);
        constraint.gridx = 1;//column 2
        constraint.gridy = 1;
        level_panel.add(medium_button, constraint);
        constraint.gridx = 0;//column 2
        constraint.gridy = 2; 
        level_panel.add(difficult_button, constraint);
        constraint.gridx = 1;//column 2
        constraint.gridy = 2;
        level_panel.add(impossible_button, constraint);
        JLabel level_label = new JLabel("CHOOSE LEVEL");
        curFont = level_label.getFont();
        level_label.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 20));
        level_label.setForeground(Color.white);
        constraint.gridx = 0;//column 2
        constraint.gridy = 0;
        constraint.gridwidth = 2; 
        level_panel.add(level_label, constraint);
        easy_button.addActionListener(this);
        medium_button.addActionListener(this);
        difficult_button.addActionListener(this);
        impossible_button.addActionListener(this);
        
        statistics_panel = new JPanel();
        statistics_panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //set up style...gridBagLayout and create a way to make constraint to the buttons
        statistics_panel.setLayout(new GridBagLayout());
        constraint.fill = GridBagConstraints.BOTH;
        statistics_panel.setBackground(Color.black);
        statistics_panel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        constraint.weightx = 0.5;
        constraint.weighty = 0.5;
        statistics_panel.setPreferredSize(new Dimension(260, 260));
        constraint.gridx = 2;//column 3
        constraint.gridy = 0;//the top row
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        panel1.add(statistics_panel, constraint);
        ImageIcon statistics_jpg = new ImageIcon("statistics.jpg");
        ImageIcon statistics_icon = resizeImage(statistics_jpg, 160, 160);
        statistics_button = new JButton(statistics_icon);
        statistics_button.setPreferredSize(new Dimension(160, 160));
        constraint.anchor = GridBagConstraints.CENTER;
        constraint.fill = GridBagConstraints.NONE;
        statistics_panel.add(statistics_button, constraint);
        JLabel statistics_label = new JLabel("GAME STATISTICS");
        curFont = statistics_label.getFont();
        statistics_label.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 20));
        statistics_label.setForeground(Color.white);
        constraint.anchor = GridBagConstraints.PAGE_START;
        statistics_panel.add(statistics_label, constraint);
        statistics_button.addActionListener(this);
        
        
        JPanel how_panel = new JPanel();
        how_panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //set up style...gridBagLayout and create a way to make constraint to the buttons
        how_panel.setLayout(new GridBagLayout());
        constraint.fill = GridBagConstraints.BOTH;
        how_panel.setBackground(Color.black);
        how_panel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        constraint.weightx = 0.5;
        constraint.weighty = 0.5;
        how_panel.setPreferredSize(new Dimension(260, 260));
        constraint.gridx = 0;//(in section 0 of the grid)column 1
        constraint.gridy = 2;//top row
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.insets = new Insets(4,2,4,2);  //padding-spacing between buttons and edges
        panel1.add(how_panel, constraint);
        ImageIcon how_jpg = new ImageIcon("how_to.jpg");
        ImageIcon how_icon = resizeImage(how_jpg, 160, 160);
        how_button = new JButton(how_icon);
        how_button.setPreferredSize(new Dimension(160, 160));
        constraint.anchor = GridBagConstraints.CENTER;
        constraint.fill = GridBagConstraints.NONE;
        how_panel.add(how_button, constraint);
        JLabel how_label = new JLabel("HOW TO PLAY");
        curFont = how_label.getFont();
        how_label.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 20));
        how_label.setForeground(Color.white);
        constraint.anchor = GridBagConstraints.PAGE_START;
        how_panel.add(how_label, constraint);
        how_button.addActionListener(this);
        
        JPanel options_panel = new JPanel();
        options_panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //set up style...gridBagLayout and create a way to make constraint to the buttons
        options_panel.setLayout(new GridBagLayout());
        options_panel.setPreferredSize(new Dimension(260, 260));
        options_panel.setBackground(Color.black);
        options_panel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 0.5;
        constraint.weighty = 0.5;
       // constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 0.5;
        constraint.gridx = 1;//column 2
        constraint.gridy = 2;//top row
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        panel1.add(options_panel, constraint);
        ImageIcon options_jpg = new ImageIcon("options.jpg");
        ImageIcon options_icon = resizeImage(options_jpg, 160, 160);
        options_button = new JButton(options_icon);
        options_button.setPreferredSize(new Dimension(160, 160));
        constraint.anchor = GridBagConstraints.CENTER;
        constraint.fill = GridBagConstraints.NONE;
        options_panel.add(options_button, constraint);
        JLabel options_label = new JLabel("SET OPTIONS");
        curFont = options_label.getFont();
        options_label.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 20));
        options_label.setForeground(Color.white);
        constraint.anchor = GridBagConstraints.PAGE_START;
        options_panel.add(options_label, constraint);
        options_button.addActionListener(this);
  
        JPanel exit_panel = new JPanel();
        exit_panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //set up style...gridBagLayout and create a way to make constraint to the buttons
        exit_panel.setLayout(new GridBagLayout());
        exit_panel.setPreferredSize(new Dimension(260, 260));
        exit_panel.setBackground(Color.black);
        exit_panel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 0.5;
        constraint.weighty = 0.5;
        constraint.gridx = 2;//column 3
        constraint.gridy = 2;//the top row
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        panel1.add(exit_panel, constraint);
        ImageIcon exit_jpg = new ImageIcon("exit.jpg");
        ImageIcon exit_icon = resizeImage(exit_jpg, 160, 160);
        exit_button = new JButton(exit_icon);
        exit_button.setPreferredSize(new Dimension(160, 160));
        constraint.anchor = GridBagConstraints.CENTER;
        constraint.fill = GridBagConstraints.NONE;
        exit_panel.add(exit_button, constraint);
        JLabel exit_label = new JLabel("EXIT");
        curFont = exit_label.getFont();
        exit_label.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 20));
        exit_label.setForeground(Color.white);
        constraint.anchor = GridBagConstraints.PAGE_START;
        exit_panel.add(exit_label, constraint);
        exit_button.addActionListener(this);
        
        tab.addTab(MainMenu, panel1);
        pane.add(tab, BorderLayout.CENTER);
    }
    
   public void addComponentToSudokuPage(Container pane){
   
	   	panel2 = new JPanel();
        panel2.setBackground(medium);
        panel2.setLayout(new GridBagLayout());
        panel2.setPreferredSize(new Dimension(550, 550));
        
        sudoku_panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        sudoku_panel.setLayout(layout);
        sudoku_panel.setBackground(Color.white);
        
        for (int l=0;l<81;l++){
    		sources.add(l, null);
    	}

        valuesToBoard(placement_matrix,given,input);

        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.anchor = GridBagConstraints.WEST;
        sudoku_panel.setPreferredSize(new Dimension(520, 520));
        panel2.add(sudoku_panel, constraint);
        
        constraint.gridy = 0;
        
        //create the buttons for sudoku game page
        change_level = new JButton("Change Level");
        reset_button = new JButton("Reset Button");
        validate_answer = new JButton("Validate Answer");
        give_up = new JButton("Give Up");
        new_game = new JButton("New Game");
        
        //add action listeners to the buttons in the sudoku game page
        change_level.addActionListener(this);
        reset_button.addActionListener(this);
        validate_answer.addActionListener(this);
        give_up.addActionListener(this);
        new_game.addActionListener(this);
        
        side_panel = new JPanel();
        side_panel.setBackground(Color.black);
        GridBagLayout layout2 = new GridBagLayout();
        side_panel.setLayout(layout2); 

        side_panel.add(change_level,constraint);
        constraint.gridy = 1;
        side_panel.add(new_game, constraint);
        constraint.gridy = 2;
        side_panel.add(reset_button,constraint);
        constraint.gridy = 3;
        side_panel.add(validate_answer,constraint);
         constraint.gridy = 4;
        side_panel.add(give_up,constraint);
        constraint.gridy = 0;
        constraint.gridx = 1; 
        side_panel.setPreferredSize(new Dimension(120, 120));
        constraint.anchor = GridBagConstraints.EAST;
        panel2.add(side_panel, constraint);
       
        tab.addTab(SudokuGame, panel2);
        //tab.addTab(GameStatistics, panel4);
        
 
   }
   
   //Puts the values read in from the file into the GUI board
   public void valuesToBoard(int[] matrix, Color giv, Color inp){

	   SudokuRun.readInGame(SudokuRun.which_line);
	   given = giv;
	   input = inp;
	   add_index = 0;
	   sources.clear();
	   sources = new ArrayList<JButton>(81);

	   
	   //Set up pop down menu do be displayed when user clicks on empty gamepiece
	   	popup = new JPopupMenu();
	  
	     items = new JMenuItem[10];
	     for(int i=0; i<9; i++) {
	         items[i] = new JMenuItem(""+(i+1));
	         items[i].addActionListener(this);
	         popup.add(items[i]);        
	     }
	     popup.addSeparator();
	     items[9] = new JMenuItem("?");
	     items[9].addActionListener(this);
	     popup.add(items[9]);

	  
	   //Sets up the game board and displays the 9x9 grid (currently in format that reads across rows)
	   //creates 3x3 large boxes and 3x3 fields totaling 81 gamepiece. The add_index runs from 0 to 80 and the column is equal to the remainder 
	   //of the index and 9 and the row is index divided by 9

       for(int external_column=0; external_column<3; external_column++){
      	 for(int internal_column=0; internal_column<3; internal_column++){
      	  for(int external_row=0; external_row<3; external_row++){ 
      	  	  	  for(int internal_row=0; internal_row<3; internal_row++){
      	  	  		  column = add_index % 9;
      	  	  		  row = add_index/9;
      	  	  		  //x and y values for each button
      	  	  	  	  y_value = (external_column*3)+internal_column;
      	  	  	  	  x_value = (external_row*3)+internal_row;
      	  	  	  	  constraint.gridx = x_value;
      	  	  	  	  constraint.gridy = y_value;
  
      	  	  	  	  //Displays as empty and a JButton that can be clicked if the number in the matrix = 0
      	  	  	  	  if (SudokuRun.given[add_index]==0 && SudokuRun.input[column][row]==0 && matrix[add_index] == 0){
      	  	  	  	  	  game_piece = new JButton();  	  	  	  
      	  	  	  	  	  game_piece.setBackground(inp);
      	  	  	  	  	  game_piece.setForeground(Color.white);
      	  	  	  	  	  game_piece.setEnabled(true);
      	  	  	  	  	  game_piece.setLayout(new BorderLayout());
      	  	  	  	  	  game_piece.addMouseListener(this);
      	  	  	  	  	  game_piece.setPreferredSize(new Dimension(57, 57));
      	  	  	  	  	  game_piece.setMinimumSize(new Dimension(57, 57));
      	  	  	  	  	  //Helps distinguish each separate gamepiece for later identification
      	  	  	  	  	        	  	  	  	  	  
      	  	  	  	  	  //sets up given array to track numbers entered in automatically into game
      	  	  	  	  	  SudokuRun.total[column][row] = matrix[add_index];
      	  	  	  	  }
      	  	  	  	  else if (SudokuRun.given[add_index]==0 && SudokuRun.input[column][row]!=0 && matrix[add_index] != 0) {     	  	  	  	  
      	  	  	  		  game_piece = new JButton("" + matrix[add_index]);
      	  	  	  	  	  Font curFont = game_piece.getFont();
  	  	  	  	  	   	  game_piece.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 20));
  	  	  	  	  	      game_piece.setForeground(Color.white);
  	  	  	  	  	      game_piece.setBackground(giv);
      	  	  	  	  	  game_piece.setEnabled(true);
      	  	  	  	  	  game_piece.setLayout(new BorderLayout());
      	  	  	  	  	  game_piece.addMouseListener(this);
      	  	  	  	  	  game_piece.setPreferredSize(new Dimension(57, 57));
      	  	  	  	  	  game_piece.setMinimumSize(new Dimension(57, 57));
      	  	  	  		  //Displays the number if there is a number other than 0 at the current area index and does not have for mouse events
      	  	  	  }
      	  	  	     else{

      	  	  	  	  	  game_piece = new JButton("" + matrix[add_index]);
      	  	  	  	  	  Font curFont1 = game_piece.getFont();
      	  	  	  	  	  game_piece.setFont(new Font(curFont1.getFontName(), curFont1.getStyle(), 20));
      	  	  	  	  	  game_piece.setForeground(Color.white);
      	  	  	  	  	  game_piece.setBackground(giv);
      	  	  	  	  	  game_piece.setEnabled(false);
      	  	  	  	  	  SudokuRun.total[column][row] = matrix[add_index];
      	  	  	  	  	  //Helps distinguish each separate gamepiece for later identification
      	  	  	  	  	  game_piece.setPreferredSize(new Dimension(57, 57));
      	  	  	  	  	  game_piece.setMinimumSize(new Dimension(57, 57));
      	  	  	  	  }
      	  	  	  	 
      	  	  	  	 
      	  	  	  	  //Sets up each gamepiece and then draws a border around each 3x3 box for clarity
      	  	  	  	  constraint.insets = new Insets(2,2,2,2);
      	  	  	  	  
      	  	  	  	  constraint.fill = GridBagConstraints.BOTH;
      	  	  	  	  constraint.gridheight = 1;
      	  	  	  	  constraint.gridwidth = 1;
      	  	  	  	  constraint.anchor = GridBagConstraints.EAST;
      	  	  	  	  if (internal_column == 2 && internal_row == 2){
      	  	  	  	  	  game_piece.setBorder(BorderFactory.createMatteBorder(0,0,3,3, medium));
      	  	  	  	  }
      	  	  	  	  else if (internal_column == 0 && internal_row == 0){
      	  	  	  	  	  game_piece.setBorder(BorderFactory.createMatteBorder(3,3,0,0, medium));
      	  	  	  	  }
      	  	  	  	  else if (internal_column == 2 && internal_row == 0){
      	  	  	  	  	  game_piece.setBorder(BorderFactory.createMatteBorder(0,3,3,0, medium));
      	  	  	  	  }
      	  	  	  	  else if (internal_column == 0 && internal_row == 2){
      	  	  	  	  	  game_piece.setBorder(BorderFactory.createMatteBorder(3,0,0,3, medium));
      	  	  	  	  }
      	  	  	  	  else if (internal_row == 2){
      	  	  	  	  	  game_piece.setBorder(BorderFactory.createMatteBorder(0,0,0,3, medium));
      	  	  	  	  }
      	  	  	  	  else if (internal_column == 2){
      	  	  	  	  	   game_piece.setBorder(BorderFactory.createMatteBorder(0,0,3,0, medium));
      	  	  	  	  }
      	  	  	  	  else if (internal_row == 0){
      	  	  	  	  	   game_piece.setBorder(BorderFactory.createMatteBorder(0,3,0,0, medium));
      	  	  	  	  }
      	  	  	  	  else if (internal_column == 0){
      	  	  	  	  	    game_piece.setBorder(BorderFactory.createMatteBorder(3,0,0,0, medium));
      	  	  	  	  }
      	  	  	  	  else{
      	  	  	  	  	   game_piece.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.black));
      	  	  	  	  }
      	  	  	  	  //index to check for each element in the given matrix until 80 (81 values...0 to 80)
      	  	  	  	  if (add_index<80){
      	  	  	  		  add_index++;
      	  	  	  	  }
      	   	  	  	  sources.add(game_piece);

      	  	  	  	  sudoku_panel.add(game_piece, constraint);
      	  	  	  	  sudoku_panel.revalidate();
      	  	  	  }
      	  	  }  
      	  }
      }  	     
   }
   public void showCorrectAnswers(int[] matrix){
	   sudoku_panel.removeAll();
	   matrix = SudokuRun.answers();
	   add_index = 0;
	   
	   for(int external_column=0; external_column<3; external_column++){
	    for(int internal_column=0; internal_column<3; internal_column++){
	     for(int external_row=0; external_row<3; external_row++){ 
	   	  for(int internal_row=0; internal_row<3; internal_row++){
	      	  column = add_index % 9;
	      	  row = add_index/9;
	      	  //x and y values for each button
	      	  y_value = (external_column*3)+internal_column;
	    	  x_value = (external_row*3)+internal_row;
	      	  constraint.gridx = x_value;
	     	  constraint.gridy = y_value;
	      	  	  	  	  
	      	  game_piece = new JButton("" + matrix[add_index]);
 	  	  	  Font curFont = game_piece.getFont();
 	  	  	  game_piece.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 20));
 	  	  	  game_piece.setForeground(Color.white);
 	  	   	  game_piece.setBackground(Color.black);
 	  	   	  game_piece.setEnabled(false);
	      	  	  	  	  
	      	  constraint.insets = new Insets(2,2,2,2);
	      	  game_piece.setPreferredSize(new Dimension(57, 57));
	  	  	  game_piece.setMinimumSize(new Dimension(57, 57));
	  	  	  constraint.fill = GridBagConstraints.BOTH;
	  	  	  constraint.gridheight = 1;
	  	  	  constraint.gridwidth = 1;
	  	  	  constraint.anchor = GridBagConstraints.EAST;
	  	  	  if (internal_column == 2 && internal_row == 2){
	  	  	  	  game_piece.setBorder(BorderFactory.createMatteBorder(0,0,3,3, medium));
	  	  	  }
	  	  	  else if (internal_column == 0 && internal_row == 0){
	  	  	  	  game_piece.setBorder(BorderFactory.createMatteBorder(3,3,0,0, medium));
	  	  	  }
	  	  	  else if (internal_column == 2 && internal_row == 0){
	  	  	  	  game_piece.setBorder(BorderFactory.createMatteBorder(0,3,3,0, medium));
	  	  	  }
	  	  	  else if (internal_column == 0 && internal_row == 2){
	  	  	  	  game_piece.setBorder(BorderFactory.createMatteBorder(3,0,0,3, medium));
	  	  	  }
	  	  	  else if (internal_row == 2){
	  	  	  	  game_piece.setBorder(BorderFactory.createMatteBorder(0,0,0,3, medium));
	  	  	  }
	  	  	  else if (internal_column == 2){
	  	  	  	   game_piece.setBorder(BorderFactory.createMatteBorder(0,0,3,0, medium));
	  	  	  }
	  	  	  else if (internal_row == 0){
	  	  	  	   game_piece.setBorder(BorderFactory.createMatteBorder(0,3,0,0, medium));
	  	  	  }
	  	  	  else if (internal_column == 0){
	  	  	  	    game_piece.setBorder(BorderFactory.createMatteBorder(3,0,0,0, medium));
	  	  	  }
	  	  	  else{
	  	  	  	   game_piece.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.black));
	  	  	  }
	  	  	  //index to check for each element in the given matrix until 80 (81 values...0 to 80)
	  	  	  if (add_index<80){
	  	  		  add_index++;
	  	  	  }
	  	  	  
	  	  	  sudoku_panel.add(game_piece, constraint);
	  	  	  sudoku_panel.revalidate();
      	   }  
        }
      }
	   }
   }
   //Adds the Instructions to the How To Panel when clicked in Main Menu
   public void addComponentsToHowPage(Container pane){
   	  panel3 = new JPanel();
   	  JLabel instructions_label = new JLabel("<html>How To Play Sudoku: <br> <br> To Play. <br> " +
        "The objective of the game is to fill all the blank squares in a game with " +
        "the correct numbers.<br>There are three very simple constraint to FOLLOW: " +
        "<br>  <br>   1) Every 3 by 3 subsection of the 9 by 9 square must include " +
        "all digits 1 through 9<br>     2) Every row of 9 numbers must include all " +
        "digits 1 through 9 in any order <br>     3) Every column of 9 numbers must " +
        "include all digits 1 through 9 in any order<br>  <br> Every sudoku game " +
        "begins with a number of squares already filled in,<br> the difficulty of " +
        "the game is largely a function of how many squares are filled in. <br> The " +
        "more squares that are given the easier it is to figure out the missing " +
        "numbers. <br> As the squares are filled in correctly, the options for the " +
        " remaining narrow<br> and it becomes easier to fill them in.<br> <br> " +
        "Solution Techniques:<br> First scan the rows and columns to see where a " +
        " certain number might go, given the three constraint. <br>For example, if "+
        "a 7 or a 5 are left to fit in the remaining boxes of a 9 square sub-region, " +
        "<br> then by looking at the rows and columns and seeing that a 7 might exist " +
        "already in the same row,<br> as one of the boxes, it is then assumed that the" +
        "7 can not go in that box but the 5 will. <br> This leaves the 7 to go in the " +
        "other box found in that sub region. <br> However, if you find that both boxes " +
        "conflict with already having 7's somewhere in the row or column <br> this can " +
        "be a hint that placements of other numbers were wrong.<br> You would want to " +
        "start over. <br> So, the best advice to give you is take your time!!!  <br>Good "+
        "Luck! <br> </html>");
        constraint.anchor = GridBagConstraints.PAGE_START;
        panel3.add(instructions_label, constraint);
        panel3.setBackground(Color.white);
        tab.addTab(Instructions, panel3);
   }
   
   //Adds the Options to the Options Panel when clicked in Main Menu
   public void addComponentsToOptionsPage(Container pane){
	   panel5 = new JPanel();
	   	  panel5.setSize(new Dimension(260, 260));
	 
	        panel5.setLayout(new GridLayout(10,2));
	 
	        //panel5.add(new JButton("Button 5")); /////Change
	 
	        //Creating the first row of elements, which is setting the Player's Name
	 
	        JLabel setPlayerName = new JLabel("Set Player's Name: ");
	        
	 
	 
	        textField1 = new JTextField(20);
	        
	        panel5.add(setPlayerName);
	        panel5.add(textField1, constraint);
	 
	        //Creating the second row of elements, which is setting the background color
	 
	        JLabel info = new JLabel("For the options below, type 0 for Black, 1 for Red, 2 for Blue, 3 for Orange, and 4 for Green");
	 
	        JLabel setBackgroundColor = new JLabel("Set Background Color: ");
	        
	        textField2 = new JTextField(20); 
	        
	        panel5.add(info);
	        panel5.add(setBackgroundColor);
	        panel5.add(textField2, constraint);
	 
	        //Creating the third row of elements, which is setting the color of numbers given
	 
	        JLabel setGivenNumber = new JLabel("Set Box Color of Given Numbers: ");
	 
	        textField3 = new JTextField(20);
	 
	 
	        panel5.add(setGivenNumber);
	        panel5.add(textField3, constraint);
	 
	        //Creating the fourth row of elements, which is setting the color of numbers inputted
	 
	        JLabel setInputNumber = new JLabel("Set Box Color of User Input: ");
	 
	        textField4 = new JTextField(20);;
	        
	        textField1.addActionListener(this);
	        textField2.addActionListener(this);
	        textField3.addActionListener(this);
	        textField4.addActionListener(this);
	 
	        panel5.add(setInputNumber);
	        panel5.add(textField4, constraint);
	        tab.addTab(Options, panel5);
	   }
	   
   //Sets up the Statistics Dialog Box when Statistics is clicked in Main Menu
   public void addComponentsToStatisticsPage(){
	   
	   String current_stat = checkStat();
	   int import_stat = JOptionPane.showConfirmDialog(null,  "Current Player's Name " + playerName + "\nWon " + wins + " times.\nLost " + losses + " times.\n" + "Would you like to import a file for updated statistics?", "Your Current Individual Statistics", JOptionPane.YES_NO_OPTION);
	   if (import_stat == 0){ 
	   		String file = JOptionPane.showInputDialog(null, "*Must put the whole directory for importing*\n*Type file name and .txt*\n\nImport File: ", "Statistics", JOptionPane.INFORMATION_MESSAGE);
	   		if (file!=null){
	   			importFile(file);
	   		}
	   }
	}

   public String checkStat(){ 
	   boolean in_stat_file = false;
		   try {
			   	play = new ArrayList<String>();
			   	wi = new ArrayList<Integer>();
			   	los = new ArrayList<Integer>();
			   	
		   		BufferedReader inputFile = new BufferedReader(new FileReader("stat.txt"));
		   		String line = null;
		   		while((line=inputFile.readLine())!=null){
		   			Scanner scan = new Scanner(line);
		   			line = scan.nextLine();
		   			
		   			String[] file = line.split(",");
		   
		   			wins = Integer.parseInt(file[0]);
		   			losses = Integer.parseInt(file[1]);
		   			
			   		wins = wins + SudokuRun.getW();
			   		losses = losses + SudokuRun.getL();
			   		wi.add(wins);
			   		los.add(losses);
			   		
		   		}
		   		exitMethod(wi, los);
			  	if (in_stat_file == true){
			  		return "Player's Name: " + playerName + "\nNumber of Wins: " + wi.get(play.indexOf(playerName)) + "\nNumber of Losses: " + los.get(play.indexOf(playerName)) +
				  	"\nPercentage: " + ((wi.get(play.indexOf(playerName))*100)/(wi.get(play.indexOf(playerName))+los.get(play.indexOf(playerName))))+ "%\n\n";
			  	}
			  	else{
			  		play.add(playerName);
	   				wi.add(SudokuRun.getW());
	   				los.add(SudokuRun.getL());
	   				if((SudokuRun.getW()+ SudokuRun.getL())==0){
	   					return "Player's Name: " + playerName + "\nNumber of Wins: " + SudokuRun.getW() + "\nNumber of Losses: " + SudokuRun.getL() +
	   				  	"\nPercentage: " + (0)+ "%\n\n";
	   				}
	   				else{
	   					return "Player's Name: " + playerName + "\nNumber of Wins: " + SudokuRun.getW() + "\nNumber of Losses: " + SudokuRun.getL() +
	   				  	"\nPercentage: " + ((SudokuRun.getW()*100)/((SudokuRun.getW()+ SudokuRun.getL())*100))+ "%\n\n";
	   				}
			  	}	
		   }
		  	catch (IOException e) {
		  		System.err.println("Error reading file. Please check the file name and directory.");
		  	}
		  
		   return "";
		}
   
   public void importFile(String fileName){
	   try {
		  	String file = "stat.txt";	   
			FileWriter writer = new FileWriter(file);
	   		BufferedReader inputFile = new BufferedReader(new FileReader(fileName));
	   		String line = null;
	   	
	   	while((line=inputFile.readLine())!=null){
	   		Scanner scan = new Scanner(line);
	   		line = scan.nextLine();
			writer.write(line);
			writer.close();
	   	}
	   		
	   }
	   catch (IOException e) {
	  		System.err.println("Error reading file. Please check the file name and directory.");
	  	}
	  
   }
   //Handles all button events from Main Menu and Side Panel of the Sudoku Page
   public void actionPerformed(ActionEvent evt)  
   {   	
	//Start Button pressed
   	if (evt.getSource() == start_button && start_pressed == 0){
   		sample.addComponentToSudokuPage(frame.getContentPane());
   		start_pressed++;
   	}
   	
   	//Level Buttons pressed
   	else if (evt.getSource() == easy_button){
   		SudokuRun.difficulty_set = SudokuRun.LEVEL_EASY;
   		JOptionPane.showMessageDialog(null, "The level has been set to EASY!", "Set Level", JOptionPane.INFORMATION_MESSAGE);
   	}
   	else if (evt.getSource() == medium_button){
   		SudokuRun.difficulty_set = SudokuRun.LEVEL_MEDIUM;  
   		JOptionPane.showMessageDialog(null, "The level has been set to MEDIUM!", "Set Level", JOptionPane.INFORMATION_MESSAGE);
   	}
   	else if (evt.getSource() == difficult_button){
   		SudokuRun.difficulty_set = SudokuRun.LEVEL_DIFFICULT;
   		JOptionPane.showMessageDialog(null, "The level has been set to DIFFICULT!", "Set Level", JOptionPane.INFORMATION_MESSAGE);
   	}
   	else if (evt.getSource() == impossible_button){
   		JOptionPane.showMessageDialog(null, "It's IMPOSSIBLE for a reason!", "Set Level", JOptionPane.INFORMATION_MESSAGE);
   	}
   	
   	//Statistics Button pressed
   	else if (evt.getSource() == statistics_button){
   		addComponentsToStatisticsPage();
   	}
   	//Options Button pressed
   	else if (evt.getSource() == options_button && option_pressed == 0){
   		sample.addComponentsToOptionsPage(frame.getContentPane());
   		option_pressed++;
   	}
   	//How To Button pressed
   	else if (evt.getSource() == how_button && instruction_pressed == 0){
   		sample.addComponentsToHowPage(frame.getContentPane());
   		instruction_pressed++;
   	}
   	//Exit Button pressed
   	else if (evt.getSource() == exit_button){
   		int exit = JOptionPane.showConfirmDialog(null, "Would you like to exit the game?" , "Exit Game", JOptionPane.YES_NO_OPTION);
   		if (exit == 0){
   			System.exit(0);
   		}
   	}
   	//Side Panel Buttons on Sudoku Page
   	else if (evt.getSource() == change_level){
   		Object[] levelOptions = {"Easy","Medium", "Difficult", "Impossible"};
		String level = (String)JOptionPane.showInputDialog(frame, "Choose a Level:\n", "Choose a Level", JOptionPane.PLAIN_MESSAGE, null, levelOptions, "Easy");
		//option easy is chosen
		if (level == "Easy") {	
			SudokuRun.difficulty_set = SudokuRun.LEVEL_EASY;
	   		JOptionPane.showMessageDialog(null, "The level has been set to EASY!", "Set Level", JOptionPane.INFORMATION_MESSAGE);
	   		SudokuRun.random_number = SudokuRun.setRandomNumber();
	 		placement_matrix = SudokuRun.init();
	 		SudokuRun.given = SudokuRun.readInGame(SudokuRun.which_line);
	 		SudokuRun.input = new int[9][9];
	    	sudoku_panel.removeAll();
	   		valuesToBoard(placement_matrix, this.given, this.input);
		}
		//option medium is chosen
		if (level == "Medium") {
            //Change the input line to the new level chosen
			SudokuRun.difficulty_set = SudokuRun.LEVEL_MEDIUM;  
	   		JOptionPane.showMessageDialog(null, "The level has been set to MEDIUM!", "Set Level", JOptionPane.INFORMATION_MESSAGE);
	   		SudokuRun.random_number = SudokuRun.setRandomNumber();
	 		placement_matrix = SudokuRun.init();
	 		SudokuRun.given = SudokuRun.readInGame(SudokuRun.which_line);
	 		SudokuRun.input = new int[9][9];
	    	sudoku_panel.removeAll();
	   		valuesToBoard(placement_matrix, this.given, this.input);
        }
		//option difficult is chosen
		if (level == "Difficult") {
			SudokuRun.difficulty_set = SudokuRun.LEVEL_DIFFICULT;
	   		JOptionPane.showMessageDialog(null, "The level has been set to DIFFICULT!", "Set Level", JOptionPane.INFORMATION_MESSAGE);
	   		SudokuRun.random_number = SudokuRun.setRandomNumber();
	 		placement_matrix = SudokuRun.init();
	 		SudokuRun.given = SudokuRun.readInGame(SudokuRun.which_line);
	 		SudokuRun.input = new int[9][9];
	    	sudoku_panel.removeAll();
	   		valuesToBoard(placement_matrix, this.given, this.input);
		}
		//option impossible is chosen
		if (level == "Impossible") {
	   		JOptionPane.showMessageDialog(null, "It's IMPOSSIBLE for a reason!", "Set Level", JOptionPane.INFORMATION_MESSAGE);
		}
		//option cancel is chosen, you can write code below, but right now it does nothing
		
   	}
 	else if (evt.getSource() == new_game){
 		SudokuRun.random_number = SudokuRun.setRandomNumber();
 		placement_matrix = SudokuRun.init();
 		SudokuRun.given = SudokuRun.readInGame(SudokuRun.which_line);
 		SudokuRun.input = new int[9][9];
    	sudoku_panel.removeAll();
   		valuesToBoard(placement_matrix, this.given, this.input);
 	}
   	else if (evt.getSource() == reset_button){

   		placement_matrix =SudokuRun.given;
   		SudokuRun.input = new int[9][9];
   		
    	sudoku_panel.removeAll();
   		valuesToBoard(placement_matrix, this.given, this.input);
   	}
   	else if (evt.getSource() == validate_answer){
   		for (int i = 0; i<SudokuRun.total.length; i++){
    		for (int j = 0; j<SudokuRun.total[i].length; j++){
    			placement_matrix[j*9+i]=SudokuRun.total[i][j];
    		}
   		}
   	   	boolean checkWin = SudokuRun.checkifWon(placement_matrix);
   	   	if(checkWin == true){
   	   		wins++;
   	   		tryAgain = new JOptionPane(
                 "You Won!\n\n==Statistics==\n" + "Current Player's Name " + playerName + "\nWon " + wins + " times.\nLost " + losses + " times.\n\n"  + "Try Again?",
                 JOptionPane.QUESTION_MESSAGE,
                 JOptionPane.YES_NO_OPTION);
   	   		dialog = new JDialog(frame,
   	   			 "You Win!",
   	   			 true);
   	   		tryAgain();
     }
     //Player Loses
     else if(checkWin == false){ 
         losses++;
         tryAgain = new JOptionPane(
                 "You LOST!\n\n==Statistics==\n" + "Current Player's Name " + playerName + "\nWon " + wins + " times.\nLost " + losses + " times.\n\n"  + "Try Again?",
                 JOptionPane.QUESTION_MESSAGE,
                 JOptionPane.YES_NO_OPTION);
         dialog = new JDialog(frame,
              "You LOST!",
              true);
         tryAgain();
         
     }
   	   	   	}
   	else if (evt.getSource() == give_up){
   		showCorrectAnswers(placement_matrix);
   	}
   	
   	//Pop Down Menu displays on Sudoku Page...calls menuAction method to set up array of numbers and positions thereof
   	else if(evt.getSource() instanceof JMenuItem) {
            menuAction((JMenuItem) evt.getSource());
   	}
   	else if(evt.getSource() == textField1){
   		playerName = textField1.getText();
   		wins = 0;
   		losses = 0;
   	}
   	else if(evt.getSource() == textField2){
   		String text2 = textField2.getText();
   		if (text2.equals("1") == true){
   			side_panel.setBackground(Color.red);
   			sudoku_panel.setBackground(Color.red);
   		}
   		else if (text2.equals("2") == true){
   			side_panel.setBackground(Color.blue);
   			sudoku_panel.setBackground(Color.blue);
   		}
   		else if(text2.equals("3") == true){
   			side_panel.setBackground(Color.orange);
   			sudoku_panel.setBackground(Color.orange);
   		}
   		else if(text2.equals("4") == true){
   			side_panel.setBackground(Color.green);
   			sudoku_panel.setBackground(Color.green);
   		}
   		else{
   			side_panel.setBackground(Color.black);
   			sudoku_panel.setBackground(Color.white);
   		}
   	}
   	else if(evt.getSource() == textField3){
   	 	String text3 = textField3.getText();
   	 	if (text3.equals("1") == true){
   	 		given = Color.red;
   	 		sudoku_panel.removeAll();
   	 		valuesToBoard(this.placement_matrix, given, this.input);
   	 	}
   	 	else if (text3.equals("2") == true){
			given = Color.blue;
			sudoku_panel.removeAll();
			valuesToBoard(this.placement_matrix, given, this.input);
		}
   	 	else if (text3.equals("3") == true){
			given = Color.orange;
			sudoku_panel.removeAll();
			valuesToBoard(this.placement_matrix, given, this.input);
		}
   	 	else if (text3.equals("4") == true){
			given = Color.green;
			sudoku_panel.removeAll();
			valuesToBoard(this.placement_matrix, given, this.input);
		}
   	 else{
   		    given = Color.black;
			sudoku_panel.removeAll();
			valuesToBoard(this.placement_matrix, given, this.input);
		}
   	}
   	else if(evt.getSource() == textField4){
   		String text4 = textField4.getText();
   	   	
		//first value is 1 
   		if (text4.equals("1") == true ){ 
   			input = Color.red;
   			sudoku_panel.removeAll();
   			valuesToBoard(this.placement_matrix, this.given, input);
   		}
   		else if (text4.equals("2") == true){
   			input = Color.blue;
   			sudoku_panel.removeAll();
   			valuesToBoard(this.placement_matrix, this.given, input);
   		}
   		else if (text4.equals("3") == true){
   			input = Color.orange;
   			sudoku_panel.removeAll();
   			valuesToBoard(this.placement_matrix, this.given, input);
   		}
   		else if (text4.equals("4") == true){
   			input = Color.green;
   			sudoku_panel.removeAll();
   			valuesToBoard(this.placement_matrix, this.given, input);
   		}
   		else{
   			input = Color.black;
   			sudoku_panel.removeAll();
   			valuesToBoard(this.placement_matrix, this.given, input);
   		}
   	}
   	//If tab already open, will not open another
   	else{
   		JOptionPane.showMessageDialog(null, "This tab has already been opened! You cannot " +
   				"reopen the same tab.", "Tab Already Open!", JOptionPane.ERROR_MESSAGE);
   	}
   }
   
   public void exitMethod(ArrayList<Integer> win, ArrayList<Integer> loss){
		 try{
		   String file = "stat.txt";
		   FileWriter writer = new FileWriter(file);
		   for(int i = 0; i < win.size(); i++){	
				toFile = win.get(i) + "," + loss.get(i) + "\n";
				if(toFile!=null){  
					   writer.write(toFile);
		   		   }
		   }
		   writer.close();

		 } catch (IOException e) {e.printStackTrace();
		 }
		 }
		   
   
   //executes when player wins or loses after validating answer
   public void tryAgain(){
	   dialog.setContentPane(tryAgain);
	   dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
       dialog.addWindowListener(new WindowAdapter() {});
      
       tryAgain.addPropertyChangeListener(
               new PropertyChangeListener() {
                   public void propertyChange(PropertyChangeEvent e) {
                       String prop = e.getPropertyName();

                       if (dialog.isVisible()
                        && (e.getSource() == tryAgain)
                        && (JOptionPane.VALUE_PROPERTY.equals(prop))) {
                           //If you were going to check something
                           //before closing the window, you'd do
                           //it here.
                           dialog.setVisible(false);
                           
                       }
                   }
               });
           
           dialog.pack();
           dialog.setLocationRelativeTo(frame);
           dialog.setVisible(true);
           
           int value = ((Integer)tryAgain.getValue()).intValue();
           if (value == JOptionPane.YES_OPTION) {
        	   SudokuRun.readInGame(SudokuRun.which_line);
        	   placement_matrix = SudokuRun.given;
        	   SudokuRun.input = new int[9][9];
        	   sudoku_panel.removeAll();
               valuesToBoard(placement_matrix, this.given, this.input);
      	   
           } 
   }
   
   //updates what the user puts in in an array in SudokuRun class
   public void menuAction(JMenuItem mi) {
	   if (mi.getText() == "?"){
		   int number = 0;
		   int k = sources.indexOf((JButton) current_source);
		   SudokuRun.input[k%9][k/9]= number;
	       SudokuRun.total[k%9][k/9] = number;
	       placement_matrix[k] = number;
	   }
	   else{
		   int number = (int)(Integer.parseInt(mi.getText())); 
		   for (int i = 0; i<SudokuRun.given.length; i++){
	       		if(SudokuRun.total[i%9][i/9]==0){
	       			SudokuRun.total[i%9][i/9] = SudokuRun.given[i];
	       		}
	       		if(SudokuRun.total[i%9][i/9]==0){
	       			SudokuRun.total[i%9][i/9] = SudokuRun.input[i%9][i/9];
	       		}
	       		placement_matrix[i]=SudokuRun.total[i%9][i/9];
	       	}
	       int k = sources.indexOf((JButton) current_source);

	       SudokuRun.input[k%9][k/9]= number;
	       SudokuRun.total[k%9][k/9] = number;
	       
	       placement_matrix[k] = number;
	      
	   }
       
      sudoku_panel.removeAll();
      valuesToBoard(placement_matrix,given,input);
   }
      
	public void itemStateChanged(ItemEvent arg0) {

	}

	//Show pop down menu when user clicks in an empty Sudoku gamepiece.
	public void mouseClicked(MouseEvent e) {	
		current_source = e.getSource();
		popup.show(e.getComponent(), e.getX(), e.getY());		
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
	}
	
	public void mouseReleased(MouseEvent e) {
	
	}
}

