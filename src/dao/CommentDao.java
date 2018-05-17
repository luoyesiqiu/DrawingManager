package dao;

import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import bean.Comment;
import bean.Student;

public class CommentDao extends BaseDao {

	private static final Object TABLE_NAME = "comment";

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		String sql=String.format("create table if not exists comment(id int not NULL auto_increment,drawing_id varchar(20) not NULL,nickname varchar(20) not NULL,content varchar(2048) not NULL,create_date datetime not NULL,primary key (id)) charset=utf8;");
		int res=0;
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			res=statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("create comment Table:"+res);
		return res==0?true:false;
	}

	/**
	 * 插入
	 * @param drawingId
	 * @param nickname
	 * @param content
	 * @param createDate
	 * @return 1成功，0失败
	 */
	public boolean insert(String drawingId,String nickname,String content,String createDate) {
		// TODO Auto-generated method stub
		String sql=String.format("insert into comment(drawing_id,nickname,content,create_date) values(?,?,?,?)");
		int res=0;
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			statement.setString(1, drawingId);
			statement.setString(2, nickname);
			statement.setString(3, content);
			statement.setString(4, createDate);
			res=statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("insert comment:"+res);
		return res==1?true:false;
	}
	
	/**
	 * 	根据内容获取comment bean
	 *  可能获取到多个comment bean
	 * @param studentNumber
	 * @return
	 */
	public List<Comment> getComments(String drawingId,int page,int pageSize)
	{
		ResultSet resultSet=null;
		List<Comment> list=new ArrayList<>();
		String sql=null;
		sql=String.format("SELECT id,drawing_id,nickname,content,create_date FROM %s where drawing_id=? ORDER BY create_date DESC limit ?,?",TABLE_NAME);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			statement.setString(1, drawingId);
			statement.setInt(2, (page-1)*pageSize);
			statement.setInt(3, pageSize);
			resultSet=statement.executeQuery();
			if(resultSet!=null){
				//有下一行
				while(resultSet.next()){
					list.add(new Comment(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	
	/**
	 * 获取表中符合的条数
	 * @param drawing
	 * @return
	 */
	public int totalItem(String drawing){
		ResultSet resultSet=null;
		String sql=null;
		int count=0;
			sql=String.format("SELECT * from %s where drawing_id=?",TABLE_NAME);	
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			preparedStatement.setString(1, drawing);
			resultSet=preparedStatement.executeQuery();//查询

			if(resultSet!=null){
				while(resultSet.next()){
					count++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
	}
	
	/**
	 * 计算表中符合的页数
	 * @param totalItem 项数
	 * @param pageSize 页大小
	 * @return
	 */
	public int totalPages(int totalItem,int pageSize){
		int count=totalItem/pageSize;
		int extra=(totalItem%pageSize)>0?1:0;
		return count+extra;
	}
	/**
	 * 获取评论数
	 * @param drawingId
	 * @return
	 */
	public int getCommentCount(String drawingId){
		ResultSet resultSet=null;
		int count=0;
		String sql=String.format("select drawing_id from %s where drawing_id=?",TABLE_NAME,drawingId);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			statement.setString(1, drawingId);
			resultSet=statement.executeQuery();
			
			if(resultSet!=null){
				while(resultSet.next()){
					count++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 删除数据
	 * @return 1：成功
	 */
	public boolean deleteItem(String id){
		String sql=String.format("DELETE FROM comment WHERE drawing_id=?");  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
		 preparedStatement.setString(1, id);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("delete comment:"+res);
		return res==1?true:false;
	}
}
