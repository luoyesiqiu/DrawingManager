package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import bean.Grade;
import bean.Major;


public class GradeDao extends BaseDao {
   

	/**
	 * ����grade��
	 * @return 0���ɹ�
	 */
	public boolean createTable(){
		String sql="create table if not exists grade(id int not NULL auto_increment,grade_name varchar(20) not NULL,primary key (id)) charset=utf8;";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("create grade Table:"+res);
		return res==0?true:false;
	}
	
	/**
	 * ��������
	 * @return 1:ok
	 */
	public boolean insert(String gradeName){
		String sql="insert into grade(grade_name) values(?)";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
		 preparedStatement.setString(1, gradeName);
			res=preparedStatement.executeUpdate();//����
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("insert grade:"+res);
		return res==1?true:false;
	}
	
	/**
	 * ��ȡ�����꼶
	 */
	public List<Grade> getAll(){
		ResultSet resultSet=null;
		List<Grade> list=new ArrayList<>();
		String sql="SELECT id,grade_name FROM grade";
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new Grade( resultSet.getString(1),resultSet.getString(2)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
