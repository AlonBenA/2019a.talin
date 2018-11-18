package playground.logic.Entities;

public class UserEntity {
	private String email;
	private String playground;
	private String username;
	private String avatar;
	private String role;
	private long points;
	private StringBuilder code;
	
	
	public UserEntity() {
		super();
	}

	public UserEntity(String email, String playground, String username, String avatar, String role, long points) {
		super();
		setEmail(email);
		setPlayground(playground);
		setUsername(username);
		setAvatar(avatar);
		setRole(role);
		setPoints(points);
		this.code.append(1234);
		/*Random r = new Random();
		int low = 1000;
		int high = 9999;
		int result = r.nextInt(high-low) + low;
		this.code.append(result);*/
		
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

	public String getCode() {
		return code.toString();
	}

	public void setCode(String code) {	
	}
	
	public boolean verify(String code) {
		if(code.equals(this.code.toString())) {
			this.code.delete(0, this.code.length());
			return true;
		}
		return false;
	}
	
	public boolean isVerified() {
		if("".equals(code.toString()))
			return true;
		return false;
	}
	
	
}
