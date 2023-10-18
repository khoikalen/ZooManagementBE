package com.fzoo.zoomanagementsystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                description = "Api documentation for Zoo Management",
                title = "Api specification - Zoo Management",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Zoo Management System",
                        url = "/"
                ),
                @Server(
                        description = "Official api website",
                        url = "https://zouzoumanagement.xyz"
                )
        }
)
public class OpenApiConfiguration {
}
