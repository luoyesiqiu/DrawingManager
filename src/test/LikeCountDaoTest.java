package test;

import dao.LikeCountDao;

public class LikeCountDaoTest {
	@org.junit.Test
	public void createtable(){
		LikeCountDao likeCountDao=new LikeCountDao();
		likeCountDao.createTable();
	}
}
