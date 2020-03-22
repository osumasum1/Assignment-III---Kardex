package stats.db;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.google.common.base.Optional;
import com.sun.tools.sjavac.Log;

import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;
import stats.core.Producto;

public class ProductoDAO extends AbstractDAO<Producto>{
	
	public ProductoDAO(SessionFactory factory) {
		super(factory);
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateWeightedPrice(String weightedPrice, String productId) {
		try
        {
			System.out.println("OSMAR:"+weightedPrice);
			Query<Producto> query = namedQuery("producto.update.weightedPrice");
			query.setParameter("weightedPrice", Double.valueOf(weightedPrice));
			query.setParameter("productId", productId);
			
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


