package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 存储点赞的人
 */
public class LikeDao extends BaseDao{
	private static final Object TABLE_NAME = "likes";

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		String sql="create table if not exists likes(id int not NULL auto_increment,user varchar(100) not NULL,drawing varchar(100) not NULL,primary key (id)) charset=utf8;";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("create likes Table:"+res);
		return res==0?true:false;
	}

	/**
	 * 添加记录(已测试)
	 * @param user
	 * @param drawingId
	 * @return 
	 */
	public boolean insert(String user,String drawingId){
		int result=0;
		String sql=String.format("insert into %s(user,drawing) values('%s','%s')"
				,TABLE_NAME,user,drawingId);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			result=statement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("insert likes:"+result);
		return result==1?true:false;
	}
	/**
	 * 判断是否存在
	 * @param user
	 * @param drawingId
	 * @return
	 */
	public boolean exists(String user,String drawingId){
		ResultSet resultSet=null;
		String sql=String.format("select user,drawing from %s where user=? and drawing=?",TABLE_NAME,user,drawingId);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			statement.setString(1, user);
			statement.setString(2, drawingId);
			resultSet=statement.executeQuery();
			if(resultSet!=null){
				//有下一行,表示注册过
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
		int count=0;
		String sql=String.format("select drawing from %s where drawing=?",TABLE_NAME,drawingId);
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
		String sql=String.format("DELETE FROM likes WHERE drawing=?");  
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
		System.out.println("delete likes:"+res);
		return res==1?true:false;
	}
}
