package ok;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import ok.savePDF;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class splitPdf {
	
	private final Stage stage = new Stage();
	private File file;
    private TextField notification = new TextField ();
	
	public splitPdf(){
		BorderPane root = new BorderPane();
		
		Scene scane = new Scene(root, 700, 700);
		HBox hbox = new HBox();
//		scane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setTitle("拆分pdf文件");
		stage.setScene(scane);
		stage.setMaximized(true);//全屏显示
		stage.show();
		ButtonBar buttonBar = new ButtonBar();
		Button btnLoad = new Button("上传文件");
		Button btnSplit = new Button("开始拆分");
//		Text textNote = new Text("注意：输入页数分割，使用 - 连，用 ，隔开，例如共有5页1-2， 3，4-5，要是没输入则默认每页分割");
		buttonBar.getButtons().addAll(btnLoad, btnSplit);
		hbox.getChildren().addAll(buttonBar, notification);
		root.setTop(hbox);
		
		MyEventHandler1 hnHandler1 = new MyEventHandler1();
		MyEventHandlerOk hnHandler3 = new MyEventHandlerOk();
		btnLoad.setOnAction(hnHandler1);
		btnSplit.setOnAction(hnHandler3);
		
	}
	
	//接口泛型，处理按钮的点击事件
	
	private class MyEventHandler1 implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            file = fileChooser.showOpenDialog(stage);
		}
	}
	
	//接口泛型，处理按钮的点击事件
	
	private class MyEventHandlerOk implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
//			System.out.println(notification.getText().length() == 0);
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
					System.out.println(file);
					try {
						splitByPageSize(file.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			  	
		}
	}
	public static void splitByPageSize(String path) throws IOException {
		System.out.println("按照页数分割");
        //按照页数划分，定义迭代器将所有文件列出
        File file = new File(path);
        PDDocument document = PDDocument.load(file);
        Splitter splitter = new Splitter();
        List<PDDocument> pages = splitter.split(document);
        Iterator<PDDocument> iterator = pages.listIterator();
        int page = 1;
        while(iterator.hasNext()) {
            PDDocument pd = iterator.next();
            pd.save(path + "+" + page++ + ".pdf");
        }
        System.out.println("Multiple PDF’s created");
        document.close();
    }
	
	public static void splitByList(String path, String range) throws IOException {
		System.out.println("按照用户输入分割");
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
                System.out.println("参数已获得");
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
            pd.save("C:/Users/钟倩文/Desktop/pdf测试文件/" + namePrefix);
            pd.close();
        }
    }
}
