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

/**
 * ע���߼���register.jsp�ļ�post������
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
		//����Ŀ¼
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
				//���ļ��ֶ�
				if(fileItem.isFormField())
				{
					//System.out.println(fileItem.getFieldName()+"---->"+fileItem.getString());
					fields[idx++]=fileItem.getString("utf-8");//ע�����
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

		//�ж��ֶ��Ƿ�Ϊ��
		for(int i=0;i<fields.length;i++)
		{
			if(fields[i]==null||fields[i].equals(""))
			{
				request.setAttribute("message", "��д�����е���Ϣ��ſ���ע��Ŷ~");
				request.setAttribute("page", "register.jsp");
				request.setAttribute("type", "warning");
				request.setAttribute("time", "3");
				request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
				empty=true;
				break;
			}
		}
		boolean registered=studentDao.exists(studentNumber);//�Ƿ�ע�����ʶ
		if(registered&&!empty)
		{
			request.setAttribute("message", "��ѧ���Ѿ�ע��~");
			request.setAttribute("page", "register.jsp");
			request.setAttribute("type", "warning");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		if(!empty&&!registered){
			//����Ϊ�ղ���û��ע����ŻῪʼע��
			boolean isMan=false;
			if(studentGender.equals("��")){
				isMan=true;
			}
			else if(studentGender.equals("Ů"))
			{
				isMan=false;
			}
			else{
				isMan=true;
			}
			boolean isSuccess=studentDao.register(studentNumber, password, studentName, isMan, studentGrade, studentMajor, studentBirth, studentPhoto);
			if(isSuccess){
				request.getSession().setAttribute("user", studentNumber);
				request.setAttribute("message", "ע��ɹ���3���������¼����");
				request.setAttribute("type", "success");
				request.setAttribute("page", "login.jsp");
				request.setAttribute("time", "3");
				request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("message", "ע��ʧ��");
				request.setAttribute("type", "danger");
				request.setAttribute("page", "register.jsp");
				request.setAttribute("time", "3");
				request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
			}
		}//empty
	}

}
