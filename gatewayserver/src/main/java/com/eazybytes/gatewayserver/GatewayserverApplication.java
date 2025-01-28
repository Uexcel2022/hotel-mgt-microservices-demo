package com.eazybytes.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}
@Bean
	public RouteLocator customRoutes(RouteLocatorBuilder routeLocatorBuilder) {
		return  routeLocatorBuilder.routes()
				.route(p->p.path("/hotel/regular/**")
						.filters(rw->rw
								.rewritePath("/hotel/regular/(?<segment>.*)","/${segment}")
								.circuitBreaker(config -> config.setName("regularCircuitBreaker")
										.setFallbackUri("forward:/contactSupport"))
						).uri("lb://regular")

				)
				.route(p->p.path("/hotel/executive/**")
						.filters(rw->rw
								.rewritePath("/hotel/executive/(?<segment>.*)","/${segment}")
						).uri("lb://executive")
				)

				.route(p->p.path("/hotel/apartment/**")
						.filters(rw->rw
								.rewritePath("/hotel/apartment/(?<segment>.*)","/${segment}")
						).uri("lb://apartment")
				)
				.build();
	}

}
