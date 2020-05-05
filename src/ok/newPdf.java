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
import ok.savePDF2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import io.woo.htmltopdf.HtmlToPdf;
import io.woo.htmltopdf.HtmlToPdfObject;

import ok.CustomHTMLEditor;

public class newPdf {
	
	private final Stage stage = new Stage();
	
	public newPdf(String str) throws IOException{
//		HTMLEditor editor = new HTMLEditor();
		CustomHTMLEditor editor = new CustomHTMLEditor();
		BorderPane root = new BorderPane();
		root.setCenter(editor);
		Scene scane = new Scene(root, 700, 700);
//		scane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		//新建一个pdf文件，edit的内容为空
		if(str.length() == 0) {
			stage.setTitle("新建pdf文件");
			stage.setScene(scane);
			stage.setMaximized(true);//全屏显示
			stage.show();
			Button btn = new Button("保存");
			root.setTop(btn);
			btn.setOnAction(new EventHandler<ActionEvent>() {
		
				@Override
				public void handle(ActionEvent event) {
		//				System.out.println(editor.getHtmlText());
					
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("保存文件");
					fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.pdf"));
					File selectedFile = fileChooser.showSaveDialog(stage);
					if(selectedFile == null) return; // 用户没有选中文件, 已经取消操作
					
					try {
						//将路径改为String类型
						String strFile = selectedFile.toString();
						//edit的html
						String strHtml = editor.getHtmlText();

						File file = new File("C:\\Users\\钟倩文\\Desktop\\pdf测试文件\\my.html");
			            PrintStream ps = new PrintStream(new FileOutputStream(file));
			            ps.println(strHtml);// 往文件里写入字符串
//						String str = strFile + "&" + strHtml;
//						System.out.println(editor.getHtmlText());
						
						
//						savePDF createPDFTest1 = new savePDF();
						savePDF2 createPDFTest1 = new savePDF2();
						createPDFTest1.createPdf(strHtml+"&"+strFile);
					}catch(Exception e)
					{
						e.printStackTrace();
					}
								
				}
			});

	}else {
		//需要显示已有的html文件
		stage.setTitle("编辑pdf文件");
		stage.setScene(scane);
		stage.setMaximized(true);//全屏显示
		stage.show();
		ButtonBar buttonBar = new ButtonBar();
		Button btnUp = new Button("上传文件");
		Button btnSave = new Button("保存");
		buttonBar.getButtons().addAll(btnUp, btnSave);
		root.setTop(buttonBar);
		
		//将html文件读出
		InputStream is;
		is = new FileInputStream("C:\\Users\\钟倩文\\Desktop\\pdf测试文件\\my.html");
		byte[] b = new byte[1234456];
    	int size = is.read(b);
//    	System.out.println("oko?ko_____________"+new String(b,0,size));
		
    	String htmlString = new String(b,0,size);
//    	System.out.println(htmlString);
    	editor.setHtmlText(htmlString);
    	//点击
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
	
			@Override
			public void handle(ActionEvent event) {
				//edit的html
				String strHtml = editor.getHtmlText();

				File file = new File("C:\\Users\\钟倩文\\Desktop\\pdf测试文件\\my.html");
	            PrintStream ps = null;
				try {
					ps = new PrintStream(new FileOutputStream(file));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            ps.println(strHtml);// 往文件里写入字符串
	            
	            //将html文件再转换为pdf
	            boolean success = HtmlToPdf.create()
	                    .object(HtmlToPdfObject.forHtml(strHtml.toString()))
	                    .convert("file.pdf");
	            
			}
		
		});
	}
	}
}
