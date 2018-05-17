package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.ResolutionSyntax;

import bean.Drawing;
import bean.Student;

public class StudentDao extends BaseDao{

	private final String TABLE_NAME="student";
	public static final String StudentNumber="student_num";
	public static final String StudentPassword="student_password";
	public static final String StudentName="student_name";
	public static final String StudentGender="student_gender";
	public static final String StudentGrade="student_grade";
	public static final String StudentMajor="student_major";
	public static final String StudentBirth="student_birth";
	public static final String StudentPhoto="student_photo";
	public StudentDao() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * ����student��
	 * @return 0�ɹ�
	 */
	public boolean createTable(){
		String sql=String.format("create table if not exists student(id int not NULL auto_increment,student_num varchar(100) not NULL,student_password varchar(100) not NULL,student_name varchar(100) not NULL,student_gender tinyint(1) not NULL,student_grade varchar (100) not NULL,student_major varchar(100) not NULL,student_birth varchar(100) not NULL,student_photo varchar(1000) not NULL,primary key (id)) charset=utf8;");
		int res=0;
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			res=statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("create student Table:"+res);
		return res==0?true:false;
	}
	/**
	 * ѧ����¼
	 * @param studentNumber ѧ��
	 * @param password ����
	 */
	public boolean login(String studentNumber,String password){
		ResultSet resultSet=null;
		String sql=String.format("select student_num,student_password from %s where student_num='%s' and student_password='%s'"
				,TABLE_NAME,studentNumber,password);
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
	 * ע��
	 * @param studentNumber ѧ��
	 * @param password ����
	 * @param studentName ѧ������
	 * @param studentGender �Ա�
	 * @param studentGrade �꼶
	 * @param studentMajor רҵ
	 * @param studentBirth ����
	 * @param studentPhoto ��Ƭ
	 * @return ע���Ƿ�ɹ�
	 */
	public boolean register(String studentNumber,String password,String studentName,boolean studentGender,String studentGrade,String studentMajor,String studentBirth,String studentPhoto){

		int res=0;
		int gender=studentGender?0:1;//����0��Ů��1
		String sql=String.format("insert into %s(student_num,student_password,student_name,student_gender,student_grade,student_major,student_birth,student_photo) values('%s','%s','%s',%d,'%s','%s','%s','%s');"
				,TABLE_NAME,studentNumber,password,studentName,gender,studentGrade,studentMajor,studentBirth,studentPhoto);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			res=statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("insert student:"+res);
		return res==1?true:false;
	}
	
	/**
	 * ����ѧ�Ż�ȡһ��student bean
	 * @param studentNumber
	 * @return
	 */
	public Student getStudent(String studentNumber)
	{
		ResultSet resultSet=null;
		String sql=String.format("select student_num,student_password,student_name,student_gender,student_grade,student_major,student_birth,student_photo from %s where student_num='%s'",TABLE_NAME,studentNumber);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			resultSet=statement.executeQuery();
			if(resultSet!=null){
				//����һ��
				if(resultSet.next()){
					int sex=resultSet.getInt(4);
					boolean gender=(sex==1?true:false);
					return new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), gender
							, resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 	�������ݻ�ȡstudent bean����Ϊ���ܻ���ͬ�������
	 *  �����п��ܻ�ȡ�����student bean
	 *  �Ա��ѯ������
	 *  
	 *  ����ģ������
	 * @param studentNumber
	 * @return
	 */
	public List<Student> getStudents(String field,String content,int page,int pageSize)
	{
		ResultSet resultSet=null;
		List<Student> list=new ArrayList<>();
		String sql=null;
		if(!field.equals(StudentGender)){
			sql=String.format("select student_num,student_password,student_name,student_gender,student_grade,student_major,student_birth,student_photo from %s where %s='%s' limit %d,%d",TABLE_NAME,field,content,(page-1)*pageSize,pageSize);
		}
		else
		{
			int gender=content.equals("��")?0:1;
			sql=String.format("select student_num,student_password,student_name,student_gender,student_grade,student_major,student_birth,student_photo from %s where %s=%d limit %d,%d",TABLE_NAME,field,gender,(page-1)*pageSize,pageSize);
		}
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			resultSet=statement.executeQuery();
			if(resultSet!=null){
				//����һ��
				while(resultSet.next()){
					int sex=resultSet.getInt(4);
					boolean gender=(sex==1?true:false);
					list.add(new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), gender
							, resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	/**
	 * 	�������ݻ�ȡstudent bean����Ϊ���ܻ���ͬ�������
	 *  �����п��ܻ�ȡ�����student bean
	 *  �Ա��ѯ������
	 *  
	 *  ����ģ������
	 * @return
	 */
	public List<Student> getStudents(String field,String content)
	{
		ResultSet resultSet=null;
		List<Student> list=new ArrayList<>();
		String sql=null;
		if(!field.equals(StudentGender)){
			sql=String.format("select student_num,student_password,student_name,student_gender,student_grade,student_major,student_birth,student_photo from %s where %s='%s'",TABLE_NAME,field,content);
		}
		else
		{
			int gender=content.equals("��")?0:1;
			sql=String.format("select student_num,student_password,student_name,student_gender,student_grade,student_major,student_birth,student_photo from %s where %s=%d",TABLE_NAME,field,gender);
		}
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			resultSet=statement.executeQuery();
			if(resultSet!=null){
				//����һ��
				while(resultSet.next()){
					int sex=resultSet.getInt(4);
					boolean gender=(sex==1?true:false);
					list.add(new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), gender
							, resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	/**
	 * ���ֶβ�ѯ
	 * @param page
	 * @param pageSize
	 * @param data �ɱ����
	 * @return
	 */
	public List<Student> searchByMultiField(int page,int pageSize,String... data){
		ResultSet resultSet=null;
		List<Student> list=new ArrayList<>();
		if(data==null||data.length%2!=0){
			//����������Ϊż�����׳��쳣
			throw new IllegalArgumentException("��������������");
		}
		StringBuilder  stringBuilder=new StringBuilder();
		int sexIdx=-1;//-1����Ҫ��Ϊ����
		for(int i=0;i<data.length;i+=2){
			stringBuilder.append(data[i]+"=?");
			
			if((i+1)!=data.length-1){
				stringBuilder.append(" and ");
			}
			//���Ա��ֶ����ر���
			if(data.equals(StudentDao.StudentGender)){
				sexIdx=i;
			}
		}
		
		String sql=String.format("select student_num,student_password,student_name,student_gender,student_grade,student_major,student_birth,student_photo from student where %s limit ?,?",stringBuilder.toString());	
		System.out.println(sql);
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			for(int i=0;i<data.length;i+=2){
				if(i==sexIdx){
					preparedStatement.setInt(i/2+1, data[i+1].equals("��")?0:1);
				}else{
					preparedStatement.setString(i/2+1, data[i+1]);
				}
			}
			preparedStatement.setInt(data.length/2+1,(page-1)*pageSize);
			preparedStatement.setInt(data.length/2+2,pageSize);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				while(resultSet.next()){
					int sex=resultSet.getInt(4);
					boolean gender=(sex==1?true:false);
					list.add(new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), gender
							, resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	/**
	 * ��ȡ���з��ϵ�����
	 * @param field
	 * @param content
	 * @param accurate
	 * @return
	 */
	public int totalItem(String field,String content,boolean accurate){
		ResultSet resultSet=null;
		String sql=null;
		int count=0;
		if(accurate){
			sql=String.format("SELECT student_num,student_password,student_name,student_gender,student_grade,student_major,student_birth,student_photo FROM student where %s = '%s'",field,content);
		}else{
			sql=String.format("SELECT student_num,student_password,student_name,student_gender,student_grade,student_major,student_birth,student_photo FROM student where %s like '%%%s%%'",field,content);
		}		
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//��ѯ

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
	 * ��ȡ���з��ϵ�����
	 * @param field
	 * @param content
	 * @param accurate
	 * @return
	 */
	public int totalItem(String...data){
		ResultSet resultSet=null;
		int count=0;
		if(data==null||data.length%2!=0){
			//����������Ϊż�����׳��쳣
			throw new IllegalArgumentException("��������������");
		}
		StringBuilder  stringBuilder=new StringBuilder();
		int sexIdx=-1;//-1����Ҫ��Ϊ����
		for(int i=0;i<data.length;i+=2){
			stringBuilder.append(data[i]+"=?");
			
			if((i+1)!=data.length-1){
				stringBuilder.append(" and ");
			}
			//���Ա��ֶ����ر���
			if(data.equals(StudentDao.StudentGender)){
				sexIdx=i;
			}
		}
		
		String sql=String.format("select student_num,student_password,student_name,student_gender,student_grade,student_major,student_birth,student_photo from student where %s",stringBuilder.toString());	
		System.out.println(sql);
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			for(int i=0;i<data.length;i+=2){
				if(i==sexIdx){
					preparedStatement.setInt(i/2+1, data[i+1].equals("��")?0:1);
				}else{
					preparedStatement.setString(i/2+1, data[i+1]);
				}
			}
			resultSet=preparedStatement.executeQuery();//��ѯ
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
	 * ��ѯ���з��ϵ�ҳ��
	 * @param field �ֶ�
	 * @param content ����
	 * @param pageSize ҳ��С
	 * @param accurate
	 * @return
	 */
	public int totalPages(String field,String content,int pageSize,boolean accurate){
		int totalItem=totalItem(field, content, accurate);
		int count=totalItem/pageSize;
		int extra=(totalItem%pageSize)>0?1:0;
		return count+extra;
		
	}
	
	/**
	 * ��ѯ���з��ϵ�ҳ��
	 * @param totalItem ����
	 * @param pageSize ҳ��С
	 * @return
	 */
	public int totalPages(int totalItem,int pageSize){
		int count=totalItem/pageSize;
		int extra=(totalItem%pageSize)>0?1:0;
		return count+extra;
		
	}
	/**
	 * ����ѧ���ж��Ƿ�ע���
	 * @param studentId
	 * @return
	 */
	public boolean exists(String studentNumber){
		ResultSet resultSet=null;
		String sql=String.format("select student_num,student_password from %s where student_num='%s'",TABLE_NAME,studentNumber);
		try {
			PreparedStatement statement=getConnection().prepareStatement(sql);
			resultSet=statement.executeQuery();
			if(resultSet!=null){
				//����һ��,��ʾע���
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
	 * ��������
	 * @return 1���ɹ�
	 */
	public boolean update(String studentNumber,String field,String value){
		String sql=String.format("update student set %s=? where student_num=?",field);  
		int res=0;
		try {
			 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			 
			 //preparedStatement.setString(1, field);
			 preparedStatement.setString(1, value);
			 preparedStatement.setString(2, studentNumber);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("update student string:"+res);
		return res==1?true:false;
	}
	
	/**
	 * ��������
	 * @return 1���ɹ�
	 */
	public boolean update(String studentNumber,String field,int value){
		String sql=String.format("update student set %s=? where student_num=?",field);  
		int res=0;
		try {
			 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			 
			 //preparedStatement.setString(1, field);
			 preparedStatement.setInt(1, value);
			 preparedStatement.setString(2, studentNumber);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("update student int:"+res);
		return res==1?true:false;
	}
	
	/**
	 * ɾ������
	 * @return 1���ɹ�
	 */
	public boolean deleteItem(String id){
		String sql=String.format("DELETE FROM student WHERE student_num=?");  
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
		System.out.println("delete student:"+res);
		return res==1?true:false;
	}
}
