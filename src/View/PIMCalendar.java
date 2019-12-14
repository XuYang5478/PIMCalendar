package View;

import Model.PIMCollection;
import Model.PIMEntity;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Collection;

/**
 * 这个类显示一个月的日历，
 * 日期在这个月内的todo和appointment将会显示在日历中
 *
 * @author 徐杨 17130110024
 * @author FG23644666@yeah.net
 */
public class PIMCalendar extends JPanel {
    private JFrame Parent;
    private int count = 0;  //翻动日历的时候，记录距今有多少月。为负表示过去，为正表示将来
    private JPanel calendarPanel = new JPanel(new GridLayout(0, 7, 5, 5));
    private JLabel dateText = new JLabel();
    private PIMCollection<PIMEntity> pimCollection;
    private String temp;

    /**
     * 在构造器中，日历界面的所有组件将会被加载
     *
     * @param PIM    用来加载和保存项目的PIM集合，由上层传递而来
     * @param parent 这个页面的父框架
     */
    public PIMCalendar(PIMCollection<PIMEntity> PIM, JFrame parent) {
        super(new BorderLayout());
        pimCollection = PIM;
        Parent = parent;
        this.add(calendarPanel, BorderLayout.CENTER);//这个界面的根布局为边框布局
        DrawCalendar(); //画出日历的函数

        //工具栏中翻动日历的按钮
        JButton preMonth = new JButton(" ◀ ");
        preMonth.addActionListener(e -> PreviousMonth());
        JButton nextMonth = new JButton(" ▶ ");
        nextMonth.addActionListener(e -> NextMonth());

        //将按钮添加到工具栏中
        JToolBar toolBar = new JToolBar("Operation Bar");
        toolBar.add(preMonth);
        toolBar.addSeparator();
        toolBar.add(dateText);
        toolBar.addSeparator();
        toolBar.add(nextMonth);
        toolBar.addSeparator();

        //添加PIM项目的按钮
        JButton addPIM = new JButton("﹢");
        addPIM.addActionListener(e -> {
            new NewItemInCalendar(parent, "Xuyang", pimCollection).setVisible(true);
            DrawCalendar();
        });
        toolBar.add(addPIM);
        this.add(toolBar, BorderLayout.SOUTH);//工具栏默认放在窗口底部，可拖动
    }

    /**
     * 这个方法用来显示上一个月的日历
     * 将月份计数器减一，然后重新画出日历
     */
    public void PreviousMonth() {
        count--;
        DrawCalendar();
    }

    /**
     * 这个方法用来显示下一个月的日历
     * 将月份计数器加一，然后重新画出日历
     */
    public void NextMonth() {
        count++;
        DrawCalendar();
    }

    /**
     * 这个方法用来画出日历
     */
    public void DrawCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, count);    //先计算目标日期的日历
        dateText.setText(calendar.getTime().toString());

        calendarPanel.removeAll();  //删除现有日历上的所有元组，然后重新绘制

        calendar.set(Calendar.DAY_OF_MONTH, 1); //建立一个日历，然后将用上面设置的日历来设置它的日期
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);   //由于我们手动设置了日期为本月1号，所以可以用这个语句
        //来提取1号是星期几，也就是本月的第一天是星期几
        int maxDay = calendar.getActualMaximum(Calendar.DATE);//获取每月的最大天数

        String[] title = {"日", "一", "二", "三", "四", "五", "六"};
        for (String s : title) {
            JLabel day = new JLabel(s, JLabel.CENTER);
            day.setFont(new Font("微软雅黑", Font.BOLD, 20));
            calendarPanel.add(day);
        }

        for (int i = 1; i < weekDay; i++)  //从1开始打印日期
            calendarPanel.add(new JLabel(""));  //根据刚才获取的第一天的位置，将之前的显示内容设置为空

        for (int i = 1; i <= maxDay; i++) {
            JPanel panel = new JPanel(new BorderLayout());//每一个日期方块由两部分构成，上面的是日期号，下面是PIM的内容
            panel.setBackground(Color.white);

            JLabel day = new JLabel(i + "  ", JLabel.RIGHT);
            day.setFont(new Font("微软雅黑", Font.BOLD, 20));
            panel.add(day, BorderLayout.NORTH);//在最上方添加日期号

            Collection<PIMEntity> collection = pimCollection.getItemsForDate(calendar.getTime(), "Xuyang");
            temp = "";
            if (!collection.isEmpty()) {
                for (PIMEntity pim : collection) {
                    temp += pim.toStringToCal() + "\n";
                }
            }
            JTextArea content = new JTextArea(temp);
            content.setMargin(new Insets(5, 10, 5, 10));
            content.setLineWrap(true);
            panel.add(content, BorderLayout.CENTER);//PIM项目占位符
            calendarPanel.add(panel);

            calendar.add(Calendar.DATE, 1);  //然后日期加一，向后移
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendarPanel.revalidate();
        this.repaint();
        this.revalidate();//最后刷新整个面板，来显示新绘制的日历
    }
}
