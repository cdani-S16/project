package delectable.service;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import delectable.dto.ErrorDTO;
import delectable.dto.IdDTO;
import delectable.dto.MenuItemDetailDTO;
import delectable.dto.MenuItemIdDTO;
import delectable.logic.MenuManager;
import delectable.dto.MenuItemDTO;

import delectable.pojo.*;
import delectable.logic.*;

@Path("/menu")
public class MenuService {
	
	   @GET
	   @Produces(MediaType.APPLICATION_JSON)
	   public Response getMenu() throws JsonProcessingException, IllegalAccessException, InvocationTargetException{

		   ObjectMapper mapper = new ObjectMapper();
		   String jsonInString = new String();
		   List<MenuItemIdDTO> mi = MenuManager.getMenu().getAllMenuItems();
		   jsonInString = mapper.writeValueAsString(mi);
		   return Response.status(200).entity(jsonInString).build();
	   }
	   
	   @GET
	   @Path("/{mid}")
	   @Produces(MediaType.APPLICATION_JSON)
	   public Response getMenuItem(@PathParam("mid") int id) throws JsonProcessingException, IllegalAccessException, InvocationTargetException{
 
		   MenuItemDetailDTO mn = new MenuItemDetailDTO();
		   ObjectMapper mapper = new ObjectMapper();
		   String jsonOutString = new String();
		   mn.id = id;
		   try{
			   mn = MenuManager.getMenu().getMenuItem(id);
		   }catch(IndexOutOfBoundsException e)
		   {
			   ErrorDTO eDTO = new ErrorDTO();
			   eDTO.setError("Invalid menu id");
			   jsonOutString = mapper.writeValueAsString(eDTO);
			   return Response.status(400).entity(jsonOutString).build();
		   }

		   jsonOutString = mapper.writeValueAsString(mn);
		   return Response.status(200).entity(jsonOutString).build();
	   }

}

