package playground.logic.Services;

import java.util.List;

import playground.logic.Entities.ActivityEntity;
import playground.logic.Entities.ElementEntity;
import playground.logic.Exceptions.ElementNotFoundException;

public interface PlaygroundService {
	
	public void addNewElement(ElementEntity elementEntity);
	
	//public void addNewUser(UserEntity userEntity);
	
	public void addNewActivity(ActivityEntity activityEntity);

	//public UserEntity getUser(String user_id) throws UserNotFoundException;
	
	public ElementEntity getElement(String element_id) throws ElementNotFoundException;
	
	public ActivityEntity getActivity(String activity_id) throws Exception;
	
	public void cleanup();

	public List<ElementEntity> getAllElements(int size, int page);
	
	public boolean validateActivityType(String type);
}
