package test;

import org.junit.Test;

import bean.DrawingType;
import dao.DrawingTypeDao;


public class DrawingTypeDaoTest {
	@Test
	public void createTable()
	{
		DrawingTypeDao drawingTypeDao=new DrawingTypeDao();
		drawingTypeDao.createTable();
	}
	
	@Test
	public void insert()
	{
		DrawingTypeDao drawingTypeDao=new DrawingTypeDao();
		drawingTypeDao.insert("ˮ�ʻ�");
		drawingTypeDao.insert("ˮī��");
		drawingTypeDao.insert("ˮ�ۻ�");
		drawingTypeDao.insert("�ͻ�");
		drawingTypeDao.insert("�滭");
		drawingTypeDao.insert("���ʻ�");
		drawingTypeDao.insert("ľ��");
		drawingTypeDao.insert("����");
		drawingTypeDao.insert("�鷨");
		drawingTypeDao.insert("׭��");
		drawingTypeDao.insert("����");
	}
	
	@Test
	public void getAll()
	{
		DrawingTypeDao drawingTypeDao=new DrawingTypeDao();
		for(DrawingType drawingType:drawingTypeDao.getAll()){
			System.out.println(drawingType);
		}
	}
}
