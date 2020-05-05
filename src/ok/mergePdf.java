package ok;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import ok.savePDF;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;;

public class mergePdf {
	
	private final Stage stage = new Stage();
	private File file1;
	private File file2;
	
	public mergePdf(){
		BorderPane root = new BorderPane();
		
		Scene scane = new Scene(root, 700, 700);
//		scane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setTitle("合并pdf文件");
		stage.setScene(scane);
		stage.setMaximized(true);//全屏显示
		stage.show();
		ButtonBar buttonBar = new ButtonBar();
		Button btnLoad1 = new Button("上传1");
		Button btnLoad2 = new Button("上传2");
		Button btn = new Button("ok");
		buttonBar.getButtons().addAll(btnLoad1, btnLoad2, btn);
		root.setTop(buttonBar);
		
		MyEventHandler1 hnHandler1 = new MyEventHandler1();
		MyEventHandler2 hnHandler2 = new MyEventHandler2();
		MyEventHandlerOk hnHandler3 = new MyEventHandlerOk();
		btnLoad1.setOnAction(hnHandler1);
		btnLoad2.setOnAction(hnHandler2);
		btn.setOnAction(hnHandler3);
		
	}
	//接口泛型，处理按钮的点击事件
	private class MyEventHandler1 implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            file1 = fileChooser.showOpenDialog(stage);
//            System.out.println(file1);
		}
	}
	
	//接口泛型，处理按钮的点击事件
	private class MyEventHandler2 implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            file2 = fileChooser.showOpenDialog(stage);
		}
	}
	private class MyEventHandlerOk implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			//("C:/Users/钟倩文/Desktop/pdf测试文件/merged.pdf");
			PDDocument doc1 = null;
			try {
				doc1 = PDDocument.load(file1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PDDocument doc2 = null;
			try {
				doc2 = PDDocument.load(file2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PDFMergerUtility PDFmerger = new PDFMergerUtility();
			PDFmerger.setDestinationFileName("C:/Users/钟倩文/Desktop/pdf测试文件/merged.pdf");
			try {
				PDFmerger.addSource(file1);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				PDFmerger.addSource(file2);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				PDFmerger.mergeDocuments();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				doc1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				doc2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
