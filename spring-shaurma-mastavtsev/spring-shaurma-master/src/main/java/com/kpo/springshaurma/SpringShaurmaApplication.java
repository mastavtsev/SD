package com.kpo.springshaurma;

import com.kpo.springshaurma.model.Ingredient;
import com.kpo.springshaurma.repository.IngredientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
)
@EnableJpaRepositories("com.kpo.springshaurma.repository")

public class SpringShaurmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringShaurmaApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo) {
		return args -> {
			repo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
			repo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
			repo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
			repo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
		};
	}
}
