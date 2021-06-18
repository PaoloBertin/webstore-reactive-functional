package eu.opensource.webstore.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Setter
@Getter
@Document(collation = "")
public class Product {

    public String id;

    @Indexed(unique = true)
    public String productCode;

    public String name;

    public BigDecimal price;

    public String category;

    public Product() {

    }

    public Product(String name, String productCode, BigDecimal price, String category) {

        this.productCode = productCode;
        this.name = name;
        this.price = price;
        this.category = category;
    }


}
