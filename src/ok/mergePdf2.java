package ok;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import ok.savePDF;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;;

public class mergePdf2 {
	
	private final Stage stage = new Stage();
	private File file1;
	private File file2;
	private ArrayList<String> list = new ArrayList<String>();
	private int num = 0;
	
	public mergePdf2(){
		BorderPane root = new BorderPane();
		
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
		stage.setTitle("合并pdf文件");
		stage.setScene(scane);
		stage.setWidth(700);
		stage.setHeight(700);
//		stage.setMaximized(true);//全屏显示
		stage.show();
		
		ButtonBar buttonBar = new ButtonBar();
		Button btnLoad1 = new Button("上传");
//		Button btnLoad2 = new Button("上传2");
		Button btn = new Button("合并");
		buttonBar.getButtons().addAll(btnLoad1, btn);
		root.setBottom(buttonBar);
		
		GridPane pane = new GridPane();
	    pane.setAlignment(Pos.CENTER);
	    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
	    pane.setHgap(5.5);
	    pane.setVgap(5.5);
		root.setCenter(pane);
	    
//		MyEventHandler2 hnHandler2 = new MyEventHandler2();
		MyEventHandlerOk hnHandler3 = new MyEventHandlerOk();
		btnLoad1.setOnAction(new EventHandler<ActionEvent>() {
		
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
	            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.pdf");
	            fileChooser.getExtensionFilters().add(extFilter);
	            file1 = fileChooser.showOpenDialog(stage);
	            list.add(file1.toString());
	            HBox hBox3 = new HBox();
	    		Button btnDel = new Button("删除"+(num+1), new ImageView("file:D:\\\\毕业设计\\\\pdfBox\\\\bishe___\\\\src\\\\icon\\\\delete.png"));
//	    		btnDel.getProperties().put("num", num);
	    		btnDel.setOnAction(new EventHandler<ActionEvent>() {
	    			
	    			@Override
	    			public void handle(ActionEvent event) {
	    				int nu = Integer.parseInt(event.getSource().toString().split("删除")[1].substring(0,1))-1;
//	    				System.out.println(event.getSource().toString().split("删除")[1].substring(0,1));
	    				pane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == nu);
	    				list.remove(nu);
	    			}
	    		});
	            Text text = new Text(file1.toString());
	    		hBox3.getChildren().addAll(text, btnDel);
	            pane.add(hBox3, 0, num);
	            num = num + 1;
			};
		});
//		btnLoad2.setOnAction(hnHandler2);
		btn.setOnAction(hnHandler3);
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
			System.out.println(list.size());
		    PDFMergerUtility PDFmerger = new PDFMergerUtility();
		    
		    FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("保存文件");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.pdf"));
			File selectedFile = fileChooser.showSaveDialog(stage);
			if(selectedFile == null) return; // 用户没有选中文件, 已经取消操作
				//将路径改为String类型
			String strPath = selectedFile.toString();
			PDFmerger.setDestinationFileName(strPath);
			for(int i = 0;i<list.size();i++) {
			    File file1 = new File(list.get(i));
				try {
					PDFmerger.addSource(file1);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    try {
				PDFmerger.mergeDocuments();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("信息提示对话框");
		    alert.setContentText("合并成功！");
		    alert.showAndWait();
		    mergePdf2 mergePdf = new mergePdf2();
		    stage.close();
		}
	}
}
