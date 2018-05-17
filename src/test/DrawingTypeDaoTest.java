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
		drawingTypeDao.insert("Ë®²Ê»­");
		drawingTypeDao.insert("Ë®Ä«»­");
		drawingTypeDao.insert("Ë®·Û»­");
		drawingTypeDao.insert("ÓÍ»­");
		drawingTypeDao.insert("°æ»­");
		drawingTypeDao.insert("¹¤±Ê»­");
		drawingTypeDao.insert("Ä¾¿Ì");
		drawingTypeDao.insert("ËØÃè");
		drawingTypeDao.insert("Êé·¨");
		drawingTypeDao.insert("×­¿Ì");
		drawingTypeDao.insert("µñËÜ");
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
