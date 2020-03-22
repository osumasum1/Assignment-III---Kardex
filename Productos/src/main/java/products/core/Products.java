package products.core;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Null;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table(name="productos")

@SqlResultSetMappings({
	@SqlResultSetMapping(name="updateResult", columns = { @ColumnResult(name = "count")}),
	@SqlResultSetMapping(name="simpleUser", entities = {
			@EntityResult(
					entityClass = Products.class,
					fields = {
							@FieldResult(name="id",column="id"),
							@FieldResult(name="name",column="name"),
							@FieldResult(name="weightedPrice",column="weighted_price")

					}
			)
	})
})

@NamedQueries({
	@NamedQuery(
			name = "select.product.by.id",
			query = "select p from Products p where p.id=:productId" 
	)

})



@NamedNativeQueries({
    @NamedNativeQuery(
            name    =   "insert.product",
            query   =   "INSERT INTO productos (id, name, weighted_price) VALUES (?, ?, ?)",
            resultSetMapping = "updateResult"
    )
})

@JsonInclude(Include.NON_NULL)
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "weighted_price")
	private double weightedPrice;
	
	@Transient
	private String kardex;
	
	public Products(String name, double weightedPrice) {
		this.name = name;
		this.weightedPrice = weightedPrice;
	}
	
	public Products(String id, String name, double weightedPrice) {
		this.id = id;
		this.name = name;
		this.weightedPrice = weightedPrice;
	}
	public Products(double weightedPrice) {
		this.weightedPrice = weightedPrice;
	}
	public Products() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeightedPrice() {
		return weightedPrice;
	}

	public void setWeightedPrice(double weightedPrice) {
		this.weightedPrice = weightedPrice;
	}

	public String getKardex() {
		return kardex;
	}

	public void setKardex(String kardex) {
		this.kardex = kardex;
	}

	
	
}
