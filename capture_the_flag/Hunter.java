import java.util.ArrayList;
import java.util.Arrays;


//Class for players that chase enemies who trespass onto their territory
//parent class for flag guard and base guards (only logic in update(f) are different)
public class Hunter extends Player{
  
  public ArrayList<Entity> opponents; //to iterate through members of opposite team
  public int target=0; //current target opponent
  public boolean hasTarget=false; //whether or not this hunter has a target
  public boolean escorting=false; //whether or not this hunter is escorting someone to jail
  
  public Hunter(Field f, int side, String name, int number, String team, char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y); //constructor
    
    //importing opposing team depending on side
    if(side==1){
      this.opponents=f.getTeam2();
    }else{
      opponents=f.getTeam1();
    }
    
    //Set initial speed
    this.speedX=Math.random()-0.5;
    this.speedY=Math.random()-0.5;
  }
  
  public void play(Field f){
    //if walkback is true (with either true or false jail) return to your side
    //[walkback & jail means you were in jail, and get a free walk back where nobody will chase you]
    if(this.walkback){this.goBack();}
    //if not walkback, and not in jail
    else if(!this.jail){
      //if escorting someone to jail, run escort method
      if(this.escorting){this.escort(f);}
      else{
        //if this hunter has a target, chase them
        if(this.hasTarget){this.go2Target(this.opponents.get(this.target));}
        //if not in jail and has no target, walk randomly on home territory
        else{this.randomWalk (f);}
      }
      //if in jail and not walkback, then go to jail
    }else{this.toJail(f);}
  }
  
  public void update(Field f){
    //if hunter has a garget
    if(this.hasTarget){
      
      //check if target is out of your territory, or is in jail.
      if(!((Player)this.opponents.get(this.target)).trespassing(f) ||
         ((Player)this.opponents.get(this.target)).inJail()        ){
        
        //if so, stop chasing them, and initiate a random speed
        //to walk around home territory
        this.speedX=Math.random()-0.5; 
        this.speedY=Math.random()-0.5;
        this.target=0;
        this.hasTarget=false;
        ((Player)this.opponents.get(this.target)).unhunt();
      }
      
      //if you catch your opponent
      if(f.catchOpponent((Player)this,(Player)this.opponents.get(this.target))){
        
        //if enemy is trespassing, arrest them, and bring them to jail
        if(((Player)this.opponents.get(this.target)).trespassing(f)){
          ((Player)this.opponents.get(this.target)).arrest();
          
          //if they are not trespassing, return to your side, then have the flag drop (go back to base)
        }else if(((Player)this.opponents.get(this.target)).hasFlag()){
          ((Player)this.opponents.get(this.target)).dropFlag(f);
        }
        
        this.hasTarget=false; //remove target
        this.escorting=true; //escort target
      }
      
    }else{//if hunter has no target
      
      //iterate through opponents (imported from constructor)
      for(int p=0;p<this.opponents.size();p+=1){
        
        //if the player is trespassing, not in jail,
        //and not already being chased by an ally
        if(((Player)this.opponents.get(p)).trespassing(f) &&
           !((Player)this.opponents.get(p)).inJail()      &&
           !((Player)this.opponents.get(p)).isHunted()    ){
          
          //make them this hunter's target
          this.target=p;
          ((Player)this.opponents.get(p)).hunt();
          this.hasTarget=true;
          break; //don't iterate through any more players once condition has been met
        }
      }
    }
  }
  
  //method to bring player to jail (similar code as go2Target() in player, but uses static speed (0.25)
  public void escort(Field f){
    int[] jPos={this.myJail.getX(),this.myJail.getY()};
    
    //finding the vertical/horizontal distance and proportional speeds
    //to beeline to target
    double[] jDist={jPos[0]-this.x, jPos[1]-this.y}; //difference between coordinates
    double    dist=Math.hypot(jDist[0],jDist[1]); //direct distance between
    double    frac=0.25/dist; //coefficient to make total speed 0.25
    
    //component speeds
    this.speedX=jDist[0]*frac; 
    this.speedY=jDist[1]*frac;
    
    //(this.x>=jPos[0]-1&& this.x<=jPos[0]+1) && (this.y>=jPos[1]-1 && this.y<=jPos[1]+6)
    //dist <= Field.ARMS_LENGTH
    
    if((this.x>=jPos[0]-1&& this.x<=jPos[0]+1) && (this.y>=jPos[1]-1 && this.y<=jPos[1]+6)){
      this.escorting=false;
      this.speedX=Math.random()-0.5; 
      this.speedY=Math.random()-0.5;
      ((Jail)myJail).addPrisoner(((Player)this.opponents.get(this.target)));
      //System.out.println(((Jail)myJail).numPrisoners());
    }
    
  }
  
}