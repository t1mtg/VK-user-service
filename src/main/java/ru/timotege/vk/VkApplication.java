package ru.timotege.vk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VkApplication {

	public static void main(String[] args) {
		SpringApplication.run(VkApplication.class, args);
	}

}
