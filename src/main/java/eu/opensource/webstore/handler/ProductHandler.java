package eu.opensource.webstore.handler;

import eu.opensource.webstore.domain.Product;
import eu.opensource.webstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@RequiredArgsConstructor
@Component
public class ProductHandler {

    private final ProductService productService;

    public Mono<ServerResponse> getProduct(ServerRequest serverRequest) {

        Mono<Product> productMono = productService.getProductById(serverRequest.pathVariable("id"));

        return productMono.flatMap(product -> ServerResponse.ok()
                                                            .body(fromValue(product)))
                          .switchIfEmpty(ServerResponse.notFound()
                                                       .build());
    }

    public Mono<ServerResponse> listProducts(ServerRequest serverRequest) {

        String name = serverRequest.queryParam("name")
                                   .orElse(null);

        return ServerResponse.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(productService.getProductsByName(name), Product.class);
    }

    public Mono<ServerResponse> addNewProduct(ServerRequest serverRequest) {

        Mono<Product> productMono = serverRequest.bodyToMono(Product.class);

        return productMono.flatMap(product -> ServerResponse.status(HttpStatus.OK)
                                                            .contentType(MediaType.APPLICATION_JSON)
                                                            .body(productService.addNewProduct(product), Product.class));

    }

    public Mono<ServerResponse> updateProduct(ServerRequest serverRequest) {

        final String productId = serverRequest.pathVariable("id");

        Mono<Product> productMono = serverRequest.bodyToMono(Product.class);

        return productMono.flatMap(product -> ServerResponse.status(HttpStatus.OK)
                                                            .contentType(MediaType.APPLICATION_JSON)
                                                            .body(productService.updateProduct(productId, product), Product.class));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {

        final String productId = serverRequest.pathVariable("id");

        return productService.getProductById(productId)
                             .flatMap(s -> ServerResponse.noContent()
                                                         .build(productService.deleteProduct(s)))
                             .switchIfEmpty(ServerResponse.notFound()
                                                          .build());
    }
}
