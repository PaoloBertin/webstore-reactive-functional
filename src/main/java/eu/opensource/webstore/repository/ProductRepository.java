package eu.opensource.webstore.repository;

import eu.opensource.webstore.domain.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository("productRepository")
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    @Query("{'category': { '$regex' : ?0 } }")
    Flux<Product> findByCategory(String category);

    @Query(value = "{}", fields = "{'id': 1, 'productCode' : 1, 'category'  :1 }")
    Flux<Product> findAllLight();

    @Query("{'productCode': ?0 }")
    Mono<Product> findByProductCode(String productCode);

    @Query("{'name': ?0 }")
    Flux<Product> findByName(String name);

}