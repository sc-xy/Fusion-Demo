package top.scxy.fusion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.scxy.fusion.constant.UtilConstant;
import top.scxy.fusion.service.AsyncService;
import top.scxy.fusion.service.EmailService;
import top.scxy.fusion.utils.MailUtil;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AsyncService asyncService;
    private final StringRedisTemplate redisTemplate;
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    public EmailServiceImpl(AsyncService asyncService, StringRedisTemplate redisTemplate) {
        this.asyncService = asyncService;
        this.redisTemplate = redisTemplate;
    }
    @Override
    public boolean sendVerifyEmail(String to, String prefix) {
        String verifyCode = MailUtil.getRandomCode();
        logger.info(prefix + to + " verifyCode: " + verifyCode);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(UtilConstant.EmailFrom+'<'+from+'>');
        message.setTo(to);
        message.setSubject(UtilConstant.EmailSubject);
        message.setText(MailUtil.getEmailContent(verifyCode));
        asyncService.addVerifyCodeToRedis(prefix + to, verifyCode,
                UtilConstant.EmailVerifyCodeExpireTime, TimeUnit.MINUTES);
        asyncService.sendEmailVerifyCode(message);
        return true;
    }
    @Override
    public boolean verifyCode(String email, String code, String prefix) {
        String verifyCode = redisTemplate.opsForValue().get(prefix + email);
        logger.info(prefix + email + " verifyCode: " + verifyCode);
        if (verifyCode != null && verifyCode.equals(code)) {
            asyncService.deleteVerifyCodeFromRedis(prefix + email);
            logger.info(prefix + email + " verifyCode: " + verifyCode + " success");
            return true;
        }
        logger.info(prefix + email + " verifyCode: " + verifyCode + " fail" + " code: " + code);
        return false;
    }
}
