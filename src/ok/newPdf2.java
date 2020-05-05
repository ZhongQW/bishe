package ok;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

import ok.CustomHTMLEditor2;

public class newPdf2 {
	
	private final Stage stage = new Stage();
	
	public newPdf2(String str) throws IOException{
//		HTMLEditor editor = new HTMLEditor();
		CustomHTMLEditor2 editor = new CustomHTMLEditor2();
		BorderPane root = new BorderPane();
		root.setCenter(editor);
		
		HBox hBox = new HBox();
		hBox.setId("hBoxMain");
		//创建一个按钮
		Button btnCreate = new Button("新建");
		btnCreate.getStyleClass().add("num-button");
		Button btnEdit = new Button("编辑");
		btnEdit.getStyleClass().add("num-button");
		Button btnMerge = new Button("合并");
		btnMerge.getStyleClass().add("num-button");
		Button btnSplit = new Button("分割");
		btnSplit.getStyleClass().add("num-button");
		Button btnHelp = new Button("帮助");
		btnHelp.getStyleClass().add("num-button");
		//将按钮加入VBOX
		hBox.getChildren().addAll(btnCreate, btnEdit, btnMerge, btnSplit, btnHelp);
		root.setTop(hBox);
		Scene scane = new Scene(root, 700, 700);
		scane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		//新建一个pdf文件，edit的内容为空
		if(str.length() == 0) {
			stage.setTitle("新建pdf文件");
			stage.setScene(scane);
			stage.setWidth(700);
			stage.setHeight(700);
//			stage.setMaximized(true);//全屏显示
			stage.show();
//			Button btn = new Button("保存");
//			root.setTop(hBox);
//			btn.setOnAction(new EventHandler<ActionEvent>() {
//		
//				@Override
//				public void handle(ActionEvent event) {
//		//				System.out.println(editor.getHtmlText());
//					
//					FileChooser fileChooser = new FileChooser();
//					fileChooser.setTitle("保存文件");
//					fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.pdf"));
//					File selectedFile = fileChooser.showSaveDialog(stage);
//					if(selectedFile == null) return; // 用户没有选中文件, 已经取消操作
//					
//					try {
//						//将路径改为String类型
//						String strFile = selectedFile.toString();
//						//edit的html
//						String strHtml = editor.getHtmlText();
//
//						File file = new File("C:\\Users\\钟倩文\\Desktop\\pdf测试文件\\my.html");
//			            PrintStream ps = new PrintStream(new FileOutputStream(file));
//			            ps.println(strHtml);// 往文件里写入字符串
////						String str = strFile + "&" + strHtml;
////						System.out.println(editor.getHtmlText());
//						
//						
////						savePDF createPDFTest1 = new savePDF();
//						savePDF2 createPDFTest1 = new savePDF2();
//						createPDFTest1.createPdf(strHtml+"&"+strFile);
//					}catch(Exception e)
//					{
//						e.printStackTrace();
//					}
//								
//				}
//			});

	}else {
		//需要显示已有的html文件
		stage.setTitle("编辑pdf文件");
		stage.setScene(scane);
		stage.setWidth(700);
		stage.setHeight(700);
//		stage.setMaximized(true);//全屏显示
		stage.show();
//		ButtonBar buttonBar = new ButtonBar();
//		Button btnUp = new Button("上传文件");
//		Button btnSave = new Button("保存");
//		buttonBar.getButtons().addAll(btnUp, btnSave);
//		root.setTop(buttonBar);
		
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
//		btnSave.setOnAction(new EventHandler<ActionEvent>() {
	
//			@Override
//			public void handle(ActionEvent event) {
//				//edit的html
//				String strHtml = editor.getHtmlText();
//
//				File file = new File("C:\\Users\\钟倩文\\Desktop\\pdf测试文件\\my.html");
//	            PrintStream ps = null;
//				try {
//					ps = new PrintStream(new FileOutputStream(file));
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	            ps.println(strHtml);// 往文件里写入字符串
//	            
//	            //将html文件再转换为pdf
//	            boolean success = HtmlToPdf.create()
//	                    .object(HtmlToPdfObject.forHtml(strHtml.toString()))
//	                    .convert("file.pdf");
//	            
//			}
//		
//		});
	}
		
		//新建Pdf
		btnCreate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
//					newPdf newPdf = new newPdf("");
					newPdf2 newPdf2 = new newPdf2("");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stage.close();
				
			}
		});
		
		//编辑Pdf
		btnEdit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//打开文件选择框
				FileChooser fileChooser = new FileChooser();
	            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.pdf");
	            fileChooser.getExtensionFilters().add(extFilter);
	            File file = fileChooser.showOpenDialog(stage);
	            if(file == null) return; // 用户没有选中文件, 已经取消操作
	            System.out.println(file.toString());
				//将文件地址传给efitPdf
				try {
					editPdf editPdf = new editPdf(file.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stage.close();
				
			}
		});
		
		//合并pdf文件
		btnMerge.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mergePdf2 mergePdf = new mergePdf2();
				stage.close();
				
			}
		});
		

		//分割pdf文件
		btnSplit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				splitPdf2 splitPdf = new splitPdf2();
				stage.close();
				
			}
		});
		
	}
}
