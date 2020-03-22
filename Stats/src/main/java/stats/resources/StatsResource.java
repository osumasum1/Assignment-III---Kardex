package stats.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import stats.core.Producto;
import stats.db.MongoService;
import stats.db.ProductoDAO;
import stats.rabbit.Send;

import javax.annotation.concurrent.ThreadSafe;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.skife.jdbi.v2.sqlobject.Transaction;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.google.common.base.Optional;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoCollection;

/*
 private MongoCollection<Document> collection;
    private final MongoService mongoService;
 
    public KudosResource(MongoCollection<Document> collection, MongoService mongoService) {
        this.collection = collection;
        this.mongoService = mongoService;
    }
 */


@Path("/stats")
@Produces(MediaType.APPLICATION_JSON)
public class StatsResource {
	
	private final ProductoDAO userDao;
	private MongoCollection<Document> collection;
    private final MongoService mongoService;
    
    public StatsResource(ProductoDAO userDao,MongoCollection<Document> collection, MongoService mongoService) {
        this.userDao = userDao;
        this.collection = collection;
        this.mongoService=mongoService;
    }
    
    @GET
    @Timed
    @Path("/weightedPrice/{id}")
    @UnitOfWork
    public double getKudosAmountByUserId(@PathParam("id") final String productId) {
        return mongoService.getPrecioPonderado(collection, productId);
    }


}
