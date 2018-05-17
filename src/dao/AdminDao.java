package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao extends BaseDao{

	private static final String TABLE_NAME = "admin";

	/**
	 * ����Admin��(�Ѳ���)
	 * @return  0����ɹ�
	 */
	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		String sql=String.format("create table if not exists admin(id int not NULL auto_increment,admin_name varchar(100) not NULL,admin_password varchar(100) not NULL,primary key (id)) charset=utf8;");
		int res=0;
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			res=statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("create admin Table:"+res);
		return res==0?true:false;
	}

	/**
	 * ����Ա��¼(�Ѳ���)
	 * @param adminName �û���
	 * @param password ����
	 */
	public boolean login(String adminName,String password){
		ResultSet resultSet=null;
		String sql=String.format("select admin_name,admin_password from %s where admin_name='%s' and admin_password='%s'"
				,TABLE_NAME,adminName,password);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			resultSet=statement.executeQuery();
			if(resultSet!=null){
				if(resultSet.first())
				{
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
	 * ��Ӽ�¼(�Ѳ���)
	 * @param adminName
	 * @param password
	 * @return 
	 */
	public boolean insert(String adminName,String password){
		int result=0;
		String sql=String.format("insert into %s(admin_name,admin_password) values('%s','%s')"
				,TABLE_NAME,adminName,password);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			result=statement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result==1?true:false;
	}
	
}
