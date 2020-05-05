package ok;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ok.editPdf;
import ok.mergePdf2;
import ok.newPdf;
import ok.splitPdf2;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

import java.io.File;
import java.io.IOException;

import ok.newPdf2;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
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
			//新建Pdf
			btnCreate.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					try {
//						newPdf newPdf = new newPdf("");
						newPdf2 newPdf2 = new newPdf2("");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					primaryStage.close();
					
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
		            File file = fileChooser.showOpenDialog(primaryStage);
		            if(file == null) return; // 用户没有选中文件, 已经取消操作
//		            System.out.println(file.toString());
					//将文件地址传给efitPdf
					try {
						editPdf editPdf = new editPdf(file.toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					primaryStage.close();
					
				}
			});
			
			//合并pdf文件
			btnMerge.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					mergePdf2 mergePdf = new mergePdf2();
					primaryStage.close();
					
				}
			});
			

			//分割pdf文件
			btnSplit.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					splitPdf2 splitPdf = new splitPdf2();
					primaryStage.close();
					
				}
			});
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("PDF编辑");
			primaryStage.getIcons().add(new Image("/icon/icon.jpg"));
			//全屏显示
//			primaryStage.setMaximized(true);//全屏显示
			
			primaryStage.setWidth(700);
			primaryStage.setHeight(700);
			primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
