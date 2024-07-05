package br.com.iotextchaincast.entity;

import br.com.iotextchaincast.external.impl.StringTypeHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public abstract class TypeHandler {

    private TypeHandler nextType;
    private static final String LEFT = "L";


    public abstract Object check(String text, IOTextChainCast ioChainCast, Class<?> clazz);

    public abstract String check(Object obj, IOTextChainCast ioChainCast);

    public abstract boolean checkType(Class<?> clazz);



    public static TypeHandler link(TypeHandler typeFirst, TypeHandler... chain) {
        TypeHandler head = typeFirst;
        for(TypeHandler nextTypeInChain : chain) {
            head.nextType = nextTypeInChain;
            head = nextTypeInChain;
        }
        return typeFirst;
    }

    public static TypeHandler link(List<TypeHandler> typeHandlersChain) {
        List<TypeHandler> chain = typeHandlersChain.stream().collect(Collectors.toList());
        return Optional.ofNullable(chain).filter(list -> !list.isEmpty()).map(handlers -> {
            TypeHandler typeFirst = handlers.get(0);
            TypeHandler head = handlers.get(0);
            handlers.remove(0);
            for(TypeHandler nextTypeInChain : handlers) {
                head.nextType = nextTypeInChain;
                head = nextTypeInChain;
            }
            return typeFirst;
        }).orElse(new StringTypeHandler());
    }


    protected Object checkNext(String text, IOTextChainCast ioChainCast, Class<?> clazz) {
        return Optional.ofNullable(nextType)
                .map(type -> type.check(text, ioChainCast, clazz))
                .orElse(null);
    }

    protected String checkNext(Object obj, IOTextChainCast ioChainCast) {
        return Optional.ofNullable(nextType)
                .map(type -> type.check(obj, ioChainCast))
                .orElse(null);
    }




    public String formatString(String txt, IOTextChainCast ioChainCast) {
        return Optional.ofNullable(txt).map(text -> {
            StringBuilder sb = new StringBuilder(text);
            int lengthTrelling = ioChainCast.length() - sb.length();
            Optional.of(ioChainCast.align())
                    .filter(align -> align.equals(LEFT))
                    .ifPresentOrElse(trelling -> sb.append(ioChainCast.trelling().repeat(lengthTrelling)).toString(),
                            () -> sb.insert(0, ioChainCast.trelling().repeat(lengthTrelling)).toString());
            return sb.toString();
        }).orElse(txt);
    }


    public BigDecimal toBigDecimal(String text, IOTextChainCast ioTextChainCast) {
        text = removeNonNumeric(text);
        return Optional.ofNullable(text)
                    .filter(txt -> !txt.trim().isEmpty())
                    .map(txt ->
                            new BigDecimal(txt)
                                    .movePointLeft(ioTextChainCast.decimalMovePoint())
                                    .setScale(ioTextChainCast.decimalPrecision(), RoundingMode.HALF_EVEN)
                    ).orElse(null);
    }


    public String removeNonNumeric(String value) {
        return Optional.ofNullable(value)
                .map(v -> v.replaceAll("[^ 0-9]", ""))
                .orElse("");
    }


    public String bigDecimalToText(Object value, IOTextChainCast ioTextChainCast) {
        BigDecimal bd = (BigDecimal) value;
        bd = bd.setScale(ioTextChainCast.decimalPrecision(), RoundingMode.HALF_EVEN);
        String bdString = bd.toString();
        return Optional.ofNullable(ioTextChainCast.decimalSeparator())
                .filter(separator -> !separator.trim().isEmpty())
                .map(separator -> decimalSeparatorFormat(bdString,separator))
                .orElse(removeNonNumeric(bdString));
    }

    public String decimalSeparatorFormat(String value, String separator) {
        Optional.ofNullable(separator)
                .filter(sep -> sep.contains(","))
                .ifPresent(sep -> value.replace(".", ","));
        return value;
    }


}
