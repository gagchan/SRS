package sqliteDao;

import java.util.List;

import dao.CourseCatalogDao;
import dao.CourseDao;
import dao.lianjie;
import model.Course;
import model.CourseCatalog;

public class CourseCatalogImpl implements CourseCatalogDao {

	
	
	@Override
	public CourseCatalog getCourseCatalog() {
		CourseCatalog courseCatalogs = new CourseCatalog();
		CourseDao cd = lianjie.createCourseDao();
		List<Course> courses = cd.findAllCourses();
		for (Course c : courses) {
			courseCatalogs.addCourse(c);
		}
		return courseCatalogs;
	}

}
