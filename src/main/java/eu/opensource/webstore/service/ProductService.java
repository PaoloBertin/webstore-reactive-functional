package eu.opensource.webstore.service;

import eu.opensource.webstore.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> getProductsByName(String name);

    Mono<Product> getProductById(String id);

    Mono<Product> addNewProduct(Product product);

    Mono<Product> updateProduct(String id, Product Product);

    Mono<Void> deleteProduct(Product product);

}
