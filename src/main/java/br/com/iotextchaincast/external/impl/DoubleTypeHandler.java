package br.com.iotextchaincast.external.impl;

import br.com.iotextchaincast.entity.IOTextChainCast;
import br.com.iotextchaincast.entity.TypeHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class DoubleTypeHandler extends TypeHandler {
    @Override
    public Object check(String text, IOTextChainCast ioChainCast, Class<?> clazz) {
        if (checkType(clazz))
            return toBigDecimal(text, ioChainCast).doubleValue();

        return this.checkNext(text, ioChainCast, clazz);
    }

    @Override
    public String check(Object obj, IOTextChainCast ioChainCast) {
        if (checkType(obj.getClass())) {
            Double doubleValue = (double) obj;
            String bdText = bigDecimalToText(BigDecimal.valueOf(doubleValue), ioChainCast);
            return formatString(bdText, ioChainCast);
        }
        return this.checkNext(obj, ioChainCast);
    }

    @Override
    public boolean checkType(Class<?> clazz) {
        return Optional.ofNullable(clazz)
                .filter(c -> clazz.equals(Double.class) || clazz.equals(double.class))
                .map(c -> true)
                .orElse(false);
    }
}
