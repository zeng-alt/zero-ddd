package com.zjj.autoconfigure.component.l2cache;

import lombok.RequiredArgsConstructor;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月16日 14:13
 */
public interface SequenceServerIdGenerator {

    Object nextId();
}
