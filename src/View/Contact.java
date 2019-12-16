package View;

import Model.PIMCollection;
import Model.PIMContact;
import Model.PIMEntity;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Contact extends JPanel {
    PIMCollection<PIMEntity> pimCollection;
    Collection<PIMEntity> contacts;
    JFrame Parent;
    String User;

    JSplitPane contactPanel=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JList<String> contactName=new JList<>();
    JLabel firstName=new JLabel("",JLabel.LEFT);
    JLabel lastName=new JLabel("",JLabel.LEFT);
    JLabel email=new JLabel("",JLabel.LEFT);
    JPanel detail=new JPanel(new GridBagLayout());
    JButton addContact=new JButton(" + ");

    String[] names;
    String[] emails;

    public Contact(PIMCollection<PIMEntity> PIM, JFrame parent, String user){
        super(new BorderLayout());
        pimCollection=PIM;
        Parent=parent;
        User=user;

        showContact();
        contactName.setFixedCellHeight(25);
        contactName.addListSelectionListener(e -> {
            int index=contactName.getSelectedIndex();
            showDetail(index);
        });

        JScrollPane scrollTable=new JScrollPane(contactName);
        scrollTable.setBorder(BorderFactory.createTitledBorder("联系人列表"));

        addContact.addActionListener(e -> {
            new addContact(parent, user, PIM).setVisible(true);
            showContact();
            showDetail(contacts.size()-1);
        });
        JToolBar listFoot=new JToolBar();
        listFoot.add(addContact);
        JPanel leftComponent=new JPanel(new BorderLayout());
        leftComponent.add(contactName,BorderLayout.CENTER);
        leftComponent.add(listFoot,BorderLayout.SOUTH);
        contactPanel.setLeftComponent(leftComponent);

        showDetail();
        contactPanel.setRightComponent(detail);

        contactPanel.setDividerLocation(300);
        contactPanel.setEnabled(false);
        this.add(contactPanel,BorderLayout.CENTER);
    }

    private void showContact(){
        contacts=User.isBlank()?pimCollection.getContacts():pimCollection.getContacts(User);
        if(contacts.size()>0){
            names=new String[contacts.size()];
            emails=new String[contacts.size()];
        }else {
            names=new String[]{""};
            emails=new String[]{""};
        }

        int i=0;
        for(PIMEntity entity:contacts){
            String[] info=entity.toStringToCal().split("/");
            names[i]=info[0]+" "+info[1];
            emails[i]=info[2];
            i++;
        }

        contactName.setListData(names);

        contactPanel.repaint();
        contactPanel.revalidate();
        this.repaint();
        this.revalidate();
    }

    private void showDetail(){
        setDetial(names[0],emails[0]);

        GridBagConstraints constraints=new GridBagConstraints();
        constraints.insets=new Insets(10,10,10,10);
        constraints.anchor=GridBagConstraints.WEST;

        constraints.gridx=0;
        constraints.gridy=0;
        constraints.gridwidth=1;
        detail.add(new JLabel("姓：",JLabel.RIGHT),constraints);

        constraints.gridx=1;
        constraints.gridy=0;
        constraints.gridwidth=2;
        detail.add(firstName,constraints);

        constraints.gridx=0;
        constraints.gridy=1;
        constraints.gridwidth=1;
        detail.add(new JLabel("名：",JLabel.RIGHT),constraints);

        constraints.gridx=1;
        constraints.gridy=1;
        constraints.gridwidth=2;
        detail.add(lastName,constraints);

        constraints.gridx=0;
        constraints.gridy=2;
        constraints.gridwidth=1;
        detail.add(new JLabel("邮箱：",JLabel.RIGHT),constraints);

        constraints.gridx=1;
        constraints.gridy=2;
        constraints.gridwidth=2;
        detail.add(email,constraints);
    }

    private void showDetail(int i){
        setDetial(names[i],emails[i]);
        detail.repaint();
        detail.revalidate();
        contactPanel.repaint();
        contactPanel.revalidate();
    }

    private void setDetial(String n,String e){
        String[] nameInfo=n.split(" ");
        if(nameInfo.length>=2){
            firstName.setText(nameInfo[0]);
            lastName.setText(nameInfo[1]);
        }else {
            firstName.setText("");
            lastName.setText("");
        }

        email.setText(e);
    }
}
