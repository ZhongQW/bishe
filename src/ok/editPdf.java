package ok;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

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

import ok.newPdf2;

import ok.Pdf2htmlEXUtil;

public class editPdf {

	public editPdf(String filePath) throws IOException {
		Pdf2htmlEXUtil pdf2htmlEXUtil = new Pdf2htmlEXUtil();
//		System.out.println(filePath);
		String str = filePath.replaceAll( "\\\\", "\\\\\\\\");
        pdf2htmlEXUtil.pdf2html("D:\\毕业设计\\pdfBox\\pdf2htmlEX-win32-0.13.6\\pdf2htmlEX.exe",str,"C:\\Users\\钟倩文\\Desktop\\pdf测试文件","my.html");
        
        
        //将生成的html读出并传给newPdf
        InputStream is;
		is = new FileInputStream("C:\\Users\\钟倩文\\Desktop\\pdf测试文件\\my.html");
		byte[] b = new byte[1234456];
    	int size = is.read(b);
		String str2 = new String(b, 0 , size);//html 标签内容
		
		newPdf2 newPdf = new newPdf2(str2);

    }
	
}
