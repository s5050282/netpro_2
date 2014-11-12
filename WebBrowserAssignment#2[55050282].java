/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webbrowserassi.pkg2;

                                 

                                    import java.awt.BorderLayout;
                                    import java.awt.Dimension;
                                    import java.awt.Label;
                                    import java.awt.TextArea;
                                    import java.awt.event.ActionEvent;
                                    import java.awt.event.ActionListener;
                                    import java.io.IOException;
                                    import java.net.MalformedURLException;
                                    import java.net.URL;
                                    import java.net.URLConnection;
                                    import java.util.ArrayList;
                                    import java.util.List;
                                    import java.util.Map;
                                    import javax.swing.AbstractAction;
                                    import javax.swing.Action;
                                    import javax.swing.JButton;
                                    import javax.swing.JEditorPane;
                                    import javax.swing.JFrame;
                                    import static javax.swing.JFrame.EXIT_ON_CLOSE;
                                    import javax.swing.JMenu;
                                    import javax.swing.JMenuBar;
                                    import javax.swing.JPanel;
                                    import javax.swing.JScrollPane;
                                    import javax.swing.JTabbedPane;
                                    import javax.swing.JTextField;
                                    import javax.swing.JToolBar;
                                    import javax.swing.event.HyperlinkEvent;
                                    import javax.swing.event.HyperlinkListener;

                                    public class WebBrowserAssi2 extends JFrame {

                                      private JTabbedPane tabbedPane = new JTabbedPane();
                                      public   WebBrowserAssi2() {
                                        super("นายณรงค์เดช  ศักดิ์ศรีจันทร์  s5050282@kmitl.ac.th - Java Web Browser (Network Programming Class Assignment)\"");

                                        createNewTab();

                                        getContentPane().add(tabbedPane);
                                        JMenu fileMenu = new JMenu("Create New Tab Here");
                                        fileMenu.add(new NewTabAction());
                                        fileMenu.addSeparator();
                                        fileMenu.add(new ExitAction());
                                        fileMenu.setMnemonic('F');
                                        JMenuBar menuBar = new JMenuBar();
                                        menuBar.add(fileMenu);
                                        setJMenuBar(menuBar);


                                      }
                                      private void createNewTab() {
                                        JPanel panel = new JPanel(new BorderLayout());
                                        WebBrowserPane browserPane = new WebBrowserPane();
                                        BoxDescript boxDes = new BoxDescript();
                                        WebToolBar toolBar = new WebToolBar(browserPane,boxDes);
                                        panel.add(toolBar, BorderLayout.NORTH);
                                        panel.add(boxDes.getTextPanel(),BorderLayout.PAGE_END);
                                        panel.add(new JScrollPane(browserPane), BorderLayout.CENTER);
                                        int x= tabbedPane.getTabCount()+1;
                                        tabbedPane.addTab("Tab "+x, panel);
                                       
                                        
                                        Thread t = new Thread(toolBar, x+"");
                                        boxDes.threadWorkingBox.append("Thread "+t.getName()+" : "+t.getState().toString()+" [ CREATED ]\n");
                                        t.start();
                                         
                                      }

                                      private class NewTabAction extends AbstractAction {

                                        public NewTabAction() {
                                          putValue(Action.NAME, "New Tab");
                                          putValue(Action.SHORT_DESCRIPTION, "Create New Tab");
                                          putValue(Action.MNEMONIC_KEY, new Integer('N'));
                                        }

                                        public void actionPerformed(ActionEvent event) {
                                        createNewTab();
                                       

                                        }
                                      }

                                      private class ExitAction extends AbstractAction {
                                        public ExitAction() {
                                          putValue(Action.NAME, "Exit");
                                          putValue(Action.SHORT_DESCRIPTION, "Exit Application");
                                          putValue(Action.MNEMONIC_KEY, new Integer('x'));
                                        }

                                        public void actionPerformed(ActionEvent event) {
                                          System.exit(0);
                                        }
                                      }

                                      public static void main(String args[]) {
                                        WebBrowserAssi2 browser = new WebBrowserAssi2();
                                        browser.setDefaultCloseOperation(EXIT_ON_CLOSE);
                                        browser.setSize(1300, 700);
                                        browser.setVisible(true);
                                       
                                      }
                                    }
                                    
                                    
                                    
                                    
                                    
                                    

                                    class WebBrowserPane extends JEditorPane {

                                      private List history = new ArrayList();

                                      private int historyIndex;

                                      public WebBrowserPane() {
                                        setEditable(false);
                                      }

                                      public void goToURL(String url) {
                                          
                                            String tmp = toURL(url);
                                         
                                            if (tmp == null) {
                                                tmp = toURL("http://" + url);
                                            }
                                          
                                          
                                          displayPage(tmp);
                                          history.add(tmp);
                                          historyIndex = history.size() - 1;
                                       
                                      }
                                      public String toURL(String str) {
                                      try {
                                          return new URL(str).toExternalForm();
                                        } catch (MalformedURLException exception) {
                                            return null;
                                       }
                                      }

                                    
                                      public String back() {
                                        historyIndex--;
                                        
                                        if (historyIndex < 0)
                                          return null;
                                        //historyIndex = 0;

                                       String url = (String) history.get(historyIndex);
                                       
                                        displayPage(url);

                                        return url;
                                      }

                                      private void displayPage(String pageURL) {
                                        try {
                                          setPage(pageURL);
                                        } catch (IOException ioException) {
                                          this.setText("Error: " +ioException);
                                        }
                                      }
                                    }

                                    
                                    
                                    
                                    
                                    class WebToolBar extends JToolBar implements Runnable {

                                      private WebBrowserPane webBrowserPane;
                                      private BoxDescript boxDescript;

                                      private JButton backButton;
                                      private JButton goButton;
                                      private JTextField urlTextField;
                                      
                                    

                                      public WebToolBar(WebBrowserPane webBrowserPane,BoxDescript boxDescript) {
                                        
                                          this.webBrowserPane = webBrowserPane;
                                          this.boxDescript = boxDescript;
                                          
                                          goButton = new JButton("Go.....");
                                          urlTextField = new JTextField(25);
                                          backButton = new JButton("back");
                                         // JTextArea a = new JTextArea();
                                         // JTextArea b = new JTextArea();
                                          
                                        //add(b);
                                        //add(a);
                                        add(backButton);
                                        add(goButton);
                                        add(urlTextField);
                                        
                                       
                                        
                                       
                                      }
                                      @Override
                                      public void run(){
                                        
                                           Thread currentThread = Thread.currentThread();
                                           //System.out.println("Thread Starting....."+currentThread.getName());
                                            boxDescript.threadWorkingBox.append("Thread "+currentThread.getName()+" : STARTED"+"\n");
                                           boxDescript.threadWorkingBox.append("Thread "+currentThread.getName()+" : "+currentThread.getState().toString()+"\n");
                                           webBrowserPane.addHyperlinkListener(new HyperlinkListener(){

                                            @Override
                                            public void hyperlinkUpdate(HyperlinkEvent e) {
                                                 if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                                                        
                                                        String url = e.getURL().toString();
                                                        webBrowserPane.goToURL(url);
                                                        urlTextField.setText(url);
                                                        
                                                        try{
                                                        URL obj = new URL(e.getURL().toString());
                                                        URLConnection conn = obj.openConnection();
                                                        Map<String, List<String>> map = conn.getHeaderFields();
                                                        String resp="";
                                                        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                                                                resp=resp+(entry.getKey() 
                                                                           + " : " +entry.getValue()+"\n");
                                                        }
                                                        boxDescript.threadWorkingBox.append("Thread "+currentThread.getName()+" : "+" GET URL : "+e.getURL().toString()+"\n");
                                                        boxDescript.httpRespondBox.append("Thread "+currentThread.getName()+"\n"+"URL HyperLink : "+e.getURL().toString()+"\n"+resp+"\n"+"_________________________________________________________________\n\n");
                                                        boxDescript.threadWorkingBox.append("Thread "+currentThread.getName()+" : "+" GET URL : "+e.getURL().toString()+" FINISHED\n");
                                                        }
                                                        catch(IOException ex){
                                                            
                                                        }
                                                        
                                                        
                                                 }
                                            }
                                        });
                                         
                                        
                                       
                                        urlTextField.addActionListener(new ActionListener() {

                                        
                                       public void actionPerformed(ActionEvent event) {

                                          
                                              String url = urlTextField.getText();
                                              webBrowserPane.goToURL(url);
                                               try{
                                                        String tmp = webBrowserPane.toURL(url);
                                         
                                                            if (tmp == null) {
                                                                tmp = webBrowserPane.toURL("http://" + url);
                                                            }
                                                            
                                                        urlTextField.setText(tmp);
                                                        URL obj = new URL(tmp);
                                                        URLConnection conn = obj.openConnection();
                                                        Map<String, List<String>> map = conn.getHeaderFields();
                                                        String resp="";
                                                        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                                                                resp=resp+(entry.getKey() 
                                                                           + " : " +entry.getValue()+"\n");
                                                        }
                                                        boxDescript.threadWorkingBox.append("Thread "+currentThread.getName()+" : "+" GET URL: "+tmp+"\n");
                                                        boxDescript.httpRespondBox.append("Thread "+currentThread.getName()+"\n"+"GET URL  : "+tmp+"\n"+resp+"\n"+"_________________________________________________________________\n\n");
                                                        boxDescript.threadWorkingBox.append("Thread "+currentThread.getName()+" : "+" GET URL: "+tmp+" FINISHED\n");
                                                     }
                                                   catch(IOException ex){
                                                            
                                                   }
                                              
                                            

                                            
                                          }
                                        });

                                     
                                        backButton.addActionListener(new ActionListener() {
                                          public void actionPerformed(ActionEvent event) {
                                            String url = webBrowserPane.back();
                                            if(url!=null)
                                            urlTextField.setText(url);
                                            
                                              try{
                                                        URL obj = new URL(url);
                                                        URLConnection conn = obj.openConnection();
                                                        Map<String, List<String>> map = conn.getHeaderFields();
                                                        String resp="";
                                                        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                                                                resp=resp+(entry.getKey() 
                                                                           + " : " +entry.getValue()+"\n");
                                                        }
                                                        boxDescript.threadWorkingBox.append("Thread "+currentThread.getName()+" : "+" BACK TO URL : "+url+"\n");
                                                        boxDescript.httpRespondBox.append("Thread "+currentThread.getName()+"\n"+"BACK TO URL  : "+url+"\n"+resp+"\n"+"_________________________________________________________________\n\n");
                                                        boxDescript.threadWorkingBox.append("Thread "+currentThread.getName()+" : "+" BACK TO URL : "+url+" FINISHED \n");
                                                  }
                                                   catch(IOException ex){
                                                            
                                                   }
                                            
                                            
                                          }
                                        });

                                     
                                         goButton.addActionListener(new ActionListener() {

                                          public void actionPerformed(ActionEvent event) {
                                             
                                              String urlText =urlTextField.getText();
                                                    webBrowserPane.goToURL(urlText);
                                                    try{
                                                        
                                                        String tmp = webBrowserPane.toURL(urlText);
                                         
                                                            if (tmp == null) {
                                                                tmp = webBrowserPane.toURL("http://" + urlText);
                                                            }
                                                        
                                                        urlTextField.setText(tmp);
                                                        URL obj = new URL(tmp);
                                                        URLConnection conn = obj.openConnection();
                                                        Map<String, List<String>> map = conn.getHeaderFields();
                                                        String resp="";
                                                        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                                                                resp=resp+(entry.getKey() 
                                                                           + " : " +entry.getValue()+"\n");
                                                        }
                                                        boxDescript.threadWorkingBox.append("Thread "+currentThread.getName()+" : "+" GET URL : "+tmp+"\n");
                                                        boxDescript.httpRespondBox.append("Thread "+currentThread.getName()+"\n"+" GET URL  : "+tmp+"\n"+resp+"\n"+"_________________________________________________________________\n\n");
                                                        boxDescript.threadWorkingBox.append("Thread "+currentThread.getName()+" : "+" GET URL : "+tmp+" FINISHED \n");
                                                    }
                                                   catch(IOException ex){
                                                            
                                                   }
                                                    
                                             
                                           
                                          }
                                        });
                                         
                                         
                                      }
                                 }

                                 class BoxDescript {
                                     
                                       private JPanel textPanel;
                                         TextArea threadWorkingBox = new TextArea("",14,70);
                                         TextArea httpRespondBox = new TextArea("",14,70);
                                     public BoxDescript() {
                                           textPanel = new JPanel();
                                           Label  threadLabel= new Label("Thread Working : ", Label.RIGHT);
                                           Label  httpResLabel= new Label("Http Respond: ", Label.LEFT);
                                           textPanel.add(threadLabel);
                                           textPanel.add(threadWorkingBox,BorderLayout.PAGE_END);
                                           textPanel.add(httpRespondBox,BorderLayout.PAGE_END);
                                           textPanel.add(httpResLabel);
                                           textPanel.setPreferredSize(new Dimension(230,230));
                                      }
                                     public JPanel getTextPanel(){
                                         return textPanel;
                                     }
                                     
                                    }