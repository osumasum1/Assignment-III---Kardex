package kardex;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import kardex.db.MongoManaged;
import kardex.db.MongoService;
import kardex.resources.KardexResource;

public class KardexApplication extends Application<KardexConfiguration> {


    public static void main(String[] args) throws Exception {
        new KardexApplication().run("server", args[0]);
    }

    @Override
    public void initialize(Bootstrap<KardexConfiguration> b) {
    }

    @Override
    public void run(KardexConfiguration config, Environment env)
            throws Exception {
        MongoClient mongoClient = new MongoClient(config.getMongoHost(), config.getMongoPort());
        MongoManaged mongoManaged = new MongoManaged(mongoClient);
        env.lifecycle().manage(mongoManaged);
        MongoDatabase db = mongoClient.getDatabase(config.getMongoDB());
        MongoCollection<Document> collection = db.getCollection(config.getCollectionName());
       
         
       KardexResource resource = new KardexResource(collection, new MongoService());
       env.jersey().register(new KardexResource(collection, new MongoService()));
       
       
    }

}
