package catchFish;

import javax.swing.*;
import java.io.IOException;

public class Main {

    private static final int SCREENWIDTH = 1300;
    private static final int SCREENHEIGHT = 900;

    public static int getSCREENWIDTH() {
        return SCREENWIDTH;
    }

    public static int getSCREENHEIGHT() {
        return SCREENHEIGHT;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Main main = new Main();
        JFrame jFrame = new JFrame();
        jFrame.setTitle("捕鱼达人-终极版");
        jFrame.setSize(SCREENWIDTH, SCREENHEIGHT);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Pool pool = new Pool();
        Thread poolThread = new Thread(pool, "pool线程");

        jFrame.add(pool);
        jFrame.setVisible(true);

        poolThread.start();
    }

}
