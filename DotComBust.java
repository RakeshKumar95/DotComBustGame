import java.util.*;
import java.io.*;

public class DotComBust{

  private GameHelper helper = new GameHelper();
  private ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();
  private int numOfGuesses = 0;

  private void setUpGame(){
    
    DotCom one = new DotCom();
    one.setName("Google.com");
    DotCom two = new DotCom();
    two.setName("Yahoo.com");
    DotCom three = new DotCom();
    three.setName("Apple.com");
    dotComsList.add(one);
    dotComsList.add(two);
    dotComsList.add(three);

    System.out.println("Your Goal is to sink three dot coms.");
    System.out.println("Google.com, Yahoo.com, Apple.com");
    System.out.println("Try to sink them all in the fewest number of guesses");

    for(DotCom dotComToSet : dotComsList){
	  ArrayList<String> newLocation = helper.placeDotCom(3);
          dotComToSet.setLocationCells(newLocation);
	}
    }
    
    private void startPlaying(){
      while(!dotComsList.isEmpty()){
	  String userGuess = helper.getUserInput("Enter a guess");
	  checkUserGuess(userGuess);
	}
     finishGame();
    }

    private void checkUserGuess(String userGuess){
      numOfGuesses++;
      String result = "miss";

      for(DotCom dotComToTest : dotComsList){
	  result = dotComToTest.checkYourself(userGuess);
	  if(result.equals("hit")){
	    break;
	  }
	  if(result.equals("kill")){
	    dotComsList.remove(dotComToTest);
	    break;
	  }
	}
	System.out.println(result);
    }

    private void finishGame(){
      System.out.println("All the Dot Coms are dead! Your stock is now worthless.");
      if(numOfGuesses <= 18){
	System.out.println("It only took you "+numOfGuesses+"guesses.");
    }  else{
	System.out.println("Took you long enough. "+numOfGuesses+" guesses.");
      }
    }

    public static void main(String[] args){
      DotComBust game = new DotComBust();
      game.setUpGame();
      game.startPlaying();
    }

}

class DotCom{
      private ArrayList<String> locationCells;
      private String name;

      public void setLocationCells(ArrayList<String> loc){
	locationCells = loc;
      }

      public void setName(String n){
	name = n;
      }
      
      public String checkYourself(String userInput){
	String result = "miss";
	int index = locationCells.indexOf(userInput);
	if(index >= 0){
	  locationCells.remove(index);
	
	
	if(locationCells.isEmpty()){
	  result = "kill";
	  System.out.println("Ouch! You sunk "+name+".");
	} else {
	  result = "hit";
	}
      }
      return result;
    }
}

class GameHelper{
  private static final String alphabet = "abcdefgh";
  private int gridLength = 7;
  private int gridSize = 49;
  private int[] grid = new int[gridSize];
  private int comCount = 0;
  
  public String getUserInput(String prompt){
    String inputLine = null;
    System.out.print(prompt+ " ");
    try{
    BufferedReader is =  new BufferedReader(new InputStreamReader(System.in));
    inputLine = is.readLine();
    if(inputLine.length() == 0) {
	return  null;
      }
    }catch(IOException e){
      System.out.println("IOException: "+e);
    }
    return inputLine.toLowerCase();
  }

  public ArrayList<String> placeDotCom(int comSize){
    ArrayList<String> alphaCells = new ArrayList<String>();
    String[] alphacoords = new String[comSize];
    String temp = null;
    int[] coords = new int[comSize];
    int attempts = 0;
    boolean success = false;
    int location = 0;

    comCount++;
    int incr = 1;
    if((comCount % 2) == 1){
      incr = gridLength;
    }

    while(!success & attempts++ < 200){
      location = (int) (Math.random() * gridSize);
      //System.out.print("try this "+location);
      int x = 0;
	success = true;
	while(success && x < comSize){
	  if(grid[location] == 0){
	    coords[x++] = location;
	    location += incr;
	    if(location >= gridSize){
	      success = false;
	    }
	    if(x>0 && (location % gridLength == 0)){
	      success = false;
	    }
	   }
	    else{
	      success = false;
	    }
	  
	}
    }
    
    int x = 0;
    int row = 0;
    int column = 0;
    while(x < comSize){
      grid[coords[x]] = 1;
      row = (int)(coords[x] / gridLength);
      column = coords[x] % gridLength;
      temp = String.valueOf(alphabet.charAt(column));

      alphaCells.add(temp.concat(Integer.toString(row)));
      x++;
	//System.out.print(" coord "+x+" = "+alphaCells.get(x-1));
    }
	//System.out.println("\n");
    return alphaCells;
  }
}