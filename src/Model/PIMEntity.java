package Model;

import java.io.Serializable;

/**
 * PIM项目的基类，是一个抽象类， 定义了常用方法的声明，
 * 设置优先级，输入相应的内容以及输出该项目的信息，
 * 继承Serializable接口，使其可以序列化并存入文件
 * 
 * @author 徐杨 17130110024
 * @author FG23644666@yeah.net
 */

public abstract class PIMEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String Priority; // every kind of item has a priority
    String Owner;
    boolean Private;

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public boolean isPrivate() {
        return Private;
    }

    public void setPrivate(boolean aPrivate) {
        Private = aPrivate;
        if(Owner.isBlank()){
            Private=false;
        }
    }

    // default constructor sets priority to "normal"
    PIMEntity() {
        Priority = "normal";
    }

    // priority can be established via this constructor.
    PIMEntity(String priority) {
        Priority = priority;
    }

    // accessor method for getting the priority string
    public String getPriority() {
        return Priority;
    }

    // method that changes the priority string
    public void setPriority(String p) {
        Priority = p;
    }

    // Each Model.PIMEntity needs to be able to set all state information
    // (fields) from a single text string.
    abstract public void fromString(String s);

    // This is actually already defined by the super class
    // Object, but redefined here as abstract to make sure
    // that derived classes actually implement it
    abstract public String toString();
    abstract public String toStringToCal();
}