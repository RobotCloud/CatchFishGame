package catchFish;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

public class Register extends JPanel {

    private static final String ID = "123456";
    private static final String PASSWORD = "123456";

    JFrame jFrame;
    JLabel jlbID;
    JLabel jlbPassword;
    JTextField jtfID;
    JTextField jtfPassword;
    JButton jbRegister;
    JButton jbQuit;

    public Register() {
        jFrame = new JFrame();
        jFrame.setTitle("登录");
        jFrame.setSize(500, 300);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        jFrame.add(this);
        jlbID = new JLabel("账号");
        jlbPassword = new JLabel("密码");
        jtfID = new JTextField("123456");
        jtfPassword = new JTextField("123456");
        jbRegister = new JButton("登录");
        jbQuit = new JButton("退出");

        jlbID.setFont(new Font("宋体", Font.BOLD, 30));
        jlbPassword.setFont(new Font("宋体", Font.BOLD, 30));
        jtfID.setFont(new Font("宋体", Font.BOLD, 30));
        jtfPassword.setFont(new Font("宋体", Font.BOLD, 30));
        jbRegister.setFont(new Font("宋体", Font.BOLD, 30));
        jbQuit.setFont(new Font("宋体", Font.BOLD, 30));

        jlbID.setBounds(50, 50, 100, 40);
        jlbPassword.setBounds(50, 100, 100, 40);
        jtfID.setBounds(150, 50, 250, 40);
        jtfPassword.setBounds(150, 100, 250, 40);
        jbRegister.setBounds(70, 170, 100, 50);
        jbQuit.setBounds(280, 170, 100, 50);

        this.add(jlbID);
        this.add(jlbPassword);
        this.add(jtfID);
        this.add(jtfPassword);
        this.add(jbRegister);
        this.add(jbQuit);

        jFrame.setVisible(true);

        jbRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);


                MySQL mySQL = new MySQL();
                User user = new User();
                user.setName(jtfID.getText());
                user.setGrade(Pool.getGrade());
                user.setDate(new Date(System.currentTimeMillis()));
                mySQL.insert(user);
                mySQL.query();

                int result = JOptionPane.showConfirmDialog(null, "再试一次", "登入失败", JOptionPane.YES_NO_OPTION);
                if (result == 0) {
                    Pool.setIsRun(true);
                    jFrame.dispose();

                } else {
                    System.exit(0);
                }

//                if (id.equals(ID) && password.equals(PASSWORD)) {
//                    jFrame.dispose();
//                    try {
//                        JFrame jFrame = new JFrame();
//                        jFrame.setTitle("捕鱼达人-终极版");
//                        jFrame.setSize(Main.getSCREENWIDTH(), Main.getSCREENHEIGHT());
//                        jFrame.setLocationRelativeTo(null);
//                        jFrame.setResizable(false);
//                        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                        Pool pool = new Pool();
//                        jFrame.add(pool);
//                        System.out.println("OK");
//                        jFrame.setVisible(true);
//                        pool.repaint();
//                        pool.action();
//                    } catch (IOException | InterruptedException ioException) {
//                        ioException.printStackTrace();
//                    }
//                } else {
//                    int result = JOptionPane.showConfirmDialog(null, "账号/密码错误，再试一次", "登入失败", JOptionPane.YES_NO_OPTION);
//                    if (result == 0) {
//                        repaint();
//                    } else {
//                        System.exit(0);
//                    }
//                }
            }
        });
        jbQuit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) {
        Register register = new Register();
    }
}
