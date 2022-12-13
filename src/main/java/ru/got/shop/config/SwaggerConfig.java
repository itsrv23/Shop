package ru.got.shop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Api-First-Approach Online-Shop-Application",
        description = "Разработка по методу API FIRST командой \"GOT\"",
        contact = @Contact(name = "Git repo", url = "https://github.com/itsrv23/Shop"),
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")),
//        servers = @Server(url = "http://localhost:8080"),
        tags = @Tag(name = "Auth"))
@Configuration
public class SwaggerConfig {
}
