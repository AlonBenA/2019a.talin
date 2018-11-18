package playground.logic.Entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import playground.logic.Location;

public class ElementEntity {

	private static int ID = 0;

	private String playground; 
	private String id;
	private Location location;
	private String name;
	private Date creationDate;
	private Date exirationDate;
	private String type;
	private Map<String,Object> attributes;
	private String creatorPlayground;
	private String creatorEmail;
	
	
	public ElementEntity() {
		super();
		this.playground = "2019a.talin";
		this.id = getID();
		this.location = new Location(0, 0);
		this.name = "Animal";
		this.creationDate = new Date();
		this.exirationDate = null;
		this.type = "Animal";
		this.attributes = new HashMap<>();
		this.creatorPlayground = "2019a.talin";
		this.creatorEmail = "2019a.Talin@Gmail.com";
	}
	
	public ElementEntity(Location location,String name,Date exirationDate,String type
			,Map<String,Object> attributes,String creatorPlayground, String creatorEmail)
	{
		this.playground = "2019a.talin";
		this.id = getID();
		setLocation(location);
		setName(name);
		this.creationDate = new Date();
		setExirationDate(exirationDate);
		setType(type);
		setAttributes(attributes);
		setCreatorPlayground(creatorPlayground);
		setCreatorEmail(creatorEmail);
	}
	
	private String getID()
	{
		int i = ID++;
		return i+"";
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
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getExirationDate() {
		return exirationDate;
	}
	public void setExirationDate(Date exirationDate) {
		this.exirationDate = exirationDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public String getCreatorPlayground() {
		return creatorPlayground;
	}
	public void setCreatorPlayground(String creatorPlayground) {
		this.creatorPlayground = creatorPlayground;
	}
	public String getCreatorEmail() {
		return creatorEmail;
	}
	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	@Override
	public String toString() {
		return "ElementTO [playground=" + playground + ", id=" + id + ", location=" + location + ", name=" + name
				+ ", creationDate=" + creationDate + ", exirationDate=" + exirationDate + ", type=" + type
				+ ", attributes=" + attributes + ", creatorPlayground=" + creatorPlayground + ", creatorEmail="
				+ creatorEmail + "]";
	}	
	
	
	
}
