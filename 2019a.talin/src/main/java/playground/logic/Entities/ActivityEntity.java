package playground.logic.Entities;

import java.util.HashMap;
import java.util.Map;

public class ActivityEntity {
	
	private String playground;
	private String id;
	private String elementPlayground;
	private String elementId;
	private String type;
	private String playerPlayground;
	private String playerEmail;
	private Map<String,Object> attributes;
	
	public ActivityEntity()
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
	
	public ActivityEntity(String elementPlayground, String elementId, String type,
			String playerPlayground, String playerEmail, Map<String, Object> attributes) {
		super();
		this.playground = "2019a.talin";
		this.id = getRandomID();
		setElementPlayground(elementPlayground);
		setElementId(elementId);
		setType(type);
		setPlayerPlayground(playerPlayground);
		setPlayerEmail(playerEmail);
		setAttributes(attributes);
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
	private String getRandomID()
	{
		int i = (int)(Math.random() * 1000) + 0;
		return i+"";
	}

	@Override
	public String toString() {
		return "ActivityEntity [playground=" + playground + ", id=" + id + ", elementPlayground=" + elementPlayground
				+ ", elementId=" + elementId + ", type=" + type + ", playerPlayground=" + playerPlayground
				+ ", playerEmail=" + playerEmail + ", attributes=" + attributes + "]";
	}

}
