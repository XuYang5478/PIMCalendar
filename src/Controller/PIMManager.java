package Controller;
/**
 * 管理PIM的类，这里定义了用户交互界面，
 * Create和List命令可以增加和查看所有的具体PIM项目,
 * Look命令可以分类别和时间检索保存的PIM项目，
 * Save命令可以将创建的项目保存至本地文件中，
 * @author 徐杨 17130110024
 * @author FG23644666@yeah.net
 */

import Model.*;
import Model.PIMCollection;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

class PIMManager {
    public static void main(String[] args) {
        String menu = "---Enter a command (suported commands are List Look Create Save Load Quit)--";
        String choice; // 每次菜单的选项
        String user;
        int i;
        String fileName = "data";// 项目信息保存的文件名
        Scanner in = new Scanner(System.in);
        PIMCollection<PIMEntity> pimCollection = new PIMCollection<>(fileName);// 保存所有项目的集合

        System.out.println("Welcome to PIM.");
        System.out.print("Please input your UserName: ");
        user=in.nextLine();

        System.out.println(menu);
        choice = in.nextLine();
        while (!choice.equalsIgnoreCase("Quit")) {
            switch (choice) {
            case "List": // 将当前所有项目列举出来
                i = 0; // 计数器
                System.out.println("There are " + pimCollection.size() + " items.");
                for (Iterator<?> iterator = pimCollection.iterator(); iterator.hasNext(); i++) {
                    System.out.print("Item " + (i + 1) + ": ");
                    System.out.println(iterator.next().toString());
                }
                if (i == 0) {
                    System.out.println("Null! You haven't created any PIM.");
                }
                break;
            case "Look":// 测试PIMCollection类
                System.out.println("Enter an item type ( todo, note, contact, appointment or date)");
                choice = in.nextLine();
                switch (choice) {
                case "todo":
                    i = 0;
                    for (Iterator<?> it = pimCollection.getTodos().iterator(); it.hasNext(); i++) {
                        System.out.println(it.next().toString());// 遍历接收到的集合
                    }
                    if (i == 0) {// 如果i为0，则表示返回的集合为空，下面同理
                        System.out.println("Null! You haven't created any todo.");
                    }
                    break;
                case "note":
                    i = 0;
                    for (Iterator<?> it = pimCollection.getNotes().iterator(); it.hasNext(); i++) {
                        System.out.println(it.next().toString());
                    }
                    if (i == 0) {
                        System.out.println("Null! You haven't created any note.");
                    }
                    break;
                case "contact":
                    i = 0;
                    for (Iterator<?> it = pimCollection.getContacts().iterator(); it.hasNext(); i++) {
                        System.out.println(it.next().toString());
                    }
                    if (i == 0) {
                        System.out.println("Null! You haven't created any contact.");
                    }
                    break;
                case "appointment":
                    i = 0;
                    for (Iterator<?> it = pimCollection.getAppointments().iterator(); it.hasNext(); i++) {
                        System.out.println(it.next().toString());
                    }
                    if (i == 0) {
                        System.out.println("Null! You haven't created any appointment.");
                    }
                    break;
                case "date":
                    i = 0;
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");

                    System.out.println("Please input the date you want to search (in format: yyyy/MM/dd): ");
                    try {
                        Date date = ft.parse(in.nextLine());// 先将输入的日期解析为标准的date
                        for (Iterator<?> it = pimCollection.getItemsForDate(date).iterator(); it.hasNext(); i++) {
                            System.out.println(it.next().toString());// 然后再遍历包含这个日期的所有集合
                        }

                        if (i == 0) {
                            System.out.println("Null! You don't hava todo or appointment at this day.");
                        }

                    } catch (ParseException e) {
                        System.out.println("Unparseable using " + ft);
                    }

                    break;
                default:
                    System.out.println("Wrong choice!");
                    break;
                }
                break;
            case "Create": // 创建项目
                System.out.println("Enter an item type ( todo, note, contact or appointment )");
                choice = in.nextLine();
                switch (choice) {
                case "todo":
                    PIMTodo todo = new PIMTodo();
                    todo.setOwner(user);
                    System.out.println("Enter date for todo item (in format: yyyy/MM/dd):");
                    todo.setDate(in.nextLine(), in);
                    System.out.println("Enter todo text:");
                    todo.fromString(in.nextLine());
                    System.out.println("Enter todo priority:");
                    todo.setPriority(in.nextLine());
                    System.out.println("Private or Public:");
                    do{
                        String pri=in.nextLine();
                        if(pri.equalsIgnoreCase("Private")){
                            todo.setPrivate(true);
                            break;
                        }
                        else if(pri.equalsIgnoreCase("Public")){
                            todo.setPrivate(false);
                            break;
                        }
                        else {
                            System.out.println("Private or Public:");
                        }
                    }while (true);

                    pimCollection.add(todo);// 将新创建的todo项加入到集合中
                    System.out.println("Successful!");
                    break;

                case "note":
                    PIMNote note = new PIMNote();
                    note.setOwner(user);
                    System.out.println("Enter note text:");
                    note.fromString(in.nextLine());
                    System.out.println("Enter note priority:");
                    note.setPriority(in.nextLine());
                    System.out.println("Private or Public:");
                    do{
                        String pri=in.nextLine();
                        if(pri.equalsIgnoreCase("Private")){
                            note.setPrivate(true);
                            break;
                        }
                        else if(pri.equalsIgnoreCase("Public")){
                            note.setPrivate(false);
                            break;
                        }
                        else {
                            System.out.println("Private or Public:");
                        }
                    }while (true);
                    pimCollection.add(note);// 将新创建的note项加入到集合中
                    System.out.println("Successful!");
                    break;

                case "contact":
                    StringBuilder sb = new StringBuilder(); // 因为向项目传递信息的接口只有一个，而这里需要多次输入信息
                    PIMContact contact = new PIMContact(); // 所以就用StringBuilder来将每次输入的信息保存起来。
                    contact.setOwner(user);
                    System.out.println("Enter the first name:");
                    sb.append(in.nextLine());
                    System.out.println("Enter the last name:");
                    sb.append(" " + in.nextLine());
                    System.out.println("Enter the email:");
                    sb.append(" " + in.nextLine());
                    contact.fromString(sb.toString()); // 在这里一并传递给具体项目
                    System.out.println("Enter contactor's priority:");
                    contact.setPriority(in.nextLine());
                    System.out.println("Private or Public:");
                    do{
                        String pri=in.nextLine();
                        if(pri.equalsIgnoreCase("Private")){
                            contact.setPrivate(true);
                            break;
                        }
                        else if(pri.equalsIgnoreCase("Public")){
                            contact.setPrivate(false);
                            break;
                        }
                        else {
                            System.out.println("Private or Public:");
                        }
                    }while (true);
                    pimCollection.add(contact);// 将新创建的联系人项目加入到集合中
                    System.out.println("Successful!");
                    break;

                case "appointment":
                    PIMAppointment appointment = new PIMAppointment();
                    appointment.setOwner(user);
                    System.out.println("Enter date for appointment item (in format: yyyy/MM/dd):");
                    appointment.setDate(in.nextLine(), in);
                    System.out.println("Enter appointment description:");
                    appointment.fromString(in.nextLine());
                    System.out.println("Enter appointment priority:");
                    appointment.setPriority(in.nextLine());
                    System.out.println("Private or Public:");
                    do{
                        String pri=in.nextLine();
                        if(pri.equalsIgnoreCase("Private")){
                            appointment.setPrivate(true);
                            break;
                        }
                        else if(pri.equalsIgnoreCase("Public")){
                            appointment.setPrivate(false);
                            break;
                        }
                        else {
                            System.out.println("Private or Public:");
                        }
                    }while (true);
                    pimCollection.add(appointment);
                    System.out.println("Successful!");
                    break;

                default:
                    System.out.println("Wrong choice, creation failed!");
                }
                break;

            case "Save":
                boolean saved = true;   //指示是否成功保存
                try {
                    OutputStream os = new FileOutputStream(fileName); // 创建输出文件流
                    ObjectOutputStream saves = new ObjectOutputStream(os); // 根据输出文件流创建输出对象流
                    saves.writeObject(pimCollection); // 然后将当前集合保存在本地文件中
                    saves.flush();
                    saves.close();
                } catch (IOException e) {
                    saved = false;
                    System.out.println("We meet some problems, your items fail to save.");
                    //System.out.println(e.toString());
                }
                if(saved)
                    System.out.println("Items have been saved.");
                break;

            default:
                System.out.println("Wrong commond! Please try again.");
                break;
            }

            System.out.println("\n" + menu);
            choice = in.nextLine();
        }
        in.close();
    }
}