package View;

import Model.PIMCollection;
import Model.PIMEntity;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class Note extends JPanel {
    PIMCollection<PIMEntity> pimCollection;
    Collection<PIMEntity> notes;
    JFrame Parent;
    String User;
    int Page = 0, totalPage = 0;//当前笔记的页数/所有笔记的总页数

    JPanel notePanel = new JPanel(new GridLayout(4, 2, 15, 15));
    JLabel pageLabel = new JLabel();

    JButton pageUp = new JButton(" ▲ ");
    JButton pageDown = new JButton(" ▼ ");

    public Note(PIMCollection<PIMEntity> PIM, JFrame parent, String user) {
        super(new BorderLayout());
        pimCollection = PIM;
        Parent = parent;
        User = user;

        notePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        notes = User.isBlank() ? pimCollection.getNotes() : pimCollection.getNotes(User);
        totalPage=(int) Math.ceil(notes.size() / 8);
        Page=totalPage;//每次查看时都默认在最后一页，因为最新的笔记在最后一页
        showNote();
        this.add(notePanel, BorderLayout.CENTER);

        pageDown.setEnabled(false);//因为一开始就在最后一页，所以不能再向下翻页
        pageUp.addActionListener(e -> PageUp());
        pageDown.addActionListener(e -> PageDown());

        JButton addNote = new JButton(" + ");
        addNote.addActionListener(e -> {
            new addNote(parent, user, PIM).setVisible(true);
            if((notes.size()+1)%8!=0)
                PageDown();
            else
                showNote();
        });

        JToolBar toolBar = new JToolBar("Operation Bar");
        toolBar.add(pageUp);
        toolBar.addSeparator();
        toolBar.add(pageLabel);
        toolBar.addSeparator();
        toolBar.add(pageDown);
        toolBar.addSeparator();
        toolBar.add(addNote);
        this.add(toolBar, BorderLayout.SOUTH);
    }

    private void PageUp() {
        if (Page>0){
            Page--;
        }else {
            pageUp.setEnabled(false);
        }
        showNote();
        pageDown.setEnabled(true);
    }

    private void PageDown() {
        if (Page<totalPage){
            Page++;
        }else {
            pageDown.setEnabled(false);
        }
        showNote();
        pageUp.setEnabled(true);
    }

    private void showNote() {
        notes = User.isBlank() ? pimCollection.getNotes() : pimCollection.getNotes(User);
        PIMEntity[] noteList = new PIMEntity[notes.size()];
        notes.toArray(noteList);
        totalPage=(int) Math.ceil(notes.size() / 8);
        int currentItem = Page * 8;

        pageLabel.setText((Page + 1) + "/" + (totalPage+1));

        notePanel.removeAll();
        for (int i = currentItem; i < currentItem + 8; i++) {
            if (i < notes.size()) {
                JTextArea note = new JTextArea(noteList[i].toStringToCal());
                note.setMargin(new Insets(5, 5, 5, 5));
                note.setLineWrap(true);
                note.setBackground(Color.white);
                JScrollPane scrollPane=new JScrollPane(note);
                notePanel.add(scrollPane);
            } else {
                notePanel.add(new JLabel());
            }
        }
        this.repaint();
        this.revalidate();
    }
}
