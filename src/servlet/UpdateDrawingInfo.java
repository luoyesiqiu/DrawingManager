package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import dao.DrawingDao;

/**
 * UpdateDrawingInfo：管理员可以直接更改作品信息
 */
//@WebServlet("/UpdateDrawingInfo")
public class UpdateDrawingInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFF_SIZE = 1*1024*1024;//1Mb
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
					if(fileItem.getSize()==0)
					{
						fields[idx++]=null;
						continue;
					}
					String fileName=UUID.randomUUID().toString();
					//System.out.println(fileItem.getFieldName()+"---->"+fileItem.getName());

					InputStream inputStream=fileItem.getInputStream();
					System.out.println("string :"+fileItem.getSize());
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
		String drawingId=fields[0];
		String drawingName=fields[1];
		String drawingCategory=fields[2];

		String createDate=fields[3];
		String prizeName=fields[4];
		String prizeLevel=fields[5];
		String prizeDate=fields[6];
		String prizePhoto=//rootPath+File.separator+
				fields[7];
		String drawingPhoto=//rootPath+File.separator+
				fields[8];
		String drawingDesc=fields[9];
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String publishDate=simpleDateFormat.format(new Date());
		

		
		if(drawingId==null){
			request.setAttribute("message", "修改作品失败");
			request.setAttribute("page", "AdminCenter.jsp");
			request.setAttribute("type", "danger");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		else{
			if(drawingName!=null&&!drawingName.equals("")){
				drawingDao.setDrawingName(drawingId, drawingName);
			}
			
			if(drawingCategory!=null&&!drawingCategory.equals("")){
				drawingDao.setDrawingCategory(drawingId, drawingCategory);
			}
			
			if(createDate!=null&&!createDate.equals("")){
				drawingDao.setCreateDate(drawingId, createDate);
			}
			
			if(prizeName!=null&&!prizeName.equals("")){
				drawingDao.setPrizeName(drawingId, prizeName);
			}
			if(prizeLevel!=null&&!prizeLevel.equals("")){
				drawingDao.setPrizeLevel(drawingId, prizeLevel);
			}
			
			if(prizeDate!=null&&!prizeDate.equals("")){
				drawingDao.setPrizeDate(drawingId, prizeDate);
			}
			
			if(prizePhoto!=null&&!prizePhoto.equals("")){
				drawingDao.setPrizePhoto(drawingId, prizePhoto);
			}
			
			if(drawingDesc!=null&&!drawingDesc.equals("")){
				drawingDao.setDrawingDesc(drawingId, drawingDesc);
			}
			
			if(drawingPhoto!=null&&!drawingPhoto.equals("")){
				BufferedImage image=ImageIO.read(new File(rootPath+File.separator+drawingPhoto));
				String drawingSize=image.getWidth()+"x"+image.getHeight();
				drawingDao.setDrawingPhoto(drawingId, drawingPhoto);
				drawingDao.setDrawingSize(drawingId, drawingSize);
			}
			
			request.setAttribute("message", "修改成功");
			request.setAttribute("page", "AdminCenter.jsp");
			request.setAttribute("type", "success");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		
	}

}
