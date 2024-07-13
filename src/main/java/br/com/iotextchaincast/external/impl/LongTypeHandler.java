package br.com.iotextchaincast.external.impl;

import br.com.iotextchaincast.entity.IOTextChainCast;
import br.com.iotextchaincast.entity.TypeHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LongTypeHandler extends TypeHandler {

    @Override
    public Object check(String text, IOTextChainCast ioChainCast, Class<?> clazz) {
        if (checkType(clazz))
            return Long.valueOf(text);

        return this.checkNext(text, ioChainCast, clazz);
    }

    @Override
    public String check(Object obj, IOTextChainCast ioChainCast) {
        if (checkType(obj.getClass()))
            return formatString(String.valueOf(obj), ioChainCast);

        return this.checkNext(obj, ioChainCast);
    }

    @Override
    public boolean checkType(Class<?> clazz) {
        return Optional.ofNullable(clazz)
                .filter(c -> clazz.equals(Long.class) || clazz.equals(long.class))
                .map(c -> Boolean.TRUE)
                .orElse(Boolean.FALSE);
    }
}
