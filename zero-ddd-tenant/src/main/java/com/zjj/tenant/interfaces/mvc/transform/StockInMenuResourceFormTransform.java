package com.zjj.tenant.interfaces.mvc.transform;

import com.zjj.tenant.domain.menu.cmd.StockInMenuResourceCmd;
import com.zjj.tenant.interfaces.mvc.form.StockInMenuResourceForm;
import org.mapstruct.Mapper;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 20:53
 */
@Mapper(componentModel = "spring")
public interface StockInMenuResourceFormTransform {

    public StockInMenuResourceCmd transform(StockInMenuResourceForm stockInMenuResourceForm);
}
