package ru.pivovarov.t1.SupplierService;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.pivovarov.t1.SupplierService.model.Category;
import ru.pivovarov.t1.SupplierService.model.Product;
import ru.pivovarov.t1.SupplierService.repository.CategoryRepository;
import ru.pivovarov.t1.SupplierService.repository.ProductRepository;

import java.util.List;

@SpringBootApplication
public class SupplierServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplierServiceApplication.class, args);
	}

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;
	@PostConstruct
	public void init() {
		Category snackCategory = Category.builder()
				.name("snack")
				.build();
		snackCategory = categoryRepository.save(snackCategory);
		Category cheeseCategory = Category.builder()
				.name("cheese")
				.build();
		cheeseCategory = categoryRepository.save(cheeseCategory);
		Category meatCategory = Category.builder()
				.name("meat")
				.build();
		meatCategory = categoryRepository.save(meatCategory);
		Product lays = Product.builder()
				.category(snackCategory)
				.price(8)
				.name("Lays")
				.description("Lays crab")
				.build();
		Product cheder = Product.builder()
				.category(cheeseCategory)
				.price(6)
				.name("cheder")
				.description("cheese cheder")
				.build();
		Product russian = Product.builder()
				.category(cheeseCategory)
				.price(5)
				.name("russian")
				.description("cheese russian")
				.build();
		Product saltBeef = Product.builder()
				.category(meatCategory)
				.price(11)
				.name("beef")
				.description("salt beef")
				.build();
		Product pepperBeef = Product.builder()
				.category(meatCategory)
				.price(10)
				.name("beef")
				.description("pepper beef")
				.build();
		Product rawBeef = Product.builder()
				.category(meatCategory)
				.price(8)
				.name("beef")
				.description("raw beef")
				.build();
		productRepository.saveAll(List.of(lays, cheder, russian, saltBeef, pepperBeef, rawBeef));
	}
}
