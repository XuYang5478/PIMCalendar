package Model;
/**
 * 设置Appointment的类，实现基类和接口中的方法
 * @author 徐杨 17130110024
 * @author FG23644666@yeah.net
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PIMAppointment extends PIMEntity implements dateInterface {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Date date;
    private String description; // 预约的描述
    private SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");

    public PIMAppointment() {
        super();
    }

    @Override
    public void setDate(String d, Scanner in) {
        while (true) {
            try {
                date = fm.parse(d);
                break;
            } catch (Exception e) {
                System.out.println("Wrong Format! Please try again!");
                d = in.nextLine();
            }
        }
    }
    public void setDate(String d) {
        try{
            date=fm.parse(d);
        }catch (ParseException e){
            date=new Date();
        }
    }

    @Override
    public String getDate() {
        return fm.format(date);
    }
    
    @Override
    public void fromString(String s) {
        description = s;
    }

    @Override
    public String toString() {
        return this.getOwner().isBlank()?"": this.getOwner()+" - "+ "Appointment   " + super.getPriority() + "   " + getDate() + "   " + description;
    }

    @Override
    public String toStringToCal() {
        return this.getOwner().isBlank()?"": this.getOwner()+" - "+ "Appointment\n" +super.getPriority() + "\n"+ description;
    }
}