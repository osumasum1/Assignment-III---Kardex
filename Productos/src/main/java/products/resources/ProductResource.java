package products.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import products.api.ProductsApi;
import products.core.Session;
import products.core.Products;
import products.db.ProductDAO;
import products.rabbit.RPCClient;
import products.rabbit.Send;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.influxdb.InfluxDB;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;

import com.google.common.base.Optional;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)

public class ProductResource {
	
	private final ProductDAO productDAO;
    
    public ProductResource(ProductDAO userDao) {
        this.productDAO = userDao;
    }
    	
    	
        
    @GET
    @Path("/{id}")
    @UnitOfWork
    public List<Products> findByName(@PathParam("id") String id) {
    	
    	return productDAO.findByProductId(id);
    	
        
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @UnitOfWork
    public boolean insertProduct(
    		@FormParam("id") String id,
    		@FormParam("name") String name,
    		@FormParam("weightedPrice") String weightedPrice
    		
    		) {
       
        return productDAO.insertProduct(id, name, weightedPrice);
    }

}
