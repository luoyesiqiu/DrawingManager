package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import bean.Drawing;
import dao.DrawingDao;

public class DrawingDaoTest {
	
	@Test
	public void createTable()
	{
		DrawingDao dao=new DrawingDao();
		dao.createTable();
		
	}
	
	@Test
	public void insert() throws Exception
	{
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=simpleDateFormat.format(new Date());
		
		System.out.println(simpleDateFormat.parse(date));
		DrawingDao dao=new DrawingDao();
		for(int i=0;i<10;i++)
			dao.insert("2014110", "忍者神龟", "水彩画", "1024x1024", "2018-02-07", "金马奖", "国家级", "2017-12-5","75d5fef1-90ce-40b0-9b40-a2fa8ba6035e.jpg", "75d5fef1-90ce-40b0-9b40-a2fa8ba6035e.jpg","这是一幅画",date);
	}
	
	
	@Test
	public void update()
	{
		DrawingDao dao=new DrawingDao();
		dao.setDrawingPhoto("201345854", "C:/hhh/jjj.jpg");
	}
	
	@Test
	public void search()
	{
		DrawingDao dao=new DrawingDao();
		List<Drawing> list=dao.searchLimit(DrawingDao.StudentNumber, "3574162",1,4,true);
		Iterator<Drawing> iterator=list.iterator();
		while(iterator.hasNext())
		{
			System.out.println(iterator.next().toString());
		}
	}
	
	@Test
	public void pages()
	{
		DrawingDao dao=new DrawingDao();
		int n=dao.totalPages(DrawingDao.PrizeName, "金",4,false);
		System.out.println(n);
	}
	
	@Test
	public void items()
	{
		DrawingDao dao=new DrawingDao();
		int n=dao.totalItem(DrawingDao.PrizeName, "金",false);
		System.out.println(n);
	}
	@Test
	public void multiSearch()
	{
		DrawingDao dao=new DrawingDao();
		List<Drawing> drawings=dao.searchByMultiField(1,10,DrawingDao.DrawingCategory,"水彩画", DrawingDao.PrizeLevel,"院级");
		
		Iterator<Drawing> iterator=drawings.iterator();
		while(iterator.hasNext())
		{
			System.out.println(iterator.next().toString());
		}
	}
	
}
