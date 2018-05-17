package test;

import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bean.Comment;

import java.io.File;
import dao.CommentDao;

public class CommentDaoTest {
	
	@org.junit.Test
	public void createTable(){
		CommentDao commentDao=new CommentDao();
		commentDao.createTable();
	}
	
	@org.junit.Test
	public void insert() throws Exception{
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=simpleDateFormat.format(new Date());
		
		System.out.println(simpleDateFormat.parse(date));
		CommentDao commentDao=new CommentDao();
		commentDao.insert("1", "没事溜溜梅", "这幅画太赞了，意境非常美",date );
	}
	
	@org.junit.Test
	public void query() throws Exception{
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=simpleDateFormat.format(new Date());
		
		System.out.println(simpleDateFormat.parse(date));
		CommentDao commentDao=new CommentDao();
		List<Comment> list=commentDao.getComments("2", 1, 10);
		for (int i = 0; i < list.size(); i++) {
			Comment comment=list.get(i);
			System.out.println(comment);
		}
	}
}
