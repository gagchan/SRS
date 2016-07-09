package service;
import dao.ProfessorDao;
import dao.StudentDao;
import dao.lianjie;
import model.Professor;
import model.Student;


public class UserService {
	public Student getStudent(String sssn){
		StudentDao spd=lianjie.createStudentDao();
		Student sp= spd.getStudent(sssn);
		return sp;
	}
	
	public Professor getProfessor(String pssn){
		ProfessorDao ppd=lianjie.createProfessorDao();
		Professor pp=ppd.getProfessor(pssn);
		return pp;
	}
	

}
