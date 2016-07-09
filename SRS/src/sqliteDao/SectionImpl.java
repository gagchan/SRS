package sqliteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dao.CourseDao;
import dao.ProfessorDao;
import dao.SectionDao;
import dao.StudentDao;
import dao.lianjie;
import model.Course;
import model.Professor;
import model.Section;
import model.Student;
import model.TranscriptEntry;
import util.DBUtil;

public class SectionImpl implements SectionDao {
	Connection Conn = DBUtil.getSqliteConnection();
	CourseDao courseDao = lianjie.createCourseDao();
	ProfessorDao professorDao = lianjie.createProfessorDao();
	
	
	@Override
	public List<Section> getAllSections() {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "select * from Section";
		List<Section> sectionList = new ArrayList<Section>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Course course = courseDao.findCourse(rs.getString("courseNo"));
				Section section = new Section(rs.getInt("sectionNo"), rs.getString("dayOfWeek"), rs.getString("timeOfDay"), course,
						rs.getString("room"), rs.getInt("seatingCapacity"));
				Professor professor = professorDao.getProfessor(rs.getString("Pssn"));
				section.setInstructor(professor);
				sectionList.add(section);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sectionList;
	}

	@Override
	public Section getSection(int sectionNo) {
		Connection Conn = DBUtil.getSqliteConnection();
		Section section = new Section();
		String sql = "select * from Section where sectionNo='"+ sectionNo + "'";
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Course course = courseDao.findCourse(rs.getString("courseNo"));
				section = new Section(sectionNo, rs.getString("dayOfWeek"), rs.getString("timeOfDay"), course,
						rs.getString("room"), rs.getInt("seatingCapacity"));
				Professor professor = professorDao.getProfessor(rs.getString("Pssn"));
				section.setInstructor(professor);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return section;
	}

	@Override
	public List<Section> getSectionsByCourse(String courseNo) {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "select * from Section,Course,Professor where Section.Pssn=Professor.Pssn and Section.courseNo=Course.courseNo and Section.courseNo='"
				+ courseNo + "'";
		List<Section> sectionList = new ArrayList<Section>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int sectionNo = rs.getInt("sectionNo");
				Course course = courseDao.findCourse(rs.getString("courseNo"));
				Section section = new Section(sectionNo, rs.getString("dayOfWeek"), rs.getString("timeOfDay"), course,
						rs.getString("room"), rs.getInt("seatingCapacity"));
				Professor professor = professorDao.getProfessor(rs.getString("Pssn"));
				section.setInstructor(professor);
				sectionList.add(section);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sectionList;
	}

	@Override
	public List<Student> getEnrolledStudents(int sectionNo) {
		Connection Conn = DBUtil.getSqliteConnection();
		List<Student> enrolledStudents = new ArrayList<Student>();
		String sql = "select * from student_section where sectionNo='"+ sectionNo + "'";
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				StudentDao studentDao = lianjie.createStudentDao();
				Student student = studentDao.getStudent(rs.getString("Sssn"));
				enrolledStudents.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enrolledStudents;
	}

	@Override
	public HashMap<Student, TranscriptEntry> getAssignedGrades(Section section) {
		Connection Conn = DBUtil.getSqliteConnection();
		int sectionNo = section.getSectionNo();
		String sql = "select * from TranscriptEntry,Student where TranscriptEntry.Sssn=Student.Sssn and TranscriptEntry.sectionNo='"
				+ sectionNo + "'";
		HashMap<Student, TranscriptEntry> assignedGrades = new HashMap<Student, TranscriptEntry>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Student student = new Student(rs.getString("studentName"), rs.getString("Sssn"), rs.getString("major"),
						rs.getString("degree") , rs.getString("password"));
				TranscriptEntry transEntry = new TranscriptEntry(rs.getInt("transEntryNo"), student,
						rs.getString("grade"), section);
				assignedGrades.put(student, transEntry);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assignedGrades;
	}

	@Override
	public void addSection(Section section) {
		Connection Conn = DBUtil.getSqliteConnection();
		int sectionNo = section.getSectionNo();
		String dayOfWeek = section.getDayOfWeek();
		String timeOfDay = section.getTimeOfDay();
		String courseNo = section.getRepresentedCourse().getCourseNo();
		String pssn = section.getInstructor().getSsn();
		String room = section.getRoom();
		int seatingCapacity = section.getSeatingCapacity();

		String sql = "insert into Section values('" + sectionNo + "','" + dayOfWeek + "','" + timeOfDay + "','"
				+ courseNo + "','" + pssn + "','" + room + "','" + seatingCapacity + "')";
		
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("�������쳣��" + e.getMessage());
		}
	}

	@Override
	public void deleteSection(Section section) {
		int sectionNo = section.getSectionNo();
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "delete from Section where sectionNo='" + sectionNo + "'";
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ������쳣��" + e.getMessage());
		}
	}

	@Override
	public void updateSection(Section section) {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "update Section set dayOfWeek='" + section.getDayOfWeek() + "',timeOfDay='"
				+ section.getTimeOfDay() + "',courseNo='" + section.getRepresentedCourse().getCourseNo() + "',Pssn='"
				+ section.getInstructor().getSsn() + "',room='" + section.getRoom() + "',seatingCapacity="
				+ section.getSeatingCapacity() + " where sectionNo=" + section.getSectionNo();
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ������쳣��" + e.getMessage());
		}
	}
	
	
	@Override
	public List<Section> getAllOfferedSection(Course course) {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "select * from Section where courseNo='"+ course.getCourseNo() + "'";
		List<Section> offeredAsSection = new ArrayList<Section>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int sectionNo = rs.getInt("sectionNo");
				Section section = new Section(sectionNo, rs.getString("dayOfWeek"), rs.getString("timeOfDay"), course,
						rs.getString("room"), rs.getInt("seatingCapacity"));
				Professor professor = professorDao.getProfessor(rs.getString("Pssn"));
				section.setInstructor(professor);
				offeredAsSection.add(section);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offeredAsSection;
	}
}
