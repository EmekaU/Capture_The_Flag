import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class View extends Canvas{
  /** The strategy that allows us to use accelerate page flipping */
  private BufferStrategy strategy;
  
  /** True if the game is currently "running", i.e. the game loop is looping */
  private boolean gameRunning = true;
  
  /** message to be displayed on the screen */
  protected String message = "";
  
  /** the actual graphics object */
  Graphics2D g;
  
  /** extremal coordinates of the field itself */
  protected int minX, minY, maxX, maxY;
  
  /**
   * Construct our game and set it running.
   */
  public View(int minX, int minY, int maxX, int maxY) {
    // set the field corner points
    this.minX = minX; this.minY = minY; this.maxX = maxX; this.maxY = maxY;
    
    // create a frame to contain our game
    JFrame container = new JFrame("Capture the Flag!");
    
    // get the content of the frame and set up the resolution of the game
    JPanel panel = (JPanel) container.getContentPane();
    panel.setPreferredSize(new Dimension(maxX + 10,maxY + 10 + 35));
    panel.setLayout(null);
    
    // set up our canvas size and put it into the content of the frame
    setBounds(0,0,maxX + 10,maxY + 10 + 35);
    panel.add(this);
    
    // Tell AWT not to bother repainting our canvas since we're
    // going to do that our self in accelerated mode
    setIgnoreRepaint(true);
    
    // finally make the window visible 
    container.pack();
    container.setResizable(false);
    container.setVisible(true);
    
    // add a listener to respond to the user closing the window. If they
    // do we'd like to exit the game
    container.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    
    
    // create the buffering strategy which will allow AWT
    // to manage our accelerated graphics
    createBufferStrategy(2);
    strategy = getBufferStrategy();
    
    g = (Graphics2D) strategy.getDrawGraphics();
    
    // draw field
    g.setColor(Color.green);
    g.fillRect(minX,minY,maxX-minX,maxY-minY);
    
    // draw message box
    g.setColor(Color.white);
    g.fillRect(minX, maxY + 10, maxX-minX, 25);
    
    // draw dividing line for field
    g.setColor(Color.black);
    g.drawString(message, minX + 10, maxY + 25);
    
    // draw everything
    strategy.show();
    
  }
  
  /** sets the display message 
    * 
    * @params message is the message to display
    */
  public void message(String message){
    this.message = message;
  }
  
  /** update graphics of field and message box*/
  public void update(){
    g = (Graphics2D) strategy.getDrawGraphics();
    g.setColor(Color.green);
    g.fillRect(minX,minY,maxX-minX,maxY-minY);
    
    g.setColor(Color.black);
    g.drawLine((maxX+minX)/2,minY, (maxX+minX)/2, maxY);
    
    g.setColor(Color.white);
    g.fillRect(minX, maxY + 10, maxX-minX, 25);
    g.setColor(Color.black);
    g.drawString(message, minX + 10, maxY + 25);
    
  }
  
  /** draw all entities on the field
    * 
    * @param team1 is all the entities of team 1 
    * @param team2 is all the entities of team 2 
    * @parsm things are the bases, jails and flags on the field
    */
  public void draw(ArrayList<Entity> team1, ArrayList<Entity> team2, ArrayList<Entity> things){
    for(Entity player: team1){
      player.getSprite().draw(g, (int) player.getX(),  (int) player.getY());
    }
    
    for(Entity player: team2){
      player.getSprite().draw(g, (int) player.getX(), (int) player.getY());
    }
    
    for(Entity thing: things){
      thing.getSprite().draw(g, (int) thing.getX(), (int) thing.getY());
    }
    
    g.dispose();
    strategy.show();
  }
  
  
  public static void main(String[] args){
    
    View v = new View(10,10,800,600);
    
    boolean game_on = true;  // game keeps playing when game_on is true
    String log     = "";    // output trace of game as it progresses
    int    tick    = 0;     // count units of "time" 
    
    
    Field field = new Field();
    
    //   Team redTeam  = new Team("red");
    //   Team blueTeam = new Team("blue");
    
    //   redTeam.init(field);
    //   blueTeam.init(field);
    
    while( game_on ){
      tick += 1;
      
      /* update players on their own territory */
      //     redTeam.updateHome(field, blueTeam);
      //     blueTeam.updateHome(field, redTeam);
      
      /* update players on opposing team's territory */
      //     redTeam.updateAway(field, blueTeam);
      //     blueTeam.updateAway(field, redTeam);
      
      /* update field (with locations of all players) */
      //     log     = field.update(redTeam, blueTeam);
      
      /* check if a team has won the game */
      //     game_on = field.gameOn();
      
      /* refresh the VIEW of the game */
      //     field.refresh();  
      
      /* output log to standard error */
      System.err.println("Time " + tick);
//      System.err.println(log);
      
    }
    
    
    
    
  }
  
}