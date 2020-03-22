package stats.db;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;

import stats.core.Kardex;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
 
import static com.mongodb.client.model.Filters.*;
 
public class MongoService {
	
	public double getPrecioPonderado(MongoCollection<Document> collection, String productId) {
		System.out.println("SACAR PRECIO POdnerado"+productId);
		
        ArrayList<Document> list = collection.find(eq("idProducto", productId)).into(new ArrayList<>());
        double resp = 0;
        double tolalPrice = 0;
        int tolalAmount = 0;
        for (Document document : list) {
        	tolalPrice = tolalPrice + (document.getInteger("cantidad")*document.getDouble("precio"));
        	tolalAmount = tolalAmount + document.getInteger("cantidad");
		}
        resp = tolalPrice/tolalAmount;
        
        return resp;
    }
 

}