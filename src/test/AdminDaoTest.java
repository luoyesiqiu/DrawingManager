package test;

import org.junit.Test;

import dao.AdminDao;

public class AdminDaoTest {
	
	@Test
	public void createTable()
	{
		AdminDao adminDao=new AdminDao();
		adminDao.createTable();
	}
	
	@Test
	public void insert()
	{
		AdminDao adminDao=new AdminDao();
		adminDao.insert("admin", "3574162");
	}
}
