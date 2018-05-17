 package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Drawing;

/**
 * 存储点赞数
 */
public class LikeCountDao extends BaseDao{

	private static final Object TABLE_NAME = "like_count";

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		String sql="create table if not exists like_count(id int not NULL auto_increment,drawing_id varchar(10) not NULL,count varchar(10) not NULL,primary key (id)) charset=utf8;";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("create like_count Table:"+res);
		return res==0?true:false;
	}
	/**
	 * 点赞
	 * @param drawingId
	 */
	public void like(String drawingId)
	{
		if(exists(drawingId)){
			int likeCount=getLikeCount(drawingId);
			updateLikeCount(drawingId, String.valueOf(likeCount+1));
		}
		else{
			insert(drawingId, "1");
		}
	}
	
	/**
	 * 更新数据
	 * @return 1：成功
	 */
	public boolean updateLikeCount(String drawingId,String likeCount){
		String sql=String.format("update like_count set count=? where drawing_id=?");  
		int res=0;
		try {
			 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			 
			 //preparedStatement.setString(1, field);
			 preparedStatement.setString(1, likeCount);
			 preparedStatement.setString(2, drawingId);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("update like_count:"+res);
		return res==1?true:false;
	}
	
	/**
	 * 添加记录
	 * @param drawingId
	 * @return 
	 */
	public boolean insert(String drawingId,String likeCount){
		int result=0;
		String sql=String.format("insert into %s(drawing_id,count) values(?,?)"
				,TABLE_NAME);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			statement.setString(1, drawingId);
			statement.setString(2, likeCount);
			result=statement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("insert like_count:"+result);
		return result==1?true:false;
	}
	/**
	 * 判断是否存在
	 * @param user
	 * @param drawingId
	 * @return
	 */
	public boolean exists(String drawingId){
		ResultSet resultSet=null;
		String sql=String.format("select drawing_id from %s where drawing_id=?",TABLE_NAME);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			statement.setString(1, drawingId);
			resultSet=statement.executeQuery();
			if(resultSet!=null){
				if(resultSet.next()){
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 获取赞数
	 * @param user
	 * @param drawingId
	 * @return
	 */
	public int getLikeCount(String drawingId){
		ResultSet resultSet=null;
		String sql=String.format("select count from %s where drawing_id=?",TABLE_NAME,drawingId);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			statement.setString(1, drawingId);
			resultSet=statement.executeQuery();
			
			if(resultSet!=null){
				if(resultSet.next()){
					return Integer.parseInt(resultSet.getString(1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获取赞数最多的作品Id
	 * @return 作品id列表
	 */
	public List<String> getTopLikeCount(int page,int pageSize){
		ResultSet resultSet=null;
		List<String> list=new ArrayList<>();
		String sql=null;
		sql=String.format("SELECT drawing_id FROM like_count ORDER BY count DESC limit %d,%d",(page-1)*pageSize,pageSize);	
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//查询
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(resultSet.getString(1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 删除数据
	 * @return 1：成功
	 */
	public boolean deleteItem(String id){
		String sql=String.format("DELETE FROM like_count WHERE drawing_id=?");  
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
		System.out.println("delete like_count:"+res);
		return res==1?true:false;
	}
}
