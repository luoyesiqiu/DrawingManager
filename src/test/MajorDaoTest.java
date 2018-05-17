package test;

import java.util.List;

import org.junit.Test;

import bean.Major;
import dao.MajorDao;

public class MajorDaoTest {
	
	
	@Test
	public void create()
	{
		MajorDao dao=new MajorDao();
		dao.createTable();
	}
	
	@Test
	public void insert()
	{
		MajorDao dao=new MajorDao();
		dao.insert("美术学");
		dao.insert( "动画");
		dao.insert("广告设计");
	}
	
	@Test
	public void getAll()
	{
		MajorDao dao=new MajorDao();
		List<Major> list=dao.getAll();
		for (int i = 0; i < list.size(); i++) {
			System.err.println(list.get(i));
		}
	}
}
