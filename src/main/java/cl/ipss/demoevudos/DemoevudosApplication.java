package cl.ipss.demoevudos;

import java.sql.DriverManager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.sql.Connection;



@SpringBootApplication
public class DemoevudosApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoevudosApplication.class, args);
	}

	@Bean
    public CommandLineRunner testConnection() {
        return args -> {
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/demoevudos",
                    "demoevudos",
                    "demoevudos")) {
                System.out.println("Conexión a PostgreSQL exitosa!");
            } catch (Exception e) {
                System.out.println("Error al conectar a PostgreSQL: " + e.getMessage());
            }
        };
    }


}
