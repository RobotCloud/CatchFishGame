package catchFish;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * 鱼的基类
 * 所有鱼的大小: 48,55,73,93,58,93,89,88,160,303,92
 */
public class Fish extends Thread {
    BufferedImage fishImg;
    Random random = new Random();
    // 存放鱼的所有动作
    BufferedImage[] allImg = new BufferedImage[10];
    // 鱼的位置
    int x = 0;
    int y = 0;
    // 鱼的速度
    int speed = 0;
    // 鱼的大小
    int width;
    int height;

    // 加载图片
    public void getImg(String fishName) throws IOException {
        // 将指定鱼的所有动作都添加到allImg中
        for (int i = 0; i < allImg.length; i++) {
            File file;
            if (i < 9) {
                file = new File("src/E/" + fishName + "_0" + (i + 1) + ".png");
            } else {
                file = new File("src/E/" + fishName + "_" + (i + 1) + ".png");
            }
            allImg[i] = ImageIO.read(file);
        }
        width = allImg[0].getWidth();
        height = allImg[0].getHeight();
        // 鱼的位置
        x = random.nextInt(Main.getSCREENWIDTH());
        y = random.nextInt(Main.getSCREENHEIGHT());
        // 鱼的速度
        speed = random.nextInt(7) + 3;
    }

    @Override
    public void run() {
        super.run();
        int index = 0;
        while (true) {
            // 每条鱼都不断重复不同动作，以达到动态效果
            //fishImg = allImg[index++%10];
            move(index++);
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            moving();
        }
    }

    public void move(int index) {
        fishImg = allImg[index % 10];
    }

    public void moving() {
        x -= speed;
        // 重置鱼的位置和速度
        if (x + fishImg.getWidth() <= 0) {
            removing();
        }
    }

    public void removing() {
        x = Main.getSCREENWIDTH();
        y = random.nextInt(Main.getSCREENHEIGHT());
        speed = random.nextInt(7) + 3;
    }

}
