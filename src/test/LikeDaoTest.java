package test;

import org.junit.Test;

import dao.LikeDao;

public class LikeDaoTest {
	@Test
	public void createTable()
	{
		LikeDao  likeDao=new LikeDao();
		likeDao.createTable();
	}
	
	@Test
	public void insert()
	{
		LikeDao  likeDao=new LikeDao();
		likeDao.insert("3574162","3");
	}
	
	
	@Test
	public void exists()
	{
		LikeDao  likeDao=new LikeDao();
		boolean exists=likeDao.exists("3574162","1");
		System.out.println(exists);
	}
	
	@Test
	public void getCount()
	{
		LikeDao  likeDao=new LikeDao();
		int count=likeDao.getLikeCount("3");
		System.out.println(count);
	}
}
