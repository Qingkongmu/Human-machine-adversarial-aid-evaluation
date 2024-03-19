package com.biggiser.streetviewratingapp.mapper;

import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.*;

import com.biggiser.streetviewratingapp.beans.BaseScore;
import com.biggiser.streetviewratingapp.beans.LogBean;
import com.biggiser.streetviewratingapp.beans.UserBean;


@Mapper
public interface UserMapper {
    
    
    /**
     * 登录
     */
    @Select("select * from t_user where username=#{un} and password=#{pw}")
    @Results({
        @Result(id=true,property="id",column="id",javaType=Integer.class),
        @Result(property="username",column="username",javaType=String.class),
        @Result(property="password",column="password",javaType=String.class)        
    })
    public UserBean login(@Param("un")String username,@Param("pw")String password);
    /**
     * 新增用戶
     */
    @Insert("insert into t_user(id,username,password,country,province,city,occupation,"
    		+ "gender,incomelevel,last_ip,last_visit) values(null,#{user.username},#{user.password},#{user.country},"
    		+ "#{user.province},#{user.city},#{user.occupation},#{user.gender},#{user.incomelevel},#{user.last_ip},#{user.last_visit})")
    @Options(useGeneratedKeys=true,keyProperty="user.id")
    public int insertUser(@Param("user")UserBean user) throws Exception;
    
    /**
     * 添加打分和预测的记录
     */
    @Insert("insert into t_log(logid,userid,imgname,safetyuser,comfortuser,convenienceuser,attractivenessuser,"
    		+ "safetypredict,comfortpredict,conveniencepredict,attractivenesspredict) "
    		+ "values(null,#{log.userid},#{log.imgname},#{log.safetyuser},#{log.comfortuser},#{log.convenienceuser},#{log.attractivenessuser},"
    		+ "#{log.safetypredict},#{log.comfortpredict},#{log.conveniencepredict},#{log.attractivenesspredict})")
    @Options(useGeneratedKeys=true,keyProperty="log.logid")
    public int insertLog(@Param("log")LogBean log) throws Exception;
    
    /**
     * 修改用戶
     */
    @Update(" update t_user set username=#{u.username},password=#{u.password},account=#{u.account} where id=#{id}")
    public int updateUser (@Param("u")UserBean user,@Param("id")int id) throws Exception;
    
     /**
      * 刪除用戶
      */
    @Delete(" delete from t_user where id=#{id}  ")
    public int deleteUser(int id) throws Exception;
    
    
    /**
     * 根据id查询用户信息
     */
    
    @Select(" select * from t_user where id=#{id}")
    @Results({
        
        @Result(id=true,property="id",column="id",javaType=Integer.class),
        @Result(property="username",column="username",javaType=String.class),
        @Result(property="password",column="password",javaType=String.class),
        @Result(property="account",column="account",javaType=Double.class)
    })
    public UserBean selectUserById(int id) throws Exception;
     /**
      * 查询所有的用户信息
      */
    
    @Select(" select * from t_user")
/*    @ResultMap("userMap")*/
    public List<UserBean> selectAllUser() throws Exception;
    
    
    /**
     * 批量增加
     */
   public int batchInsertUser(@Param("users")List<UserBean> user) throws Exception;
   
   /**
    * 批量删除
    */
   public int batchDeleteUser(@Param("list")List<Integer> list) throws Exception;
   
   
   /**
    * 分页查询数据
    */
   public List<UserBean> pagerUser(Map<String, Object> parmas) throws Exception;
   
   /**
    * 分页统计数据
    */
    public int countUser(Map<String, Object> parmas) throws Exception;       
}