package products;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import products.core.Products;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class ProductsConfiguration extends Configuration {
	
	//influxHost: http://localhost:8086
	//	influxDataBase: logs 
	
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    
    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
    
    
    
    
    public DataSourceFactory getDatabase() {
		return database;
	}




	public void setDatabase(DataSourceFactory database) {
		this.database = database;
	}




	private final HibernateBundle<ProductsConfiguration> hibernateBundle
    = new HibernateBundle<ProductsConfiguration>(
            Products.class
    ) {

        @Override
        public DataSourceFactory getDataSourceFactory(
        		ProductsConfiguration configuration
        ) {
            return configuration.getDataSourceFactory();
        }

    };


    
    
    
}
