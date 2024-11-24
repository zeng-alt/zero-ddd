package com.zjj.domain.component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月18日 21:04
 */
public interface CommandHandler<H extends Command> {

    public void handler(H h);
}
