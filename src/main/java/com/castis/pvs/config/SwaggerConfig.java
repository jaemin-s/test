package com.castis.pvs.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// SwaggerConfig.java
@OpenAPIDefinition(
    // servers = {@Server(url ="https://dev-pvs.ochoice.co.kr/PVS/", description = "Defalut Server URL")},
    // servers = {@Server(url ="/", description = "Defalut Server URL")},
    info = @Info(title = "PVS SO 연계 API 명세서",
                description = "PVS SO 연계 API 명세서",
                version = "v2"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {""};

        return GroupedOpenApi.builder()
                .group("PVS API")
                .pathsToMatch(paths)
                .build();
    }
}