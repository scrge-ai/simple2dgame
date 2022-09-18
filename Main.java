import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/*class Listener implements KeyListener{

}*/

public class Main extends JPanel implements KeyListener{
   int sqSize = 20;
   int size = 12;
   int enemies = 12;
   int fences = 12;
   Game game = new Game(size, enemies, fences);
   static Main  mainPanel = new Main();
   JLabel label;

   public Main(){
      this.setFocusable(true);
      addKeyListener(this);
   }

   public void keyTyped(KeyEvent e){
      System.out.println("u");
      if(e.getKeyCode() == 87){
         
         game.Update(1, 0);
      }
   }

   public void keyPressed(KeyEvent e){
    //call repaint here
   }

   public void keyReleased(KeyEvent e){
      
   }

   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g.setColor(new Color(255, 255, 255));
      g.drawRect(0, 0, 600, 600);
      for(int i = 0; i < size; i++){
        for(int j = 0; j < size; j++){
            if(game.grid[i][j] == 0){
                g.setColor(new Color(0, 0, 0));
                g.drawRect(i*sqSize, j*sqSize, sqSize, sqSize);
            } else if(game.grid[i][j] == 1){
                g.setColor(new Color(0, 255, 0));
                g.fillRect(i*sqSize, j*sqSize, sqSize, sqSize);
            } else if(game.grid[i][j] == 2){
                g.setColor(new Color(255, 0, 0));
                g.fillRect(i*sqSize, j*sqSize, sqSize, sqSize);
            } else if(game.grid[i][j] == 3){
                g.setColor(new Color(0, 0, 0));
                g.fillRect(i*sqSize, j*sqSize, sqSize, sqSize);
            }
        }
      }
      //g.drawRect(10, 10, 100, 100);
      repaint();
   }

   @Override
   public Dimension getPreferredSize() {
      // so that our GUI is big enough
      return new Dimension(600, 600);
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
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}