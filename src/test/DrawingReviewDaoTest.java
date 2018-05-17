package test;

import org.junit.Test;

import dao.DrawingReviewDao;

public class DrawingReviewDaoTest {
	
	@Test
	public void createTable()
	{
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		System.out.println(drawingReviewDao.createTable());
	}
	
	@Test
	public void insert()
	{
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		drawingReviewDao.insert("", "2013110", "��ʰ��", "ˮ�ʻ�", "1080x1980", "2018-02-28", "���ӽ�", "���ʽ�", "2018-04-15", "c128a993-8a41-49b0-8561-9a63bf413b83.JPG"
				, "c128a993-8a41-49b0-8561-9a63bf413b83.JPG", "����һ���⾳�����Ļ�", "2018-04-15", 0);
	}
	
	@Test
	public void searchById()
	{
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		System.out.println(drawingReviewDao.searchById("1"));
	}
	
	@Test
	public void getLastId()
	{
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		System.out.println(drawingReviewDao.getLastId());
	}
}
