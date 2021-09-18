package hcl.practice.inventory.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	 @Bean
	 @LoadBalanced
	 public RestTemplate restTemplate() {
		 ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
		 RestTemplate restTemplate=new RestTemplate(requestFactory);
		 List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		 if (CollectionUtils.isEmpty(interceptors)) {
	            interceptors = new ArrayList<>();
	        }
		 interceptors.add(new RestTemplateInterceptor());
	     restTemplate.setInterceptors(interceptors);
	     
	     return restTemplate;
	 }

	 
}
