package com.fantasticos.studentservice;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class StudentServiceApp {
	@Value("${com.studentService.configToken.readTimeout}")
	private int readTimeout;
	@Value("${com.studentService.configToken.connectionTimeout}")
	private int connectionTimeout;
	@Value("${com.studentService.configToken.jwkUrl}")
	private String jwkUrl;

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(StudentServiceApp.class, args);
	}
	
	@Bean
	public ConfigurableJWTProcessor configurableJWTProcessor() throws MalformedURLException {
		ResourceRetriever resourceRetriever=new DefaultResourceRetriever(connectionTimeout,readTimeout);
		URL jwkURL=new URL(jwkUrl);
		JWKSource jwkSource=new RemoteJWKSet(jwkURL,resourceRetriever);
		ConfigurableJWTProcessor jwtProcessor=new DefaultJWTProcessor();
		JWSKeySelector keySelector= new JWSVerificationKeySelector(JWSAlgorithm.RS256,jwkSource);
		jwtProcessor.setJWSKeySelector(keySelector);
		return jwtProcessor;
	}
}
