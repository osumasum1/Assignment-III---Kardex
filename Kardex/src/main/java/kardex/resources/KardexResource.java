package kardex.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import kardex.core.Kardex;
import kardex.db.MongoService;
import kardex.rabbit.SendCreateKardex;
import kardex.rabbit.SendUpdateKardex;

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

@Path("/kardex")
@Produces(MediaType.APPLICATION_JSON)
public class KardexResource {
 
    private MongoCollection<Document> collection;
    private final MongoService mongoService;
 
    public KardexResource(MongoCollection<Document> collection, MongoService mongoService) {
        this.collection = collection;
        this.mongoService = mongoService;
    }
 
    @POST
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createKardex(@NotNull @Valid final Kardex kardex) throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(kardex);
        SendCreateKardex create = new SendCreateKardex();
        create.sendMessage(json);
        
       // mongoService.insertOne(collection, new Document(BasicDBObject.parse(json)));
        Map<String, String> response = new HashMap<>();
        response.put("message", "kardex created successfully");
        
       // SendCalculateKardex calculateKudos = new SendCalculateKardex();
      //  calculateKudos.sendMessage(Long.valueOf(kardex.getDestino()));
        return Response.ok(response).build();
    }
    
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Timed
    @Path("/updatePrice/{idProducto}/{date}")
    public Response putKardexPrecio(@PathParam("idProducto") final String idProducto,
    		@PathParam("date") final String date,@NotNull @Valid final double price) throws Exception {
    	SendUpdateKardex update = new SendUpdateKardex();
    	update.sendMessage(String.valueOf(idProducto)+"\n"+
    			String.valueOf(date)+"\n"+
    			String.valueOf(price));
    	
    	 Map<String, String> response = new HashMap<>();
         response.put("message", "kardex update successfully");
         return Response.ok(response).build();
    }
    
    
    ///////////////////////////////
 
    
    
 
}
    
    
