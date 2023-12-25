package top.scxy.fusion.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.scxy.fusion.entity.Shield;
import top.scxy.fusion.mapper.ShieldMapper;
import top.scxy.fusion.service.ShieldService;

@Service
@Transactional
public class ShieldServiceImpl implements ShieldService {
    private final ShieldMapper shieldMapper;

    public ShieldServiceImpl(ShieldMapper shieldMapper) {
        this.shieldMapper = shieldMapper;
    }

    @Override
    public Integer shield(String user_name, String shield_name) {
        Shield shield = new Shield();
        shield.setUser_name(user_name);
        shield.setShield_name(shield_name);
        shieldMapper.insertShield(shield);
        return shield.getId() == null ? 0 : 1;
    }

    @Override
    public Integer selectShield(String user_name, String shield_name) {
        return shieldMapper.selectShield(user_name, shield_name);
    }
}
