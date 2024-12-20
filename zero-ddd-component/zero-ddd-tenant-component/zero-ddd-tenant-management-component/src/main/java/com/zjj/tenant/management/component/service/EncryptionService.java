package com.zjj.tenant.management.component.service;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月20日 21:31
 */
public interface EncryptionService {

    String decrypt(String strToDecrypt, String secret, String salt);
}
