package Model; /**
 * 设置Todo的类，实现基类和接口中的方法
 * 
 * @author 徐杨 17130110024
 * @author FG23644666@yeah.net
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PIMTodo extends PIMEntity implements dateInterface {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Date date; // 时间
    private String todo_item; // 待办事项的具体内容
    private SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");

    public PIMTodo() {
        super();
        todo_item = "";
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

    public void setDate(String d){
        try {
            date = fm.parse(d);
        } catch (Exception e) {
            date=new Date();
        }
    }

    @Override
    public String getDate() {
        return fm.format(date);
    }

    @Override
    public void fromString(String s) {
        todo_item = s;
    }

    @Override
    public String toString() {
        return this.getOwner().isBlank()?"": this.getOwner()+" - "+ "TODO   " + super.getPriority() + "   " + getDate() + "   " + todo_item;
    }

    @Override
    public String toStringToCal() {
        return this.getOwner().isBlank()?"": this.getOwner()+" - "+ "TODO\n"+super.getPriority()+"\n"+ todo_item;
    }
}