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

/**
 * @author Tali
 *
 */
@RestController
public class WebUI {
	
	private String defaultUserName;
	private MessageGenerator messageGenerator;
	
	@Autowired
	public void setMessageGenerator(MessageGenerator messageGenerator) {
		this.messageGenerator = messageGenerator;
	}
	
	@Value("${name.of.user.to.be.greeted:Anonymous}")
	public void setDefaultUserName(String defaultUserName) {
		this.defaultUserName = defaultUserName;
	}
	
//	@RequestMapping(
//			method=RequestMethod.GET,
//			path="/showMessage",
//			produces=MediaType.APPLICATION_JSON_VALUE)
	public Message getDefaultMessage () {
		return this.messageGenerator.createMessage(this.defaultUserName);
	}
	
//	@RequestMapping(
//			method=RequestMethod.GET,
//			path="/showMessageWithNullName",
//			produces=MediaType.APPLICATION_JSON_VALUE)
	public Message getMessageWithNullName () {
		return this.messageGenerator.createMessage(null);
	}
	
	@RequestMapping(
			method=RequestMethod.GET,
			path="/messages",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Message[] getAllMessages () {
		List<Message> messages = Arrays.asList(
				new Message("first"),
				new Message("second"),
				new Message("last")
				);
		
		return messages.toArray(new Message[0]);		
	}

	@RequestMapping(
			method=RequestMethod.GET,
			path="/messages/{name}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Message getCustomMessage (@PathVariable("name") String name) {
		return this.messageGenerator.createMessage(name);
	}
	
	@RequestMapping(
			method=RequestMethod.POST,
			path="/messages",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public Message storeMessage (@RequestBody Message newMessage) {
		return newMessage;
	}

	@RequestMapping(
			method=RequestMethod.PUT,
			path="/messages/{name}",
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public void updateMessage (
			@PathVariable("name") String name,
			@RequestBody Message newMessage) throws Exception {
		validateNull(name);
	}
	
	
	@RequestMapping(
			method=RequestMethod.DELETE,
			path="/messages/{name}")
	public void deleteMessage (@PathVariable("name") String name) throws Exception {
		validateNull(name);
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
			@PathVariable("distan ce") String center) {
		
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

}









