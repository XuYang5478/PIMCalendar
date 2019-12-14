package View;

import Model.PIMAppointment;
import Model.PIMCollection;
import Model.PIMEntity;
import Model.PIMTodo;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
/**
 * 这个类用来向日历中添加新的项目
 *  @author 徐杨 17130110024
 *  @author FG23644666@yeah.net
 * */
public class NewItemInCalendar extends JDialog {
    private PIMCollection<PIMEntity> pimCollection;
    private String User;

    //下面是所有需要获取信息的输入组件
    private JComboBox<String> item =new JComboBox<>(new String[]{"--请选择--", "Appointment", "To-do"});
    private JTextArea detail=new JTextArea(3,20);
    private JTextArea date=new JTextArea(1,19);
    private JTextArea priority=new JTextArea(1,19);
    private JRadioButton pri;
    private JRadioButton pub;

    private Calendar calendar=Calendar.getInstance();

    /**
     * 在构造器中设置窗口的大小、位置，并加载所有组件
     * @param parent 父框架，用来定位本窗口
     * @param user 用户名，用来设置新建项目的属性
     * @param PIM 用来保存项目的集合
     * */
    public NewItemInCalendar(JFrame parent, String user, PIMCollection<PIMEntity> PIM) {
        super(parent,true);
        this.setLocation(140,parent.getHeight()-390);
        User=user;
        pimCollection = PIM;

        init();
    }

    /**
     * 这个方法用来加载窗口中的所有组件
     * */
    private void init() {
        //属性设置
        this.setTitle("新建日历事项");
        this.setLayout(new BorderLayout());
        this.setSize(330, 350);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        JPanel content = new JPanel(new FlowLayout(FlowLayout.LEFT,20,10));

        //选择项目是todo还是appointment
        JPanel setitem=new JPanel(new FlowLayout());
        setitem.add(new JLabel("项目：  ", JLabel.RIGHT));
        setitem.add(item);
        content.add(setitem);

        //设置项目的日期
        JPanel setdate=new JPanel(new FlowLayout());
        setdate.add(new JLabel("日期：  ", JLabel.RIGHT));
        date.setMargin(new Insets(5,5,5,5));
        String pleaseholder=calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH);
        date.setText(pleaseholder);
        setdate.add(date);
        content.add(setdate);

        //输入项目的内容
        JPanel con=new JPanel(new FlowLayout());
        con.add(new JLabel("内容：  ",JLabel.RIGHT));
        detail.setLineWrap(true);//可换行
        detail.setMargin(new Insets(5,5,5,5));
        con.add(detail);
        content.add(con);

        //设置优先级
        JPanel setprivoity=new JPanel(new FlowLayout());
        setprivoity.add(new JLabel("优先级：",JLabel.RIGHT));
        priority.setMargin(new Insets(5,5,5,5));
        setprivoity.add(priority);
        content.add(setprivoity);

        //设置权限
        JPanel setprivate=new JPanel(new FlowLayout());
        JPanel chooseprivatre=new JPanel(new FlowLayout(FlowLayout.LEFT,30,5));
        if(User.isBlank()){
            pri=new JRadioButton("私有");
            pub=new JRadioButton("公开",true);
        }else{
            pri=new JRadioButton("私有",true);
            pub=new JRadioButton("公开");
        }
        ButtonGroup group=new ButtonGroup();
        group.add(pri);
        group.add(pub);
        chooseprivatre.add(pri);
        chooseprivatre.add(pub);
        setprivate.add(new JLabel("权限：  ",JLabel.RIGHT));
        setprivate.add(chooseprivatre);
        content.add(setprivate);

        this.add(content,BorderLayout.CENTER);

        //窗口底部的“提交”和“取消”按钮，同时为它们设置动作监听器
        JPanel foot=new JPanel(new FlowLayout(FlowLayout.CENTER,50,5));
        JButton submit=new JButton("提交");
        submit.addActionListener(e -> {
            String type= (String) item.getSelectedItem();
            String datee=date.getText();
            String words=detail.getText();
            String prio=priority.getText();
            assert type != null;
            switch (type){
                case "Appointment":
                    PIMAppointment appointment=new PIMAppointment();
                    appointment.setOwner(User);
                    appointment.setDate(datee);
                    appointment.fromString(words);
                    appointment.setPriority(prio);
                    appointment.setPrivate(pri.isSelected());
                    System.out.println(appointment.toString());
                    cleanContent();
                    break;
                case "To-do":
                    PIMTodo todo=new PIMTodo();
                    todo.setOwner(User);
                    todo.setDate(datee);
                    todo.fromString(words);
                    todo.setPriority(prio);
                    todo.setPrivate(pri.isSelected());
                    pimCollection.add(todo);
                    cleanContent();
                    break;
                default:
                    JOptionPane.showMessageDialog(this,"请选择代办事项类别！","添加失败",JOptionPane.ERROR_MESSAGE);
                    break;
            }
            pimCollection.save();
        });

        JButton canel=new JButton("取消");
        canel.addActionListener(e -> cleanContent());

        ButtonGroup footButton=new ButtonGroup();
        footButton.add(submit);
        footButton.add(canel);
        foot.add(submit);
        foot.add(canel);
        this.add(foot,BorderLayout.SOUTH);
    }

    /**
     * 这个方法用来初始化所有的输入控件
     * 并关闭新建项目的窗口
     * */
    private void cleanContent(){
        item.setSelectedIndex(0);
        detail.setText("");
        date.setText(calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH));
        priority.setText("");
        pri.setSelected(!User.isBlank());
        pub.setSelected(User.isBlank());

        this.setVisible(false);
    }
}
