package playground.logic.Services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import playground.layout.ElementTO;
import playground.logic.Location;
import playground.logic.Entities.ActivityEntity;
import playground.logic.Entities.ElementEntity;
import playground.logic.Exceptions.ElementNotFoundException;

@Service
public class PlaygroundServiceStub implements PlaygroundService{
	//private Map<String, UserEntity> usersDatabase;
	private Map<String, ElementEntity> elementsDatabase;
	
	public void setElementsDatabase(Map<String, ElementEntity> elementsDatabase) {
		Location location = new Location();
		Date exirationDate = null;
		String type = "animal";
		String creatorPlayground = "2019a.talin";
		String creatorEmail = "2019a.Talin@Gmail.com";
		Map<String,Object> attributes = new HashMap<>();
		
		//add specific attribute
		Random rand = new Random();
		if (rand.nextInt(100) < 20) { // 20% of the elements
		    attributes.put("Eat", "meat");
		}
				
		//location,value,exirationDate,type,attributes,creatorPlayground,creatorEmail
		this.elementsDatabase = IntStream.range(0, 100) // int stream
				.mapToObj(value -> new ElementEntity(location,"animal #" + value,exirationDate,type,attributes,creatorPlayground,creatorEmail)) //  ElementTO stream using constructor reference
				.collect(Collectors.toMap(ElementEntity::getId, Function.identity()));
	}

	private Map<String, ActivityEntity> activitiesDatabase;
	
	@PostConstruct
	public void init() {
		//this.usersDatabase = new HashMap<>();
		setElementsDatabase(elementsDatabase);
		this.activitiesDatabase = new HashMap<>();
	}

	@Override
	public void addNewElement(ElementEntity elementEntity) {
		this.elementsDatabase.put(elementEntity.getId(), elementEntity);
	}

	@Override
	public void addNewActivity(ActivityEntity activityEntity) {
		this.activitiesDatabase.put(activityEntity.getId(), activityEntity);
		
	}

	@Override
	public ElementEntity getElement(String element_id) throws ElementNotFoundException {
		ElementEntity rv =  this.elementsDatabase.get(element_id);
		if (rv == null) {
			throw new ElementNotFoundException("could not find element by id: " + element_id);
		}
		return rv;
	}
	
	@Override
	public ActivityEntity getActivity(String activity_id) throws Exception {
		ActivityEntity rv =  this.activitiesDatabase.get(activity_id);
		if (rv == null) {
			throw new RuntimeException("could not find activity by id: " + activity_id);
		}
		return rv;
	}

	@Override
	public void cleanup() {
		this.elementsDatabase.clear();
		this.activitiesDatabase.clear();
		//this.usersDatabase.clear();	
	}

	@Override
	public List<ElementEntity> getAllElements(int size, int page) {
		return this.elementsDatabase
				.values() // collection of entities
				.stream() // stream of entities
				.skip(size*page)
				.limit(size)
				.collect(Collectors.toList());
	}

	@Override
	public boolean validateActivityType(String type) {
		boolean result;
				
		switch(type)
		{
		case "Play":
			result = true;
			break;	
			
		case "Place an add":
			result = true;
			break;
			
		case "Read an add":
			result = true;
			break;
			
		default:
			result = false;
			break;
		}
		
		return result;
	}

}
