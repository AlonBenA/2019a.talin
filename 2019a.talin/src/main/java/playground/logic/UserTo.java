package playground.logic;

public class UserTo {
	private String email;
	private String playground;
	private String username;
	private String avatar;
	private String role;
	private long points;
	
	public UserTo() {
	}
	
	
	public UserTo(String email, String playground, String usernam, String avatar, String role, long points) {
		super();
		setEmail(email);
		setPlayground(playground);
		setUsername(usernam);
		setAvatar(avatar);
		setRole(role);
		setPoints(points);
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPlayground() {
		return playground;
	}

	public void setPlayground(String playground) {
		this.playground = playground;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}
	
}
