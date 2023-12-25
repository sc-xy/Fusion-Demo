package top.scxy.fusion.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.scxy.fusion.entity.User;
import top.scxy.fusion.entity.UserInfo;

@Mapper
@Repository
public interface UserMapper {
    /*
    * @Description: 通过用户名和密码查询用户ID
    * @Param: [username, password]
    * @return: java.lang.Integer
    * */
    @Select("select id from user where username = #{username} and password = #{password}")
    Integer selectByUsernameAndPassword(String username, String password);
    /*
    * @Description: 通过用户名查询用户ID
    * @Param: [username]
    * @return: java.lang.Integer
    * */
    @Select("select id from user where username = #{username}")
    Integer selectByUsername(String username);
    /*
    * @Description: 通过用户ID查询用户名
    * @Param: [id]
    * @return: java.lang.String
    * */
    @Select("select username from user where id = #{id}")
    String selectUsernameById(Integer id);
    /*
    * @Description: 通过用户ID查询用户信息
    * @Param: [id]
    * @return: top.scxy.fusion.entity.User
    * */
    @Select("select * from user_info where id = #{id}")
    UserInfo selectById(Integer id);
    /*
    * @Description: 通过用户名查询用户邮箱
    * @Param: [username]
    * @return: java.lang.String
    * */
    @Select("select email from user_info where username = #{username}")
    String selectEmailByUsername(String username);
    /*
    * @Description: 注册用户，返回用户ID
    * @Param: [username, password]
    * */
    @Insert("insert into user(username, password) values(#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertUser(User user);
    /*
    * @Description: 添加用户信息，返回用户ID
    * @Param: [id, username, nickname, avatar, email]
    * */
    @Insert("insert into user_info(id, username, nickname, avatar, email) values(#{id}, #{username}, #{nickname}, #{avatar}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertUserInfo(UserInfo userInfo);
    /*
    * @Description: 更新用户信息
    * @Param: [id, email, nickname,  avatar]
    * */
    @Insert("update user_info set email = #{email}, nickname = #{nickname}, avatar = #{avatar} where id = #{id}")
    void updateUserInfo(Integer id, String email, String nickname, String avatar);
    /*
    * @Description: 更新用户密码
    * @Param: [id, password]
    * */
    @Insert("update user set password = #{password} where id = #{id}")
    void updateUserPassword(Integer id, String password);
    /*
    * @Description: 更新用户邮箱
    * @Param: [id, email]
    * */
    @Update("update user_info set email = #{email} where id = #{id}")
    void updateUserEmail(Integer id, String email);
    /*
    * @Description: 更新用户信息，除邮箱
    * @Param: [id, nickname, avatar]
    * */
    @Update("update user_info set nickname = #{nickname}, avatar = #{avatar} where id = #{id}")
    void updateUserInfoWithoutEmail(Integer id, String nickname, String avatar);
}
