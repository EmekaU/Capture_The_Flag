public abstract class Player extends Entity{
  
  
  /** this player's team name */
  protected String team;
  
  /** this player's name */
  protected String name;
  
  /** this player's number */
  protected int number;
  
  
  /** gets this player's team name
    * 
    * @return the team name that this player is on
    */
  public final String getTeam(){ return this.team; }
  
  
  /** gets this player's name
    * 
    * @return the name of this player
    */
  public final String getName(){ return this.name; }
  
  /** gets this player's number
    * 
    * @return the number of this player
    */
  public final int getNumber(){ return this.number; }
  
  
  /** creates a player with specified symbol at specified position 
    * 
    * @param f is the field the player will be playing on
    * @param side is the side of the field the player will play on
    * @param name is this name of the player
    * @param number is this player's number 
    * @param team is this player's team name
    * @param symbol is a character (char) representation of this player
    * @param x is the x-coordinate of this player
    * @param y is the y-coordinate of this player
    */
  public Player(Field f, int side, String name, int number, String team, char symbol, double x, double y){
    super(symbol, x, y);
    this.name = name;
    this.number = number;
    this.team = team;
    
    this.side=side; //setting player's side.
    
    //initializing significant Entities depending on side
    if(side==1){
      this.myJail=f.jail1;
      this.enemyJail=f.jail2;
      this.myBase=f.base1;
      this.enemyBase=f.base2;
      this.myFlag=f.flag1;
      this.enemyFlag=f.flag2;
    }else{
      this.myJail=f.jail2;
      this.enemyJail=f.jail1;
      this.myBase=f.base2;
      this.enemyBase=f.base1;
      this.myFlag=f.flag2;
      this.enemyFlag=f.flag1;
    }
    
    f.registerPlayer(this, this.id, side);  // register the player on the field
  }
  
  /** attempt to catch an opponent player
    * 
    * @param opponent a player on the opponent's team that you are trying to catch
    * @param field is the field the game is being played on
    * @return true if this player successfully catches the opponent player, false otherwise
    */
  public final boolean catchOpponent(Player opponent, Field field){
    return field.catchOpponent(this, opponent);
  }
  
  
  
  /** Informs this player that they have been caught by another player. 
    * <p>
    * This method should only be called from within the Field class.  
    * 
    * @param opponent is the player that caught this player  
    * @param id should be the id of the this player
    */
  public void beenCaught(Player opponent, int id){
    /* check if the caller knows this entity's id */
    if( this.id != id ){
      throw new SecurityException("Unauthorized attempt to call beenCaught ");
    }
    
  }
  
  /** attempt to free a teammate from jail
    * 
    * @param teammate is another player on this player's team
    * @param field is the field the game is being played on
    * @return true if the <code>teammate</code> is successfully freed from jail, false otherwise 
    */
  public final boolean freeTeammate(Player teammate, Field field){
    return field.freeTeammate(this, teammate);
  }
  
  /** Informs this player that they have been freed by a teammate 
    * <p>
    * This method should only be called from within the Field class.  
    * 
    * @param teammate is the player that caught this player  
    * @param id should be the id of the this player
    */
  public void hasBeenFreed(Player teammate, int id){
    /* check if the caller knows this entity's id */
    if( this.id != id){
      throw new SecurityException("Unauthorized attempt to call hasBeenFreed ");
    }
    
  }
  
  
  
  /** attempt to pick up the opponent's flag
    * 
    * @param field is the field the game is being played on
    * @return true if this player successfully picked up the opponent's flag, false otherwise 
    */
  public final boolean pickUpFlag(Field field){
    return field.pickUpFlag(this);
  }
  
  
  /** Informs this player that they have picked up the flag
    * <p>
    * This method should only be called from with the Field class.  
    * 
    * @param id should be the id of the this player
    */
  public void hasPickedUpFlag(int id){
    /* check if the caller knows this entity's id */
    if( this.id != id ){
      throw new SecurityException("Unauthorized attempt to call hasPickedUpFlag ");
    }
    
  }
  
  /** Informs this player that they have dropped the flag
    * <p>
    * This method should only be called from within the Field class.  
    * 
    * @param id should be the id of the this player
    */
  public void hasDroppedFlag(int id){
    /* check if the caller knows this entity's id */
    if( this.id != id ){
      throw new SecurityException("Unauthorized attempt to call hasDroppedFlag ");
    }
    
  }
  
  
  /** attempt to win the game
    * 
    * @param field is the field the game is being played on
    * @return true if this player successfully brings the opponent's flag back to this player's base, false otherwise 
    */
  public final boolean winGame(Field field){
    return field.winGame(this);
  }
  
  //check if (depending on side) this player is on the opponents side or not.
  //returns boolean so it can be used in if statements.
  public boolean trespassing(Field f){
    if(this.side==1){
      if(this.x>f.bound-this.getSprite().getWidth()+10){
        return true;
      }
    }else{
      if(this.x<f.bound-10){
        return true;
      }
    }
    if(!flag){this.unhunt();}
    return false;
  }
  
  public int side; //player's side
  
  //storing friendly and enemy jails, flags and bases for easy access
  //initialized in constructor based on input side
  public Entity myJail;
  public Entity myFlag;
  public Entity myBase;
  
  public Entity enemyJail;
  public Entity enemyFlag;
  public Entity enemyBase;
  
  public boolean hunted=false; //whether or not the player is being hunted
  public boolean isHunted(){return hunted;} //returns hunted
  public void hunt(){this.hunted=true;} //sets hunted to true
  public void unhunt(){this.hunted=false;} //sets hunted to false
  
  public boolean jail=false; //whether or not the player is arrested/in jail
  public boolean inJail(){return jail;} //returns jail
  
  public boolean walkback = false; //whether or not the player is walking back to their side
  
  public void saved(){this.walkback=true;} //saving someone from jail (jail boolean remains true until reaching other side so that they are not chased)
  //method to stop hunting someone, send them to jail, and stop them from walking back
  //(all players can walkback, with and without jail being true)
  public void arrest(){
    this.jail=true;
    this.walkback=false;
    this.unhunt();
  }
  
  public boolean flag=false; //whether or not this player has the flag
  public boolean hasFlag(){return flag;} //returns flag
  //dropping flag (run only when on friendly side, and the flag teleports back to enemy base)
  public void dropFlag(Field f){
    ((Flag)this.enemyFlag).return2Base(f);
    ((Flag)this.enemyFlag).dropped();
    this.flag=false;
  }
  
  //when a player's job isn't able to be done (no trespassing enemies, no allies in enemy jail)
  //walk randomly around their own side
  public void randomWalk(Field f){
    this.stop=false; //reset edge flag
    
    //for side 1, the boundary is the upper limit
    if(this.side==1){
      if(this.x>f.bound-this.getSprite().getWidth() ||
         this.x<f.minX+1 ){this.speedX=-this.speedX;} //reflect when hitting boundary
      
      //for side 2, the boundary is the lower limit
    }else{
      if(this.x>f.maxX -this.getSprite().getWidth() || 
         this.x<f.bound){this.speedX=-this.speedX;} //reflect when hitting boundary
    }
    
    //no boundary int the vertical direction, except for field edges
    if(this.y >= f.maxY-(this.getSprite().getHeight()) || 
       this.y <= f.minY+2 ){this.speedY=-this.speedY;} //reflect speed vertically
  }
  
  //player goes to opposing team's jail (for arrests)
  public void toJail(Field f){
    int[] jPos={this.enemyJail.getX(),this.enemyJail.getY()}; //to hold position of the jail 
    
    //if the jail is reached, stop.
    if((this.x>=jPos[0]-1&& this.x<=jPos[0]+1) && (this.y>=jPos[1]-1 && this.y<=jPos[1]+6)){
      this.speedX=0;
      this.speedY=0;
    }else{
      //finding the vertical/horizontal distance and proportional speeds 
      //to beeline to target 
      double[] jDist={(double)jPos[0]-x,(double)jPos[1]-y}; //difference between x and y values 
      double    dist=Math.hypot(jDist[0],jDist[1]); //direct distance between jail and player 
      double    frac=0.25/dist; //coefficient to make total speed 0.5
      
      //System.out.println(fPos[0]); 
      //component speeds 
      this.speedX=jDist[0]*frac; 
      this.speedY=jDist[1]*frac; 
    }
  }
  
  //method to make a beeline to a target entity (overload for standard speed, or to go slightly faster
  public void go2Target(Entity target){this.go2Target(target,0.5);}
  
  public void go2Target(Entity target, double factor){
    //getting target's position
    int[] tPos={target.getX(),target.getY()};
    
    //finding the vertical/horizontal distance and proportional speeds 
    //to beeline to target 
    double[] tDist={(double)tPos[0]-x,(double)tPos[1]-y}; //difference between x and y values 
    double    dist=Math.hypot(tDist[0],tDist[1]); //direct distance between base and player 
    double    frac=(Math.random()*factor)/dist; //coefficient to make total speed result of random()*factor
    
    //component speeds 
    this.speedX=tDist[0]*frac; 
    this.speedY=tDist[1]*frac; 
  }
  
  //go back to a certain distance from base (runs with walkback boolean)
  public void goBack(){
    int[] bPos={this.myBase.getX(),this.myBase.getY()};
    
    //finding the vertical/horizontal distance and proportional speeds 
    //to beeline to target 
    double[] bDist={(double)bPos[0]-x,(double)bPos[1]-y}; //difference between x and y values 
    double    dist=Math.hypot(bDist[0],bDist[1]); //direct distance between base and player 
    
    if(dist<50){
      this.walkback=false;
      this.jail=false;
      this.speedX=Math.random()-0.5;
      this.speedY=Math.random()-0.5;
    }else{
      double    frac=0.5/dist; //coefficient to make total speed 0.5
      
      //component speeds 
      this.speedX=bDist[0]*frac; 
      this.speedY=bDist[1]*frac; 
    }
  }
}