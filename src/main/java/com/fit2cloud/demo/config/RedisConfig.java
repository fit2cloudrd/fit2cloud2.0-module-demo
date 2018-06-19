package com.fit2cloud.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.Resource;

/**
 * Author: chunxing
 * Date: 2018/5/4  上午10:25
 * Description:
 */
@Configuration
public class RedisConfig {

    @Resource
    private Environment environment;


    /**
     * 原子性执行lua脚本，执行lua脚本期间redis不执行任何命令，超时时间默认5s。
     * Example
     *
     * @return
     * @Autowired private DefaultRedisScript<Boolean> distributedLockScript;
     * @Test public void distributedLock(String lockName, String lockValue, Long seconds) {
     * Boolean execute = stringRedisTemplate.execute(distributedLockScript, Collections.singletonList(lockName), lockValue, String.valueOf(seconds));
     * System.out.println(execute);
     * }
     */
    @Bean("distributedLockScript")
    public DefaultRedisScript<Boolean> distributedLock() {
        DefaultRedisScript<Boolean> defaultRedisScript = new DefaultRedisScript<>();
        ClassPathResource resource = new ClassPathResource("script/lock.lua");
        defaultRedisScript.setLocation(resource);
        defaultRedisScript.setResultType(Boolean.class);
        return defaultRedisScript;
    }
}
