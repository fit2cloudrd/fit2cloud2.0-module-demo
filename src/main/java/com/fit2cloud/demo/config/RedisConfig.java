package com.fit2cloud.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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

    @Bean("lettuceConnectionFactory")
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(environment.getProperty("redis.hostname", "localhost"));
        configuration.setPassword(RedisPassword.of(environment.getProperty("redis.password")));
        configuration.setPort(environment.getProperty("redis.port", Integer.class, 6379));
        configuration.setDatabase(environment.getProperty("redis.database", Integer.class, 0));
        return new LettuceConnectionFactory(configuration);
    }


    @Bean("sessionRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        lettuceConnectionFactory.setDatabase(15);
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
