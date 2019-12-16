package Controller;

import Model.PIMEntity;
import View.Contact;
import View.Note;
import View.PIMCalendar;
import Model.PIMCollection;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 这个类是PIM的图形化管理界面
 * 可以添加和浏览项目
 *
 * @author 徐杨 17130110024
 * @author FG23644666@yeah.net
 */
public class PIMGUI {
    public static void main(String[] args) {
        AtomicInteger closed = new AtomicInteger(0);
        String fileName = "data";// 项目信息保存的文件名
        PIMCollection<PIMEntity> pimCollection = new PIMCollection<>(fileName);// 保存所有项目的集合
        AtomicReference<String> User = new AtomicReference<>("Xuyang");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//设置为本机系统默认观感
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame rootFrame = new JFrame("View.PIMCalendar");//定义和设置根框架
        rootFrame.setSize(1280, 720);
        rootFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        User.set(JOptionPane.showInputDialog(rootFrame, "请输入名称：", "欢迎使用", JOptionPane.QUESTION_MESSAGE));
        if (User.get() == null)
            User.set("");

        JTabbedPane tabbedPane = new JTabbedPane();//采用卡片式布局
        tabbedPane.add("日历", new PIMCalendar(pimCollection, rootFrame, User.get()));//第一个选项卡中显示日历，可以操作todo和appointment
        tabbedPane.add("笔记", new Note(pimCollection, rootFrame, User.get()));//第二个窗口显示笔记
        tabbedPane.add("联系人", new Contact(pimCollection, rootFrame, User.get()));//第三个窗口显示联系人
        tabbedPane.add("切换用户", null);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == 3) {
                    rootFrame.dispose();
                    User.set(JOptionPane.showInputDialog(rootFrame, "请输入名称：", "欢迎使用", JOptionPane.QUESTION_MESSAGE));
                    if (User.get() == null)
                        User.set("");

                    //删除卡片布局中的所有组件，并用新的用户重新加载
                    tabbedPane.removeAll();
                    tabbedPane.add("日历", new PIMCalendar(pimCollection, rootFrame, User.get()));
                    tabbedPane.add("笔记", new Note(pimCollection, rootFrame, User.get()));
                    tabbedPane.add("联系人", new Contact(pimCollection, rootFrame, User.get()));
                    tabbedPane.add("切换用户", null);

                    tabbedPane.revalidate();
                    tabbedPane.repaint();
                    rootFrame.revalidate();
                    rootFrame.repaint();
                    rootFrame.setVisible(true);
                }
            }
        });
        rootFrame.setContentPane(tabbedPane);
        rootFrame.setVisible(true);
    }
}
