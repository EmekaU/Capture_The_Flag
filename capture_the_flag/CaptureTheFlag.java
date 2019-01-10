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

public class CaptureTheFlag{
  
  
  public static void main(String[] args){
    
    Field f = new Field();
    f.START_FROM_BASE = true;
    System.out.println(f.minX + "," + f.minY);
    
    
    /* --------------------------------------------- */
    /* create players in the game                    */
//
//    Player p,q,c,d,e,
//        pp,qq,cc,dd,ee;
//    
//    int NUM_PLAYERS = 3;
//    
//    for(int i=0; i<NUM_PLAYERS; i+=1){
//      // create a player and register them with territory 1
//        
//        //Blue Team
//        p = new GuardF(f, 1, "Cat van Kittenish", 12, "blues", 'b', Math.random()*374+10, Math.random()*574+10);
//        q = new Seeker(f, 1, "Cat van Kittenish", 12, "blues", 'b', Math.random()*374+10, Math.random()*574+10);
//        d = new Hunter(f, 1, "Cat van Kittenish", 12, "blues", 'b', Math.random()*374+10, Math.random()*574+10);
//        e = new GuardB(f, 1, "Cat van Kittenish", 12, "blues", 'b', Math.random()*150+16, Math.random()*150+100);
//        c = new Saviour(f, 1, "Cat van Kittenish", 12, "blues", 'b', Math.random()*374+10, Math.random()*574+10);
//        
//        //Red Team
//        pp = new GuardF(f, 2, "Bunny El-Amin", 7, "reds", 'r', Math.random()*378+416, Math.random()*574+10);
//        qq = new Seeker(f, 2, "Bunny El-Amin", 7, "reds", 'r', Math.random()*378+416, Math.random()*574+10);
//        dd = new Hunter(f, 2, "Bunny El-Amin", 7, "reds", 'r', Math.random()*378+416, Math.random()*574+10);
//        ee = new GuardB(f, 2, "Bunny El-Amin", 7, "reds", 'r', Math.random()*100+650, Math.random()*150+300);
//        cc = new Saviour(f, 2, "Bunny El-Amin", 7, "reds", 'r', Math.random()*378+416, Math.random()*574+10);
//    }
    
    //pick up flag
//    Team bD1=new Team(f,1,0,0,0,0,1);
//    Team rD1=new Team(f,1,0,0,0,0,2);
    
    //catch and go to jail
//    Team bD2=new Team(f,1,0,0,0,0,1);
//    Team rD2=new Team(f,0,2,0,0,0,2);
    
    //freed from jail (drop flag)
//    Team bD3=new Team(f,0,0,0,0,2,1);
//    Team rD3=new Team(f,1,0,2,0,0,2);
    
    //one of everything
//    Team bD4=new Team(f,1,1,1,1,1,1);
//    Team rD4=new Team(f,1,1,1,1,1,2);
    
    //6 person teams
//    Team b6= new Team(f,6,1);
//    Team r6 = new Team(f,6,2);
    
    //12 person teams
    Team b12=new Team (f,12,1);
    Team r12=new Team (f,12,2);
    
    //50 person teams
//    Team b50 = new Team(f,50,1);
//    Team r50 = new Team(f,50,2);
    
    //500 person teams
//    Team b500 = new Team(f,500,1);
//    Team r500 = new Team(f,500,2);
    
//    reading a text file
//    Team demoBR=new Team(f,"gamefile.txt");
    
    
    
    
    /* ----------------------------------------- */
    /* play the game                               */
    
    boolean gameRunning = true;
    
    long iterations = 0;
    while( gameRunning ){
      iterations += 1;
      
      /* allow players to think about what to do and change directions */
      f.play();
      
      /* move all players */
      f.update();
      
      /* redraw all the players in their new positions */
      f.draw();
      
      
      /* give some message to display on the field */
      if(iterations < 100){
        f.view.message("game on!");
      }else if(iterations < 300){
        f.view.message("keeps on going...");
      }else{
        f.view.message("and going...");
      }
      
      // uncomment this if game is moving too fast , sleep for 10 ms
      try{ Thread.sleep(1); } catch(Exception e1) {}
      
      gameRunning = f.gameStillRunning();
    }
    
    
  }
  
}