package storeKardex.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import storeKardex.core.Kardex;
import storeKardex.db.MongoService;
import storeKardex.rabbit.SendKardexCreated;
import storeKardex.rabbit.SendKardexUpdated;

import org.bson.Document;
 
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/storeKardex")
@Produces(MediaType.APPLICATION_JSON)
public class StoreKardexResource {
 
    private MongoCollection<Document> collection;
    private final MongoService mongoService;
 
    public StoreKardexResource(MongoCollection<Document> collection, MongoService mongoService) {
        this.collection = collection;
        this.mongoService = mongoService;
    }
 
    @POST
    @Timed
   // @Path("/createkudo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createKudo(@NotNull @Valid final String json) throws Exception {
        
        
       mongoService.insertOne(collection, new Document(BasicDBObject.parse(json)));
        Map<String, String> response = new HashMap<>();
        response.put("message", "kardex created successfully");
        
        Gson g = new Gson();
        Kardex kardex = g.fromJson(json, Kardex.class);
        
        SendKardexCreated cardexCreated= new SendKardexCreated();
        cardexCreated.sendMessage(kardex.getIdProducto());
       // SendCalculateKardex calculateKudos = new SendCalculateKardex();
      //  calculateKudos.sendMessage(Long.valueOf(kardex.getDestino()));
        return Response.ok(response).build();
    }
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Timed
    @Path("/updatePrice/{idProducto}/{date}")
    public Response putKardexPrecio(@PathParam("idProducto") final String productId,
    		@PathParam("date") final String date,@NotNull @Valid final double price) throws Exception {
    	
    	 mongoService.updatePriceByProductIdAndDate(collection, productId, date, price);
    	
    	
    	 Map<String, String> response = new HashMap<>();
         response.put("message", "kardex update successfully");
         
         SendKardexUpdated kardexUpdated = new SendKardexUpdated();
         kardexUpdated.sendMessage(productId);
         
         return Response.ok(response).build();
    }
    
    /////////
 

    
 
}
    
    
