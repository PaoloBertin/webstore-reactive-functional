package eu.opensource.webstore.service.impl;

import eu.opensource.webstore.domain.Product;
import eu.opensource.webstore.repository.ProductRepository;
import eu.opensource.webstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Flux<Product> getProductsByName(String name) {

        return (name != null) ? productRepository.findByName(name) : productRepository.findAll();
    }

    @Override
    public Mono<Product> getProductById(String id) {

        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> addNewProduct(Product product) {

        return productRepository.save(product);
    }

    @Override
    public Mono<Product> updateProduct(String id, Product product) {

        return productRepository.findById(id)
                                .flatMap(s -> {
                                    product.setId(s.getId());
                                    return productRepository.save(product);
                                });

    }

    @Override
    public Mono<Void> deleteProduct(Product product) {

        return productRepository.delete(product);
    }

}
