package br.com.iotextchaincast.external.impl;

import br.com.iotextchaincast.entity.IOTextChainCast;
import br.com.iotextchaincast.entity.TypeHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class FloatTypeHandler extends TypeHandler {
    @Override
    public Object check(String text, IOTextChainCast ioChainCast, Class<?> clazz) {
        if (checkType(clazz)) {
            BigDecimal bd = toBigDecimal(text, ioChainCast);
            return Float.valueOf(bd.toString());
        }

        return this.checkNext(text, ioChainCast, clazz);
    }

    @Override
    public String check(Object obj, IOTextChainCast ioChainCast) {
        if (checkType(obj.getClass())) {
            Float floatValue = (float) obj;
            String bdText = bigDecimalToText(BigDecimal.valueOf(floatValue), ioChainCast);
            return formatString(bdText, ioChainCast);
        }
        return this.checkNext(obj, ioChainCast);
    }

    @Override
    public boolean checkType(Class<?> clazz) {
        return Optional.ofNullable(clazz)
                .filter(c -> clazz.equals(Float.class) || clazz.equals(float.class))
                .map(c -> true)
                .orElse(false);
    }
}
