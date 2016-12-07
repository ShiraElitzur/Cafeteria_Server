package services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import data.Server;
import db.JpaManager;

@Path("/server")
public class ServerServices {
	
	private JpaManager jpa = JpaManager.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getServers")
	public String getServers() {
		System.out.println("inside get servers");
		Gson json = new Gson();
		List<Server> servers = new ArrayList<>();
		servers = jpa.getServers();
		return json.toJson(servers.toArray());	
	}

}
