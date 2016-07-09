package service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.ProfessorDao;
import dao.lianjie;
import model.Professor;
import model.Student;

public class ProfessorService {
	
	ProfessorDao pd=lianjie.createProfessorDao();
	
	public String getAllProfessorsJSON() {
		JSONArray ja = new JSONArray();
		ProfessorDao pd = lianjie.createProfessorDao();
		List<Professor> professors = pd.getAllProfessors();
		for (Professor p : professors) {
			JSONObject jo = new JSONObject();
			jo.put("Pssn", p.getSsn());
			jo.put("name", p.getName());
			jo.put("title", p.getTitle());
			jo.put("department", p.getDepartment());
			ja.put(jo);
		}
		return ja.toString();
	}

	public String getProfessorJSON(String Pssn) {
		JSONObject jo = new JSONObject();

		ProfessorDao pd = lianjie.createProfessorDao();
		Professor professor = pd.getProfessor(Pssn);
		jo.put("Pssn", professor.getSsn());
		jo.put("name", professor.getName());
		jo.put("title", professor.getTitle());
		jo.put("department", professor.getDepartment());

		return jo.toString();
	}
	
	public void addProfessor(String professorName,String pssn,String title,String department,String password){
		Professor p=new Professor(professorName, pssn, title, department,password);
		
		pd.addProfessor(p);
	}
	
	public void deleteProfessor(String pssn){
		
		pd.deleteProfessor(pssn);
	}
	
	public void updateProfessor(Professor professor){
		
		pd.updateProfessor(professor);
	}
	
	public boolean testPassword(Professor professor){
		String ssn = professor.getSsn();
		String password = professor.getPassword();
		boolean flag = false;
		if(password!=null && pd.getProfessor(ssn).getPassword().equals(password)){
			flag = true;
		}
		return flag;
	}
}
