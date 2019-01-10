public class Flag extends Entity{
  
  /* flag does not have any logic yet */
  @Override
  public void play(Field field){}
  
  /* flag does not move yet */
  @Override
  public void update(Field field){}
  
  public int side;
  
  
  @Override
  public boolean equals(Object o){
    if(this == o){return true;}
    
    if(o == null){ 
      System.err.println("null equals");
      return false;  
    }
    
    /* flags are the same if they have the same symbol */
    if(o instanceof Flag && this.getSymbol() == ((Flag)o).getSymbol()){
      return true;
    }
    
    return false;
  }
  
  
  public Flag(char symbol, int x, int y){
    super(symbol, x, y);
    speedX = speedY = 0;
  }
  
  public Flag(Field f, int side, char symbol, int x, int y, String ref){
    super(f, side, symbol, x, y, ref);
    speedX = speedY = 0;
    this.side=side;
  }
  
  
  public boolean caught=false; //whether or not the flag is caught
  public boolean isCaught(){return this.caught;} //method to check above value
  public void captured(){this.caught=true;} //sets caught to true when player catches it
  public void dropped(){ //if player is caught, then turn caught false, and stop in places
    this.caught=false;
    this.speedX=0;
    this.speedY=0;
  }
  
  //when caught on opponents side, teleports back to base
  public void return2Base(Field f){
    int[] bPos;
    if(side==1){
      bPos=f.getBase1Position();
      this.x=bPos[0]+5;
      this.y=bPos[1]-8;
    }else{
      bPos=f.getBase2Position();
      this.x=bPos[0]+5;
      this.y=bPos[1]-8;
    }
  }
  
  
  //set flag's speed to that of input player
  //(allows for the flag to move when captured)
  public void follow(Player a){
    this.speedX = a.getSpeedX(); 
    this.speedY = a.getSpeedY();
  }
  
}