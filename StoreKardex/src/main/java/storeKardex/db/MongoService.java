package storeKardex.db;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReturnDocument;

import storeKardex.core.Kardex;
import storeKardex.rabbit.SendKardexCreated;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
 
import static com.mongodb.client.model.Filters.*;
 
public class MongoService {
 
    public void insertOne(MongoCollection<Document> collection, Document document) {
        collection.insertOne(document);
    }
    
    public void updatePriceByProductIdAndDate(MongoCollection<Document> collection,String productId,
    		String date, double price) {
    	
    	FindOneAndUpdateOptions findOptions = new FindOneAndUpdateOptions();
        findOptions.upsert(true);
        findOptions.returnDocument(ReturnDocument.AFTER);
    	
    	BasicDBObject query = new BasicDBObject();
    	query.append("idProducto", productId);
    	query.append("fecha", date);
    	
    	BasicDBObject updateFields = new BasicDBObject();
    	updateFields.append("precio", price);
    	
    	BasicDBObject setQuery = new BasicDBObject();
    	setQuery.append("$set", updateFields);
    	
    	//BasicDBObject update = new BasicDBObject();
    	//query.append("precio", price);


        collection.updateOne(query, setQuery);
    }
 
    public void insertMany(MongoCollection<Document> collection, List<Document> documents) {
        collection.insertMany(documents);
    }
 
    public List<Document> find(MongoCollection<Document> collection) {
        return collection.find().into(new ArrayList<>());
    }
    
    public List<Document> findSimple(MongoCollection<Document> collection) {
        return collection.find().projection(Projections.include("fuente","destino","tema")).into(new ArrayList<>());
    }
 
    public List<Document> findByKey(MongoCollection<Document> collection, String key, int value) {
        return collection.find(eq(key, value)).projection(Projections.exclude("_id", "destino", "fecha", "lugar", "texto"))
        		.into(new ArrayList<>());
    }
 
    public List<Document> findByCriteria(MongoCollection<Document> collection, String key, int lessThanValue, int greaterThanValue, int sortOrder) {
        List<Document> documents = new ArrayList<>();
        FindIterable iterable = collection.find(and(lt(key, lessThanValue),
                gt(key, greaterThanValue))).sort(new Document(key, sortOrder));
        iterable.into(documents);
        return documents;
    }

	public void deleteOneByObjectId(MongoCollection<Document> collection, String value) {
		collection.deleteOne(new Document("_id",new ObjectId(value)));
		
	}
	
	public void deleteAllKudosUser(MongoCollection<Document> collection, long user) throws NumberFormatException, Exception {
		List<Document> fuenteKudos = (List<Document>) collection.find(eq("fuente", user)).into(
				new ArrayList<Document>());
		
		collection.deleteMany(eq("destino", user));
		collection.deleteMany(eq("fuente", user));
		
		for (Document kudos : fuenteKudos) {
			SendKardexCreated calculate = new SendKardexCreated();
			//calculate.sendMessage(Long.valueOf(kudos.getInteger("destino")));
		}
	}
 

}