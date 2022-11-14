package ru.timotege.vk;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@OpenAPIDefinition(info =
@Info(title = "VK", version = "1.6.12", description = "VK API Documentation"))
public class VkApplication {

	public static void main(String[] args) {
		SpringApplication.run(VkApplication.class, args);
	}

}
