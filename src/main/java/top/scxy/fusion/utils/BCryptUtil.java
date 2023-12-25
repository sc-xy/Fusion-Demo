package top.scxy.fusion.utils;

import org.mindrot.jbcrypt.BCrypt;
import top.scxy.fusion.constant.UtilConstant;

public class BCryptUtil {
    /*
    * @Description: BCrypt加密
    * @Param: [password]
    * @return: java.lang.String
    * */
    public static String encode(String password){
        return BCrypt.hashpw(password, UtilConstant.BCryptSalt);
    }

    public static void main(String[] args) {
        System.out.println(encode("admin"));
        System.out.println(encode("123456"));
        System.out.println(encode("13uy1h34guihx3"));
//        $2a$10$BEnZ3bP6oPOHl3CYS3gmwuZ/wPN3fIUL9ULTaQQSBhQsGTdoDpsIK
//        $2a$10$BEnZ3bP6oPOHl3CYS3gmwujFndNDJKUgGqbzN0I/FTSA1AyocBQrm
//        $2a$10$BEnZ3bP6oPOHl3CYS3gmwuPr048Fy/UmlMlXTR.cj4QFZdSzd8hou
    }
}
