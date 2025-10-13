package univer.university;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "univer")
public class UniversityApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }


    @Bean
    public CommandLineRunner checkBeans(ApplicationContext context) {
        return args -> {
            System.out.println("=== Beans list ===");
            for (String name : context.getBeanDefinitionNames()) {
                if (name.toLowerCase().contains("user")) {
                    System.out.println("➡️ " + name);
                }
            }
            System.out.println("==================");
        };
    }

}
