package com.zjj.main.domain.resource.http;

import com.zjj.main.domain.resource.http.cmd.DeleteHttpResourceCmd;
import com.zjj.main.domain.resource.http.cmd.StockInHttpResourceCmd;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:14
 */
@Component
public record HttpResourceHandler(
        HttpResourceRepository repository,
        HttpResourceFactory factory

) {

    @CommandHandler
    public void handler(StockInHttpResourceCmd cmd) {
        this.factory.create(cmd).forEach(h -> h.stock(cmd));
    }

    @CommandHandler
    public void handler(DeleteHttpResourceCmd cmd) {
        this.repository.findById(cmd.id()).forEach(HttpResourceAgg::delete);
    }
}
