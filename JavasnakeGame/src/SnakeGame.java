import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;  //käytetään käärmeen ruuan asettamiseen peliin randomisti
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25; //Määritellään "kuutiot" näytölle

    Tile Head;
    ArrayList<SnakeGame.Tile> Body;
    Tile Apple;

    Random random;

    //peli logiikka
    Timer gameLoop;
    int velocityX;
    int velocityY;

    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.ORANGE);
        addKeyListener(this);
        setFocusable(true);

        Head = new Tile (5,5);
        Body = new ArrayList<Tile>();
        Apple = new Tile (10,10);

        random= new Random();
        placeApple();

        gameLoop = new Timer(100, this);
        gameLoop.start();

        velocityX = 0; //jos -1 menee vasemmalle
        velocityY = 0;  //jos 1, käärme menee alaspäin

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g) {
        //käärme on tällainen

        for (int i = 0; i < boardWidth/tileSize; i++) {
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        }

        g.setColor(Color.red);  //Käärmeen väri
        g.fillRect(Apple.x * tileSize, Apple.y * tileSize, tileSize, tileSize); //määritellään koko

        g.setColor(Color.black);  //Käärmeen väri
        g.fillRect(Head.x * tileSize, Head.y * tileSize, tileSize, tileSize); //määritellään koko

        for (int i = 0; i < Body.size(); i++) {
            Tile snakePart = Body.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize );
        }

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game over: " + String.valueOf(Body.size()), tileSize -16, tileSize);
        }
        else {
            g.drawString("Score: " + String.valueOf(Body.size()), tileSize -16, tileSize);
        }


    }
    
    public void placeApple() {
        Apple.x =random.nextInt(boardWidth/tileSize);  //x vaihtuu random ominaisuuden avulla
        Apple.y =random.nextInt(boardHeight/tileSize); //y vaihtuu random ominaisuuden avulla
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        if (collision(Head, Apple)) { //jos tulee törmäys pään ja omenan välillä
            Body.add(new Tile (Apple.x, Apple.y)); // Lisää kehoon uusi "osa"
            placeApple(); //Lisää randomisti uusi omena
        }

        for(int i = Body.size()-1; i >= 0; i--) {
            Tile snakePart = Body.get(i);
            if (i == 0) {
                snakePart.x = Head.x;
                snakePart.y = Head.y;
            }
            else {
                Tile prevSnakePart = Body.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        Head.x += velocityX;
        Head.y += velocityY;

        for(int i = 0; i < Body.size(); i++){
            Tile snakePart = Body.get(i);

            if (collision(Head, snakePart)) {
                gameOver = true;
            }
        }

        if (Head.x*tileSize < 0 || Head.x*tileSize > boardWidth || Head.y*tileSize < 0 || Head.y*tileSize > boardHeight) {
        gameOver = true;
        System.out.println("Game over");
    }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move(); 
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }
    
    //Määritetään näppäimet
      @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
        velocityX = 0;
        velocityY = -1;
      }

     else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
        velocityX = 0;
        velocityY = 1;
      }
     else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
        velocityX = -1;
        velocityY = 0;
      }
     else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
        velocityX = 1;
        velocityY = 0;
      }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
