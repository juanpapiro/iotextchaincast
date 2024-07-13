package br.com.iotextchaincast;

import br.com.iotextchaincast.entity.TypeHandler;
import br.com.iotextchaincast.external.dto.Example;
import br.com.iotextchaincast.infrastructue.IOTextChainCastController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class IochaincastApplication {

	public static void main(String[] args) {
		SpringApplication.run(IochaincastApplication.class, args);
	}


}
