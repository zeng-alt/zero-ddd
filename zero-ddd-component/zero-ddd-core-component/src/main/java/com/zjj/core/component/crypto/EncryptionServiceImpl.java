package com.zjj.core.component.crypto;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月03日 14:50
 */
@Slf4j
public class EncryptionServiceImpl implements EncryptionService {

    private final SymmetricCrypto aes;

    public EncryptionServiceImpl(String key) {
        this.aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key.getBytes());
    }

    @Override
    public String decrypt(String password) {
        return aes.decryptStr(password, CharsetUtil.CHARSET_UTF_8);
    }

    @Override
    public String encrypt(String password) {
        return aes.encryptHex(password);
    }
}
