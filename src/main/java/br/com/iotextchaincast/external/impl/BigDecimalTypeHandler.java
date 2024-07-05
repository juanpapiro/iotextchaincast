package br.com.iotextchaincast.external.impl;

import br.com.iotextchaincast.entity.IOTextChainCast;
import br.com.iotextchaincast.entity.TypeHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class BigDecimalTypeHandler extends TypeHandler {

    @Override
    public Object check(String text, IOTextChainCast ioChainCast, Class<?> clazz) {
        if (checkType(clazz))
            return toBigDecimal(text, ioChainCast);

        return this.checkNext(text, ioChainCast, clazz);
    }

    @Override
    public String check(Object obj, IOTextChainCast ioChainCast) {
        if (checkType(obj.getClass())) {
            String val = bigDecimalToText(obj, ioChainCast);
            return formatString(val, ioChainCast);
        }
        return this.checkNext(obj, ioChainCast);
    }

    @Override
    public boolean checkType(Class<?> clazz) {
        return Optional.ofNullable(clazz)
                .filter(c -> clazz.equals(BigDecimal.class))
                .map(c -> true)
                .orElse(false);
    }
}
