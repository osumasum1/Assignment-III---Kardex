package products.auth;

import io.dropwizard.auth.Authorizer;
import products.core.Session;

public class ProductsAuthorizer1 implements Authorizer<Session> {

	@Override
	public boolean authorize(Session principal, String role) {
		// TODO Auto-generated method stub
		return false;
	}
/*
    @Override
    public boolean authorize(Session session, String role) {
        return session.getRoles() != null && session.getRoles().contains(role);
    }
    */
}
