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

import playground.logic.Location;
import playground.logic.Entities.ActivityEntity;
import playground.logic.Entities.ElementEntity;
import playground.logic.Entities.UserEntity;
import playground.logic.Exceptions.ElementNotFoundException;
import playground.logic.Exceptions.UserNotFoundException;

@Service
public class PlaygroundServiceStub implements PlaygroundService {
	private Map<String, UserEntity> usersDatabase;
	private Map<String, ElementEntity> elementsDatabase;
	private Map<String, ActivityEntity> activitiesDatabase;

	@PostConstruct
	public void init() {
		this.usersDatabase = new HashMap<>();
		this.elementsDatabase = new HashMap<>();
		this.activitiesDatabase = new HashMap<>();
	}

	public void setElementsDatabase(Map<String, ElementEntity> elementsDatabase) {
		Date exirationDate = null;
		String type = "animal";
		String creatorPlayground = "2019a.talin";
		String creatorEmail = "2019a.Talin@Gmail.com";
		Map<String, Object> attributes = new HashMap<>();

		// add specific attribute
		Random rand = new Random();
		if (rand.nextInt(100) < 20) { // 20% of the elements
			attributes.put("Eat", "meat");
		}

		// location,value,exirationDate,type,attributes,creatorPlayground,creatorEmail
		this.elementsDatabase = IntStream.range(0, 100) // int stream
				.mapToObj(value -> new ElementEntity(new Location(value, value), "animal #" + value,
						exirationDate, type, attributes, creatorPlayground, creatorEmail)) // ElementTO stream using
																							// constructor reference
				.collect(Collectors.toMap(ElementEntity::getId, Function.identity()));
	}

	@Override
	public synchronized ElementEntity addNewElement(ElementEntity elementEntity) {
		this.elementsDatabase.put(elementEntity.getPlayground() + elementEntity.getId(), elementEntity);
		return elementEntity;
	}

	@Override
	public void addNewActivity(ActivityEntity activityEntity) {
		this.activitiesDatabase.put(activityEntity.getId(), activityEntity);

	}

	@Override
	public ElementEntity getElement(String element_id, String element_Playground) throws ElementNotFoundException {
		ElementEntity rv = this.elementsDatabase.get(element_Playground + element_id);
		if (rv == null) {
			throw new ElementNotFoundException("could not find element by id: " + element_Playground + element_id);
		}
		return rv;
	}

	@Override
	public ActivityEntity getActivity(String activity_id) throws Exception {
		ActivityEntity rv = this.activitiesDatabase.get(activity_id);
		if (rv == null) {
			throw new RuntimeException("could not find activity by id: " + activity_id);
		}
		return rv;
	}

	@Override
	public synchronized List<ElementEntity> getAllElements(int size, int page) {
		return this.elementsDatabase.values() // collection of entities
				.stream() // stream of entities
				.skip(size * page).limit(size).collect(Collectors.toList());
	}

	@Override
	public boolean validateActivityType(String type) {
		boolean result;

		switch (type) {
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

	public Map<String, ActivityEntity> getActivitiesDatabase() {
		return activitiesDatabase;
	}

	public void setActivitiesDatabase(Map<String, ActivityEntity> activitiesDatabase) {
		this.activitiesDatabase = activitiesDatabase;
	}

	public Map<String, ElementEntity> getElementsDatabase() {
		return elementsDatabase;
	}

	public synchronized List<ElementEntity> getAllNearElements(double x, double y, double distance, int size,
			int page) {
		return this.elementsDatabase.values().stream() // stream of entities
				.filter(ent -> Math.abs(ent.getLocation().getX() - x) < distance)
				.filter(ent -> Math.abs(ent.getLocation().getY() - y) < distance).skip(size * page).limit(size)
				.collect(Collectors.toList());
	}

	@Override
	public synchronized void updateElement(ElementEntity updatedElementEntity, String playground, String id)
			throws Exception {

		if (this.elementsDatabase.containsKey(playground + id)) {
			ElementEntity elementEntity = this.elementsDatabase.get(playground + id);

			if (elementEntity.getLocation() != null && !elementEntity.getLocation().equals(updatedElementEntity.getLocation())) {
				elementEntity.setLocation(updatedElementEntity.getLocation());
			}

			if (elementEntity.getName() != null && !elementEntity.getName().equals(updatedElementEntity.getName())) {
				elementEntity.setName(updatedElementEntity.getName());
			}

			if (elementEntity.getExirationDate() != null && !elementEntity.getExirationDate().equals(updatedElementEntity.getExirationDate())) {
				elementEntity.setExirationDate(updatedElementEntity.getExirationDate());
			}

			if (elementEntity.getType() != null && !elementEntity.getType().equals(updatedElementEntity.getType())) {
				elementEntity.setType(updatedElementEntity.getType());
			}

			if (elementEntity.getAttributes() != null && !elementEntity.getAttributes().equals(updatedElementEntity.getAttributes())) {
				elementEntity.setAttributes(updatedElementEntity.getAttributes());
			}

			this.elementsDatabase.put(playground + id, elementEntity);

		} else {
			throw new ElementNotFoundException("Did not found the element");
		}

	}

	@Override
	public UserEntity addNewUser(UserEntity userEntity) {
		return this.usersDatabase.put(userEntity.getEmail()+userEntity.getPlayground(), userEntity);
	}

	@Override
	public UserEntity getUser(String email, String playground) throws UserNotFoundException {
		UserEntity userEntity = this.usersDatabase.get(email + playground);
		if (userEntity == null) {
			throw new RuntimeException("could not find user by id: " + email+playground);
		}
		return userEntity;
	}
	
	@Override
	public void cleanup() {
		this.elementsDatabase.clear();
		this.activitiesDatabase.clear();
		this.usersDatabase.clear();
	}

}
