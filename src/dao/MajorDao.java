package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import bean.Major;


public class MajorDao extends BaseDao {
   

	/**
	 * 创建Major表
	 * @return 0：成功
	 */
	public boolean createTable(){
		String sql="create table if not exists major(id int not NULL auto_increment,major_name varchar(20) not NULL,primary key (id)) charset=utf8;";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("create major Table:"+res);
		return res==0?true:false;
	}
	
	/**
	 * 插入数据
	 * @return 1:ok
	 */
	public boolean insert(String majorName){
		String sql="insert into major(major_name) values(?)";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
		 preparedStatement.setString(1, majorName);
			res=preparedStatement.executeUpdate();//更新
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("insert major:"+res);
		return res==1?true:false;
	}
	
	/**
	 * 获取所有专业
	 */
	public List<Major> getAll(){
		ResultSet resultSet=null;
		List<Major> list=new ArrayList<>();
		String sql="SELECT id,major_name FROM major";
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//查询
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new Major( resultSet.getString(1),resultSet.getString(2)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
