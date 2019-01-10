

public class Base extends Entity{
  
  @Override
  public void play(Field field){}
  
  @Override
  public void update(Field field){}
  
  @Override
  public boolean equals(Object o){
    if(o == null) return false;
    if(o instanceof Base && this.getSymbol() == ((Base)o).getSymbol()){
      return true;
    }
    return false;
  }
  
  
  public Base(char symbol, int x, int y){
    super(symbol, x, y);
    speedX = speedY = 0;
  }
  
  public Base(Field f, int side, char symbol, int x, int y, String ref){
    super(f, side, symbol, x, y, ref);
    speedX = speedY = 0;
  }
}