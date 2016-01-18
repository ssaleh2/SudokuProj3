import java.io.*;
import java.util.*;

public class SudokuRun{
	public static int[] given;
	public static int[][] input;
	public static int[][] total;
	public static int[] game_setup = new int[81];
	public static int[] read_matrix;
	public static Object[] sourcearray;
	public static int column = 9;
	public static int row = 9;
	public static final int LEVEL_EASY = 1;
	public static final int LEVEL_MEDIUM = 2;
	public static final int LEVEL_DIFFICULT = 3;
	public static final int LEVEL_IMPOSSIBLE = 4;
	public static ArrayList<int[]> easy_levels = new ArrayList<int[]>(5);
	public static ArrayList<int[]> med_levels = new ArrayList<int[]>(5);
	public static ArrayList<int[]> hard_levels = new ArrayList<int[]>(5);
	public static ArrayList<int[]> imposs_levels = new ArrayList<int[]>(5);
	public static String[] tokens;
	public static int difficulty = 0;
	public static int difficulty_set = 1;
	public static Random random = new Random();
	public static int random_number = setRandomNumber();
	public static int initial_read = 0;
	private static int[] answer_matrix;
	public static int which_line = 0;
	private static int answer_line;
	public static Integer w = 0;
	public static Integer l = 0;
	
	public static int setRandomNumber(){
		return random.nextInt(4);
	}
   
	public static void setGiven(int[] givenIn){
		given=givenIn;
	}
	public static void readIn(){
		//System.out.println("How many times readin runs: " + initial_read);
		try {
	   		BufferedReader inputFile = new BufferedReader(new FileReader("board.txt"));
	   		String line = null;
	   		while((line=inputFile.readLine())!=null){
	   			read_matrix = new int[81];
	   			Scanner scan = new Scanner(line);
	   			line = scan.nextLine();
	   			
	   			String comma = ",";
	   			int indexOfComma = line.indexOf(comma);
	   			difficulty = Integer.parseInt(line.substring(0, indexOfComma));
	   			line = line.substring(indexOfComma+1, line.length());
	   			
	   			tokens = line.split(",");
	   		
	   			if (difficulty == 1){
	   				for (int index = 0; index < tokens.length; index++)
	   	   			{
	   	   				int game_number = Integer.parseInt(tokens[index]);
	   	   				read_matrix[index] = game_number;
	   	   			}
	   				
   	   				easy_levels.add(read_matrix);
	   			}
	   			else if (difficulty == 2){
	   				for (int index = 0; index < tokens.length; index++)
	   	   			{
	   	   				int game_number = Integer.parseInt(tokens[index]);
	   	   				read_matrix[index] = game_number;
	   	   			}
	   				med_levels.add(read_matrix);
	   			}
	   			else if (difficulty == 3){
	   				for (int index = 0; index < tokens.length; index++)
	   	   			{
	   	   				int game_number = Integer.parseInt(tokens[index]);
	   	   				read_matrix[index] = game_number;
	   	   			}
	   				hard_levels.add(read_matrix);
	   			}
	   			else{
	   				for (int index = 0; index < tokens.length; index++)
	   	   			{
	   	   				int game_number = Integer.parseInt(tokens[index]);
	   	   				read_matrix[index] = game_number;
	   	   			}   
	   				imposs_levels.add(read_matrix);
	   			}
	   		}
	   	}catch (IOException e){
	   		System.err.println("Error reading file. Please restart program.");
	     }
	}
	public static int[] init() {
		if (initial_read == 0){
			initial_read = 1;
			readIn();
		}
		input = new int[column][row];
		//given = new int[81];
		total = new int[column][row];
   	
   		if (difficulty_set == 2){
   			which_line = 6 + random_number;
   			game_setup = med_levels.get(random_number);
   			//for (int i = 0; i<SudokuRun.given.length; i++){
        	//	SudokuRun.given[i]=game_setup[i];
   			//} 
   			return game_setup;
   		}
   		else if(difficulty_set == 3){
   			which_line = 11 + random_number;
   			game_setup = hard_levels.get(random_number);
   			//for (int i = 0; i<SudokuRun.given.length; i++){
        	//	SudokuRun.given[i]=game_setup[i]; 
   			//} 
   			return game_setup;
   		}
   		else if(difficulty_set == 4){
   			which_line = 15;
   			game_setup = imposs_levels.get(0);
   			//for (int i = 0; i<SudokuRun.given.length; i++){
        	//	SudokuRun.given[i]=game_setup[i];
   			//} 
   			return game_setup;
   		}
   		else{
   			which_line = 1 + random_number;
   			game_setup = easy_levels.get(random_number);
   			//for (int i = 0; i<SudokuRun.given.length; i++){
   			//	SudokuRun.given[i]=game_setup[i];
   			//} 
   			//for (int[] m : easy_levels){
   			//for (int n : m){
	   		//	System.out.println("Game_Setup Check: " + n);
	   		//	}
   			return game_setup;
   		}
   }
	public static int[] readInGame(int line_game){
		int current_line = 0;
		int[] current_game = new int[81];  	
		try {
	   		BufferedReader inputFile = new BufferedReader(new FileReader("board.txt"));
	   		String line = null;
	   		
	   		while((line=inputFile.readLine())!=null && current_line!=line_game){
	   			current_line++;		
	   			Scanner scan = new Scanner(line);
	   			line = scan.nextLine();
	   			String[] tokens = line.split(",");
	   		 	for (int index = 1; index < tokens.length; index++)
	   			{
	   				int game_number = Integer.parseInt(tokens[index]);
	   				current_game[index-1] = game_number;
	   			}
	   		 	setGiven(current_game);
	   		}
	   		
	   	}catch (IOException e) {
	      System.err.println("Error reading file. Please restart program.");
	     }
	   
	   	return current_game;
	}
	public static int[] answers() {
		answer_line = 0;
	 	try {
	   		BufferedReader inputFile = new BufferedReader(new FileReader("answers.txt"));
	   		String line = null;
	   		while((line=inputFile.readLine())!=null && answer_line!=which_line){
	   			answer_line++;
	   			answer_matrix = new int[81];  			
	   			Scanner scan = new Scanner(line);
	   			line = scan.nextLine();
	   			String[] tokens = line.split(",");
	   		 	for (int index = 1; index < tokens.length; index++)
	   			{
	   				int game_number = Integer.parseInt(tokens[index]);
	   				answer_matrix[index-1] = game_number;
	   			}
	   		}
	   		
	   	}catch (IOException e) {
	      System.err.println("Error reading file. Please restart program.");
	      }
	   	//System.out.println("answer line: " + answer_line);
	   return answer_matrix;
	}
	public static boolean checkifWon(int[] check_matrix){
		int number_matched =0;
		for(int element = 0; element<check_matrix.length; element++){
			if (check_matrix[element] == answers()[element]){
				number_matched++;
			}
		}
		if (number_matched == 81){
			w++;
			return true;
		
		}
		else{
			l++;
			return false;
		}	
	}
	public static Integer getW() {
        return w;
    }
    public static void setW(Integer w) {
        SudokuRun.w = w;
    }
    
    public static Integer getL() {
        return l;
    }
    public static void setL(Integer l) {
        SudokuRun.l = l;
    }
} 