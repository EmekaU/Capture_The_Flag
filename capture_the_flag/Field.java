import java.util.ArrayList; 
import java.util.Hashtable; 
import java.util.Arrays; 


/** This is the field that a game of Capture the Flag plays on. 
  * <p> 
  * The field also acts as a moderator (umpire) for the game to  
  * try and prevent players from cheating. 
  * <p> 
  * A field will have some width (x directions) and a height (y direction). 
  * The upper left-hand corner is the [0,0] coordinate.   
  *  
  * The values of x increase as you move to the right 
  * The values of y increase as you move down 
  */ 
public class Field{ 
  /** determines if players start at base or not */ 
  public static boolean START_FROM_BASE = true; 
  
  /** constant scaling for moving */ 
  protected static long SCALE = 30; 
  
  /** determines if the game has been won (completed) */ 
  protected static boolean GAME_IS_WON = false; 
  
  /** distance between entities to be considered touching (from centre to centre) */ 
  public static Double ARMS_LENGTH = 12.0; 
  
  /** max x-coordinate for entity on field */ 
  final int maxX; 
  
  /** max y-coordinate for entity on field */ 
  final int maxY;  
  
  /** min x-coordinate for entity on field */ 
  final int minX; 
  
  /** min y-coordinate for entity on field */ 
  final int minY;  
  
  
  /** the graphical view of the game */ 
  final View view; 
  
  
  
  
  
  
  /*                   */ 
  /* teams in the game */ 
  /*                   */ 
  
  /** the team for territory 1 */ 
  protected ArrayList<Entity> team1 = new ArrayList<Entity>(); 
  
  /** the team for territory 2 */ 
  protected ArrayList<Entity> team2 = new ArrayList<Entity>(); 
  
  /** getter for the players in team 1  
    *  
    * @return the players in team 1 
    */ 
  public ArrayList<Entity> getTeam1(){ return this.team1; } 
  
  /** getter for the players in team 2  
    *  
    * @return the players in team 2 
    */ 
  public ArrayList<Entity> getTeam2(){ return this.team2; } 
  
  
  
  
  
  /*                                    */ 
  /* basic entities that all games need */  
  /*                                    */ 
  
  
  
  /** A look-up table for players and their ids */ 
  protected Hashtable<Entity, Integer> getID = new Hashtable<Entity, Integer>(); 
  
  
  
  /** things for territory 1 */ 
  protected final Entity flag1; 
  protected final Entity jail1;  
  protected final Entity base1; 
  
  /** things for territory 2 */ 
  protected final Entity flag2;  
  protected final Entity jail2;  
  protected final Entity base2;  
  
  
  /** the flag for territory 2 */ 
  
  /** the jail for territory 1 */ 
  
  /** the jail for territory 2 */ 
  
  /** the base for territory 1 */ 
  /** the base for territory 2 */ 
  
  /** holds the "things" in a game: flags, bases and jails */ 
  ArrayList<Entity> things = new ArrayList<Entity>(); 
  
  /** A look-up table that maps entity's to the team (territory) they belong to */ 
  protected Hashtable<Entity, Integer> getTeam = new Hashtable<Entity, Integer>(); 
  
  /** gets the x and y coordinates of flag 1  
    *  
    * @return [x,y], the x and y coordinates of the flag 1 on this field 
    */ 
  public int[] getFlag1Position(){ return new int[]{ flag1.getX(), flag1.getY() }; } 
  
  /** gets the x and y coordinates of flag 2  
    *  
    * @return [x,y], the x and y coordinates of the flag 2 on this field 
    */ 
  public int[] getFlag2Position(){ return new int[]{ flag2.getX(), flag2.getY() }; } 
  
  /** gets the x and y coordinates of jail 1  
    *  
    * @return [x,y], the x and y coordinates of the jail 1 on this field 
    */ 
  public int[] getJail1Position(){ return new int[]{ jail1.getX(), jail1.getY() }; } 
  
  /** gets the x and y coordinates of jail 2  
    *  
    * @return [x,y], the x and y coordinates of the jail 2 on this field 
    */ 
  public int[] getJail2Position(){ return new int[]{ jail2.getX(), jail2.getY() }; } 
  
  /** gets the x and y coordinates of base 1  
    *  
    * @return [x,y], the x and y coordinates of the base 1 on this field 
    */ 
  public int[] getBase1Position(){ return new int[]{ base1.getX(), base1.getY() }; } 
  
  /** gets the x and y coordinates of base 2  
    *  
    * @return [x,y], the x and y coordinates of the base 2 on this field 
    */ 
  public int[] getBase2Position(){ return new int[]{ base2.getX(), base2.getY() }; } 
  
  
  /** getter for all the "things" in the game: flags, bases and jails 
    *  
    * @return an arraylist containing both flags, bases and jails in this field 
    */ 
  public ArrayList<Entity> getThings(){ 
    /* hard-coded list of all things in the game. Change this if you want to add more things */ 
    Entity[] allThings = new Entity[]{ flag1, flag2, base1, base2, jail1, jail2 }; 
    return new ArrayList<Entity>( Arrays.asList(allThings) ); 
    
  } 
  
  
  
  
  /*                                               */ 
  /* methods that the game will use                */ 
  /*                                               */ 
  
  
  /** update all entities on the field   
    * <p> 
    * update the position of all entity's on this field 
    */ 
  public void update(){ 
    for(Entity en: team1){ 
      try{ 
        en.updatePosition(SCALE*1, this, getID.get(en)); 
      }catch(EntityOutOfBoundsException e){ 
        // add code here 
        en.stop=true; //<============================================================================ Edited 
      } 
    } 
    for(Entity en: team2){ 
      try{ 
        en.updatePosition(SCALE*1, this, getID.get(en)); 
      }catch(EntityOutOfBoundsException e){ 
        // add code here 
        en.stop=true; //<============================================================================ Edited 
      } 
    } 
    for(Entity en: things){ 
      try{ 
        en.updatePosition(SCALE*1, this, getID.get(en)); 
      }catch(EntityOutOfBoundsException e){ 
        // add code here 
        en.stop=true; //<============================================================================ Edited 
      } 
    } 
  } 
  
  /** have all entities on this field "play" (perform their own logic) */ 
  public void play(){ 
    for(Entity e: team1){ 
      e.play(this); 
    } 
    for(Entity e: team2){ 
      e.play(this); 
    } 
    for(Entity e: things){ 
      e.play(this); 
    } 
  } 
  
  /** draw the current state of the game */ 
  public void draw(){ 
    view.update(); 
    view.draw(team1, team2, things); 
  } 
  
  
  /** Assigns a player to given territory on the field 
    * <p> 
    * Registers a player to a side (territory) on the field.  If <code>START_FROM_BASE</code> is  
    * true, a registered player will have its position set be the same as the base for  
    * territory they are being registered to. 
    * <p> 
    * Side effects: registers a player with a territory.  Assigns a sprite (image) to the player. 
    * Players position is set to their base if START_FROM_BASE is true. 
    *  
    * @param a is a player 
    * @param id is the player's id number 
    * @param territory is either 1 or 2 (representing a territory) 
    * @return true if <code>a</code> is not already registered, <code>territory</code> is 1 or 2,  
    * and the player's coordinates are not on the other territory. False, otherwise.   
    */ 
  public boolean registerPlayer(Player a, int id, int territory){ 
    if( getTeam.containsKey(a) || territory < 1 || territory > 2){ 
      return false; 
    } 
    if( territory == 1 ){  
      if (a.getX() > maxX/2){ 
        /* player must be on the correct side of the field */ 
        return false; 
      } 
      a.setSprite("sprites/blue.png", id); 
      if(START_FROM_BASE){ 
//                a.setX(base1.getX(), id); 
//                a.setY(base1.getY(), id); 
      } 
      team1.add(a); 
    }else{ 
      if (a.getX() < maxX/2){ 
        /* player must be on the correct side of the field */ 
        return false; 
      } 
      a.setSprite("sprites/red.png", id); 
      if(START_FROM_BASE){ 
//                a.setX(base2.getX(), id); 
//                a.setY(base2.getY(), id); 
      } 
      team2.add(a); 
    } 
    
    getTeam.put(a, territory); 
    getID.put(a, id); 
    return true; 
  } 
  
  
  /** Assigns a non-player entity to given territory on the field 
    * <p> 
    * Side effects: registers a non-player entity with a territory.   
    * Assigns a sprite (image) to the entity. 
    * Remembers the entity's id. 
    *  
    * @param a is an entity (non-player) 
    * @param id is the entity's id number 
    * @param territory is either 1 or 2 (representing a territory) 
    */ 
  public boolean registerThing(Entity a, int id, int territory){ 
    if( territory < 1 || territory > 2){ 
      return false; 
    } 
    
    // currently only remembers the thing's id 
    
    getID.put(a, id); 
    return true; 
  } 
  
  
  
  /*                                               */ 
  /* methods that players use                      */ 
  /*                                               */ 
  
  /** Attempt to catch a player 
    *  
    * @param a is a player trying to catch player <code>b<\code> 
    * @param b is a player  
    * @return true if <code>a</code> and <code>b</code> on are on different teams 
    * and are within ARMS_LENGTH of each other. False otherwise. 
    */ 
  public boolean catchOpponent(Player a, Player b){ 
    
    if( a.getTeam().equals(b.getTeam()) ){ 
      return false; 
    } 
    if( Math.hypot( a.getX() - b.getX(), a.getY() - b.getY() ) <= ARMS_LENGTH ){ 
      return true; 
    } 
    return false; 
  } 
  
  /** attempt to free a player from jail 
    *  
    * @param a is a player trying to free a teammate from jail 
    * @param b is a player 
    * @return true if <code>a</code> and <code>b</code> are on the same team,  
    * <code<b</code> is in jail, and <code>a</code> is within ARMS_REACH of  
    * the jail where <code>b</code> is located.  False otherwise. 
    */ 
  public boolean freeTeammate(Player a, Player b){ 
    if(a.getTeam().equals(b.getTeam()) &&
       Math.hypot(a.getX()-a.enemyJail.getX(),a.getY()-a.enemyJail.getY())<=Field.ARMS_LENGTH && 
       b.inJail()){
      return true;
    }
    return false; 
  } 
  
  /** attempt to pick up a flag 
    *  
    * @param a is a player trying to pick up a flag 
    * @return true if <code>a</code> is within ARMS_REACH of  
    * the opposing team's flag and it is not being carried by anyone else.   
    * Returns false otherwise. 
    */ 
  public boolean pickUpFlag(Player a){ 
    Entity f = flag1;     
    if( getTeam.get(a).equals(new Integer(1)) ){ 
      f = flag2; 
    }
    if( Math.hypot( a.getX() - f.getX(), a.getY() - f.getY() ) <= ARMS_LENGTH ){ 
      return true; 
    } 
    return false; 
  } 
  
  
  /** attempt to win the game 
    *  
    * @param a is a player 
    * @return true if <code>a</code> is carrying its opponent's flag 
    * and is with ARMS_REACH of its own base.  False otherwise. 
    */ 
  public boolean winGame(Player a){ 
    Entity b = base2; 
    if( getTeam.get(a).equals(new Integer(1)) ){ 
      b = base1; 
    } 
    Entity c = flag1;     
    if( getTeam.get(a).equals(new Integer(1)) ){ 
      c = flag2; 
    } 
    
    if (a.hasFlag()){
      
      // 
      // needs some code to determine if a is carrying the flag 
      // 
      
      if( Math.hypot( c.getX() - b.getX(), c.getY() - b.getY() ) <= ARMS_LENGTH ){ 
        GAME_IS_WON = true; 
        return true; 
      } 
    }
    return false; 
  } 
  
  /** Asks if the game is still running (no winner yet) 
    *  
    * @return true if the game is still running (the game has not been won) and false otherwise. 
    */ 
  public boolean gameStillRunning(){ 
    return !GAME_IS_WON; 
  } 
  
  
  /** create the default playing field  
    * <p> 
    * width of field = 800 pixels 
    * height of field = 600 pixels 
    */ 
  public Field(){ 
    // initialize field dimensions (hardcoded for now) 
    maxX = 810; minX = 10; 
    maxY = 610; minY = 10; 
    
    //creating attribute for middle boundary line
    bound=(maxX+minX)/2; 
    
    // initialize all the "things" in the game 
    
    flag1 = new Flag(this, 1, 'f', 34+minX, 191+minY, "sprites/blueFlag.png"); 
    jail1 = new Jail(this, 1, 'j', 29+minX, 399+minY, "sprites/jail.png"); 
    base1 = new Base(this, 1, 'b', 29+minX, 199+minY, "sprites/blueBase.png"); 
    getTeam.put(base1, 1); getTeam.put(jail1,1); getTeam.put(flag1, 1); 
    things.add(base1); things.add(jail1); things.add(flag1); 
    
    flag2 = new Flag(this, 2, 'F', 754+minX, 391+minY, "sprites/redFlag.png"); 
    jail2 = new Jail(this, 2, 'J', 749+minX, 199+minY, "sprites/jail.png"); 
    base2 = new Base(this, 2, 'B', 749+minX, 399+minY, "sprites/redBase.png"); 
    getTeam.put(base2, 2); getTeam.put(jail2,2); getTeam.put(flag2, 2); 
    things.add(base2); things.add(jail2); things.add(flag2); 
    
    // initialize the view 
    view = new View(minX, minY, maxX, maxY); 
    
    
  } 
  
  //middle line of the field (for use in trespassing method in player)
  public int bound;
  
}