package ok;

import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

import io.woo.htmltopdf.HtmlToPdf;
import io.woo.htmltopdf.HtmlToPdfObject;

public class savePDF2 {
	public void createPdf(String str) throws IOException {
//		System.out.println(str.split("&")[0].split("</head>")[1]);
		String str2 = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"></head>"+str.split("&")[0].split("</head>")[1];
//		System.out.println(str2);
		//将html文件再转换为pdf
	    boolean success = HtmlToPdf.create()
	            .object(HtmlToPdfObject.forHtml(str2.toString()))
	            .convert("file.pdf");
	}
}
