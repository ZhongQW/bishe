package ok;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class savePDF {

	public void createPdf(String str) {
		try {
			//BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			//直接引用 下载好的字体文件
			BaseFont bfChinese = BaseFont.createFont("D:/毕业设计/pdfBox/test/src/resources/simhei.ttf", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
			
			Font font = new Font(bfChinese, 12, Font.NORMAL);
			Document document = new Document(PageSize.A4, 10, 10, 10, 10);
			PdfWriter mPdfWriter = PdfWriter.getInstance(document, new FileOutputStream(str));
			document.open();
//			System.out.println(str.split("&")[1]);
//			document.add(new Paragraph("创建pdf文件.支持中文......", font));
			
//			StringBuffer html = new StringBuffer();
//			html.append(str.split("&")[1]);			
			//将html文件读出
			InputStream is;
			is = new FileInputStream("C:\\Users\\钟倩文\\Desktop\\pdf测试文件\\my.html");
			byte[] b = new byte[1234456];
	    	int size = is.read(b);
//	    	System.out.println(new String(b, 0 , size));
			String s = new String(b, 0 , size);//html 标签内容
	    	
			ByteArrayInputStream bin = new ByteArrayInputStream(s.getBytes());
			XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter, document, bin, Charset.forName("UTF-8"), new ChinaFontProvide());
			
			//将图片合成pdf
//			Image image = Image.getInstance("D:/毕业设计/pdfBox/test/src/html/logo.jpg");
			
//			image.scalePercent(10);// 控制图片大小
//			image.setAbsolutePosition(200, 500);
//			document.add(image);
			
			//base64 编码的图片类型
//			Image image2 = Image.getInstance(Base64.decode(TESTEVSCHOP));
			
//			image2.scalePercent(10);// 控制图片大小
//			image2.setAbsolutePosition(400, 500);
//			document.add(image2);
			
			
			
			document.close();
			mPdfWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
 
	/**
	 * 
	 * 提供中文
	 * 
	 */
	public static final class ChinaFontProvide implements FontProvider {
 
		@Override
		public Font getFont(String arg0, String arg1, boolean arg2, float arg3,
				int arg4, BaseColor arg5) {
			BaseFont bfChinese = null;
			try {
				//bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
				//直接引用 下载好的字体文件
				 bfChinese = BaseFont.createFont("D:/毕业设计/pdfBox/test/src/resources/simhei.ttf", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
			return FontChinese;
		}
 
		@Override
		public boolean isRegistered(String arg0) {
			return false;
		}
	}
}
