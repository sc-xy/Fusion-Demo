package top.scxy.fusion.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import top.scxy.fusion.entity.Shield;

@Mapper
public interface ShieldMapper {
    /*
    * @Description: 添加屏蔽
    * @Param: [user_id, shield_id]
    * @return: Integer
    * */
    @Insert("insert into shield(user_name, shield_name) values(#{user_name}, #{shield_name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertShield(Shield shield);
    /*
    * @Description: 查询是否已经屏蔽
    * */
    @Select("select count(*) from shield where user_name = #{user_name} and shield_name = #{shield_name}")
    Integer selectShield(String user_name, String shield_name);
}
