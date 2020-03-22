package products.db;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.google.common.base.Optional;
//import com.sun.tools.sjavac.Log;

import io.dropwizard.hibernate.AbstractDAO;
import products.core.Products;

public class ProductDAO extends AbstractDAO<Products>{
	
	public ProductDAO(SessionFactory factory) {
		super(factory);
	}
	
	public Optional<Products> findById(long id){
		//Optional.of(get(id));
		return Optional.fromNullable(get(id));
		//return Optional.ofNullable(get(id));
	}
	
	@SuppressWarnings("unchecked")
	public boolean insertProduct(String id, String name, String weightedPrice) {
		try
        {
			Query<Products> query = namedQuery("insert.product");
			query.setParameter(0, id);
			query.setParameter(1, name);
			query.setParameter(2, weightedPrice);
			
			query.executeUpdate();
             
            return true;
        }
        catch (Exception e)
        {
        	System.out.println("ERROR OSMAR: "+e.toString());
            return false;
        }
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Products> findAll() {
        return list(namedQuery("kudos.core.User.findAll"));
    }
	
	@SuppressWarnings("unchecked")
	public List<Products> findByProductId(String productId) {
		Query<Products> query = namedQuery("select.product.by.id");
		query.setParameter("productId", productId);
		return query.getResultList();
    }
	
	
	
	@SuppressWarnings("unchecked")
	public List<Products> findUsersByUsernameAndPassword(String username, String password){
		Query<Products> query = namedQuery("kudos.core.User.usernamePassword");
		query.setParameter("username", username);
		query.setParameter("password", password);
		return query.getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public boolean deleteUserById(long id) {
		try
        {
			Query<Products> query = namedQuery("kudos.core.User.deleteUserbyId");
			query.setParameter(0, id);
			
			query.executeUpdate();
             
            return true;
        }
        catch (Exception e)
        {
        	System.out.println(e.toString());
            return false;
        }
		
		
	}

}


