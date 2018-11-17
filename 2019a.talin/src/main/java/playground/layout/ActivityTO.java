package playground.layout;

import java.util.HashMap;
import java.util.Map;

import playground.logic.Entities.ActivityEntity;
import playground.logic.Entities.ElementEntity;

public class ActivityTO {
	
	private String playground;
	private String id;
	private String elementPlayground;
	private String elementId;
	private String type;
	private String playerPlayground;
	private String playerEmail;
	private Map<String,Object> attributes;
	
	public ActivityTO()
	{
		super();
		this.playground = "2019a.talin";
		this.id = "0";
		this.elementPlayground = "2019a.talin";
		this.elementId = "0";
		this.type = "feed";
		this.playerPlayground = "2019a.talin";
		this.playerEmail = "email@gmail.com";
		this.attributes = new HashMap<>();
		this.attributes.put("eat", "meat");
	}
	
	public ActivityTO(String elementPlayground, String elementId, String type,
			String playerPlayground, String playerEmail, Map<String, Object> attributes) {
		super();
		this.playground = "2019a.talin";
		this.id = "0";
		setElementPlayground(elementPlayground);
		setElementId(elementId);
		setType(type);
		setPlayerPlayground(playerPlayground);
		setPlayerEmail(playerEmail);
		setAttributes(attributes);
	}
	
	public ActivityTO(ActivityEntity activityEntity)
	{
		setPlayground(activityEntity.getPlayground());
		setId(activityEntity.getId());
		setElementPlayground(activityEntity.getElementPlayground());
		setElementId(activityEntity.getElementId());
		setType(activityEntity.getType());
		setPlayerPlayground(activityEntity.getPlayerPlayground());
		setPlayerEmail(activityEntity.getPlayerEmail());
		setAttributes(activityEntity.getAttributes());
	}
	
	public String getPlayground() {
		return playground;
	}
	public void setPlayground(String playground) {
		this.playground = playground;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getElementPlayground() {
		return elementPlayground;
	}
	public void setElementPlayground(String elementPlayground) {
		this.elementPlayground = elementPlayground;
	}
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPlayerPlayground() {
		return playerPlayground;
	}
	public void setPlayerPlayground(String playerPlayground) {
		this.playerPlayground = playerPlayground;
	}
	public String getPlayerEmail() {
		return playerEmail;
	}
	public void setPlayerEmail(String playerEmail) {
		this.playerEmail = playerEmail;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String toString() {
		return "ActivityTO [playground=" + playground + ", id=" + id + ", elementPlayground=" + elementPlayground
				+ ", elementId=" + elementId + ", type=" + type + ", playerPlayground=" + playerPlayground
				+ ", playerEmail=" + playerEmail + ", attributes=" + attributes + "]";
	}
	
	public ActivityEntity convertFromActivityTOToActivityEntity()
	{
		ActivityEntity activityEntity = new ActivityEntity();
		activityEntity.setPlayground(playground);
		activityEntity.setId(id);
		activityEntity.setElementPlayground(elementPlayground);
		activityEntity.setElementId(elementId);
		activityEntity.setPlayerPlayground(playerPlayground);
		activityEntity.setPlayerEmail(playerEmail);
		activityEntity.setType(type);
		activityEntity.setAttributes(attributes);
		return activityEntity;
	}

}
