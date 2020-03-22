package products;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import products.auth.ProductsAuthenticator;
import products.core.Session;
import products.core.Products;
import products.db.ProductDAO;
import products.rabbit.Send;
import products.resources.ProductResource;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class ProductsApplication extends Application<ProductsConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ProductsApplication().run(args);
    }
    
    private final HibernateBundle<ProductsConfiguration> hibernateBundle =
            new HibernateBundle<ProductsConfiguration>(Products.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(ProductsConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };


    @Override
    public void initialize(final Bootstrap<ProductsConfiguration> bootstrap) {
    	bootstrap.addBundle(hibernateBundle);
    	
    }
    

    @Override
    public void run(final ProductsConfiguration configuration,
                    final Environment environment) throws Exception {
    	
    	/*
    	final UsersResource resource = new UsersResource(
    	        configuration.getTemplate(),
    	        configuration.getDefaultName()
    	    );
    	    environment.jersey().register(resource);
    	    */
    	
    	final ProductDAO userDao = new ProductDAO(hibernateBundle.getSessionFactory());
    	
    	
    	environment.jersey().register(new ProductResource(userDao));
    	
    	environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<Session>()
                .setAuthenticator(new ProductsAuthenticator(userDao))
                //.setAuthorizer(new UserAuthorizer())
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));
    	environment.jersey().register(new AuthValueFactoryProvider.Binder<>(Session.class));
    	environment.jersey().register(RolesAllowedDynamicFeature.class);
    	
    }

}
