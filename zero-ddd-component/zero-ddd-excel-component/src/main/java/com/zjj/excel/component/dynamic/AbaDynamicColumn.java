package com.zjj.excel.component.dynamic;

import com.zjj.i18n.component.MessageSourceHelper;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月28日 15:53
 */
@Getter
public abstract class AbaDynamicColumn<T extends DynamicEntity> implements InterfaceDynamicColumn<T> {

    @ExcelDynamicProperty
    protected @Delegate List<T> dynamicEntity = new ArrayList<>();


}
