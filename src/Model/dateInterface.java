package Model;

import java.util.Scanner;

/**
 * 这是一个设置时间的公共接口文件
 * 
 * @author 徐杨 17130110024
 * @author FG23644666@yeah.net
 */

public interface dateInterface {
    void setDate(String d, Scanner in); // 定义的设置时间的接口

    String getDate();
}