package mysqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.ProfessorDao;
import dao.lianjie;
import model.Course;
import model.Professor;
import model.Section;
import util.DBUtil;

public class ProfessorImpl implements ProfessorDao {
	
	@Override
	public List<Professor> getAllProfessors() {
		Connection Conn = DBUtil.getMySqlConnection();
		String sql = "select * from Professor";
		List<Professor> professorList = new ArrayList<Professor>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String Pssn = rs.getString("Pssn");
				String title = rs.getString("title");
				String department = rs.getString("department");
				String professorName = rs.getString("professorName");
				String password = rs.getString("password");
				Professor professor = new Professor(professorName,Pssn,title,department,password);
				professorList.add(professor);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return professorList;
	}

	@Override
	public Professor getProfessor(String Pssn) {
		Professor professor = new Professor();
		Connection Conn = DBUtil.getMySqlConnection();
		String sql = "select * from Professor where Pssn='" + Pssn + "'";
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String title = rs.getString("title");
				String department = rs.getString("department");
				String professorName = rs.getString("professorName");
				String password = rs.getString("password");
				professor = new Professor(professorName,Pssn,title,department,password);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return professor;
	}

	@Override
	public List<Section> getSectionTeached(Professor professor) {
		Connection Conn = DBUtil.getMySqlConnection();
		String Pssn = professor.getSsn();
		String sql = "select * from Section where Pssn='" + Pssn
				+ "'";
		List<Section> sectionTeached = new ArrayList<Section>();
		
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int sectionNo = rs.getInt("sectionNo");
				Course course = lianjie.createCourseDao().findCourse(rs.getString("courseNo"));
				Section section = new Section(sectionNo, rs.getString("dayOfWeek"), rs.getString("timeOfDay"), course,
						rs.getString("room"), rs.getInt("seatingCapacity"));
				section.setInstructor(professor);
				sectionTeached.add(section);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sectionTeached;
	}

	@Override
	public void addProfessor(Professor professor) {
		Connection Conn = DBUtil.getMySqlConnection();
		String Pssn = professor.getSsn();
		String title = professor.getTitle();
		String department = professor.getDepartment();
		String professorName = professor.getName();

		String sql = "insert into Professor values('" + Pssn + "','" + title + "','" + department + "','"
				+ professorName + "','')";
		
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteProfessor(String ssn) {
		Connection Conn = DBUtil.getMySqlConnection();
		String sql = "delete from Professor where Pssn='" + ssn + "'";
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(sql);
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println( e.getMessage());
		}
	}

	@Override
	public void updateProfessor(Professor professor) {
		String Pssn = professor.getSsn();
		Connection Conn = DBUtil.getMySqlConnection();
		String Sql = "update Professor set title='" + professor.getTitle() + "',department='"
				+ professor.getDepartment() + "',professorName='" + professor.getName() + "',password = "+professor.getPassword()+" where Pssn='" + Pssn + "'";
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(Sql);
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println( e.getMessage());
		}
	}

	@Override
	public void teachSection(Professor professor, Section section) {
		Connection Conn = DBUtil.getMySqlConnection();
		String Pssn = professor.getSsn();
		int sectionNo = section.getSectionNo();
		String Sql = "insert into Professor_Section (Pssn,sectionNo) values('" + Pssn + "','" + sectionNo + "')";
		
		try {
			Statement stmt = Conn.createStatement();
			stmt.executeUpdate(Sql);
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
}
