package top.scxy.fusion.service;

import top.scxy.fusion.entity.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserService {
    /*
     * @Description: 检查用户名或邮箱和密码是否匹配，返回用户ID
     * @Param: [usernameOrEmail, password]
     * @return: java.lang.Integer
     * */
    Integer checkUserWithUsernameAndPassword(String username, String password);
    /*
    * @Description: 注册用户，返回用户ID
    * @Param: [username, password, email, nickname, avatar]
    * @return: java.lang.Integer
    * */
    Integer register(String username, String password, String email, String nickname, String avatar);
    /*
    * @Description: 更新用户信息
    * @Param: [id, password, email, nickname, avatar, session]
    * */
    void updateUserInfo(Integer id, String password, String email, String nickname, String avatar);
    /*
    * @Description: 获取用户信息
    * @Param: [id]
    * @return: top.scxy.fusion.entity.UserInfo
    * */
    UserInfo getUserInfo(Integer id);
    /*
    * @Description: 根据用户名获取用户ID，用于查询用户名是否存在
    * @Param: [username]
    * @return: java.lang.Integer
    * */
    Integer getUserIdByUsername(String username);
    /*
    * @Description: 根据用户名获取用户邮箱
    * @Param: [username]
    * @return: java.lang.String
    * */
    String getUserEmailByUsername(String username);
    /*
    * @Description: 更新用户密码
    * @Param: [id, password]
    * */
    void updateUserPassword(Integer id, String password);
    /*
    * @Description: 更新用户邮箱
    * @Param: [id, email]
    * */
    void updateUserEmail(Integer id, String email);
    /*
    * @Description: 更新用户信息，除邮箱
    * @Param: [id, nickname, avatar]
    * */
    void updateUserInfo(Integer id, String nickname, String avatar);
    /*
    * @Description: 根据用户ID查询用户名
    * @Param: [id]
    * @return: java.lang.String
    * */
    String getUsernameById(Integer id);
    /*
    * @Description: 根据用户ID列表查询用户信息列表，返回Map
    * @Param: [userIds]
    * @return: java.util.Map<java.lang.Integer,top.scxy.fusion.entity.UserInfo>
    * */
    Map<Integer, UserInfo> getUserInfoByIds(List<Integer> userIds);
}
