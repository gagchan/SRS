package service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import dao.SectionDao;
import dao.StudentDao;
import dao.lianjie;
import model.Section;
import model.Student;

public class StudentService {
	StudentDao sd = lianjie.createStudentDao();
	public String getEnrolledSections(Student student) {
		JSONArray ja = new JSONArray();
		List<Section> sections = sd.getEnrolledSections(student);
		for (Section s : sections) {
			JSONObject jo = new JSONObject();
			jo.put("sectionNo", s.getSectionNo());
			jo.put("day", s.getDayOfWeek());
			jo.put("time", s.getTimeOfDay());
			jo.put("room", s.getRoom());
			jo.put("sCapacity", s.getSeatingCapacity());
			jo.put("courseName", s.getRepresentedCourse().getCourseName());
			jo.put("professor", s.getInstructor().getName());
			ja.put(jo);
		}
		return ja.toString();
	}
	
	public void dropEnrolledSection(Student student, int sectionNo){
		sd.dropEnrolledSection(student, sectionNo);
		SectionDao scd=lianjie.createSectionDao();
		Section section=scd.getSection(sectionNo);
		student.dropSection(section);
	}
	
	public boolean testPassword(Student student){
		String ssn = student.getSsn();
		String password = student.getPassword();
		boolean flag = false;
		if(sd.getStudent(ssn).getPassword().equals(password)){
			flag = true;
		}
		return flag;
	}
}
