package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import bean.DrawingType;
import bean.Grade;
import bean.Major;


public class DrawingTypeDao extends BaseDao {
   

	/**
	 * ����drawing_type��
	 * @return 0���ɹ�
	 */
	public boolean createTable(){
		String sql="create table if not exists drawing_type(id int not NULL auto_increment,drawing_type_name varchar(20) not NULL,primary key (id)) charset=utf8;";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("create drawing_type Table:"+res);
		return res==0?true:false;
	}
	
	/**
	 * ��������
	 * @return 1:ok
	 */
	public boolean insert(String drawingName){
		String sql="insert into drawing_type(drawing_type_name) values(?)";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
		 preparedStatement.setString(1, drawingName);
			res=preparedStatement.executeUpdate();//����
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("insert drawing_type:"+res);
		return res==1?true:false;
	}
	
	/**
	 * ��ȡ������Ʒ����
	 */
	public List<DrawingType> getAll(){
		ResultSet resultSet=null;
		List<DrawingType> list=new ArrayList<>();
		String sql="SELECT id,drawing_type_name FROM drawing_type";
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new DrawingType( resultSet.getString(1),resultSet.getString(2)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
