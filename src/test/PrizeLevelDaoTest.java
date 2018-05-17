package test;

import org.junit.Test;

import bean.PrizeLevel;
import dao.PrizeLevelDao;

public class PrizeLevelDaoTest {
	@Test
	public void createTable()
	{
		PrizeLevelDao prizeLevelDao=new PrizeLevelDao();
		prizeLevelDao.createTable();
	}
	
	@Test
	public void insert()
	{
		PrizeLevelDao prizeLevelDao=new PrizeLevelDao();
		prizeLevelDao.insert("Ժ��");
		prizeLevelDao.insert("У��");
		prizeLevelDao.insert("�м�");
		prizeLevelDao.insert("ʡ��");
		prizeLevelDao.insert("���Ҽ�");
		prizeLevelDao.insert("���缶");
	}
	
	@Test
	public void getAll()
	{
		PrizeLevelDao prizeLevelDao=new PrizeLevelDao();
		for(PrizeLevel prizeLevel:prizeLevelDao.getAll()){
			System.out.println(prizeLevel);
		}
	}
}
