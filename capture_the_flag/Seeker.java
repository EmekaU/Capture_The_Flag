
/* Class for a player in Capture the Flag that 
 * goes straight to the flag, and stops */
public class Seeker extends Player{ 
  
  public int side; 
  public Field f;
  
  //constructor 
  public Seeker(Field f, int side, String name, int number, String team, char symbol, double x, double y){ 
    super(f, side, name, number, team, symbol, x, y); 
    this.side=side; 
    this.f=f;
  }
  
  public void play(Field f){
    
    //if walkback, go back to base
    if(this.walkback){this.goBack();}
    
    //if not walkback, if not in jail
    else if(!jail){
      
      //not in possession of the flag and if the flag isn't caught
      if(!this.flag && !((Flag)this.enemyFlag).isCaught()){
        this.go2Target(this.enemyFlag,.75); //go to the flag
        
        //if not in posession of the flag, and the flag is caught
      }else if(!this.flag && ((Flag)this.enemyFlag).isCaught()){
        
        //if trespassing, walkback
        if(this.trespassing(f)){
          this.walkback=true;
          this.goBack();
          
          //if not trespassing randomWalk
        }else{this.randomWalk(f);}
        
        //if in possession of the flag, have the flag follow
      }else if(this.flag){((Flag)this.enemyFlag).follow(this);}
      
      //if jail is tru, go to jail, drop flag
    }else{
      this.toJail(f);
      if(this.flag){
        ((Flag)this.enemyFlag).dropped();
        this.flag=false;
      }
    }
  }
  
  public void update(Field f){
    
    //if able to pick up flag, and the flag isn't already caught, pick it up
    if(this.pickUpFlag(f) && !((Flag)this.enemyFlag).isCaught()){
      
      this.flag = true;//this player has the flag
      go2Target(this.myBase,0.5);//go back to the base
      ((Flag)this.enemyFlag).captured();//capture flag
    }
    
    //if win conditions are met stop, and win game
    if(f.winGame(this)){ 
      this.speedX=0; 
      this.speedY=0; 
    }
  } 
}