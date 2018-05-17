package test;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import bean.Student;
import dao.StudentDao;

public class StudentDaoTest {

	
	@Test
	public void register()
	{
		StudentDao studentDao=new StudentDao();
		int i=0;
		while(i++<20000)
		{
		studentDao.register((new Random().nextInt(9999999))+"", "3574162", "陈独秀", true, "2015", "美术学", "2018-9-9", "09a9d6bc-d310-4098-b608-0d19a6e9c0b1");
		}
	}
	
	
	@Test
	public void getStudent()
	{
		StudentDao studentDao=new StudentDao();
		Student student=studentDao.getStudent("213");
		System.out.println(student.getStudentName());
	}

	@Test
	public  void totalItem() {
		// TODO Auto-generated method stub
		StudentDao studentDao=new StudentDao();
		int n=studentDao.totalItem(StudentDao.StudentName, "", false);
		System.out.println(n);
	}
	
	
	@Test
	public void getStudents()
	{
		StudentDao studentDao=new StudentDao();
		List<Student> list=studentDao.getStudents(StudentDao.StudentName,"赵炎文",1,10);
		for(Student student:list){
			System.out.println(student.toString());
		}
	}
	
	@Test
	public void getStudents2()
	{
		StudentDao studentDao=new StudentDao();
		List<Student> list=studentDao.getStudents(StudentDao.StudentMajor,"美术学",1,10);
		for(Student student:list){
			System.out.println(student.toString());
		}
	}
	
	@Test
	public void getStudents3()
	{
		StudentDao studentDao=new StudentDao();
		List<Student> list=studentDao.getStudents(StudentDao.StudentGender,"男",1,10);
		for(Student student:list){
			System.out.println(student.toString());
		}
	}
	@Test
	public void multiSearch()
	{
		StudentDao studentDao=new StudentDao();
		List<Student> list=studentDao.searchByMultiField(1, 10, StudentDao.StudentMajor,"美术学",StudentDao.StudentGrade,"2017");
		for(Student student:list){
			System.out.println(student.toString());
		}
	}
	
}
