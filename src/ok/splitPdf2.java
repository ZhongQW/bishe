package ok;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import ok.savePDF;
import ok.splitPdf;


import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.Splitter; 
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
//import com.itextpdf.tool.xml.html.Image;

public class splitPdf2 {
	
	private final Stage stage = new Stage();
	private File file;
    private TextField notification = new TextField ();
    private Text textNote = new Text("");	
    private static String strPath = "";
	public splitPdf2(){
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
		HBox hbox2 = new HBox();
		scane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setTitle("拆分pdf文件");
		stage.setScene(scane);
		stage.setWidth(700);
		stage.setHeight(700);
//		stage.setMaximized(true);//全屏显示
		stage.show();
		
//		ButtonBar buttonBar = new ButtonBar();
		Button btnLoad = new Button("上传", new ImageView("file:D:\\\\毕业设计\\\\pdfBox\\\\bishe___\\\\src\\\\icon\\\\input.png"));
		HBox hBox3 = new HBox();
		hBox3.getChildren().addAll(btnLoad, textNote);
		Text textNote2 = new Text("注意：输入页数分割，使用 - 连，用 ，隔开，例如共有5页1-2,3,4-7,9-11要是没输入则默认每页分割");
		Button btnSplit2 = new Button("开始拆分");
		//		buttonBar.getButtons().addAll(btnLoad, btnSplit2);
		VBox vBox = new VBox(15);
//	    vBox.setPadding(new Insets(55, 5, 5, 5));
		hbox2.getChildren().addAll(notification, btnSplit2);
		vBox.getChildren().addAll(hBox3, textNote2, hbox2);
		root.setCenter(vBox);
		
		MyEventHandler1 hnHandler1 = new MyEventHandler1();
		MyEventHandlerOk hnHandler3 = new MyEventHandlerOk();
		btnLoad.setOnAction(hnHandler1);
		btnSplit2.setOnAction(hnHandler3);
		//新建Pdf
		btnCreate.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			try {
//							newPdf newPdf = new newPdf("");
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
            file = fileChooser.showOpenDialog(stage);
        	if(file == null) return; // 用户没有选中文件, 已经取消操作
        	textNote.setText(file.toString());
		}
	}
	
	//接口泛型，处理按钮的点击事件
	
	private class MyEventHandlerOk implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			 	Stage stage = new Stage();
			 	DirectoryChooser directoryChooser=new DirectoryChooser();
			 	File file2 = directoryChooser.showDialog(stage);
			 	String path = file2.getPath();//选择的文件夹路径
			 	if(path == null) return; // 用户没有选中文件, 已经取消操作
				strPath = path.toString();
				//用户输入页数
				if(notification.getText().length() != 0) {
	                try {
						splitByList(file.toString(), notification.getText());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
				//用户为输入，则默认
//					System.out.println(file);
					try {
						splitByPageSize(file.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				 Alert alert = new Alert(Alert.AlertType.INFORMATION);
				 alert.setTitle("信息提示对话框");
				 alert.setContentText("拆分成功！");
				 alert.showAndWait();
				 splitPdf2 splitPdf2 = new splitPdf2();
				 stage.close();
			  	
		}
	}
	public static void splitByPageSize(String path) throws IOException {
//		System.out.println("按照页数分割");
        //按照页数划分，定义迭代器将所有文件列出
        File file = new File(path);
        PDDocument document = PDDocument.load(file);
        Splitter splitter = new Splitter();
        List<PDDocument> pages = splitter.split(document);
        Iterator<PDDocument> iterator = pages.listIterator();
        int page = 1;
		strPath = strPath+"\\";
        while(iterator.hasNext()) {
            PDDocument pd = iterator.next();
            pd.save(strPath +"split"+ page++ + ".pdf");
        }
//        System.out.println("Multiple PDF’s created");
        document.close();
    }
	
	public static void splitByList(String path, String range) throws IOException {
//		System.out.println("按照用户输入分割");
        ////按照起始将文件划分为几部分，例如[1-2,3,4-7,8] 等
        Map<String, ArrayList<Object>> map = checkStartEndList(range);
        assert map != null;
        ArrayList<Object> startList = map.get("first");
        ArrayList<Object> endList = map.get("end");
        File file = new File(path);
        PDDocument document = PDDocument.load(file, MemoryUsageSetting.setupTempFileOnly());
        List<PDDocument> docs = new ArrayList<>();
        for(int i = 0; i < startList.size(); i++){
            int start = (int) startList.get(i);
            int end = (int) endList.get(i);
            Splitter splitter = new Splitter();
            splitter.setStartPage(start);
            splitter.setEndPage(end);
            splitter.setSplitAtPage(end - start + 1);
            docs.add(splitter.split(document).get(0));
        }
        writeFiles(docs);
    }
	
	 public static Map<String, ArrayList<Object>> checkStartEndList(String range){
        //列表有效性检查
        ArrayList<Object> startList = new ArrayList<>();
        ArrayList<Object> endList = new ArrayList<>();
        try{
            //首先使用 ， 进行分割 [1-2,3,4-6,8]
            String[] list = range.split(",");
            for(String item : list){
                //对于列表中的每一组，使用 - 进行分割[1,2],[3],[4,5,6],[8]
                String[] values = item.split("-");
                int start = Integer.parseInt(values[0].trim());
                int end;
                if (values.length != 2){
                    end = Integer.parseInt(values[0].trim());
                }else{
                    end = Integer.parseInt(values[1].trim());
                }
                if (start > 0 && end >= start) {
                    startList.add(start);
                    endList.add(end);
                }else {
                    System.out.println("输入非法");
                    break;
                }
            }
            if (startList.isEmpty()) {
                System.out.println("空字符串");
            }else {
//                System.out.println("参数已获得");
                Map<String, ArrayList<Object>> map = new HashMap<String, ArrayList<Object>>();
                map.put("first", startList);
                map.put("end", endList);
                return map;
            }
        }catch (Exception e){

        }
        return null;
	    }
	 
	 private static void writeFiles(List<PDDocument> docs) throws IOException {
        int index = 1;
        if (docs == null || docs.isEmpty()) {
            return;
        }
        PDDocumentInformation info = new PDDocumentInformation();
        info.setCreationDate(Calendar.getInstance());
        info.setModificationDate(Calendar.getInstance());
        int total = docs.size();
        
        strPath =strPath.replaceAll("\\\\", "/");
		strPath = strPath+"/";
        
        for (PDDocument pd : docs) {
            pd.setDocumentInformation(info);
            PDPage page = pd.getPage(0);
            PDPageXYZDestination dest = new PDPageXYZDestination();
            dest.setPage(page);
            dest.setZoom(1f);
            dest.setTop((int) page.getCropBox().getHeight());
            PDActionGoTo action = new PDActionGoTo();
            action.setDestination(dest);
            pd.getDocumentCatalog().setOpenAction(action);
            String namePrefix = "merge.pdf" + "_" + index++ + ".pdf";
            pd.save(strPath + namePrefix);
            pd.close();
        }
    }
	 
	
}
