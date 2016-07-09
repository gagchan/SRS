package mysqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.CourseDao;
import model.Course;
import util.DBUtil;

public class CourseImpl implements CourseDao {
	
	
	
	@Override
	public List<Course> findAllCourses() {
		Connection Conn = DBUtil.getMySqlConnection();
		String sql = "select * from Course";
		List<Course> courseList = new ArrayList<Course>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String courseNo = rs.getString("courseNo");
				String courseName = rs.getString("courseName");
				double credits = rs.getDouble("credits");
				Course course = new Course(courseNo,courseName,credits);
				courseList.add(course);
			}
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return courseList;
	}

	@Override
	public Course findCourse(String courseNo) {
		Connection Conn = DBUtil.getMySqlConnection();
		String sql = "select * from Course where courseNo='" + courseNo + "'";
		Course course = new Course();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				course = new Course(courseNo,rs.getString("courseName"),rs.getDouble("credits"));
			}
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return course;
	}




	@Override
	public Course findPrerequisite(Course course) {
		Connection Conn = DBUtil.getMySqlConnection();
		Course preCourse = new Course();
		String sql = "select * from course where courseNo = '"+course.getCourseNo()+"' ";
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String preCourseNo = rs.getString("PreCourseNo");
				preCourse = findCourse(preCourseNo);
			}
			Conn.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return preCourse;
	}

	@Override
	public void addCourse(Course course, String preCourseNo) {
		Connection Conn = DBUtil.getMySqlConnection();
		String courseNo = course.getCourseNo();
		String courseName = course.getCourseName();
		double credits = course.getCredits();

		String sql = "insert into Course values('" + courseNo + "','" + courseName + "','" + credits + "', ' "+preCourseNo+" ')";
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("����γ��쳣��" + e.getMessage());
		}
	}



	@Override
	public void updatePrerequisite(Course course) {
		Connection Conn = DBUtil.getMySqlConnection();
		String sql = "update Course set preCourseNo='" + course.getPrerequisite().getCourseNo() + "' where courseNo='"
				+ course.getCourseNo() + "' ";
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("����γ��쳣��" + e.getMessage());
		}
	}

	
	
	@Override
	public void deleteCourse(String courseNo) {
		Connection Conn = DBUtil.getMySqlConnection();
		String sql = "delete from Course where courseNo='" + courseNo + "'";
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ���γ��쳣��" + e.getMessage());
		}
	}

	@Override
	public void updateCourse(Course course) {
		Connection Conn = DBUtil.getMySqlConnection();
		String sql = "update Course set courseName='" + course.getCourseName() + "',credits=" + course.getCredits()
				+ " where courseNo='" + course.getCourseNo() + "'";
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ���γ��쳣��" + e.getMessage());
		}
	}

	@Override
	public void addPrerequisite(String courseNo, String preCourseNo) {
		Connection Conn = DBUtil.getMySqlConnection();
		String sql = "update Course set PreCourseNo='" + preCourseNo + "' where courseNo='" + courseNo + "'";
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ���γ��쳣��" + e.getMessage());
		}
		
	}
}
