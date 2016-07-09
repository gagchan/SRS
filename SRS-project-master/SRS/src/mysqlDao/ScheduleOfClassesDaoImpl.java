package mysqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import dao.CourseDao;
import dao.ProfessorDao;
import dao.ScheduleOfClassesDao;
import dao.lianjie;
import model.Course;
import model.Professor;
import model.ScheduleOfClasses;
import model.Section;
import util.DBUtil;

public class ScheduleOfClassesDaoImpl implements ScheduleOfClassesDao {

	
	@Override
	public ScheduleOfClasses getScheduleOfClassess(String semester) {
		Connection Conn = DBUtil.getMySqlConnection();
		CourseDao courseDao = lianjie.createCourseDao();
		ProfessorDao professorDao = lianjie.createProfessorDao();
		String sql = "select * from Section where semester ='"+ semester +"'";
		ScheduleOfClasses schedule = new ScheduleOfClasses(semester);
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Course course = courseDao.findCourse(rs.getString("courseNo"));
				Professor professor = professorDao.getProfessor(rs.getString("Pssn"));
				Section section = new Section(rs.getInt("sectionNo"),rs.getString("dayOfWeek"),rs.getString("timeOfDay"),course,rs.getString("room"),rs.getInt("seatingCapacity"),professor);
				System.out.println("-----------courseName"+course.getCourseName());
				schedule.addSection(section);
			}
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(schedule);
		return schedule;
	}

	@Override
	public List<Section> getAllSectionsOffered(ScheduleOfClasses schedule) {
		List<Section> sectionsList = new ArrayList<Section>();
		HashMap<String, Section> sectionsOffered = schedule.getSectionsOffered();
		Iterator<Entry<String, Section>> iter = sectionsOffered.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Section> entry = (Entry<String, Section>) iter.next();
			Section section = entry.getValue();
			sectionsList.add(section);
		}
		return sectionsList;
	}



}
