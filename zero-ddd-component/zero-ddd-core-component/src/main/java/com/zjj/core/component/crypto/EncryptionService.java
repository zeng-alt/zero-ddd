package com.zjj.core.component.crypto;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月20日 21:31
 */
public interface EncryptionService {

    String decrypt(String password);


    String encrypt(String password);
}
