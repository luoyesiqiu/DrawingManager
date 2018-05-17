package test;

import org.junit.Test;

import bean.Grade;
import dao.GradeDao;

public class GradeDaoTest {
	
	@Test
	public void createTable()
	{
		GradeDao gradeDao=new GradeDao();
		gradeDao.createTable();
	}
	
	@Test
	public void insert()
	{
		GradeDao gradeDao=new GradeDao();
		gradeDao.insert("2017");
		gradeDao.insert("2016");
		gradeDao.insert("2015");
		gradeDao.insert("2014");
	}
	
	@Test
	public void getAll()
	{
		GradeDao gradeDao=new GradeDao();
		for(Grade grade:gradeDao.getAll()){
			System.out.println(grade);
		}
	}
}
