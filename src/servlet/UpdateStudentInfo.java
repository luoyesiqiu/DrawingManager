package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.jdt.internal.compiler.env.IGenericField;

import dao.StudentDao;
import util.MD5;

/**
 * Servlet implementation class UpdateStudentInfo
 */
//@WebServlet("/UpdateStudentInfo")
public class UpdateStudentInfo extends HttpServlet {
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
					if(fileItem.getSize()==0)
					{
						fields[idx++]=null;
						continue;
					}
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
		
		String studentNumber=fields[0];
		String studentPhoto=fields[1];
		String studentName=fields[2];
		String studentOldPassword=fields[3];
		String studentNewPassword=fields[4];
		String studentGender=fields[5];
		String studentGrade=fields[6];
		String studentMajor=fields[7];
		String studentBirth=fields[8];
		String role=fields[9];//�û���ɫ��admin.����Ա��studnet.ѧ��
		if(!(role.equals("admin")||role.equals("student"))){
			request.setAttribute("message", "��ɫ����ȷ");
			request.setAttribute("page", "index.jsp");
			request.setAttribute("type", "danger");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
			return;
		}
		int i=0;
		for(String f:fields)
		{
			System.out.println((i++)+":"+f);
		}
		
		StudentDao studentDao=new StudentDao();
		
		if(studentOldPassword==null||studentOldPassword.equals("")||!studentDao.login(studentNumber, MD5.encrypt(studentOldPassword))){
			request.setAttribute("message", "ԭ���������޸��û���Ϣʧ��");
			if(role.equals("student")){
				request.setAttribute("page", "StudentCenter.jsp");
			}
			else if(role.equals("admin")){
				request.setAttribute("page", "AdminCenter.jsp");
			}
			request.setAttribute("type", "danger");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
			return;
		}
		if(studentName!=null&&!studentBirth.equals("")){
			studentDao.update(studentNumber, StudentDao.StudentName, studentName);
		}
		if(studentNewPassword!=null&&!studentNewPassword.equals("")){
			studentDao.update(studentNumber, StudentDao.StudentPassword, MD5.encrypt(studentNewPassword));
		}
		if(studentGender!=null&&!studentGender.equals("")){
			int gender=studentGender.equals("��")?0:1;
			studentDao.update(studentNumber, StudentDao.StudentGender, gender);
		}
		
		if(studentGrade!=null&&!studentGrade.equals("")){
			studentDao.update(studentNumber, StudentDao.StudentGrade, studentGrade);
		}
		
		if(studentMajor!=null&&!studentMajor.equals("")){
			studentDao.update(studentNumber, StudentDao.StudentMajor, studentMajor);
		}
		
		if(studentBirth!=null&&!studentBirth.equals("")){
			studentDao.update(studentNumber, StudentDao.StudentBirth, studentBirth);
		}
		if(studentPhoto!=null&&!studentPhoto.equals("")){
			//��ɾ������Ƭ
			File file=new File(rootPath+File.separator+studentDao.getStudent(studentNumber).getStudentPhoto());
			if(file.delete()){
				System.out.println("ɾ������Ƭ�ɹ�");
			}
			studentDao.update(studentNumber, StudentDao.StudentPhoto, studentPhoto);
			
		}
		request.setAttribute("message", "�޸��û���Ϣ�ɹ�");
		if(role.equals("student")){
			request.setAttribute("page", "StudentCenter.jsp");
		}
		else if(role.equals("admin")){
			request.setAttribute("page", "AdminCenter.jsp");
		}
		request.setAttribute("type", "success");
		request.setAttribute("time", "3");
		request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
	}

}
