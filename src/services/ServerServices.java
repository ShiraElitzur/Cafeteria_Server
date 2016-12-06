package services;

import javax.ws.rs.Path;

import db.JpaManager;

@Path("/server")
public class ServerServices {
	
	private JpaManager jpa = JpaManager.getInstance();
	
	@Path("/getServerAddress")
	public String getServerAddress() {
		return jpa.getServerAddress();
	}

}
