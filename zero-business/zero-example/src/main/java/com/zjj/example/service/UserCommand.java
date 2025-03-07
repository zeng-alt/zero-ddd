package com.zjj.example.service;

import lombok.Data;
import org.jmolecules.architecture.cqrs.Command;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月20日 17:07
 */
@Command
@Data
public class UserCommand {
    private String name;
    private Integer age;
}
