package View;

import Model.PIMCollection;
import Model.PIMEntity;
import Model.PIMNote;

import javax.swing.*;
import java.awt.*;

public class addNote extends JDialog {
    String User;
    PIMCollection<PIMEntity> pimCollection;

    JTextArea note = new JTextArea(4, 20);
    JTextArea priority = new JTextArea(1, 20);
    JRadioButton pri;
    JRadioButton pub;

    public addNote(JFrame parent, String user, PIMCollection<PIMEntity> PIM) {
        super(parent, true);
        this.setLocation(0, parent.getHeight() - 360);
        User = user;
        pimCollection = PIM;

        init();
    }

    private void init() {
        //属性设置
        this.setTitle("新建笔记");
        this.setLayout(new BorderLayout());
        this.setSize(330, 320);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets=new Insets(10,0,10,0);

        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add(new JLabel("内容：", JLabel.RIGHT), constraints);

        note.setMargin(new Insets(5, 5, 5, 5));
        note.setLineWrap(true);
        note.setWrapStyleWord(true);
        JScrollPane noteScroll=new JScrollPane(note);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 3;
        content.add(noteScroll, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        content.add(new JLabel("优先级：", JLabel.RIGHT), constraints);

        priority.setMargin(new Insets(5, 5, 5, 5));
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        content.add(priority, constraints);

        constraints.gridx=0;
        constraints.gridy=5;
        constraints.gridwidth=1;
        constraints.gridheight=1;
        content.add(new JLabel("权限：",JLabel.RIGHT),constraints);

        JPanel setprivatre=new JPanel(new FlowLayout(FlowLayout.LEFT,30,5));
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
        setprivatre.add(pri);
        setprivatre.add(pub);

        constraints.gridx=1;
        constraints.gridy=5;
        constraints.gridwidth=3;
        constraints.gridheight=1;
        content.add(setprivatre,constraints);

        this.add(content, BorderLayout.CENTER);

        //窗口底部的“提交”和“取消”按钮，同时为它们设置动作监听器
        JPanel foot = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
        JButton submit = new JButton("提交");
        submit.addActionListener(e -> {
            PIMNote newNote=new PIMNote();
            newNote.setOwner(User);
            newNote.fromString(note.getText());
            newNote.setPriority(priority.getText());
            newNote.setPrivate(pri.isSelected());
            pimCollection.add(newNote);
            pimCollection.save();
            cleanContent();
        });

        JButton cancel = new JButton("取消");
        cancel.addActionListener(e -> cleanContent());

        ButtonGroup footButton = new ButtonGroup();
        footButton.add(submit);
        footButton.add(cancel);
        foot.add(submit);
        foot.add(cancel);
        this.add(foot, BorderLayout.SOUTH);
    }

    private void cleanContent(){
        note.setText("");
        priority.setText("");
        pri.setSelected(!User.isBlank());
        pub.setSelected(User.isBlank());

        this.setVisible(false);
    }
}
