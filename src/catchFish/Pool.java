package catchFish;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

/**
 * 池塘类
 */
public class Pool extends JPanel implements Runnable {

    BufferedImage poolImg;
    ArrayList<Fish> allFish = new ArrayList<>();
    Net net;

    public static int getGrade() {
        return grade;
    }

    private static int grade = 0;

    public static void setIsRun(boolean isRun) {
        Pool.isRun = isRun;
    }

    private static boolean isRun = true;

    public Pool() throws IOException, InterruptedException {

        getImg();

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Pool pool = new Pool();
    }

    // 读取图片
    public void getImg() throws IOException {
        // 读取背景图
        File file = new File("src/bg.jpg");
        poolImg = ImageIO.read(file);
        // 读取网的图片
        net = new Net();
        net.getImg();
        // 读取鱼，每种鱼有三条
        for (int i = 0; i < 9; i++) {
            Fish fish1 = new Fish();
            Fish fish2 = new Fish();
            Fish fish3 = new Fish();
            fish1.getImg("fish0" + (i + 1));
            fish2.getImg("fish0" + (i + 1));
            fish3.getImg("fish0" + (i + 1));
            allFish.add(fish1);
            allFish.add(fish2);
            allFish.add(fish3);
        }
        // 特殊的两种鱼，每种各一条
        Fish fish4 = new Fish();
        Fish fish5 = new Fish();
        fish4.getImg("fish13");
        fish5.getImg("fish14");
        allFish.add(fish4);
        allFish.add(fish5);
    }

    @Override
    public void paint(Graphics g) { // 将图片画出来
        super.paint(g);
        if (isRun) {
            g.drawImage(poolImg, 0, 0, Main.getSCREENWIDTH(), Main.getSCREENHEIGHT(), null);
            for (Fish fish : allFish) {
                g.drawImage(fish.fishImg, fish.x, fish.y, null);
            }
            g.drawImage(net.netImg, net.x, net.y, net.width, net.height, null);
            // 得分
            g.setFont(new Font("宋体", Font.BOLD, 50));
            g.setColor(Color.RED);
            g.drawString("当前得分:" + grade, 30, 50);
        } else {
            System.out.println("stop");
        }
    }

    // 抓鱼
    private void catchFish(Point netPoint) {
        for (Fish fish : allFish) {
            Point fishPoint = new Point(fish.x, fish.y);
            // 当网和鱼的距离小于网的半径，则可以抓到鱼
            if ((int) netPoint.distance(fishPoint) < net.width / 2) {
                updateGrade(fish);
                // 让当前鱼重新游（相当于抓走了）
                fish.removing();
            }
        }
    }

    // 更新得分
    // 所有鱼的大小: 48,55,73,93,58,93,89,88,160,303,92
    private void updateGrade(Fish fish) {
        if (fish.width >= 0 && fish.width <= 80) {
            grade += 50;
        } else if (fish.width > 80 && fish.width <= 100) {
            grade += 100;
        } else {
            grade += 200;
        }
    }

    public void reStart() throws IOException {
        grade = 0;
//        allFish.clear();
//        getImg();
        repaint();
    }

    public void isWin() throws IOException {
        if (grade >= 1000) {
            String result = JOptionPane.showInputDialog(this, "请留下您的名字", "输入用户名", 1);
            System.out.println(result);
            if (result != null) {
                MySQL mySQL = new MySQL();
                User user = new User();
                user.setName(result);
                user.setGrade(Pool.getGrade());
                user.setDate(new Date(System.currentTimeMillis()));
                mySQL.insert(user);
//                mySQL.query();
            }
            hintRestart();
        }
    }

    public void hintRestart() throws IOException {
        int res = JOptionPane.showConfirmDialog(null, "您想再玩一次吗？", "再玩一次？", JOptionPane.YES_NO_OPTION);
        if (res == 0) {
            reStart();
        } else {
            System.exit(0);
        }
    }

    @Override
    public void run() {
        // 开启每条鱼的线程
        for (Fish fish : allFish) {
            fish.start();
        }

        // 创建鼠标监听器
        MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                // 当鼠标移除窗口时，网消失
                net.x = 2 * Main.getSCREENWIDTH();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();
                // 创建网的点
                Point netPoint = new Point(x, y);
                catchFish(netPoint);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                int x = e.getX();
                int y = e.getY();
                net.x = x - net.width / 2;
                net.y = y - net.height / 2;
            }
        };
        // 对鼠标进行监听
        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);

        // 不断刷新
        while (true) {
            try {
                isWin();
            } catch (IOException e) {
                e.printStackTrace();
            }
            repaint();

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
