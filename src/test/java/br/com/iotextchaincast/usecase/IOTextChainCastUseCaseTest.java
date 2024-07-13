package br.com.iotextchaincast.usecase;

import br.com.iotextchaincast.entity.TypeHandler;
import br.com.iotextchaincast.external.dto.Example;
import br.com.iotextchaincast.infrastructue.IOTextChainCastController;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2
@SpringBootTest
public class IOTextChainCastUseCaseTest {

    @Autowired
    private List<TypeHandler> typeHandlerList;

    @Test
    public void testToObject() {
        IOTextChainCastController ioChainCastController = new IOTextChainCastController(typeHandlerList);
        Example example = (Example) ioChainCastController.toObject("0201203123TESTE00000123456781230122024155959000001234567800000001234567801000001234567123000001234567001", new Example());
        log.info("Objeto formatado: {}", example.toString());
        assertNotNull(example);
    }

    @Test
    public void testToText() {
        IOTextChainCastController ioChainCastController = new IOTextChainCastController(typeHandlerList);
        Example example = (Example) ioChainCastController.toObject("0201203123TESTE00000123456781230122024155959000001234567800000001234567801000001234567123000001234567001", new Example());
        assertNotNull(example); String txtFormatter = ioChainCastController.toText(example);
        log.info("Texto formatado: {}", txtFormatter);
        assertNotNull(txtFormatter);
    }

}
