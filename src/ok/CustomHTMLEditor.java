package ok;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import org.apache.commons.io.FileUtils;

import io.woo.htmltopdf.HtmlToPdf;
import io.woo.htmltopdf.HtmlToPdfObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
class CustomHTMLEditor extends HTMLEditor {

  public static final String TOP_TOOLBAR = ".top-toolbar";
  public static final String BOTTOM_TOOLBAR = ".bottom-toolbar";
  public static final String WEB_VIEW = ".web-view";
  private static final String IMPORT_BUTTON_GENERAL = "/icon/embed.png";
  private static final String IMPORT_BUTTON_SAVE = "/icon/save.png";


  private final WebView mWebView;
  private final ToolBar mTopToolBar;
  private final ToolBar mBottomToolBar;
  private Button mImportFileButton;

  public CustomHTMLEditor() {
	  
	  
      mWebView = (WebView) this.lookup(WEB_VIEW);
      mTopToolBar = (ToolBar) this.lookup(TOP_TOOLBAR);
      mBottomToolBar = (ToolBar) this.lookup(BOTTOM_TOOLBAR);
      createCustomButtons();
      createCustomButtons2();
      this.setHtmlText("<html />");
  }

  /**
   * Inserts HTML data after the current cursor. If anything is selected, they
   * get replaced with new HTML data.
   *
   * @param html HTML data to insert.
   */
  public void insertHtmlAfterCursor(String html) {
      //replace invalid chars
      html = html.replace("\\", "\\\\")
              .replace("\"", "\\\"")
              .replace("\r", "\\r")
              .replace("\n", "\\n");
      //get script
      String script = String.format(
              "(function(html) {"
                      + "  var sel, range;"
                      + "  if (window.getSelection) {"
                      + "    sel = window.getSelection();"
                      + "    if (sel.getRangeAt && sel.rangeCount) {"
                      + "      range = sel.getRangeAt(0);"
                      + "      range.deleteContents();"
                      + "      var el = document.createElement(\"div\");"
                      + "      el.innerHTML = html;"
                      + "      var frag = document.createDocumentFragment(),"
                      + "        node, lastNode;"
                      + "      while ((node = el.firstChild)) {"
                      + "        lastNode = frag.appendChild(node);"
                      + "      }"
                          + "      range.insertNode(frag);"
                      + "      if (lastNode) {"
                      + "        range = range.cloneRange();"
                      + "        range.setStartAfter(lastNode);"
                      + "        range.collapse(true);"
                      + "        sel.removeAllRanges();"
                      + "        sel.addRange(range);"
                      + "      }"
                      + "    }"
                      + "  }"
                      + "  else if (document.selection && "
                      + "           document.selection.type != \"Control\") {"
                      + "    document.selection.createRange().pasteHTML(html);"
                      + "  }"
                      + "})(\"%s\");", html);
      //execute script
      mWebView.getEngine().executeScript(script);
  }

  /**
   * Creates Custom ToolBar buttons and other controls
   */
  private void createCustomButtons() {
      //add embed file button
      ImageView graphic = new ImageView(new Image(
              getClass().getResourceAsStream(IMPORT_BUTTON_GENERAL)));
      mImportFileButton = new Button("", graphic);
      mImportFileButton.setTooltip(new Tooltip("Import File"));
      mImportFileButton.setOnAction((event) -> onImportFileButtonAction());

      //add to top toolbar
      mTopToolBar.getItems().add(mImportFileButton);
      mTopToolBar.getItems().add(new Separator(Orientation.VERTICAL));
  }
  
  private void createCustomButtons2() {
      //add embed file button
      ImageView graphic2 = new ImageView(new Image(
              getClass().getResourceAsStream(IMPORT_BUTTON_SAVE)));
      
      mImportFileButton = new Button("", graphic2);
      mImportFileButton.setTooltip(new Tooltip("Import File"));
      mImportFileButton.setOnAction((event) -> onImportFileButtonAction2());

      //add to top toolbar
      mTopToolBar.getItems().add(mImportFileButton);
      mTopToolBar.getItems().add(new Separator(Orientation.VERTICAL));
  }

  /**
   * Action to do on Import Image button click
   */
  private void onImportFileButtonAction() {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Select a file to import");
      fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("All Files", "*.*"));
      File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
      if (selectedFile != null) {
          importDataFile(selectedFile);
      }
  }
  
  private void onImportFileButtonAction2() {
      String str = getHtmlText();
      String str2 = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"></head>"+str.split("</head>")[1];
//		System.out.println(str2);
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("保存文件");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.pdf"));
		File selectedFile = fileChooser.showSaveDialog(stage);
		if(selectedFile == null) return; // 用户没有选中文件, 已经取消操作
			//将路径改为String类型
		String strPath = selectedFile.toString();
//		System.out.println(str2);
		//将html文件再转换为pdf
	    boolean success = HtmlToPdf.create()
	            .object(HtmlToPdfObject.forHtml(str2.toString()))
	            .convert(strPath);
	    stage.close();
  }

  /**
   * Imports an image file.
   *
   * @param file Image file.
   */
  private void importDataFile(File file) {
      try {
          //check if file is too big
          if (file.length() > 1024 * 1024) {
              throw new VerifyError("File is too big.");
          }
          //get mime type of the file
          String type = Files.probeContentType(file.toPath());
          //get html content
          byte[] data = FileUtils.readFileToByteArray(file);
          String base64data = Base64.getEncoder().encodeToString(data);
          System.out.println(base64data);
          String htmlData = String.format("<embed src='data:%s;base64,%s' type='%s' />", type, base64data, type);
          //insert html
          insertHtmlAfterCursor(htmlData);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
}