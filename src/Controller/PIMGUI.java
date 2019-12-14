package Controller;

import Model.PIMEntity;
import View.PIMCalendar;
import Model.PIMCollection;

import javax.swing.*;
/**
 * 这个类是PIM的图形化管理界面
 * 可以添加和浏览项目
 * @author 徐杨 17130110024
 * @author FG23644666@yeah.net
 * */
public class PIMGUI {
    public static void main(String[] args) {
        String fileName = "data";// 项目信息保存的文件名
        PIMCollection<PIMEntity> pimCollection = new PIMCollection<>(fileName);// 保存所有项目的集合

        JFrame rootFrame = new JFrame("View.PIMCalendar");//定义和设置根框架
        rootFrame.setSize(1280, 720);
        rootFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();//采用卡片式布局
        tabbedPane.add("日历", new PIMCalendar(pimCollection, rootFrame));//第一个选项卡中显示日历，可以操作todo和appointment
        tabbedPane.add("笔记", new JTextArea("Make note here~"));//第二个窗口显示笔记
        tabbedPane.add("联系人", new JTextArea("Make note here~"));//第三个窗口显示联系人

        rootFrame.setContentPane(tabbedPane);
        rootFrame.setVisible(true);
    }
}
