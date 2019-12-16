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
    JTable contactName;
    JScrollPane scrollTable;
    JLabel firstName=new JLabel("",JLabel.LEFT);
    JLabel lastName=new JLabel("",JLabel.LEFT);
    JLabel email=new JLabel("",JLabel.LEFT);
    JPanel detail=new JPanel(new GridBagLayout());

    Object[][] names;
    Object[] emails;

    public Contact(PIMCollection<PIMEntity> PIM, JFrame parent, String user){
        super(new BorderLayout());
        pimCollection=PIM;
        Parent=parent;
        User=user;

        showContact();
        contactName.setRowHeight(25);
        scrollTable=new JScrollPane(contactName);
        scrollTable.setBorder(BorderFactory.createTitledBorder("联系人列表"));
        contactPanel.setLeftComponent(scrollTable);

        showDetail();
        contactPanel.setRightComponent(detail);

        contactPanel.setDividerLocation(300);
        contactPanel.setEnabled(false);
        this.add(contactPanel,BorderLayout.CENTER);
    }

    private void showContact(){
        contacts=User.isBlank()?pimCollection.getContacts():pimCollection.getContacts(User);
        names=new Object[contacts.size()][1];
        emails=new Object[contacts.size()];
        int i=0;
        for(PIMEntity entity:contacts){
            String[] info=entity.toStringToCal().split("/");
            names[i][0]=info[0]+" "+info[1];
            emails[i]=info[2];
            i++;
        }

        contactName=new JTable(names,new Object[]{""});

        contactPanel.repaint();
        contactPanel.revalidate();
        this.repaint();
        this.revalidate();
    }

    private void showDetail(){
        setDetial((String) names[0][0],(String) emails[0]);

        GridBagConstraints constraints=new GridBagConstraints();
        constraints.insets=new Insets(10,10,10,10);
        constraints.anchor=GridBagConstraints.WEST;

        constraints.gridx=0;
        constraints.gridy=1;
        constraints.gridwidth=1;
        detail.add(new JLabel("姓：",JLabel.RIGHT),constraints);

        constraints.gridx=1;
        constraints.gridy=1;
        constraints.gridwidth=2;
        detail.add(firstName,constraints);

        constraints.gridx=0;
        constraints.gridy=2;
        constraints.gridwidth=1;
        detail.add(new JLabel("名：",JLabel.RIGHT),constraints);

        constraints.gridx=1;
        constraints.gridy=2;
        constraints.gridwidth=2;
        detail.add(lastName,constraints);

        constraints.gridx=0;
        constraints.gridy=3;
        constraints.gridwidth=1;
        detail.add(new JLabel("邮箱：",JLabel.RIGHT),constraints);

        constraints.gridx=1;
        constraints.gridy=3;
        constraints.gridwidth=2;
        detail.add(email,constraints);
    }

    private void showDetail(int index){
        setDetial((String) names[index][0],(String)emails[index]);
        detail.repaint();
        detail.revalidate();
        contactPanel.repaint();
        contactPanel.revalidate();
    }

    private void setDetial(String n,String e){
        String[] nameInfo=n.split(" ");
        firstName.setText(nameInfo[0]);
        lastName.setText(nameInfo[1]);
        email.setText(e);
    }
}
