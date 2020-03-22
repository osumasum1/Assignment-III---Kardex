package storeKardex;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import storeKardex.db.MongoManaged;
import storeKardex.db.MongoService;
import storeKardex.rabbit.ReceiverCreateKardex;
import storeKardex.rabbit.ReceiverUpdateKardex;
import storeKardex.resources.StoreKardexResource;

public class StoreKardexApplication extends Application<StoreKardexConfiguration> {


    public static void main(String[] args) throws Exception {
        new StoreKardexApplication().run("server", args[0]);
    }

    @Override
    public void initialize(Bootstrap<StoreKardexConfiguration> b) {
    }

    @Override
    public void run(StoreKardexConfiguration config, Environment env)
            throws Exception {
        MongoClient mongoClient = new MongoClient(config.getMongoHost(), config.getMongoPort());
        MongoManaged mongoManaged = new MongoManaged(mongoClient);
        env.lifecycle().manage(mongoManaged);
        MongoDatabase db = mongoClient.getDatabase(config.getMongoDB());
        MongoCollection<Document> collection = db.getCollection(config.getCollectionName());
       
         
       StoreKardexResource resource = new StoreKardexResource(collection, new MongoService());
       env.jersey().register(new StoreKardexResource(collection, new MongoService()));
       
       ReceiverCreateKardex recv =  new ReceiverCreateKardex(resource);
       ReceiverUpdateKardex recvUpdate =  new ReceiverUpdateKardex(resource);

       recv.start();
       recvUpdate.start();
    }

}
