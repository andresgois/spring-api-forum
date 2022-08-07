package br.com.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport // Pegar dados de páginação do Pageable
@EnableCaching				// habilita o uso de cache
@EnableSwagger2
//public class Application extends SpringBootServletInitializer{
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// Tudo isso para gerar um war
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}*/
}
