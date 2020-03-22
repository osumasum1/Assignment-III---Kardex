package stats.core;

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
					entityClass = Producto.class,
					fields = {
							@FieldResult(name="id",column="id"),
							@FieldResult(name="name",column="name"),
							@FieldResult(name="weightedPrice",column="weighted_price")

					}
			)
	})
})

@NamedNativeQueries({
    @NamedNativeQuery(
    		//UPDATE `users` SET `kudos_amount` = '1' WHERE `users`.`id` = 6;
            name    =   "producto.update.weightedPrice",
            query   =   "UPDATE productos SET weighted_price=:weightedPrice WHERE id=:productId",
            resultSetMapping = "updateResult"
    )
})

@JsonInclude(Include.NON_NULL)
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "weighted_price")
	private double weightedPrice;
	
	@Transient
	private String kardex;
	
	public Producto(String name, double weightedPrice) {
		this.name = name;
		this.weightedPrice = weightedPrice;
	}
	
	public Producto(String id, String name, double weightedPrice) {
		this.id = id;
		this.name = name;
		this.weightedPrice = weightedPrice;
	}
	public Producto(double weightedPrice) {
		this.weightedPrice = weightedPrice;
	}
	public Producto() {}

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
