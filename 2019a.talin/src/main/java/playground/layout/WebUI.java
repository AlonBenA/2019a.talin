package playground.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import playground.logic.ActivityTO;
import playground.logic.ElementTO;
import playground.logic.Location;
import playground.logic.Message;
import playground.logic.MessageGenerator;
import playground.logic.UserTo;
import playground.logic.NewUserForm;

/**
 * @author Tali
 *
 */
@RestController
public class WebUI {
	
	private String defaultUserName;

	
	@Value("${name.of.user.to.be.greeted:Anonymous}")
	public void setDefaultUserName(String defaultUserName) {
		this.defaultUserName = defaultUserName;
	}
	
	private void validateNull(String name) throws Exception {
		if ("null".equals(name) || name == null) {
			throw new Exception("message not found");
		}
	}

	//Sprint2: Write the GET/playground/elements/{userPlayground}/{email}/search/{attributeNa me}/{value} 
	@RequestMapping(
			method=RequestMethod.GET,
			path="/playground/elements/{userPlayground}/{email}/search/{attributeName}/{value}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ElementTO[] getElementsWithAttribute(@PathVariable("userPlayground") String userPlayground,
			@PathVariable("email") String email, @PathVariable("attributeName") String attributeName,
			@PathVariable("value") String value) {
		
		List<ElementTO> allElements = getListOfElementsTO();
		List<ElementTO> elementsWithAttribute = new ArrayList<>();
		
		for(ElementTO element: allElements)
			if(element.getAttributes().containsKey(attributeName))
				if(element.getAttributes().get(attributeName).equals(value))
					elementsWithAttribute.add(element);	
			
		return elementsWithAttribute.toArray(new ElementTO[0]);
	}
	
	//Sprint2: Write the /playground/activities/{userPlayground}/{email} 
	@RequestMapping(
			method=RequestMethod.POST,
			path="/playground/activities/{userPlayground}/{email}",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public Object activateElement (
			@PathVariable("userPlayground") String userPlayground,
			@PathVariable("email") String email, @RequestBody ActivityTO activityTo) {
		
		String activityResult = "";
		
		switch(activityTo.getType())
		{
		case "Play":
			activityResult = "You have played with " + activityTo.getElementId() + " element";
			break;
			
		case "Feed":
			activityResult = "You fed " + activityTo.getElementId() + " element";
			break;
			
		case "Place an add":
			activityResult = "You placed an Ad on " + activityTo.getElementId() + " element";
			break;
			
		case "Read an add":
			activityResult = "You read an Ad on " + activityTo.getElementId() + " element";
			break;
		}	
		return activityResult;
	}
	
	//Sprint2: Write the GET /playground/elements/{userPlayground}/{email}/all
	@RequestMapping(
			method=RequestMethod.GET,
			path="/playground/elements/{userPlayground}/{email}/all",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ElementTO[] getAllElements (@PathVariable("userPlayground") String userPlayground,@PathVariable("email") String email) {
		
		List<ElementTO> elements = getListOfElementsTO();
		
		return elements.toArray(new ElementTO[0]);		
	}
	
	
	//Sprint2: Write the GET /playground/elements/{userPlayground}/{email}/near/{x}/{y}/{distan ce}
	// to check {distan ce} Represents 
	@RequestMapping(
			method=RequestMethod.GET,
			path= "/playground/elements/{userPlayground}/{email}/near/{x}/{y}/{distan ce}" ,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ElementTO[] getAllNearElements (@PathVariable("userPlayground") String userPlayground,
			@PathVariable("email") String email,
			@PathVariable("x") double x,
			@PathVariable("y") double y,
			@PathVariable("distan ce") double center) {
		
		List<ElementTO> Allelements = getListOfElementsTO();
		
		return Allelements.toArray(new ElementTO[0]);	
	}
	
	
	public List<ElementTO> getListOfElementsTO()
	{
		Location location1 = new Location(10.0,10.0);
		Location location2 = new Location(100.0,100.0);
		String name1 = "Dog";
		String name2 = "Cat";
		String type1 = "Dog";
		String type2 = "Cat";
		Map<String,Object> attributes1  = new HashMap<>();
		attributes1.put("Play", "Woof");
		Map<String,Object> attributes2 = new HashMap<>();
		attributes2.put("Play", "Meow");
		String creatorPlayground = "2019a.Talin";
		String creatorEmail = "AlonMail@Mail.com";
		
		List<ElementTO> elements = Arrays.asList(
				new ElementTO(location1,name1, new Date(),type1,attributes1,creatorPlayground,creatorEmail),
				new ElementTO(location2,name2, new Date(),type2,attributes2,creatorPlayground,creatorEmail),
				new ElementTO()
				);
		
		return elements;
	}
	
	//Sprint2: Write the PUT /playground/elements/{userPlayground}/{email}/{playground}/{id}
	@RequestMapping(
			method=RequestMethod.PUT,
			path="/playground/elements/{userPlayground}/{email}/{playground}/{id}",
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public void updateElement (
			@PathVariable("userPlayground") String userPlayground,
			@PathVariable("email") String email,
			@PathVariable("playground") String playground,
			@PathVariable("id") String id,
			@RequestBody ElementTO newElement) throws Exception {
		//DO something
		
	}
	
	//Sprint2: Write the PUT /playground/users/{playground}/{email}
	@RequestMapping(
			method=RequestMethod.PUT,
			path="/playground/users/{playground}/{email}",
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public void updateUser (
			@PathVariable("playground") String playground,
			@PathVariable("email") String email,
			@RequestBody UserTo newUser) throws Exception {
		validateNull(email);
	}
	
	//Sprint2: Write the /playground/elements/{userPlayground }/{email}
	@RequestMapping(
			method=RequestMethod.POST,
			path="/playground/elements/{userPlayground }/{email}",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ElementTO updateElement (
			@PathVariable("userPlayground") String userPlayground,
			@PathVariable("email") String email, 
			@RequestBody ElementTO elementTO) {
		
		return elementTO;
	}
	
	
	//Sprint2: Write the GET /playground/elements/{userPlayground}/{email}/{playground}/{id}
	@RequestMapping(
			method=RequestMethod.GET,
			path="/playground/elements/{userPlayground}/{email}/{playground}/{id}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ElementTO getElement (
			@PathVariable("userPlayground") String userPlayground,
			@PathVariable("email") String email,
			@PathVariable("playground") String playground,
			@PathVariable("id") String id) {
		
		List<ElementTO> elements = getListOfElementsTO();
		
		return elements.get(Integer.parseInt(id));		
	}
	
	
	//Sprint2: Write the POST /playground/users
	@RequestMapping(
			method=RequestMethod.POST,
			path="/playground/users",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public UserTo userSignup (@RequestBody NewUserForm newUserForm) {
		UserTo user = new UserTo(newUserForm.getEmail(),"2019a.Talin",newUserForm.getUsername(),newUserForm.getAvatar(),newUserForm.getRole(),20);
		return user;
	}
	
	
	//Sprint2: Write the GET /playground/users/confirm/{playground}/{email}/{code}
	@RequestMapping(
			method=RequestMethod.GET,
			path="/playground/users/confirm/{playground}/{email}/{code}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public UserTo userValidate (@PathVariable("playground") String userPlayground,@PathVariable("email") String email, @PathVariable("code") String code) throws Exception {
		
		UserTo user = null;
		List<UserTo> users = getListOfUserTO();
		for (int i = 0; i < users.size(); i++) 
			if(email.equals(users.get(i).getEmail()) && userPlayground.equals(users.get(i).getPlayground()))
				user = users.get(i);
		
		if (user != null)
			if (code.equals("1234"))
				return user;
		
		throw new Exception(); // user not found	
	}
	
	
	//Sprint2: Write the GET /playground/users/login/{playground}/{email}
	@RequestMapping(
			method=RequestMethod.GET,
			path="/playground/users/login/{playground}/{email}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public UserTo login (@PathVariable("playground") String userPlayground,@PathVariable("email") String email) throws Exception {
		
		List<UserTo> users = getListOfUserTO();
		for (int i = 0; i < users.size(); i++) 
			if(email.equals(users.get(i).getEmail()) && userPlayground.equals(users.get(i).getPlayground()))
				return users.get(i);	
		
		throw new Exception(); // user not found
	}
	
	public List<UserTo> getListOfUserTO()
	{
		String email1 = "usermail1@usermail.com";
		String email2 = "usermail2@usermail.com";
		String playground1 = "2019a.Talin";
		String playground2 = "2019a.Talin";
		String username1 = "username1";
		String username2 = "username2";
		String avatar1 = "avatar1";
		String avatar2 = "avatar2";
		String role1 = "Manager";
		String role2 = "Player";
		long points1 = 0;
		long points2 = 20;
		
		List<UserTo> users = Arrays.asList(
				new UserTo(email1, playground1, username1, avatar1, role1, points1),
				new UserTo(email2, playground2, username2, avatar2, role2, points2)
				);
		
		return users;
	}
	

		

	

	
	
}









