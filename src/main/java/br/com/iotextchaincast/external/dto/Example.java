package br.com.iotextchaincast.external.dto;

import br.com.iotextchaincast.entity.IOTextChainCast;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Example {


    @IOTextChainCast(length = 2, order = 1, align = "R", trelling = "0")
    private Integer numInteger;

    @IOTextChainCast(length = 3, order = 2, align = "R", trelling = "0")
    private int numInt;

    @IOTextChainCast(length = 2, order = 3, align = "R", trelling = "0")
    private Long numLong;

    @IOTextChainCast(length = 3, order = 4, align = "R", trelling = "0")
    private long numLongPrimitive;

    @IOTextChainCast(length = 5, order = 5)
    private String strValue;

    @IOTextChainCast(length = 15, order = 6, align = "R", trelling = "0",
            decimalPrecision = 2, decimalMovePoint = 2)
    private BigDecimal bdValue;

    @IOTextChainCast(length = 14, order = 7, pattern = "ddMMyyyyHHmmss")
    private LocalDateTime date;

    @IOTextChainCast(length = 15, order = 8, align = "R", trelling = "0",
            decimalPrecision = 2, decimalMovePoint = 2)
    private Double doubleValue;

    @IOTextChainCast(length = 15, order = 9, align = "R", trelling = "0",
            decimalPrecision = 2, decimalMovePoint = 2)
    private double doubleValuePrimitive;

    @IOTextChainCast(length = 15, order = 10, align = "R", trelling = "0",
            decimalPrecision = 3, decimalMovePoint = 3)
    private Float floatValue;

    @IOTextChainCast(length = 15, order = 11, align = "R", trelling = "0",
            decimalPrecision = 3, decimalMovePoint = 3)
    private float floatValuePrimitive;


}
