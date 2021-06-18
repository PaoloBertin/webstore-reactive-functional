package eu.opensource.webstore.router;

import eu.opensource.webstore.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ProductRouter {

    @Bean
    public RouterFunction<ServerResponse> route(ProductHandler productHandler) {

        return RouterFunctions.route(GET("/products/{id:[0-9]+}").and(accept(APPLICATION_JSON)), productHandler::getProduct)
                              .andRoute(GET("/products").and(accept(APPLICATION_JSON)), productHandler::listProducts)
                              .andRoute(POST("/products").and(accept(APPLICATION_JSON)), productHandler::addNewProduct)
                              .andRoute(PUT("products/{id:[0-9]+}").and(accept(APPLICATION_JSON)), productHandler::updateProduct)
                              .andRoute(DELETE("/products/{id:[0-9]+}").and(accept(APPLICATION_JSON)), productHandler::deleteProduct);
    }
}
