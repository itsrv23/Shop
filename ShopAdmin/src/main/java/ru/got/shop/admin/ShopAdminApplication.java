package ru.got.shop.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class ShopAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopAdminApplication.class, args);
    }

}
