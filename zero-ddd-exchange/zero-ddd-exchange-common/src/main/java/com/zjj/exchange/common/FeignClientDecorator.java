package com.zjj.exchange.common;


import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class FeignClientDecorator implements Decoder {

    private final Decoder delegate;
    private final String tenantToken;

    public FeignClientDecorator(Decoder delegate, String tenantToken) {
        this.delegate = delegate;
        this.tenantToken = tenantToken;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        Map<String, Collection<String>> headers = response.request().headers();
        if (headers.containsKey(tenantToken)) {
            Collection<String> tenantHeader = headers.get(tenantToken);
            if (!CollectionUtils.isEmpty(tenantHeader)) {
                TenantContextHolder.setTenantId(tenantHeader.iterator().next());
            }
        }
        return delegate.decode(response, type);
    }
}