package dao;

import java.util.List;

import model.Course;
import model.Section;

public interface CourseDao {
	public List<Course> findAllCourses();
	public Course findCourse(String courseNo);
	public Course findPrerequisite(Course course);
	public void addCourse(Course course,String preCourseNo);
	public void deleteCourse(String courseNo);
	public void updateCourse(Course course);
	public void addPrerequisite(String courseNo, String preCourseNo);
	public void updatePrerequisite(Course course);
}
