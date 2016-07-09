package model;

import java.util.ArrayList;
import java.util.List;

import dao.CourseDao;
import dao.lianjie;

public class Course {
	private String courseNo;
	private String courseName;
	private double credits;
	private ArrayList<Section> offeredAsSection;
	private Course prerequisite;
	
	CourseDao courseDao = lianjie.createCourseDao();
	
	public Course(String cNo, String cName, double credits) {
		setCourseNo(cNo);
		setCourseName(cName);
		setCredits(credits);
		offeredAsSection = new ArrayList<Section>();
		prerequisite = new Course();
	}

	public Course() {
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public double getCredits() {
		return credits;
	}

	public void setCredits(double credits) {
		this.credits = credits;
	}

	

	public void setPrerequisite(Course prerequisite) {
		this.prerequisite = prerequisite;
	}

	public void addSection(Section s) {
		offeredAsSection.add(s);
	}


	public boolean hasPrerequisite() {
		if (prerequisite!=null)
			return true;
		else
			return false;
	}

	public Course getPrerequisite() {
		return prerequisite;
	}

	public Section scheduleSection(String day, String time, String room, int capacity) {
		Section s = new Section();
		s.setDayOfWeek(day);
		s.setTimeOfDay(time);
		s.setRepresentedCourse(this);
		s.setRoom(room);
		s.setSectionNo(offeredAsSection.size() + 1);
		addSection(s);
		return s;
	}
	
	public boolean isCourseInSimilar(List<Course> courses){
		boolean flag=false;
		for(Course c:courses){
			if(courseNo.equals(c.getCourseNo())||courseName.equals(c.getCourseName())){
				flag=true;
				break;
			}
		}
		return flag;
	}
}
