package com.sygamer.starreduxgm;

import org.apache.http.impl.client.HttpClientBuilder;
import org.mapstruct.Qualifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class StarreduxgmserverApplication {

	private static final Logger log = LoggerFactory.getLogger(StarreduxgmserverApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StarreduxgmserverApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				HttpClientBuilder.create()
						.disableContentCompression()
						.setMaxConnPerRoute(1000)
						.setMaxConnTotal(2000).build());
		RestTemplate template = builder.build();
		template.setRequestFactory(clientHttpRequestFactory);
		return template;
	}
}
