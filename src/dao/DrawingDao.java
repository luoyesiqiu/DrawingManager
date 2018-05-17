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
 * drawing表操作类
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
	 * 创建表
	 * @return 0：成功
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
	 * 根据作品id从数据表中搜索内容
	 * 
	 * @param content 要搜索的内容
	 * @return
	 */
	public Drawing searchById(String id){
		ResultSet resultSet=null;
		String sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where id = '%s'",id);
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//查询
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
	 * 根据学号从数据表中搜索内容
	 * 
	 * 学号是精准的，所以查询的时候不是模糊查询
	 * 
	 * @param content 要搜索的内容
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
			resultSet=preparedStatement.executeQuery();//查询
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
	 * 根据学号从数据表中搜索内容
	 * 
	 * 学号是精准的，所以查询的时候不是模糊查询
	 * 
	 * @param content 要搜索的内容
	 * @return
	 */
	public List<Drawing> searchByStudentNumber(String content){
		ResultSet resultSet=null;
		List<Drawing> list=new ArrayList<>();
		String sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing where student_num = '%s'",content);
		try {
			PreparedStatement preparedStatement=getConnection().prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();//查询
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
	 * 根据字段从数据表中搜索内容
	 * @param field 字段
	 * @param content 要搜索的内容
	 * @param accurate 是否精确查找
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
			resultSet=preparedStatement.executeQuery();//查询
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
	 * 根据字段从数据表中按页搜索内容
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
			resultSet=preparedStatement.executeQuery();//查询
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
	 * 多字段并查询
	 * @param page
	 * @param pageSize
	 * @param data 可变参数
	 * @return
	 */
	public List<Drawing> searchByMultiField(int page,int pageSize,String... data){
		ResultSet resultSet=null;
		List<Drawing> list=new ArrayList<>();
		if(data==null||data.length%2!=0){
			//参数个数不为偶数，抛出异常
			throw new IllegalArgumentException("参数个数不合理");
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
			resultSet=preparedStatement.executeQuery();//查询
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
	 * 多字段或查询(模糊)
	 * @param page
	 * @param pageSize
	 * @param data 可变参数
	 * @return
	 */
	public List<Drawing> searchByMultiFieldOr(int page,int pageSize,String... data){
		ResultSet resultSet=null;
		List<Drawing> list=new ArrayList<>();
		if(data==null||data.length%2!=0){
			//参数个数不为偶数，抛出异常
			throw new IllegalArgumentException("参数个数不合理");
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
			resultSet=preparedStatement.executeQuery();//查询
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
	 * 获取新内容
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
			resultSet=preparedStatement.executeQuery();//查询
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
	 * 获取表中符合的条数
	 * @param field
	 * @param content
	 * @param accurate
	 * @return
	 */
	public int totalItem(String...data){
		ResultSet resultSet=null;
		int count=0;
		if(data==null||data.length%2!=0){
			//参数个数不为偶数，抛出异常
			throw new IllegalArgumentException("参数个数不合理");
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
	 * 获取表中符合的条数
	 * @param field
	 * @param content
	 * @param accurate
	 * @return
	 */
	public int totalItemOr(String...data){
		ResultSet resultSet=null;
		int count=0;
		if(data==null||data.length%2!=0){
			//参数个数不为偶数，抛出异常
			throw new IllegalArgumentException("参数个数不合理");
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
	 * 获取表中符合的条数
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

		sql=String.format("SELECT id,student_num,drawing_name,drawing_category,drawing_size,create_date,prize_name,prize_level,prize_date,prize_photo,drawing_photo,drawing_desc,publish_date FROM drawing");		
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
	 * 查询表中符合的页数
	 * @param field 字段
	 * @param content 内容
	 * @param pageSize 页大小
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
	/**
	 * 插入数据
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
			res=preparedStatement.executeUpdate();//更新
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("insert drawing:"+res);
		return res==1?true:false;
	}
	
	/**
	 * 设置作品照片
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setDrawingPhoto(String id,String value)
	{
		return update(id, "drawing_photo", value);
	}
	
	/**
	 * 设置奖状照片
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setPrizePhoto(String id,String value)
	{
		return update(id, "prize_photo", value);
	}
	
	/**
	 * 设置获奖日期
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setPrizeDate(String id,String value)
	{
		return update(id, "prize_date", value);
	}
	/**
	 * 设置获奖等级
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setPrizeLevel(String id,String value)
	{
		return update(id, "prize_level", value);
	}
	
	/**
	 * 设置获奖名称
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setPrizeName(String id,String value)
	{
		return update(id, "prize_name", value);
	}
	/**
	 * 设置作品创建日期
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setCreateDate(String id,String value)
	{
		return update(id, "create_date", value);
	}
	/**
	 * 设置作品尺寸
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setDrawingSize(String id,String value)
	{
		return update(id, "drawing_size", value);
	}
	/**
	 * 设置作品类别
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setDrawingCategory(String id,String value)
	{
		return update(id, "drawing_category", value);
	}
	/**
	 * 设置作品名称
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setDrawingName(String id,String value)
	{
		return update(id, DrawingName, value);
	}
	/**
	 * 设置作品描述
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setDrawingDesc(String id,String value)
	{
		return update(id, DrawingDesc, value);
	}
	
	/**
	 * 设置作品发布日期
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean setPublishDate(String id,String value)
	{
		return update(id, DrawingDesc, value);
	}
	/**
	 * 更新数据
	 * @return 1：成功
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
	 * 删除数据
	 * @return 1：成功
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
