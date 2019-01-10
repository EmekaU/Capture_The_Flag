import java.io.*;
import java.lang.*;
import java.util.*;

public class Team{
  public Team(){}
  
  //relevant attributes
  private Field field;
  private Field check;
  private int side;
  private int x;
  private int id;
  private int numSeekers;
  private int numHunters;
  private int numSaviors;
  private int numDefenders;
  private int numGuards;
  private Hunter hunters;
  private Seeker seekers;
  int num;
  String teamName;
  char symbol;
  private GuardF flagGuard;
  private GuardB baseGuard;
  private int size;
  private Saviour saviors;
  private int numberOfPlayers;
  private Scanner scan;
  int numWin;
  int numLose;
  
  //constructor for inputting field, number of players and side
  public Team(Field field, int numberOfPlayers, int side){
    this.field = field;
    this.side = side;
    
    //set team name based on side
    if (side==1){
      num = 10; //offset for spawning
      teamName = "Blues"; //team name
      symbol = 'b'; //set symbol
      id = 12; //set ID
    }
    if(side==2){
      num = 410;
      teamName = "reds";
      symbol = 'r';
      id = 7;
    }
    this.numberOfPlayers = numberOfPlayers;
    
    //error if no players
    if (this.numberOfPlayers < 1){
      System.out.println("There are no players in the team.");
    }
    
    x = numberOfPlayers % 5; //number of overflow players
    size = numberOfPlayers/5; //number of times a full team composition (one of each type) can be formed
    
    //one of each type of player, with priotiy
    for(int i=0; i < size; i++){
      seekers = new Seeker(field, side, "seeker", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
      hunters = new Hunter(field, side, "hunter", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
      saviors = new Saviour(field, side,"saviors", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
      flagGuard = new GuardF(field, side, "defender", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
      baseGuard = new GuardB(field, side,"defender", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
    }
    int i = 0;
    
    //prioritizing overflow
    //overflow order: Seeker, Hunter, Saviour, GuardF
    if(x==1){
      seekers = new Seeker(field, side, "seeker", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
    }
    else if(x==2){
      seekers = new Seeker(field, side, "seeker", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
      hunters = new Hunter(field, side, "hunter", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
    }
    else if(x==3){
      seekers = new Seeker(field, side, "seeker", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
      hunters = new Hunter(field, side, "hunter", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
      saviors = new Saviour(field, side,"savior"+ num, id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
    }
    else if(x==4){
      seekers = new Seeker(field, side, "seeker", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
      hunters = new Hunter(field, side, "hunter", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
      saviors = new Saviour(field, side,"savior", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
      flagGuard = new GuardF(field, side, "flagGuard", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
    }
  }
  
  //Constructor for inputting field, numbers of each type of player, side
  public Team(Field field, int numSeekers, int numHunters, int numSaviors, int numDefenders, int numGuards, int side){
    this.field = field;
    this.side = side;
    
    //setting team name based on side.
    if (side==1){
      num = 10;
      teamName = "Blues";
      symbol = 'b';
      id = 12;
    }
    if(side==2){
      num = 410;
      teamName = "reds";
      symbol = 'r';
      id = 7;
    }
    this.numSeekers = numSeekers;
    this.numHunters = numHunters;
    this.numSaviors = numSaviors;
    this.numDefenders = numDefenders;
    this.numGuards = numGuards;
    
    //create number of each type of player
    for(int a =0; a<numSeekers; a++){
      seekers = new Seeker(field, side, "seeker", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
    }
    for(int b =0; b<numHunters; b++){
      hunters = new Hunter(field, side, "hunter", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
    }
    for(int c =0; c<numSaviors; c++){
      saviors = new Saviour(field, side, "saviour", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
    }
    for(int d =0; d<numDefenders; d++){
      flagGuard = new GuardF(field, side, "flagGuard", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
    }
    for(int e =0; e<numGuards; e++){
      baseGuard = new GuardB(field, side, "baseGuard", id, teamName, symbol, Math.random()*380+num, Math.random()*500+10);
    }
  }
  
  
  public static Player[] read(String fileName, Field field){
    
    //arraylist of players
    List<Player> member= new ArrayList(); 
    int size=0;
    
    //try/catch for scanning in file
    try{
      
      //scanning file
      Scanner in = new Scanner(new FileReader(fileName)); 
      
      Player type;
      String playertype;
      int side;
      String name; 
      int id;
      char charsymbol;
      String team_name;
      int x;
      int y;
      
      //while loop to repeat until done
      while(true){ 
        
        //if has a next value
        if( in.hasNext()){
          
          side = Integer.parseInt(in.next()); //read side
          name  = in.next(); //read name
          
          System.out.println(name);
          
          id = Integer.parseInt(in.next()); //read id
          team_name = in.next(); //read team name
          
          //depending on side set symbol
          if (side ==1){charsymbol = 'b';}
          else{charsymbol = 'r';}
          
          //read x/y values
          x = Integer.parseInt(in.next());
          y = Integer.parseInt(in.next());
          
          playertype = in.next(); //read player type
          
          //create player depending on player type
          if (playertype.equals("Seeker"))     {type  = new Seeker (field, side, name,id,  team_name,  charsymbol,  x,  y);}
          else if (playertype.equals("Hunter")){type  = new Hunter (field, side, name,id,  team_name,  charsymbol,  x,  y);}
          else if (playertype.equals("GuardF")){type  = new GuardF (field, side, name,id,  team_name,  charsymbol,  x,  y);}
          else  if(playertype.equals("GuardB")){type  = new GuardB (field, side, name,id,  team_name,  charsymbol,  x,  y);}
          else                                 {type  = new Saviour(field, side, name,id,  team_name,  charsymbol,  x,  y);}
        }else{break;} 
        size += 1; //increment team size
        member.add(type); 
      } 
      in.close(); //close file
      
      //cathcing FileNotFound
    }catch(FileNotFoundException e){ 
      System.err.println("Error: could not find file \"" + fileName + "\""); 
    } 
    
    Player[] players =new Player[size]; 
    
    for(int i=0;i<size;i++){ 
      players[i]=member.get(i); 
    } 
    
    return players; 
  }
  
  //constructor for reading in file with players and sides (format ex. in gamefile.txt)
  public Team(Field field, String fileName){
    this.field = field;
    Player[] players =read(fileName,field);
    
    for ( int i = 0; i < players.length; i++){
      
      Player player =  players[i];
    }
  }
}



