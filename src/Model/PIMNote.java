package Model;

/**
 * 设置Note的类，实现基类和接口中的方法
 * 
 * @author 徐杨 17130110024
 * @author FG23644666@yeah.net
 */

public class PIMNote extends PIMEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String note; // 笔记的具体内容

    public PIMNote() {
        super();
        note = "";
    }

    @Override
    public void fromString(String s) {
        note = s;
    }

    @Override
    public String toString() {
        return this.getOwner().isBlank()?"": this.getOwner()+" - "+  "Note   " + super.getPriority() + "   " + note;
    }

    @Override
    public String toStringToCal() {
        return this.getOwner().isBlank()?"": this.getOwner()+" - "+  "Note\n" + super.getPriority() + "\n\n" + note;
    }
}