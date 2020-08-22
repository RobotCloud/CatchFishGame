package catchFish;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * 捕鱼网
 */
public class Net {
    BufferedImage netImg;
    Random random = new Random();
    int x = 0;
    int y = 0;
    int width;
    int height;

    public void getImg() throws IOException {
        File file = new File("src/net09.png");
        netImg = ImageIO.read(file);
        x = random.nextInt(Main.getSCREENWIDTH());
        y = random.nextInt(Main.getSCREENHEIGHT());
        width = netImg.getWidth() + 100;
        height = netImg.getHeight() + 100;
    }
}
