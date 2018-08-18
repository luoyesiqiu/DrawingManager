package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import dao.StudentDao;
import util.JavascriptUtils;
import util.MD5;

/**
 * 注册逻辑，register.jsp文件post到此类
 */
//@WebServlet(name = "StudentRegister", urlPatterns = { "/student-register" })
public class StudentRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFF_SIZE=1*1024*1024;//1Mb
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//工程目录
		String rootPath=request.getServletContext().getRealPath("/photos");
		if(!new File(rootPath).exists()){
			new File(rootPath).mkdirs();
		}
		DiskFileItemFactory diskFileItemFactory=new DiskFileItemFactory();
		diskFileItemFactory.setSizeThreshold(BUFF_SIZE);
		diskFileItemFactory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload fileUpload=new ServletFileUpload(diskFileItemFactory);
		String[] fields=null;
		try {
			List<FileItem> fileItemList= fileUpload.parseRequest(request);
		    fields=new String[fileItemList.size()];
			
			Iterator<FileItem> iterator=fileItemList.iterator();
			int idx=0;
			while(iterator.hasNext())
			{
				FileItem fileItem=iterator.next();
				//非文件字段
				if(fileItem.isFormField())
				{
					//System.out.println(fileItem.getFieldName()+"---->"+fileItem.getString());
					fields[idx++]=fileItem.getString("utf-8");//注意编码
				}
				else{
					String fileName=UUID.randomUUID().toString()+fileItem.getName().substring(fileItem.getName().lastIndexOf("."),fileItem.getName().length());
					System.out.println("path :"+rootPath);
					InputStream inputStream=fileItem.getInputStream();
					FileOutputStream fileOutputStream=new FileOutputStream(rootPath+File.separator+fileName);
					byte[] buf=new byte[1024];
					int len=0;
					while((len=inputStream.read(buf))!=-1){
						fileOutputStream.write(buf, 0, len);
						fileOutputStream.flush();
					}
					fileOutputStream.close();
					fields[idx++]=fileName;
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out=response.getWriter();

		StudentDao studentDao=new StudentDao();
		String studentNumber=fields[0];
		String password=fields[1];
		String studentName=fields[2];
		String studentGender=fields[3];
		String studentGrade=fields[4];
		String studentMajor=fields[5];
		String studentBirth=fields[6];
		String studentPhoto=fields[7];
//		String[] fields={studentNumber,password,studentName,studentGender,studentGrade,studentMajor,studentBirth,studentPhoto};
		boolean empty=false;

		//判断字段是否为空
		for(int i=0;i<fields.length;i++)
		{
			if(fields[i]==null||fields[i].equals(""))
			{
				request.setAttribute("message", "填写完所有的信息后才可以注册哦~");
				request.setAttribute("page", "register.jsp");
				request.setAttribute("type", "warning");
				request.setAttribute("time", "3");
				request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
				empty=true;
				break;
			}
		}
		boolean registered=studentDao.exists(studentNumber);//是否注册过标识
		if(registered&&!empty)
		{
			request.setAttribute("message", "该学号已经注册~");
			request.setAttribute("page", "register.jsp");
			request.setAttribute("type", "warning");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		if(!empty&&!registered){
			//都不为空并且没有注册过才会开始注册
			boolean isMan=false;
			if(studentGender.equals("男")){
				isMan=true;
			}
			else if(studentGender.equals("女"))
			{
				isMan=false;
			}
			else{
				isMan=true;
			}
			boolean isSuccess=studentDao.register(studentNumber, MD5.encrypt(password), studentName, isMan, studentGrade, studentMajor, studentBirth, studentPhoto);
			if(isSuccess){
				request.getSession().setAttribute("user", studentNumber);
				request.setAttribute("message", "注册成功，3秒后跳到登录界面");
				request.setAttribute("type", "success");
				request.setAttribute("page", "login.jsp");
				request.setAttribute("time", "3");
				request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("message", "注册失败");
				request.setAttribute("type", "danger");
				request.setAttribute("page", "register.jsp");
				request.setAttribute("time", "3");
				request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
			}
		}//empty
	}

}
