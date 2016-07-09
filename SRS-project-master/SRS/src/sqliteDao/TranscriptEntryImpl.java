package sqliteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.CourseDao;
import dao.SectionDao;
import dao.StudentDao;
import dao.TranscriptEntryDao;
import dao.lianjie;
import model.Course;
import model.Professor;
import model.Section;
import model.Student;
import model.TranscriptEntry;
import util.DBUtil;

public class TranscriptEntryImpl implements TranscriptEntryDao {
	@Override
	public List<TranscriptEntry> getAllTranscriptEntrys() {
		Connection Conn = DBUtil.getSqliteConnection();
		StudentDao studentDao = lianjie.createStudentDao();
		SectionDao sectionDao = lianjie.createSectionDao();
		String sql = "select * from TranscriptEntry";
		List<TranscriptEntry> transcriptEntryList = new ArrayList<TranscriptEntry>();
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int transcriptEntryNo = rs.getInt("transEntryNo");
				String gradeReceived = rs.getString("gradeReceived");
				Student student = studentDao.getStudent(rs.getString("Sssn"));
				Section section = sectionDao.getSection(rs.getInt("sectionNo"));
				TranscriptEntry transcriptEntry = new TranscriptEntry(transcriptEntryNo, student, gradeReceived,
						section);
				transcriptEntryList.add(transcriptEntry);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transcriptEntryList;
	}

	@Override
	public TranscriptEntry getTranscriptEntry(int transEntryNo) {
		Connection Conn = DBUtil.getSqliteConnection();
		StudentDao studentDao = lianjie.createStudentDao();
		SectionDao sectionDao = lianjie.createSectionDao();
		String sql = "select * from TranscriptEntry where transEntryNo='"+ transEntryNo + "'";
		TranscriptEntry transcriptEntry = null;
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			String gradeReceived = rs.getString("gradeReceived");
			Student student = studentDao.getStudent(rs.getString("Sssn"));
			Section section = sectionDao.getSection(rs.getInt("sectionNo"));
			transcriptEntry = new TranscriptEntry(transEntryNo, student, gradeReceived, section);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transcriptEntry;
	}

	@Override
	public void addTranscriptEntry(TranscriptEntry transcriptEntry) {
		Connection Conn = DBUtil.getSqliteConnection();
		String Sssn = transcriptEntry.getStudent().getSsn();
		int sectionNo = transcriptEntry.getSection().getSectionNo();
		String gradeReceived = transcriptEntry.getGrade();

		String sql = "insert into TranscriptEntry (Sssn,sectionNo,gradeReceived) values('" + Sssn + "','" + sectionNo
				+ "','" + gradeReceived + "')";
		
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
	public void deleteTranscriptEntry(TranscriptEntry transcriptEntry) {
		Connection Conn = DBUtil.getSqliteConnection();
		int transcriptEntryNo = transcriptEntry.getTransEntryNo();
		
		String sql = "delete from TranscriptEntry where transEntryNo='" + transcriptEntryNo + "'";
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
	public void updateTranscriptEntry(TranscriptEntry transcriptEntry) {
		Connection Conn = DBUtil.getSqliteConnection();
		String sql = "update TranscriptEntry set Sssn='" + transcriptEntry.getStudent().getSsn() + "',sectionNo="
				+ transcriptEntry.getSection().getSectionNo() + ",gradeReceived='" + transcriptEntry.getGrade()
				+ "' where transEntryNo=" + transcriptEntry.getTransEntryNo();
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
