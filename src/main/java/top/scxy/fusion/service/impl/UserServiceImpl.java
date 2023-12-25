package top.scxy.fusion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.scxy.fusion.constant.UtilConstant;
import top.scxy.fusion.entity.User;
import top.scxy.fusion.entity.UserInfo;
import top.scxy.fusion.mapper.UserMapper;
import top.scxy.fusion.service.AsyncService;
import top.scxy.fusion.service.UserService;
import top.scxy.fusion.utils.BCryptUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AsyncService  asyncService;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    public UserServiceImpl(UserMapper userMapper, RedisTemplate<String, Object> stringRedisTemplate,
                           AsyncService asyncService) {
        this.userMapper = userMapper;
        this.redisTemplate = stringRedisTemplate;
        this.asyncService = asyncService;
    }
    @Override
    public Integer checkUserWithUsernameAndPassword(String username, String password) {
        password = BCryptUtil.encode(password);
        return userMapper.selectByUsernameAndPassword(username, password);
    }
    @Override
    public Integer register(String username, String password, String email, String nickname, String avatar) {
        if (userMapper.selectByUsername(username) != null) {
            return null;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(BCryptUtil.encode(password));
        userMapper.insertUser(user);
        if (user.getId() != null) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(username);
            userInfo.setEmail(email);
            userInfo.setNickname(nickname);
            userInfo.setAvatar(avatar);
            userMapper.insertUserInfo(userInfo);
            // 添加用户信息到Redis
            asyncService.addInfoToRedis(UtilConstant.UserInfoPrefix + user.getId(), userInfo);
            asyncService.addInfoToRedis(UtilConstant.UserIdPrefix + username, user.getId());
            return user.getId();
        }
        return null;
    }
    // TODO 处理用户信息更新，Redis+MySQL保持一致性
    @Override
    public void updateUserInfo(Integer id, String password, String email,  String nickname, String avatar) {
        userMapper.updateUserInfo(id, email, nickname, avatar);
        if (password != null && !password.equals("")) {
            userMapper.updateUserPassword(id, BCryptUtil.encode(password));
        }
    }
    @Override
    public UserInfo getUserInfo(Integer id) {
        // 从redis中获取用户信息
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(UtilConstant.UserInfoPrefix + id);
        if (userInfo == null) {
            // 从数据库中获取用户信息
            userInfo = userMapper.selectById(id);
            asyncService.addInfoToRedis(UtilConstant.UserInfoPrefix + id, userInfo);
            asyncService.addInfoToRedis(UtilConstant.UserIdPrefix + userInfo.getUsername(), id);
            logger.info("从数据库中获取用户信息: " + userInfo.getUsername());
        }
        return userInfo;
    }
    @Override
    public Integer getUserIdByUsername(String username) {
        // 从redis中获取用户id
        Integer id = (Integer) redisTemplate.opsForValue().get(UtilConstant.UserIdPrefix + username);
        logger.info("从Redis中获取用户id: " + username + " id: " + id);
        if (id == null) {
            // 从数据库中获取用户id
            id = userMapper.selectByUsername(username);
            // 先直接返回，异步更新redis
            asyncService.addInfoToRedis(UtilConstant.UserIdPrefix + username, id);
            logger.info("从数据库中获取用户id: " + username);
        }
        return id;
    }
    @Override
    public String getUserEmailByUsername(String username) {
        // 先获取用户id
        Integer id = getUserIdByUsername(username);
        // 再获取用户信息
        UserInfo userInfo = getUserInfo(id);
        return userInfo.getEmail();
    }

    @Override
    public void updateUserPassword(Integer id, String password) {
        userMapper.updateUserPassword(id, BCryptUtil.encode(password));
    }
    @Override
    public void updateUserEmail(Integer id, String email) {
        // TODO MQ异步更新Redis和MySQL
        userMapper.updateUserEmail(id, email);
        // 先直接删除Redis中的用户信息
        redisTemplate.delete(UtilConstant.UserInfoPrefix + id);
    }
    @Override
    public void updateUserInfo(Integer id, String nickname, String avatar) {
        // TODO MQ异步更新Redis和MySQL
        userMapper.updateUserInfoWithoutEmail(id, nickname, avatar);
        // 先直接删除Redis中的用户信息
        redisTemplate.delete(UtilConstant.UserInfoPrefix + id);
    }
    @Override
    public String getUsernameById(Integer id) {
        UserInfo userInfo = getUserInfo(id);
        return userInfo.getUsername();
    }
    @Override
    public Map<Integer, UserInfo> getUserInfoByIds(List<Integer> ids) {
        Map<Integer, UserInfo> userInfoMap = new HashMap<>();
        for (Integer id: ids) {
            UserInfo userInfo = getUserInfo(id);
            userInfoMap.put(id, userInfo);
        }
        return userInfoMap;
    }
}
