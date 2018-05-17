package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Drawing;
import bean.DrawingReview;

public class DrawingReviewDao extends BaseDao{
	public final static String DrawingId="drawing_id";
	public final static String StudentNumber="student_num";
	public final static String DrawingName="drawing_name";
	public final static String DrawingCategory="drawing_category";
	public final static String DrawingSize="drawing_size";
	public final static String CreateDate="create_date";
	public final static String PrizeName="prize_name";
	public final static String PrizeLevel="prize_level";
	public final static String PrizeDate="prize_date";
	public final static String PrizePhoto="prize_photo";
	public final static String DrawingPhoto="drawing_photo";
	public final static String DrawingDesc="drawing_desc";
	public final static String PublishDate="publish_date";
	public final static String Action="action";
	/**
	 * 创建表
	 * @return 0：成功
	 */
	public boolean createTable(){
		String sql="create table if not exists drawing_review(id int not NULL auto_increment,drawing_id varchar(20) not NULL,student_num varchar(20) not NULL,drawing_name varchar(100) not NULL,drawing_category varchar(10) not NULL,drawing_size varchar(20) not NULL,create_date varchar(20) not NULL,prize_name varchar(30) not NULL,prize_level varchar(10) not NULL,prize_date varchar(20) not NULL,prize_photo varchar(1000) not NULL,drawing_photo varchar(1000) not NULL,drawing_desc varchar(3000) not NULL,publish_date datetime not NULL,action int not NULL,primary key (id)) charset=utf8;";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("create drawing Table:"+res);
		return res==0?true:false;
	}
	
	/**
	 * 插入数据
	 * @return
	 */
	public boolean insert(String drawingId,String studentNumber,String drawingName,String drawingCategory,String drawingSize,String createDate
			,String prizeName,String prizeLevel,String prizeDate,String prizePhoto
			,String drawingPhoto,String drawingDesc,String publishDate,int action){
		String sql="insert into drawing_review(drawing_id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date,action) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
		 preparedStatement.setString(1, drawingId);
		 preparedStatement.setString(2, studentNumber);
		 preparedStatement.setString(3, drawingName);
		 preparedStatement.setString(4, drawingCategory);
		 preparedStatement.setString(5, drawingSize);
		 preparedStatement.setString(6, createDate);
		 preparedStatement.setString(7, prizeName);
		 preparedStatement.setString(8, prizeLevel);
		 preparedStatement.setString(9, prizeDate);
		 preparedStatement.setString(10, prizePhoto);
		 preparedStatement.setString(11, drawingPhoto);
		 preparedStatement.setString(12, drawingDesc);
		 preparedStatement.setString(13, publishDate);
		 preparedStatement.setInt(14, action);
			res=preparedStatement.executeUpdate();//更新
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("insert drawing_review:"+res);
		return res==1?true:false;
	}
	
	
	/**
	 * 获取新内容
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<DrawingReview> getNewItems(int page,int pageSize){
		ResultSet resultSet=null;
		List<DrawingReview> list=new ArrayList<>();
		String sql=null;
		sql=String.format("SELECT id,drawing_id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date,action FROM drawing_review ORDER BY publish_date DESC limit %d,%d",(page-1)*pageSize,pageSize);	
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//查询
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new DrawingReview( String.valueOf(resultSet.getInt(1)),resultSet.getString(2),resultSet.getString(3)
							,resultSet.getString(4),resultSet.getString(5),resultSet.getString(6)
							,resultSet.getString(7),resultSet.getString(8),resultSet.getString(9)
							,resultSet.getString(10),resultSet.getString(11),resultSet.getString(12)
							,resultSet.getString(13),resultSet.getString (14),resultSet.getInt(15)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	/**
	 * 根据id从数据表中项
	 * 
	 * @return
	 */
	public DrawingReview searchById(String id){
		ResultSet resultSet=null;
		String sql=String.format("SELECT id,drawing_id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date,action FROM drawing_review where id = ?");
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			preparedStatement.setString(1, id);
			resultSet=preparedStatement.executeQuery();//查询
			if(resultSet!=null){
				if(resultSet.next()){
					return new DrawingReview(String.valueOf(resultSet.getInt(1)),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
							,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10)
							,resultSet.getString(11),resultSet.getString(12),resultSet.getString(13),resultSet.getString(14),resultSet.getInt(15));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 根据作品id从数据表中项
	 * 
	 * @return
	 */
	public DrawingReview searchByDrawingId(String drawingId){
		ResultSet resultSet=null;
		String sql=String.format("SELECT id,drawing_id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date,action FROM drawing_review where drawing_id = ?");
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			preparedStatement.setString(1, drawingId);
			resultSet=preparedStatement.executeQuery();//查询
			if(resultSet!=null){
				if(resultSet.next()){
					return new DrawingReview(String.valueOf(resultSet.getInt(1)),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
							,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10)
							,resultSet.getString(11),resultSet.getString(12),resultSet.getString(13),resultSet.getString(14),resultSet.getInt(15));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 获取最后插入一项的ID
	 * @return
	 */
	public int getLastId()
	{
		String sql="SELECT MAX(id) FROM drawing_review;";
		ResultSet res=null;
		try {
			 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			 
			res=preparedStatement.executeQuery();
			if(res.next()){
				return res.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	/**
	 * 更新数据
	 * @return 1：成功
	 */
	public boolean update(String id,String field,String value){
		String sql=String.format("update drawing_review set %s=? where id=?",field);  
		int res=0;
		try {
			 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			 preparedStatement.setString(1, value);
			 preparedStatement.setString(2, id);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("update drawing_review:"+res);
		return res==1?true:false;
	}
	/**
	 * 删除数据
	 * @return 1：成功
	 */
	public boolean deleteItem(String id){
		String sql=String.format("DELETE FROM drawing_review WHERE id=?");  
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
		System.out.println("delete drawing_review:"+res);
		return res==1?true:false;
	}
	/**
	 * 获取表中所有条数
	 * @param field
	 * @param content
	 * @param accurate
	 * @return
	 */
	public int totalItem(){
		ResultSet resultSet=null;
		String sql=null;
		int count=0;

		sql=String.format("SELECT id FROM drawing_review");		
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
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
}
