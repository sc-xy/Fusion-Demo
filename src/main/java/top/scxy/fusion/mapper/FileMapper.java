package top.scxy.fusion.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.scxy.fusion.entity.File;

@Mapper
@Repository
public interface FileMapper {
    /*
    * @Description: 上传文件, 返回文件id
    * @Param: [file]
    * @return: java.lang.Integer
    * */
    @Insert("insert into file(name, author_id, create_time) values(#{name}, #{author_id}, #{create_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer uploadFile(File file);
    /*
    * @Description: 删除文件
    * @Param: [id]
    * @return: java.lang.Integer
    * */
    @Delete("delete from file where id = #{id}")
    Integer deleteFile(Integer id);
    /*
    * @Description: 根据文件id获取文件
    * @Param: [id]
    * @return: top.scxy.fusion.entity.File
    * */
    @Select("select * from file where id = #{id}")
    File getFileById(Integer id);
    /*
    * @Description: 获取用户文件列表，按需分页
    * @Param: [id，pageStart，pageSize]
    * @return: java.util.List<top.scxy.fusion.entity.File>
    * */
    @Select("select * from file where author_id = #{id} limit #{pageStart}, #{pageSize}")
    java.util.List<File> getFileListByUserId(Integer id, Integer pageStart, Integer pageSize);
    /*
    * @Description: 获取用户文件总数
    * @Param: [id]
    * @return: java.lang.Integer
    * */
    @Select("select count(*) from file where author_id = #{id}")
    Integer getFileCountByUserId(Integer id);
}
