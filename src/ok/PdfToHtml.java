package ok;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;

public class PdfToHtml{
	public void PdfToHtml(File input,Writer out) throws IOException, ParserConfigurationException {
        PDDocument pdf = PDDocument.load(input);
        PDFDomTree tree = new PDFDomTree();
        tree.writeText(pdf,out);
	}
	public static void main(String[] args) {
//		PdfToHtml("");
	}
}