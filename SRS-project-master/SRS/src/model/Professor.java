package model;

import java.util.ArrayList;

public class Professor extends Person {

	private String title;
	private String department;
	private ArrayList<Section> teaches;
	private String password;
	

	public Professor(String name, String ssn, String title, String dept ,String password) {

		super(name, ssn);

		this.setTitle(title);
		this.setDepartment(dept);
		this.setPassword(password);
		teaches = new ArrayList<Section>();
	}

	public Professor() {
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDepartment(String dept) {
		department = dept;
	}

	public String getDepartment() {
		return department;
	}

	
	public ArrayList<Section> getTeaches() {
		return teaches;
	}

	public void setTeaches(ArrayList<Section> teaches) {
		this.teaches = teaches;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void agreeToTeach(Section s) {
		teaches.add(s);

		s.setInstructor(this);
	}
}
