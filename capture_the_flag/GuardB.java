//class to guard the base
//extends Hunter so that play, escort, constructors and attributes of hunter can be used
public class GuardB extends Hunter{
  
  public GuardB(Field f, int side, String name, int number, String team, char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y); //constructor
  }
  
  //overrides Hunter's update method for changes in logic (noted in comments)
  @Override
  public void update(Field f){
    if(this.hasTarget){
      
      //if they are not trespassing, in jail, not within 100 of the flag
      if(!((Player)this.opponents.get(this.target)).trespassing(f)            ||
         ((Player)this.opponents.get(this.target)).inJail()                   ||
         Math.hypot(this.myBase.getX()-this.opponents.get(this.target).getX(),
                    this.myBase.getY()-this.opponents.get(this.target).getY()) > 200 ){
        
        //stop chasing
        this.speedX=Math.random()-0.5; 
        this.speedY=Math.random()-0.5;
        this.target=0;
        this.hasTarget=false;
        ((Player)this.opponents.get(this.target)).unhunt();
      }
      if(f.catchOpponent((Player)this,(Player)this.opponents.get(this.target))){
        if(((Player)this.opponents.get(this.target)).trespassing(f)){
          ((Player)this.opponents.get(this.target)).arrest();
        }else if(((Player)this.opponents.get(this.target)).hasFlag()){
          ((Flag)this.myFlag).return2Base(f);
          ((Flag)this.myFlag).dropped();
          ((Player)this.opponents.get(this.target)).flag=false;
          
        }
        this.hasTarget=false;
        this.escorting=true; 
      }
    }else{
      for(int p=0;p<this.opponents.size();p+=1){
        
        //if they are trespassing, not in jail, not being hunted, within a certain distance of the flag
        if(((Player)this.opponents.get(p)).trespassing(f)                   &&
           !((Player)this.opponents.get(p)).inJail()                        &&
           !((Player)this.opponents.get(p)).isHunted()                      &&
           Math.hypot(this.myBase.getX()-this.opponents.get(p).getX(),
                      this.myBase.getY()-this.opponents.get(p).getY()) < 200 ){
          
          //chase them
          this.target=p;
          ((Player)this.opponents.get(p)).hunt();
          this.hasTarget=true;
          break;
        }
      }
    }
  }
}