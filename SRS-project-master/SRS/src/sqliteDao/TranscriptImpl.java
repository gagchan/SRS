package sqliteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.SectionDao;
import dao.TranscriptDao;
import dao.lianjie;

import model.Section;
import model.Student;
import model.Transcript;
import model.TranscriptEntry;
import util.DBUtil;

public class TranscriptImpl implements TranscriptDao {
	
	
	@Override
	public Transcript getTranscriptByStudent(Student student) {
		Connection Conn = DBUtil.getSqliteConnection();
		String Sssn = student.getSsn();
		SectionDao sectionDao = lianjie.createSectionDao();
		String sql = "select * from transcriptentry where Sssn='"+ Sssn + "'";
		Transcript t = new Transcript(student);
		try {
			PreparedStatement pstmt = Conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int transEntryNo = rs.getInt("transEntryNo");
				Section section = sectionDao.getSection(rs.getInt("sectionNo"));
				String gradeReceived = rs.getString("gradeReceived");
				TranscriptEntry te = new TranscriptEntry(transEntryNo, student, gradeReceived, section);
				t.addTranscriptEntry(te);
			}
			rs.close();
			pstmt.close();
			Conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
}
