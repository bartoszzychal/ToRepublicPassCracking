package passcrack.user;

public class User {
	private int id;
	private String username;
	private String password;
	private String salt;
	private String email;
	private boolean decode;
	public User(int id, String username, String email, String password, String salt) {
		this.decode = false;
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.salt = salt;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public boolean isDecode() {
		return decode;
	}

	public void setDecode(boolean decode) {
		this.decode = decode;
	}

	@Override
	public String toString() {
		return id +";" + username+";"+email+";"+password+";"+salt ;
	}

}
