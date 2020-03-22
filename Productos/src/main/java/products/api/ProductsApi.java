package products.api;
import products.core.Products;

public class ProductsApi {
	private Products user;

	public ProductsApi() {
		// Jackson deserialization
	}

	public ProductsApi(int id, String nickname, String firstName, String lastName,int kudosAmount) {
		//user = new User(nickname, firstName, lastName);
	}
}
