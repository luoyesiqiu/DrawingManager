package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import bean.Grade;
import bean.Major;
import bean.PrizeLevel;


public class PrizeLevelDao extends BaseDao {
   

	/**
	 * ����prize_level��
	 * @return 0���ɹ�
	 */
	public boolean createTable(){
		String sql="create table if not exists prize_level(id int not NULL auto_increment,prize_level_name varchar(20) not NULL,primary key (id)) charset=utf8;";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("create prize_level Table:"+res);
		return res==0?true:false;
	}
	
	/**
	 * ��������
	 * @return 1:ok
	 */
	public boolean insert(String prizeName){
		String sql="insert into prize_level(prize_level_name) values(?)";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
		 preparedStatement.setString(1, prizeName);
			res=preparedStatement.executeUpdate();//����
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("insert prize_level:"+res);
		return res==1?true:false;
	}
	
	/**
	 * ��ȡ������߻�����
	 */
	public List<PrizeLevel> getAll(){
		ResultSet resultSet=null;
		List<PrizeLevel> list=new ArrayList<>();
		String sql="SELECT id,prize_level_name FROM prize_level";
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new PrizeLevel( resultSet.getString(1),resultSet.getString(2)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
