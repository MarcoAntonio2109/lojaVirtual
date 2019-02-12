package br.com.lojavirtual.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.lojavirtual.service.ProdutoService;

/**
 * Hello world!
 *
 */
@ComponentScan(basePackages = {"br.com.lojavirtual"})
@EnableJpaRepositories(basePackages = {"br.com.lojavirtual"})
@EntityScan(basePackages = {"br.com.lojavirtual", "br.com.lojavirtual.config"})
@SpringBootApplication
public class App implements ApplicationListener<ContextRefreshedEvent>   
{
	@Autowired
	ProdutoService produtoService;
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }

    
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
    	produtoService.carga();
    }
}
