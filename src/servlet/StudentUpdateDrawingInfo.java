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

import bean.Drawing;
import bean.DrawingReview;
import dao.DrawingDao;
import dao.DrawingReviewDao;

/**
 * StudentUpdateDrawingInfo:学生请求更新作品信息，管理员通过审核后才可以生效
 */
//@WebServlet("/StudentUpdateDrawingInfo")
public class StudentUpdateDrawingInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFF_SIZE = 1*1024*1024;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentUpdateDrawingInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

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
				FileItem formItem=iterator.next();
				if(formItem.isFormField())
				{

					fields[idx++]=formItem.getString("utf-8");//注意编码
					System.out.println("field["+(idx-1)+"]:"+formItem.getFieldName()+"---->"+formItem.getString("UTF-8"));
				}
				else{ 
					if(formItem.getSize()==0)
					{
						fields[idx++]=null;
						continue;
					}
					String fileName=UUID.randomUUID().toString();
					//System.out.println(fileItem.getFieldName()+"---->"+fileItem.getName());

					InputStream inputStream=formItem.getInputStream();
					System.out.println("string :"+formItem.getSize());
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
		DrawingReviewDao drawingReviewDao=new DrawingReviewDao();
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
		String drawingSize=null;
		if(drawingPhoto!=null){
			BufferedImage image=ImageIO.read(new File(rootPath+File.separator+drawingPhoto));
			drawingSize=image.getWidth()+"x"+image.getHeight();
		}
		if(drawingId==null||drawingId.equals("")){
			request.setAttribute("message", "请求修改作品失败");
			request.setAttribute("page", "StudentCenter.jsp");
			request.setAttribute("type", "danger");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		}
		//1.将drawing表的数据复制到drawing_review表
		//2.根据字段修改drawing_review表
		Drawing drawingTemp=drawingDao.searchById(drawingId);
		drawingReviewDao.insert(drawingId
				, drawingTemp.getStudentNumber()
				, (drawingName==null||drawingName.equals(""))?drawingTemp.getDrawingName():drawingName
				, (drawingCategory==null||drawingCategory.equals(""))?drawingTemp.getDrawingCategory():drawingCategory
				, (drawingSize==null||drawingSize.equals(""))?drawingTemp.getDrawingSize():drawingSize
				, (createDate==null||createDate.equals(""))?drawingTemp.getCreateDate():createDate
				, (prizeName==null||prizeName.equals(""))?drawingTemp.getPrizeName():prizeName
				, (prizeLevel==null||prizeLevel.equals(""))?drawingTemp.getPrizeLevel():prizeLevel
				, (prizeDate==null||prizeDate.equals(""))?drawingTemp.getPrizeDate():prizeDate
				, (prizePhoto==null||prizePhoto.equals(""))?drawingTemp.getPrizePhoto():prizePhoto
				, (drawingPhoto==null||drawingPhoto.equals(""))?drawingTemp.getDrawingPhoto():drawingPhoto
				, (drawingDesc==null||drawingDesc.equals(""))?drawingTemp.getDrawingDesc():drawingDesc
				, (publishDate==null||publishDate.equals(""))?drawingTemp.getPrizeDate():publishDate
				, 1);
		
//		String reviewId=drawingReviewDao.searchById(String.valueOf(drawingReviewDao.getLastId())).getId();
//		if(drawingId==null){
//			request.setAttribute("message", "请求修改作品失败");
//			request.setAttribute("page", "AdminCenter.jsp");
//			request.setAttribute("type", "danger");
//			request.setAttribute("time", "3");
//			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
//		}
//		else{
//			if(drawingId!=null&&!drawingId.equals("")){
//				drawingReviewDao.update(reviewId,DrawingReviewDao.DrawingId, drawingId);
//			}
//			if(drawingName!=null&&!drawingName.equals("")){
//				drawingReviewDao.update(reviewId,DrawingReviewDao.DrawingName, drawingName);
//			}
//			
//			if(drawingCategory!=null&&!drawingCategory.equals("")){
//				drawingReviewDao.update(reviewId,DrawingReviewDao.DrawingCategory, drawingCategory);
//			}
//			
//			if(createDate!=null&&!createDate.equals("")){
//				drawingReviewDao.update(reviewId,DrawingReviewDao.CreateDate, createDate);
//			}
//			
//			if(prizeName!=null&&!prizeName.equals("")){
//				drawingReviewDao.update(reviewId,DrawingReviewDao.PrizeName, prizeName);
//			}
//			if(prizeLevel!=null&&!prizeLevel.equals("")){
//				drawingReviewDao.update(reviewId,DrawingReviewDao.PrizeLevel, prizeLevel);
//			}
//			
//			if(prizeDate!=null&&!prizeDate.equals("")){
//				drawingReviewDao.update(reviewId,DrawingReviewDao.PrizeDate, prizeDate);
//			}
//			
//			if(prizePhoto!=null&&!prizePhoto.equals("")){
//				drawingReviewDao.update(reviewId,DrawingReviewDao.PrizePhoto, prizePhoto);
//			}
//			
//			if(drawingDesc!=null&&!drawingDesc.equals("")){
//				drawingReviewDao.update(reviewId,DrawingReviewDao.DrawingDesc, drawingDesc);
//			}
//			
//			if(drawingPhoto!=null&&!drawingPhoto.equals("")){
//				image=ImageIO.read(new File(rootPath+File.separator+drawingPhoto));
//				drawingSize=image.getWidth()+"x"+image.getHeight();
//				drawingReviewDao.update(reviewId,DrawingReviewDao.DrawingPhoto, drawingPhoto);
//				drawingReviewDao.update(reviewId,DrawingReviewDao.DrawingSize, drawingSize);
//			}
			
			request.setAttribute("message", "请求修改成功，管理员通过后，才可以生效");
			request.setAttribute("page", "StudentCenter.jsp");
			request.setAttribute("type", "success");
			request.setAttribute("time", "3");
			request.getRequestDispatcher("/AutoJumpMsgPage.jsp").forward(request, response);
		//}
	}

}
