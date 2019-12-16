package Model;

/**
 * 设置Contact的类，实现基类和接口中的方法
 *
 * @author 徐杨 17130110024
 * @author FG23644666@yeah.net
 */

public class PIMContact extends PIMEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String first_name, last_name, email;

    public PIMContact() {
        super();
        first_name = "";
        last_name = "";
        email = "";
    }

    @Override
    public void fromString(String s) { // 因为接口只能接受一个单独的字符串，所以需要
        String[] infos = s.split(" "); // 将包含有联系人信息的字符串切分开
        first_name = infos[0]; // 然后分发到每个变量中
        last_name = infos[1];
        email = infos[2];
    }

    @Override
    public String toString() {
        return this.getOwner().isBlank() ? "" : this.getOwner() + " - " + "Contact   " + first_name + "   " + last_name + "   " + email;
    }

    @Override
    public String toStringToCal() {
        return first_name + "/" + last_name + "/" + email;
    }
}