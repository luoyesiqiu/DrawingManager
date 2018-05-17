package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.developer.StreamingAttachment;

import bean.Drawing;
import bean.Major;

/**
 * drawing�������
 */
public class DrawingDao extends BaseDao{

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
	/**
	 * ������
	 * @return 0���ɹ�
	 */
	public boolean createTable(){
		String sql="create table if not exists drawing(id int not NULL auto_increment,student_num varchar(20) not NULL,drawing_name varchar(100) not NULL,drawing_category varchar(10) not NULL,drawing_size varchar(20) not NULL,create_date varchar(20) not NULL,prize_name varchar(30) not NULL,prize_level varchar(10) not NULL,prize_date varchar(20) not NULL,prize_photo varchar(1000) not NULL,drawing_photo varchar(1000) not NULL,drawing_desc varchar(1000) not NULL,publish_date datetime not NULL,primary key (id)) charset=utf8;";  
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
	 * ������Ʒid�����ݱ�����������
	 * 
	 * @param content Ҫ����������
	 * @return
	 */
	public Drawing searchById(String id){
		ResultSet resultSet=null;
		String sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where id = '%s'",id);
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				if(resultSet.next()){
					return new Drawing(String.valueOf(resultSet.getInt(1)),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
							,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10)
							,resultSet.getString(11),resultSet.getString(12),resultSet.getString(13));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * ����ѧ�Ŵ����ݱ�����������
	 * 
	 * ѧ���Ǿ�׼�ģ����Բ�ѯ��ʱ����ģ����ѯ
	 * 
	 * @param content Ҫ����������
	 * @param page 
	 * @param pageSize 
	 * @return
	 */
	public List<Drawing> searchByStudentNumberLimit(String content, int page, int pageSize){
		ResultSet resultSet=null;
		List<Drawing> list=new ArrayList<>();
		String sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where student_num = '%s' limit %d,%d",content,(page-1)*pageSize,pageSize);
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new Drawing( String.valueOf(resultSet.getInt(1)),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
							,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10)
							,resultSet.getString(11),resultSet.getString(12),resultSet.getString(13)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	/**
	 * ����ѧ�Ŵ����ݱ�����������
	 * 
	 * ѧ���Ǿ�׼�ģ����Բ�ѯ��ʱ����ģ����ѯ
	 * 
	 * @param content Ҫ����������
	 * @return
	 */
	public List<Drawing> searchByStudentNumber(String content){
		ResultSet resultSet=null;
		List<Drawing> list=new ArrayList<>();
		String sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where student_num = '%s'",content);
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new Drawing( String.valueOf(resultSet.getInt(1)),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
							,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10)
							,resultSet.getString(11),resultSet.getString(12),resultSet.getString(13)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	/**
	 * �����ֶδ����ݱ�����������
	 * @param field �ֶ�
	 * @param content Ҫ����������
	 * @param accurate �Ƿ�ȷ����
	 * @return
	 */
	public List<Drawing> search(String field,String content,boolean accurate){
		ResultSet resultSet=null;
		List<Drawing> list=new ArrayList<>();
		String sql=null;
		if(accurate){
			sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where %s = '%s'",field,content);
		}else{
			sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where %s like '%%%s%%'",field,content);
		}
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new Drawing( String.valueOf(resultSet.getInt(1)),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
							,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10)
							,resultSet.getString(11),resultSet.getString(12),resultSet.getString(13)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	
	/**
	 * �����ֶδ����ݱ��а�ҳ��������
	 * @param field
	 * @param content
	 * @param page
	 * @param pageSize
	 * @param accurate
	 * @return
	 */
	public List<Drawing> searchLimit(String field,String content,int page,int pageSize,boolean accurate){
		ResultSet resultSet=null;
		List<Drawing> list=new ArrayList<>();
		String sql=null;
		if(accurate){
			sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where %s = '%s' limit %d,%d",field,content,(page-1)*pageSize,pageSize);
		}else{
			sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where %s like '%%%s%%' limit %d,%d",field,content,(page-1)*pageSize,pageSize);
		}		
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new Drawing( String.valueOf(resultSet.getInt(1)),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
							,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10)
							,resultSet.getString(11),resultSet.getString(12),resultSet.getString (13)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	/**
	 * ���ֶβ���ѯ
	 * @param page
	 * @param pageSize
	 * @param data �ɱ����
	 * @return
	 */
	public List<Drawing> searchByMultiField(int page,int pageSize,String... data){
		ResultSet resultSet=null;
		List<Drawing> list=new ArrayList<>();
		if(data==null||data.length%2!=0){
			//����������Ϊż�����׳��쳣
			throw new IllegalArgumentException("��������������");
		}
		StringBuilder  stringBuilder=new StringBuilder();
		for(int i=0;i<data.length;i+=2){
			stringBuilder.append(data[i]+"=?");
			if((i+1)!=data.length-1){
				stringBuilder.append(" and ");
			}
		}
		String sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where %s limit ?,?",stringBuilder.toString());	
		//System.out.println(sql);
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			for(int i=0;i<data.length;i+=2){
				preparedStatement.setString(i/2+1, data[i+1]);
			}
			preparedStatement.setInt(data.length/2+1,(page-1)*pageSize);
			preparedStatement.setInt(data.length/2+2,pageSize);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new Drawing( String.valueOf(resultSet.getInt(1)),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
							,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10)
							,resultSet.getString(11),resultSet.getString(12),resultSet.getString (13)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	/**
	 * ���ֶλ��ѯ(ģ��)
	 * @param page
	 * @param pageSize
	 * @param data �ɱ����
	 * @return
	 */
	public List<Drawing> searchByMultiFieldOr(int page,int pageSize,String... data){
		ResultSet resultSet=null;
		List<Drawing> list=new ArrayList<>();
		if(data==null||data.length%2!=0){
			//����������Ϊż�����׳��쳣
			throw new IllegalArgumentException("��������������");
		}
		StringBuilder  stringBuilder=new StringBuilder();
		stringBuilder.append("(");
		for(int i=0;i<data.length;i+=2){
			stringBuilder.append(data[i]+" like binary ?");
			if((i+1)!=data.length-1){
				stringBuilder.append(" or ");
			}
		}
		stringBuilder.append(")");
		String sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where %s limit ?,?",stringBuilder.toString());	
		System.out.println(sql);
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			for(int i=0;i<data.length;i+=2){
				preparedStatement.setString(i/2+1, "%"+data[i+1]+"%");
			}
			preparedStatement.setInt(data.length/2+1,(page-1)*pageSize);
			preparedStatement.setInt(data.length/2+2,pageSize);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new Drawing( String.valueOf(resultSet.getInt(1)),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
							,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10)
							,resultSet.getString(11),resultSet.getString(12),resultSet.getString (13)));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	/**
	 * ��ȡ������
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<Drawing> getNewItems(int page,int pageSize){
		ResultSet resultSet=null;
		List<Drawing> list=new ArrayList<>();
		String sql=null;
		sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing ORDER BY publish_date DESC limit %d,%d",(page-1)*pageSize,pageSize);	
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//��ѯ
			if(resultSet!=null){
				while(resultSet.next()){
					list.add(new Drawing( String.valueOf(resultSet.getInt(1)),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)
							,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10)
							,resultSet.getString(11),resultSet.getString(12),resultSet.getString (13)));
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
	public int totalItem(String...data){
		ResultSet resultSet=null;
		int count=0;
		if(data==null||data.length%2!=0){
			//����������Ϊż�����׳��쳣
			throw new IllegalArgumentException("��������������");
		}
		StringBuilder  stringBuilder=new StringBuilder();
		for(int i=0;i<data.length;i+=2){
			stringBuilder.append(data[i]+"=?");
			if((i+1)!=data.length-1){
				stringBuilder.append(" and ");
			}
		}
		String sql=String.format("SELECT * FROM drawing where %s",stringBuilder.toString());	
		System.out.println(sql);
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			for(int i=0;i<data.length;i+=2){
				preparedStatement.setString(i/2+1, data[i+1]);
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
	 * ��ȡ���з��ϵ�����
	 * @param field
	 * @param content
	 * @param accurate
	 * @return
	 */
	public int totalItemOr(String...data){
		ResultSet resultSet=null;
		int count=0;
		if(data==null||data.length%2!=0){
			//����������Ϊż�����׳��쳣
			throw new IllegalArgumentException("��������������");
		}
		StringBuilder  stringBuilder=new StringBuilder();
		stringBuilder.append("(");
		for(int i=0;i<data.length;i+=2){
			stringBuilder.append(data[i]+" like binary ?");
			if((i+1)!=data.length-1){
				stringBuilder.append(" or ");
			}
		}
		stringBuilder.append(")");
		String sql=String.format("SELECT * FROM drawing where %s",stringBuilder.toString());	
		System.out.println(sql);
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			for(int i=0;i<data.length;i+=2){
				preparedStatement.setString(i/2+1, "%"+data[i+1]+"%");
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
			sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where %s = '%s'",field,content);
		}else{
			sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where %s like '%%%s%%'",field,content);
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
	 * ��ȡ������������
	 * @param field
	 * @param content
	 * @param accurate
	 * @return
	 */
	public int totalItem(){
		ResultSet resultSet=null;
		String sql=null;
		int count=0;

		sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing");		
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
	 * ������з��ϵ�ҳ��
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
	 * ��������
	 * @return
	 */
	public boolean insert(String studentNumber,String drawingName,String drawingCategory,String drawingSize,String createDate
			,String prizeName,String prizeLevel,String prizeDate,String prizePhoto
			,String drawingPhoto,String drawingDesc,String publishDate){
		String sql="insert into drawing(student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date) values(?,?,?,?,?,?,?,?,?,?,?,?)";  
		int res=0;
		try {
		 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
		 preparedStatement.setString(1, studentNumber);
		 preparedStatement.setString(2, drawingName);
		 preparedStatement.setString(3, drawingCategory);
		 preparedStatement.setString(4, drawingSize);
		 preparedStatement.setString(5, createDate);
		 preparedStatement.setString(6, prizeName);
		 preparedStatement.setString(7, prizeLevel);
		 preparedStatement.setString(8, prizeDate);
		 preparedStatement.setString(9, prizePhoto);
		 preparedStatement.setString(10, drawingPhoto);
		 preparedStatement.setString(11, drawingDesc);
		 preparedStatement.setString(12, publishDate);
			res=preparedStatement.executeUpdate();//����
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("insert drawing:"+res);
		return res==1?true:false;
	}
	
	/**
	 * ������Ʒ��Ƭ
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setDrawingPhoto(String id,String value)
	{
		return update(id, "drawing_photo", value);
	}
	
	/**
	 * ���ý�״��Ƭ
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setPrizePhoto(String id,String value)
	{
		return update(id, "prize_photo", value);
	}
	
	/**
	 * ���û�����
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setPrizeDate(String id,String value)
	{
		return update(id, "prize_date", value);
	}
	/**
	 * ���û񽱵ȼ�
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setPrizeLevel(String id,String value)
	{
		return update(id, "prize_level", value);
	}
	
	/**
	 * ���û�����
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setPrizeName(String id,String value)
	{
		return update(id, "prize_name", value);
	}
	/**
	 * ������Ʒ��������
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setCreateDate(String id,String value)
	{
		return update(id, "create_date", value);
	}
	/**
	 * ������Ʒ�ߴ�
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setDrawingSize(String id,String value)
	{
		return update(id, "drawing_size", value);
	}
	/**
	 * ������Ʒ���
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setDrawingCategory(String id,String value)
	{
		return update(id, "drawing_category", value);
	}
	/**
	 * ������Ʒ����
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setDrawingName(String id,String value)
	{
		return update(id, DrawingName, value);
	}
	/**
	 * ������Ʒ����
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setDrawingDesc(String id,String value)
	{
		return update(id, DrawingDesc, value);
	}
	
	/**
	 * ������Ʒ��������
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setPublishDate(String id,String value)
	{
		return update(id, DrawingDesc, value);
	}
	/**
	 * ��������
	 * @return 1���ɹ�
	 */
	public boolean update(String id,String field,String value){
		String sql=String.format("update drawing set %s=? where id=?",field);  
		int res=0;
		try {
			 PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			 
			 //preparedStatement.setString(1, field);
			 preparedStatement.setString(1, value);
			 preparedStatement.setString(2, id);
			res=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("update drawing:"+res);
		return res==1?true:false;
	}
	
	/**
	 * ɾ������
	 * @return 1���ɹ�
	 */
	public boolean deleteItem(String id){
		String sql=String.format("DELETE FROM drawing WHERE id=?");  
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
		System.out.println("delete drawing:"+res);
		return res==1?true:false;
	}
}
