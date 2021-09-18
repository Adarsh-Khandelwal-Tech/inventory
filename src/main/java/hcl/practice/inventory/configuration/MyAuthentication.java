package hcl.practice.inventory.configuration;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class MyAuthentication extends AbstractAuthenticationToken{

	public MyAuthentication(Collection<? extends GrantedAuthority> arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	private Object credentials;
	
	private Object principal;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return principal;
	}

	public void setCredentials(Object credentials) {
		this.credentials = credentials;
	}

	public void setPrincipal(Object principal) {
		this.principal = principal;
	}

	
	
}
