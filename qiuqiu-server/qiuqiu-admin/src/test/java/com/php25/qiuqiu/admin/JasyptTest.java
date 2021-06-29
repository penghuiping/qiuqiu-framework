package com.php25.qiuqiu.admin;

import lombok.extern.log4j.Log4j2;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.Test;

/**
 * @author penghuiping
 * @date 2021/4/4 13:57
 */
@Log4j2
public class JasyptTest {

    @Test
    public void test() {
        String password = "@WSXcft6#EDCvgy7";
        //redis
        String redis_password = "123456";

        //mysql
        String mysql_user= "root";
        String mysql_password= "123456";

        //rabbit
        String rabbit_user = "guest";
        String rabbit_password = "guest";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName(null);
        config.setProviderClassName(null);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.salt.NoOpIVGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        log.info("需要加密的数据加密后为:redis_password:{}",encryptor.encrypt(redis_password));
        log.info("需要加密的数据加密后为:mysql_user:{}",encryptor.encrypt(mysql_user));
        log.info("需要加密的数据加密后为:mysql_password:{}",encryptor.encrypt(mysql_password));
        log.info("需要加密的数据加密后为:rabbit_user:{}",encryptor.encrypt(rabbit_user));
        log.info("需要加密的数据加密后为:rabbit_password:{}",encryptor.encrypt(rabbit_password));
    }

    @Test
    public void test1() {
        String password = "@WSXcft6#EDCvgy7";
        //redis
        String redis_password = "eLzU55rt62";

        //mysql
        String mysql_user= "root";
        String mysql_password= "G7bnF83jFt";

        //rabbit
        String rabbit_user = "user";
        String rabbit_password = "F5gWSJxLv7";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName(null);
        config.setProviderClassName(null);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.salt.NoOpIVGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        log.info("需要加密的数据加密后为:redis_password:{}",encryptor.encrypt(redis_password));
        log.info("需要加密的数据加密后为:mysql_user:{}",encryptor.encrypt(mysql_user));
        log.info("需要加密的数据加密后为:mysql_password:{}",encryptor.encrypt(mysql_password));
        log.info("需要加密的数据加密后为:rabbit_user:{}",encryptor.encrypt(rabbit_user));
        log.info("需要加密的数据加密后为:rabbit_password:{}",encryptor.encrypt(rabbit_password));
    }
}
