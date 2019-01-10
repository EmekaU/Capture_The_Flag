//Class for players that save people from jail.
public class Saviour extends Player{
  
  public static int saving=0; //static variable for the total number of players saving allies trapped in jail
  
  public boolean save=false; //whether or not this player is saving someone
  
  public Field f;
  
  public Saviour(Field f, int side, String name, int number, String team, char symbol, double x, double y){ 
    super(f, side, name, number, team, symbol, x, y); 
    this.speedX=Math.random()-0.5; 
    this.speedY=Math.random()-0.5;
  }
  
  public void go2Jail(){
    int[] jPos={this.enemyJail.getX(),this.enemyJail.getY()}; //to hold position of the jail 
    
    //finding the vertical/horizontal distance and proportional speeds 
    //to beeline to target 
    double[] jDist={(double)jPos[0]-x,(double)jPos[1]-y}; //difference between x and y values 
    double    dist=Math.sqrt(Math.pow(jDist[0],2)+Math.pow(jDist[1],2)); //direct distance between jail and player 
    double    frac=Math.random()*.2/dist; //coefficient to make total speed result of random() 
    
    //component speeds 
    this.speedX=jDist[0]*frac; 
    this.speedY=jDist[1]*frac;  
  }
  
  public void play(Field f){
    if(!jail){ //if not in jail
      
      //if saving someone or (the number of people saving people from jail is less than the number of people in jail, and not walking back)
      if(this.save || ((((Jail)this.enemyJail).numPrisoners()-this.saving)>0 && !this.walkback)){
        
        //if save is false then number of players in jail is more than number of people saving
        if(!this.save){
          this.saving+=1; //increase number players saving allies
          this.save=true; //this player is now saving someone
        }
        this.go2Target(this.enemyJail,0.75); //go to the jail to save ally
        
        //if close enough to jail
        if(Math.hypot(this.getX()-this.enemyJail.getX(),this.getY()-this.enemyJail.getY())<=Field.ARMS_LENGTH){
          Player prisoner=((Jail)enemyJail).breakOut(); //free prisoner
          this.saving-=1; //decrease number of people currently saving allies
          this.save=false; //this is not saving anyone anymore
          prisoner.walkback=true; //the prisoner walks back
          this.walkback=true; //this player brings prisoner back
        }
        
      }else if(!this.walkback){this.randomWalk(f);} //if not walking back, randomwalk
      else                    {this.goBack();} //else walkback
    }else if(this.walkback){this.goBack();} //walk back
    
    //if in jail
    else{
      this.toJail(f); //go to jail
      
      //if saving someone from jail when jail is true, stop that, and decrease static int saving
      if(this.save){
        this.save=false;
        this.saving-=1;
      }
    }
    
  }
  
  public void update(Field f){
    
  }
}