package top.scxy.fusion.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.scxy.fusion.service.AsyncService;
import top.scxy.fusion.utils.UploadUtil;

import java.util.Arrays;

@Service
@Transactional
public class AsyncServiceImpl implements AsyncService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final MailSender mailSender;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AsyncServiceImpl.class);
    @Autowired
    public AsyncServiceImpl(RedisTemplate<String, Object> redisTemplate, MailSender mailSender,
                            StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.mailSender = mailSender;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @Async("asyncExecutor")
    @Override
    public void sendEmailVerifyCode(SimpleMailMessage message) {
        logger.info("Async send email: " + Arrays.toString(message.getTo()));
        mailSender.send(message);
    }
    @Async("asyncExecutor")
    @Override
    public void addInfoToRedis(String key, Object value) {
        if (value == null) {
            return;
        }
        logger.info("Async add info to Redis: " + key + " " + value);
        redisTemplate.opsForValue().set(key, value);
    }
    @Async("asyncExecutor")
    @Override
    public void addVerifyCodeToRedis(String key, String value, long expireTime, java.util.concurrent.TimeUnit timeUnit) {
        if (value == null) {
            return;
        }
        logger.info("Async add verify code to Redis: " + key + " " + value);
        stringRedisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
    }
    @Async("asyncExecutor")
    @Override
    public void deleteVerifyCodeFromRedis(String key) {
        logger.info("Async delete verify code from Redis: " + key);
        stringRedisTemplate.delete(key);
    }
    @Async("asyncExecutor")
    @Override
    public void deleteFileFromQiniu(String key) {
        logger.info("Async delete file from Qiniu: " + key);
        UploadUtil.deleteFileByFileName(key);
    }
}
