package service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.CourseCatalogDao;
import dao.CourseDao;
import dao.lianjie;
import model.Course;
import model.CourseCatalog;

public class CourseService {

	private CourseDao cd = lianjie.createCourseDao();
	private CourseCatalogDao ccd = lianjie.createCourseCatalogDao();
	private CourseCatalog courseCatalog = ccd.getCourseCatalog();

	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	public void setCourseCatalog(CourseCatalog courseCatalog) {
		this.courseCatalog = courseCatalog;
	}

	public String getAllCoursesJSON() {
		JSONArray ja = new JSONArray();
		List<Course> courses = cd.findAllCourses();

		for (Course c : courses) {
			JSONObject jo = new JSONObject();
			jo.put("courseNo", c.getCourseNo());
			jo.put("courseName", c.getCourseName());
			jo.put("credits", c.getCredits() );
			ja.put(jo);
		}
		return ja.toString();
	}

	public String getCourseJSON(String courseNo) {
		JSONObject jo = new JSONObject();
		Course c = cd.findCourse(courseNo);
		jo.put("courseNo", c.getCourseNo());
		jo.put("courseName", c.getCourseName());
		jo.put("credits", c.getCredits());
		jo.put("preCourseNo", c.getPrerequisite().getCourseNo());
		return jo.toString();
	}

	public boolean isCourseInSimilar(Course course) {
		List<Course> courses = cd.findAllCourses();
		return course.isCourseInSimilar(courses);
	}

	public String addCourseResult(String courseNo, String courseName, double credits, String preCourseNo) {
		JSONObject jo = new JSONObject();
		Course c = new Course(courseNo, courseName, credits);
		jo.put("result", !isCourseInSimilar(c));
		if (!isCourseInSimilar(c)) {
			cd.addCourse(c, preCourseNo);
			courseCatalog.addCourse(c);
			jo.put("warning", "添加成功！");
		} else {
			jo.put("warning", "添加失败：已有相同的课程或课程ID重复！");
		}
		return jo.toString();
	}

	public void deleteCourse(String courseNo) {
		cd.deleteCourse(courseNo);
	}

	public void updateCourse(Course course) {
		cd.updateCourse(course);
		cd.updatePrerequisite(course);
	}
}
