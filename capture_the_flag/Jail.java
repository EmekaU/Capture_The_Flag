import java.util.LinkedList; 

public class Jail extends Entity{
  
  //linked list to contain all prisoners within jail
  public LinkedList<Player> prisoners=new LinkedList<Player>();
  
  //returns the number of prisoners
  public int numPrisoners(){return this.prisoners.size();}
  
  //adds a prisoner to jail, and ensures their jail and walkback booleans are proper
  public void addPrisoner(Player a){
    a.jail=true;
    a.walkback=false;
    this.prisoners.add(a);
  }
  
  //removes a player from jail, and returns them
  public Player breakOut(){return this.prisoners.removeFirst();}
  
  /* no logic for the jail yet */
  @Override
  public void play(Field field){}
  
  
  /* jails does not move yet */
  @Override
  public void update(Field field){}
  
  
  
  @Override
  public boolean equals(Object o){
    if(this==o){return true;}
    
    if(o == null) return false;
    
    /* jails are the same if they have the same symbol */
    if(o instanceof Jail && this.getSymbol() == ((Jail)o).getSymbol()){
      return true;
    }
    
    return false;
  }
  
  public Jail(char symbol, int x, int y){
    super(symbol, x, y);
    speedX = speedY = 0;
  }
  
  public Jail(Field f, int side, char symbol, int x, int y, String ref){
    super(f, side, symbol, x, y, ref);
    speedX = speedY = 0;
  }
  
}