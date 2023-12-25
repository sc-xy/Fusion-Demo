package top.scxy.fusion.service;

public interface ShieldService {
    /*
    * @Description: 屏蔽用户
    * @Param: [user_id, shield_id]
    * @return: Integer
    * */
    Integer shield(String user_name, String shield_name);
    /*
    * @Description: 查询是否已经屏蔽
    * @Param: [user_id, shield_id]
    * */
    Integer selectShield(String user_name, String shield_name);
}
