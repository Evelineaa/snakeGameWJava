import javax.swing.*; //lisätään projektiin "hyppyikkuna"

public class App {
    public static void main(String[] args) throws Exception {
    int boardWidth = 600;  //Määritellään ikkunan koko
    int boardHeight = boardWidth;

    JFrame frame = new JFrame("Snake");  //Ikkunan nimi
    frame.setVisible(true);                //Laittaa ikkunan näkyväksi
    frame.setSize(boardWidth, boardHeight); //Asettaa rajat ikkunalle käyttäen annettuja määritteitä
    frame.setLocationRelativeTo(null);  
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sulkee projektin, kun käyttäjä klikkaa x

    SnakeGame snakeGame = new SnakeGame (boardWidth, boardHeight);
    frame.add(snakeGame);
    frame.pack();
    snakeGame.requestFocus(); //Peli kuuntelee näppäimistöä

    
    }
}
