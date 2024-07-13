package br.com.iotextchaincast.usecase;

import br.com.iotextchaincast.entity.IOTextChainCast;
import br.com.iotextchaincast.entity.TypeHandler;
import lombok.extern.log4j.Log4j2;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class IOTextChainCastUseCase {

    private TypeHandler typeHandler;

    private List<TypeHandler> typeHandlers;

    public IOTextChainCastUseCase(List<TypeHandler> typeHandlers) {
        this.typeHandlers = typeHandlers;
    }

    private void init() {
        typeHandler = TypeHandler.link(typeHandlers);
    }

    public Object toObject(String txt, Object obj) {
        init();
        StringBuilder sb = new StringBuilder(txt);
        List<Field> fields = builderOrderFields(obj);
        fields.forEach(field -> {
            IOTextChainCast ioChainCast = field.getAnnotation(IOTextChainCast.class);
            try{
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), obj.getClass());
                Method setter = propertyDescriptor.getWriteMethod();
                Object objCasting = typeHandler.check(sb.substring(0,ioChainCast.length()),
                        ioChainCast, propertyDescriptor.getPropertyType());
                setter.invoke(obj, objCasting);
                sb.delete(0,ioChainCast.length());
            } catch(IntrospectionException e) {
                log.error("Setter inexistente para o atributo [{}] da classe [{}]",
                        field.getName(), obj.getClass().getSimpleName());
            } catch(Exception e) {
                log.error(e);
            }
        });
        return obj;
    }

    public String toText(Object obj) {
        init();
        StringBuilder sb = new StringBuilder("");
        List<Field> fields = builderOrderFields(obj);

        fields.forEach(field -> {
            IOTextChainCast ioChainCast = field.getAnnotation(IOTextChainCast.class);
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), obj.getClass());
                Method getter = descriptor.getReadMethod();
                Object objGetterField = getter.invoke(obj);
                String str = typeHandler.check(objGetterField, ioChainCast);
                sb.append(str);
            } catch(IntrospectionException e) {
                log.error("Getter inexistente para o atributo [{}] da classe [{}]",
                        field.getName(), obj.getClass().getSimpleName());
            } catch (Exception e) {
                log.error(e);
            }
        });

        return sb.toString();
    }



    private List<Field> builderOrderFields(Object obj) {
        List<Field> fields = List.of(obj.getClass().getDeclaredFields());
        return fields.stream()
                .filter(field -> field.isAnnotationPresent(IOTextChainCast.class))
                .sorted((field1, field2) -> Integer.compare(field1.getAnnotation(IOTextChainCast.class).order(),
                        field2.getAnnotation(IOTextChainCast.class).order()))
                .toList();
    }



}
