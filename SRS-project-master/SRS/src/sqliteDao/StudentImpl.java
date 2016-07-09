package sqliteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import util.DBUtil;

public class StudentImpl implements StudentDao {
	Connection Conn = DBUtil.getSqliteConnection();
	
	@Override
	public List<Student> getAllStudents() {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "select * from student";
		List<Student> studentList = new ArrayList<Student>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String Sssn = rs.getString("Sssn");
				String major = rs.getString("major");
				String degree = rs.getString("degree");
				String studentName = rs.getString("studentName");
				String password = rs.getString("password");
				Student student = new Student(studentName,Sssn,major,degree,password);
				studentList.add(student);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public Student getStudent(String Sssn) {
		Connection Conn = DBUtil.getSqliteConnection();
		Student student = new Student();
		String sql = "select * from student where Sssn = '"+Sssn+"'";
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
			String major = rs.getString("major");
			String degree = rs.getString("degree");
			String studentName = rs.getString("studentName");
			String password = rs.getString("password");
			student = new Student(studentName,Sssn,major,degree,password);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}

	@Override
	public List<Section> getEnrolledSections(Student student) {
		Connection Conn = DBUtil.getSqliteConnection();
		String Sssn = student.getSsn();
		SectionDao sectionDao = lianjie.createSectionDao();
		
		List<Section> enrolledSections = new ArrayList<Section>();
		String sql = "select * from student_section where Sssn='"+ Sssn + "'";
		
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Section section = sectionDao.getSection(rs.getInt("sectionNo"));
				enrolledSections.add(section);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enrolledSections;
	}

	@Override
	public void addStudent(Student student) {
		Connection Conn = DBUtil.getSqliteConnection();
		String Sssn = student.getSsn();
		String major = student.getMajor();
		String degree = student.getDegree();
		String studentName = student.getName();
		String sql = "insert into student values('" + Sssn + "','" + studentName + "','" + major + "','" + degree
				+ "')";
		
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("�����ʦ�쳣��" + e.getMessage());
		}
	}

	@Override
	public void deleteStudent(Student student) {
		String Sssn = student.getSsn();
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "delete from Student where Sssn='" + Sssn + "'";
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ����ʦ�쳣��" + e.getMessage());
		}
	}

	@Override
	public void updateStudent(Student student) {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "update Student set studentName='" + student.getName() + "',major='" + student.getMajor()
				+ "',degree='" + student.getDegree() + "' , password =' "+student.getPassword()+" ' where Sssn='" + student.getSsn() + "'";
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ����ʦ�쳣��" + e.getMessage());
		}
	}

	@Override
	public void addEnrolledSection(Student student, int sectionNo) {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "insert into Student_Section (Sssn,sectionNo) values('" + student.getSsn() + "'," + sectionNo
				+ ")";
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ����ʦ�쳣��" + e.getMessage());
		}
	}

	@Override
	public void dropEnrolledSection(Student student, int sectionNo) {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "delete from Student_Section where Sssn='" + student.getSsn() + "' and sectionNo=" + sectionNo;
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			Conn.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ɾ����ʦ�쳣��" + e.getMessage());
		}
	}
}