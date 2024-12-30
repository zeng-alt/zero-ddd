package com.zjj.auth.controller;


import java.io.Serializable;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月30日 20:50
 * @version 1.0
 */
public record CaptchaVO(String captchaKey, String captchaImg) implements Serializable {
}
