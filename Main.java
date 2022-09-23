import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*class Listener implements KeyListener{

}*/

public class Main extends JPanel implements KeyListener{
   int sqSize = 50;
   static int enemies = 12;
   static int fences = 12;
   static int size = 12;
   static Game game;
   static Main  mainPanel = new Main();
   JLabel label;

   Image player;
   Image enemy;
   Image fence;
   /*int w = player.getWidth(null);
   int h = player.getHeight(null);
   BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
   Graphics g_player = bi.getGraphics();*/
  /* w = enemy.getWidth(null);
   h = enemy.getHeight(null);
   BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
   Graphics g_enemy = bi.getGraphics();*/

   //BufferedImage fence = ImageIO.read(new File("fence.jpg"));
   /*w = fence.getWidth(null);
   h = fence.getHeight(null);
   BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
   Graphics g_fence = bi.getGraphics();*/

   //BufferedImage enemy = new 

   public Main(){
      this.setFocusable(true);
      addKeyListener(this);
      Toolkit t=Toolkit.getDefaultToolkit();  
      enemy = t.getImage("enemy.png");  
      player = t.getImage("player.png");
      fence = t.getImage("fence.png");
   }

   public void keyTyped(KeyEvent e){

   }

   public void keyPressed(KeyEvent e){
      System.out.println(e.getKeyCode());
      if(e.getKeyCode() == 87){ // w
         System.out.println("mememe");
         game.Update(0, -1);
         repaint();
      }
      if(e.getKeyCode() == 83){ //s
         System.out.println("mememe");
         game.Update(0, 0);
         repaint();
      }
      if(e.getKeyCode() == 68){ // d
         System.out.println("mememe");
         game.Update(1, 0);
         repaint();
      }
      if(e.getKeyCode() == 65){ // a
         System.out.println("mememe");
         game.Update(-1, 0);
         repaint();
      }
      if(e.getKeyCode() == 88){ //x
         game.Update(0, 1);
         repaint();
      }
      if(e.getKeyCode() == 90){ // z
         game.Update(-1, 1);
         repaint();
      }
      if(e.getKeyCode() == 67){ // c
         game.Update(1, 1);
         repaint();
      }
      if(e.getKeyCode() == 81){ // q
         game.Update(-1, -1);
         repaint();
      }
      if(e.getKeyCode() == 69){ // e
         game.Update(1, -1);
         repaint();
      }

      if(e.getKeyCode() == 74){
         game.Jump();
         repaint();
      }

      if(e.getKeyCode() == 82){ //r
         game = new Game(size, enemies, fences);
      }

      if(e.getKeyCode() == 85){ //u
         game.Undo();
         repaint();
      }
   }

   public void keyReleased(KeyEvent e){
      
   }

   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      //System.out.println("alsdnf");
      g.setColor(new Color(255, 255, 255));
      g.drawRect(0, 0, 600, 600);
      for(int i = 0; i < size; i++){
        for(int j = 0; j < size; j++){
            if(game.grid[i][j] == 0){
                g.setColor(new Color(0, 0, 0));
                g.drawRect(i*sqSize, j*sqSize, sqSize, sqSize);
            } else if(game.grid[i][j] == 1){
                g.setColor(new Color(0, 255, 0));
                //g.fillRect(i*sqSize, j*sqSize, sqSize, sqSize);
                g.drawImage(player, i*sqSize, j*sqSize, this);
            } else if(game.grid[i][j] == 2){
                g.setColor(new Color(255, 0, 0));
                //g.fillRect(i*sqSize, j*sqSize, sqSize, sqSize);
                g.drawImage(enemy, i*sqSize, j*sqSize, this);
            } else if(game.grid[i][j] == 3){
                g.setColor(new Color(0, 0, 0));
                //g.fillRect(i*sqSize, j*sqSize, sqSize, sqSize);
                g.drawImage(fence, i*sqSize, j*sqSize, this);
            }
        }
      }

	  int xiaopingguo = 0;
	  for(int i = 0; i < size; i++){
	    for (int j = 0; j < size; j++) {
	        if (game.grid[i][j] != game.startgrid[i][j]) {
	          //g.setColor(new Color(0, 0, 0));
	          //g.drawRect(i * sqSize, j * sqSize, sqSize, sqSize);
	        xiaopingguo = xiaopingguo + 1;
	        }
		}
	  }
    if (xiaopingguo <= 1){
    g.setColor(new Color(0, 255, 0));
    g.fillRect(0, 0, sqSize*size, sqSize*size);
    g.setColor(new Color(0, 0, 0));
    g.drawString("YOU WIN", 200, 200);
    }

      if(game.game_over){
         g.setColor(new Color(255, 0, 0));
         g.fillRect(0, 0, sqSize*size, sqSize*size);
         g.setColor(new Color(0, 0, 0));
         g.drawString("GAME OVER", 200, 200);
      } /*else if(game.winThing){
         g.setColor(new Color(255, 0, 0));
         g.fillRect(0, 0, sqSize*size, sqSize*size);
         g.setColor(new Color(0, 0, 0));
         g.drawString("YOU WIN", 200, 200);
      }*/
      //g.drawRect(10, 10, 100, 100);
      repaint();
   }

   @Override
   public Dimension getPreferredSize() {
      // so that our GUI is big enough
      return new Dimension(50*size, 50*size);
   }

   // create the GUI explicitly on the Swing event thread
   private static void createAndShowGui() {
      JFrame frame = new JFrame("DrawRect");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(mainPanel);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
   }

   public static void main(String[] args) {;
      Scanner input = new Scanner(System.in);
      System.out.println("size: ");
      size = input.nextInt();
      //Scanner input = new Scanner(System.in);
      System.out.println("enemies: ");
      enemies = input.nextInt();
      //Scanner input = new Scanner(System.in);
      System.out.println("fences: ");
      fences = input.nextInt();
      
      game = new Game(size, enemies, fences);
      
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}