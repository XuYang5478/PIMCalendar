package Model;
/**
 * PIM集合类，这个类继承自ArrayList，可以保存所有的项目，
 * 每种项目都定义了get方法，可以返回包含所有该类项目的集合，
 * 同时定义了按时间查找项目的方法，返回输入日期内的todo和appointment，
 * 将这个集合类序列化，以便能够将包含有具体内容的集合保存到文件中，
 * 创建集合时，读取本地保存集合的文件，将其中的对象转化为集合并将其中的元素加入到这个集合中，
 *
 * @author 徐杨 17130110024
 * @author FG23644666@yeah.net
 */

import Controller.RemotePIMCollection;
import Model.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class PIMCollection<T> extends ArrayList<T> implements Serializable, RemotePIMCollection<T> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String FileName;

    public PIMCollection(String fileName) {
        super();
        FileName=fileName;
        try {
            File file=new File(fileName);
            if(!file.exists())
                file.createNewFile();

            InputStream is = new FileInputStream(file); //根据给定的文件名读取文件输出流
            ObjectInputStream gets = new ObjectInputStream(is); //根据读到的文件流创建对象输出流
            ArrayList<T> pims = (ArrayList<T>) gets.readObject();   //然后把文件中的对象加载出来，转换为集合
            gets.close();
            this.addAll(pims);  //将包含有已保存项目的集合并入当前集合中
        } catch (ClassNotFoundException e) {
            System.out.println("No initialize items data.");
            //System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println("Fail to import your saved items!");
            System.out.println("If your are the first time to run this program, please ignore this message.");
            //System.out.println(e.toString());
        }
    }

    public Collection<T> getNotes() {
        ArrayList<T> notes = new ArrayList<>();// 创建一个结果数组列表用来保存符合条件的元素
        for (T temp : this) {// 遍历已经保存了的项目
            if (temp.getClass().equals(PIMNote.class)&&!((PIMNote) temp).isPrivate()) {// 判断元素的类名是否是要求的类目（这里是PIMNote类）
                notes.add(temp);// 如果有符合条件的，就把这个项目加入到结果集中
            }
        }
        return notes;// 最后将结果集返回。下面同理
    }

    public Collection<T> getNotes(String owner) {
        ArrayList<T> notes = new ArrayList<>();
        for (T temp : this) {
            if (temp.getClass().equals(PIMNote.class) && ((PIMNote) temp).getOwner().equals(owner))
                notes.add(temp);
        }
        return notes;
    }

    public Collection<T> getTodos() {
        ArrayList<T> todos = new ArrayList<>();
        for (T temp : this) {
            if (temp.getClass().equals(PIMTodo.class) && !((PIMTodo) temp).isPrivate()) {
                todos.add(temp);
            }
        }
        return todos;
    }

    public Collection<T> getTodos(String owner){
        ArrayList<T> todos=new ArrayList<>();
        for (T temp : this) {
            if (temp.getClass().equals(PIMTodo.class) && ((PIMTodo) temp).getOwner().equals(owner))
                todos.add(temp);
        }
        return todos;
    }

    public Collection<T> getAppointments() {
        ArrayList<T> appointments = new ArrayList<>();
        for (T temp : this) {
            if (temp.getClass().equals(PIMAppointment.class) && !((PIMAppointment) temp).isPrivate()) {
                appointments.add(temp);
            }
        }
        return appointments;
    }

    public Collection<T> getAppointments(String owner){
        ArrayList<T> appointments = new ArrayList<>();
        for (T temp : this) {
            if (temp.getClass().equals(PIMAppointment.class) && ((PIMAppointment) temp).getOwner().equals(owner))
                appointments.add(temp);
        }
        return appointments;
    }

    public Collection<T> getContacts() {
        ArrayList<T> contacts = new ArrayList<>();
        for (T temp : this) {
            if (temp.getClass().equals(PIMContact.class) && !((PIMContact) temp).isPrivate()) {
                contacts.add(temp);
            }
        }
        return contacts;
    }

    public Collection<T> getContacts(String owner){
        ArrayList<T> contacts = new ArrayList<>();
        for (T temp : this) {
            if (temp.getClass().equals(PIMContact.class) && ((PIMContact) temp).getOwner().equals(owner))
                contacts.add(temp);
        }
        return contacts;
    }

    public Collection<T> getItemsForDate(Date d) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
        String date = ft.format(d);// 先将输入的日期解析为标准的字符串

        ArrayList<T> same_date = new ArrayList<>();
        for (T temp : this) {
            if (temp.toString().contains(date) && !((PIMEntity) temp).isPrivate()) {// 检查该项目的详细信息中是否包含有输入日期的子字符串
                same_date.add(temp);// 如果有，表示这个日期下有项目，然后将其加入到结果集中。
            }
        }
        return same_date;
    }

    public Collection<T> getItemsForDate(Date d, String owner){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
        String date = ft.format(d);// 先将输入的日期解析为标准的字符串

        ArrayList<T> same_date = new ArrayList<>();
        for (T temp : this) {
            if (temp.toString().contains(date) && ((PIMEntity) temp).getOwner().equals(owner)) {
                same_date.add(temp);
            }
        }
        return same_date;
    }

    public Collection<T> getAll() {
        ArrayList<T> pim = new ArrayList<>();
        for (T temp : this) {
            if (!((PIMEntity) temp).isPrivate())
                pim.add(temp);
        }
        return pim;
    }

    public Collection<T> getAllByOwner(String owner){
        ArrayList<T> pim = new ArrayList<>();
        for (T temp : this) {
            if (((PIMEntity) temp).getOwner().equals(owner))
                pim.add(temp);
        }
        return pim;
    }

    public boolean addItems(PIMEntity entity){
        try{
            this.add((T) entity);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean save(){
        boolean saved = true;   //指示是否成功保存
        try {
            OutputStream os = new FileOutputStream(FileName); // 创建输出文件流
            ObjectOutputStream saves = new ObjectOutputStream(os); // 根据输出文件流创建输出对象流
            saves.writeObject(this); // 然后将当前集合保存在本地文件中
            saves.flush();
            saves.close();
        } catch (IOException e) {
            saved = false;
            System.out.println("We meet some problems, your items fail to save.");
            //System.out.println(e.toString());
        }
        return saved;
    }
}