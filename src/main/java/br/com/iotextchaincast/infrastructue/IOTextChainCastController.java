package br.com.iotextchaincast.infrastructue;

import br.com.iotextchaincast.entity.TypeHandler;
import br.com.iotextchaincast.usecase.IOTextChainCastUseCase;

import java.util.List;

public class IOTextChainCastController {

    List<TypeHandler> typeHandlerList;
    IOTextChainCastUseCase ioChainCast;

    public IOTextChainCastController(List<TypeHandler> typeHandlerList) {
        this.typeHandlerList = typeHandlerList;
        this.ioChainCast = new IOTextChainCastUseCase(this.typeHandlerList);
    }

    public Object toObject(String txt, Object obj) {
        return ioChainCast.toObject(txt, obj);
    }

    public String toText(Object obj) {
        return ioChainCast.toText(obj);
    }

}
