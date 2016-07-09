package service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.swing.internal.plaf.metal.resources.metal;

import dao.CourseDao;
import dao.SectionDao;
import dao.StudentDao;
import dao.TranscriptDao;
import dao.lianjie;
import model.Course;
import model.EnrollmentStatus;
import model.Section;
import model.Student;
import model.Transcript;

public class SectionService {
	public String getAllSectionsJSON(String courseNo) {
		JSONArray ja = new JSONArray();

		SectionDao sd = lianjie.createSectionDao();
		List<Section> sections = sd.getSectionsByCourse(courseNo);

		for (Section s : sections) {
			JSONObject jo = new JSONObject();
			jo.put("sectionNo", s.getSectionNo());
			jo.put("day", s.getDayOfWeek());
			jo.put("time", s.getTimeOfDay());
			jo.put("room", s.getRoom());
			jo.put("sCapacity", s.getSeatingCapacity());
			jo.put("professor", s.getInstructor().getName());
			jo.put("pssn", s.getInstructor().getSsn());
			ja.put(jo);
		}
		return ja.toString();
	}

	public String getEnrolledStudents(int sectionNo) {
		JSONArray ja = new JSONArray();

		SectionDao sd = lianjie.createSectionDao();
		List<Student> enrolled = sd.getEnrolledStudents(sectionNo);

		for (Student s : enrolled) {
			JSONObject jo = new JSONObject();
			jo.put("sssn", s.getSsn());
			jo.put("name", s.getName());
			jo.put("major", s.getMajor());
			jo.put("degree", s.getDegree());
			ja.put(jo);
		}
		return ja.toString();
	}

	public String getEnrollmentResult(int sectionNo, Student student) {
		JSONObject jo = new JSONObject();
		StudentDao sd = lianjie.createStudentDao();
		List<Section> enrolledSections = sd.getEnrolledSections(student);
		student.setAttends(enrolledSections);

		TranscriptDao td = lianjie.createTranscriptDao();
		Transcript transcript = td.getTranscriptByStudent(student);
		student.setTranscript(transcript);

		SectionDao scd = lianjie.createSectionDao();
		Section section = scd.getSection(sectionNo);

		List<Student> enrolledStudents = scd.getEnrolledStudents(sectionNo);
		section.setEnrolledStudents(enrolledStudents);

		Course course = section.getRepresentedCourse();
		section.setRepresentedCourse(course);
		
		CourseDao courseDao = lianjie.createCourseDao();
		
		course.setPrerequisite(courseDao.findPrerequisite(course));
		
		EnrollmentStatus result = section.enroll(student);
		jo.put("result", result.toString());
		if(result.toString().equals("prereq")){
			jo.put("sorry", result.value()+"("+course.getPrerequisite().getCourseName()+")！");
		}else if(result.toString().equals("success")){
			sd.addEnrolledSection(student, sectionNo);
			jo.put("sorry", result.value());
		}else{
			jo.put("sorry", result.value());
		}
		return jo.toString();
	}
}
