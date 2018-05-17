package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.jasper.tagplugins.jstl.core.Out;

import dao.DrawingDao;
import dao.DrawingReviewDao;

/**
 * Servlet implementation class PublishDrawing
 */
//@WebServlet("/PublishDrawing")
public class PublishDrawing extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFF_SIZE = 1*1024*1024;//1Mb
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//工程目录
		String rootPath=request.getServletContext().getRealPath("/drawing");
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
				if(fileItem.isFormField())
				{

					fields[idx++]=fileItem.getString("utf-8");//注意编码
					System.out.println("field["+(idx-1)+"]:"+fileItem.getFieldName()+"---->"+fileItem.getString("UTF-8"));
				}
				else{ 
					String fileName=UUID.randomUUID().toString()+fileItem.getName().substring(fileItem.getName().lastIndexOf("."),fileItem.getName().length());
					//System.out.println(fileItem.getFieldName()+"---->"+fileItem.getName());
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
		DrawingDao drawingDao=new DrawingDao();
		String studentNumber=fields[0];
		String drawingName=fields[1];
		String drawingCategory=fields[2];

		String createDate=fields[3];
		String prizeLevel=fields[4];
		String prizeName="";
		String prizeDate="";
		String prizePhoto="";
		String drawingPhoto=null;
		String drawingDesc=null;
		if(prizeLevel.equals("未获奖")){
			drawingPhoto=fields[5];
			drawingDesc=fields[6];
		}
		else{
			prizeName=fields[5];
			prizeDate=fields[6];
			prizePhoto=fields[7];
			
			drawingPhoto=fields[8];
			drawingDesc=fields[9];
		}
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String publishDate=simpleDateFormat.format(new Date());
		
		BufferedImage image=ImageIO.read(new File(rootPath+File.separator+drawingPhoto));
		String drawingSize=image.getWidth()+"x"+image.getHeight();
		
//		out.println(String.format("studentNumber:%s,drawingName:%s,drawingCategory:%s,drawingSize:%s,createDate:%s,prizeName:%s,prizeLevel:%s,prizeDate:%s,prizePhoto:%s,drawingPhoto:%s\n",studentNumber, drawingName, drawingCategory, drawingSize,
//				createDate, prizeName, prizeLevel, prizeDate, prizePhoto, drawingPhoto));
//		if(drawingDao.insert(studentNumber, drawingName, drawingCategory, drawingSize,
//				createDate, prizeName, prizeLevel, prizeDate, prizePhoto, drawingPhoto,drawingDesc,publishDate)){
//			request.setAttribute("message", "发布成功");
//			request.setAttribute("page", "StudentCenter.jsp");
//			request.setAttribute("type", "success");
//			request.setAttribute("time", "3");
//			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
//		}
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
		if(drawingReviewDao.insert("",studentNumber, drawingName, drawingCategory, drawingSize,
				createDate, prizeName, prizeLevel, prizeDate, prizePhoto, drawingPhoto,drawingDesc,publishDate,0)){
			request.setAttribute("message", "提交成功，管理员审核通过后才会发布");
			request.setAttribute("page", "StudentCenter.jsp");
			request.setAttribute("type", "success");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		else{
			request.setAttribute("message", "提交失败");
			request.setAttribute("page", "StudentCenter.jsp");
			request.setAttribute("type", "danger");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		
	}

}
