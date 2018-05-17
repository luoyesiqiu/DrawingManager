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
		prizeLevelDao.insert("院级");
		prizeLevelDao.insert("校级");
		prizeLevelDao.insert("市级");
		prizeLevelDao.insert("省级");
		prizeLevelDao.insert("国家级");
		prizeLevelDao.insert("世界级");
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
