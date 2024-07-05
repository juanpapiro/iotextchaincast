package br.com.iotextchaincast.external.impl;

import br.com.iotextchaincast.entity.IOTextChainCast;
import br.com.iotextchaincast.entity.TypeHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class LocalDateTimeTypeHandler extends TypeHandler {
    @Override
    public Object check(String text, IOTextChainCast ioChainCast, Class<?> clazz) {
        if (checkType(clazz)){
            DateTimeFormatter formatter = patternDefine(ioChainCast);
            return Optional.ofNullable(text)
                    .filter(txt -> !txt.trim().isEmpty())
                    .map(txt -> LocalDateTime.parse(txt.trim(), formatter))
                    .orElse(null);
        }
        return this.checkNext(text, ioChainCast, clazz);
    }

    @Override
    public String check(Object obj, IOTextChainCast ioChainCast) {
        if (checkType(obj.getClass())) {
            DateTimeFormatter formatter = patternDefine(ioChainCast);
            LocalDateTime dt = (LocalDateTime) obj;
            try {
                String dateTimeStr = Optional.ofNullable(dt)
                        .map(dateTime -> dateTime.format(formatter))
                        .orElse("");
                return formatString(dateTimeStr, ioChainCast);
            } catch (Exception e) {
                return "";
            }
        }
        return this.checkNext(obj, ioChainCast);
    }

    @Override
    public boolean checkType(Class<?> clazz) {
        return Optional.ofNullable(clazz)
                .filter(c -> clazz.equals(LocalDateTime.class))
                .map(c -> true)
                .orElse(false);
    }

    private DateTimeFormatter patternDefine(IOTextChainCast ioTextChainCast) {
        return Optional.ofNullable(ioTextChainCast.pattern())
                .filter(pattern -> !pattern.trim().isEmpty())
                .map(pattern -> DateTimeFormatter.ofPattern(pattern.trim()))
                .orElse(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
    }
}
