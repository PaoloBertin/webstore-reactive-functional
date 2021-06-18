package eu.opensource.webstore.initializaion;

import eu.opensource.webstore.domain.Product;
import eu.opensource.webstore.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Slf4j
@Component
public class DataInitializer {

    public static class Category {

        public static final String SPRING = "Spring";
        public static final String JAVA = "Java";
        public static final String WEB = "Web";
    }

    @Autowired
    ReactiveMongoOperations operations;

    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        operations.collectionExists(Product.class)
                  .flatMap(exists -> exists ? operations.dropCollection(Product.class) : Mono.just(exists))
                  .then(operations.createCollection(Product.class, CollectionOptions.empty()))
                  .subscribe(data -> log.info("Collection saved: {}", data),
                             error -> log.info("Opps!"),
                             () -> log.info("Collection initialized!")
                  );

        log.info(" -->> Starting collection initialization...");

        productRepository
                .saveAll(Flux.just(new Product("Da Visual Basic a Java", "00001", new BigDecimal("19.90"), Category.JAVA),
                                   new Product("Java Web Service", "00002", new BigDecimal("24.90"), Category.WEB),
                                   new Product("Spring in Motion", "00003", new BigDecimal("35.00"), Category.SPRING)))
                .subscribe(data -> log.info("Saved {} products", data),
                           error -> log.error("Oops!"),
                           () -> log.info("Collection initialized!"));
    }
}
