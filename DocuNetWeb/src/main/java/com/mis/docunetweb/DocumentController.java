package com.mis.docunetweb;

import com.itextpdf.text.DocumentException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.event.ResizeEvent;
import org.primefaces.extensions.event.ImageAreaSelectEvent;
import org.primefaces.extensions.event.RotateEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author smutlak
 */
@ManagedBean
@SessionScoped
public class DocumentController implements Serializable {

    private static final long serialVersionUID = 20111021L;

    //String docPath = "D:/temp/4242521";
    //String TEMP_DIR = System.getProperty("java.io.tmpdir") + "\\DND\\";
    static String DOCUNET_DOCUMENTS_PATH = "D:/temp/4242521";
    static Integer DOCUNET_SCREEN_WIDTH = 1366;
    static Integer DOCUNET_PAGE_COMPRESSION = 2;
    private String dndID;
    private String enablePrinting;
    private String perName;
    private List<String> pages;
    private Integer currIndex;
    private Boolean lastPage;
    private Boolean firstPage;
    private String pageFrom;

    private org.json.JSONObject docInfo;

    static {
        try {
            DOCUNET_DOCUMENTS_PATH = (String) (new InitialContext().lookup("java:comp/env/DOCUNET_DOCUMENTS_PATH"));
        } catch (NamingException ex) {
            Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE,
                    "Exception caught", ex);
        }

        try {
            DOCUNET_SCREEN_WIDTH = Integer.parseInt((String) (new InitialContext().lookup("java:comp/env/DOCUNET_SCREEN_WIDTH")));
            DOCUNET_PAGE_COMPRESSION = Integer.parseInt((String) (new InitialContext().lookup("java:comp/env/DOCUNET_PAGE_COMPRESSION")));
        } catch (NamingException ex) {
            Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE,
                    "Exception caught", ex);
        }

    }

    public DocumentController() {
        currIndex = 0;
        firstPage = true;
        lastPage = false;
    }

    @PostConstruct
    public void init() {
        /*        pages = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            pages.add("img" + i + ".jpg");
        }*/
        currIndex = 0;
        firstPage = true;
        lastPage = false;
    }

    public List<String> getPages() {
        if (pages == null || pages.isEmpty()) {
            pages = new ArrayList();
            File file = new File(DOCUNET_DOCUMENTS_PATH + File.separator + dndID);
            if (file.exists()) {
                for (File p : file.listFiles()) {
                    if (!p.getName().endsWith("thb") && !p.getName().endsWith("txt")
                            && !p.getName().endsWith("ann")) {
                        String page = p.getAbsolutePath();
                        pages.add(page);
                    }
                }
            }
            //convert image format
            for (int i = 0; i < pages.size(); i++) {
                String page = pages.get(i);
                int ret = new NativeImageConverter().convertImage(page, 125);
                switch (ret) {
                    case 0:
                        System.out.println("DocumentController::getPages::No need to convert image: " + page);
                        break;
                    case 1: {
                        String newName = page.substring(0, page.lastIndexOf("."));
                        newName += ".jpg";
                        try {
                            if ((new File(page + ".jpg").exists())) {
                                new File(page).delete();
                                Files.move(java.nio.file.Paths.get(page + ".jpg"), java.nio.file.Paths.get(newName), java.nio.file.StandardCopyOption.ATOMIC_MOVE);
                            }
                        } catch (IOException e) {
                            Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE,
                                    "Exception caught in renaming files.", e);
                        }

                        page = newName;
                    }
                    break;
                    default:
                        System.out.println("DocumentController::getPages::Error converting image: " + page + "Error Code:" + ret);
                }
                pages.set(i, page);
            }

            //sort according to page numbers
            if (!pages.isEmpty()) {
                boolean stopLoop = false;
                while (!stopLoop) {
                    boolean swap = false;
                    for (int i = 0; i < pages.size(); i++) {
                        String p = pages.get(i);
                        String sIndex = p.substring(p.lastIndexOf(File.separator) + 1, p.lastIndexOf("."));
                        int in = Integer.parseInt(sIndex);
                        if (in != (i + 1)) {
                            String p1 = pages.get(in - 1);
                            String p2 = pages.get(i);
                            pages.set(i, p1);
                            pages.set(in - 1, p2);
                            swap = true;
                            break;
                        }
                    }
                    if (!swap) {
                        stopLoop = true;
                    }
                }
            }
//            //convert the first page then run a thread to convert the rest
//            for (int i = 0; i < pages.size(); i++) {
//                String page = pages.get(i);
//                int ret = new NativeImageConverter().convertImage(page, 125);
//                if (ret != 1) {
//                    System.out.println("there is an error: " + ret);
//                } else {
//                    String oldName = page;
//                    String newName = page.substring(1, page.lastIndexOf("."));
//                    newName += ".jpg";
//
//                    new File(oldName).delete();
//                    new File(oldName + ".jpg").renameTo(new File(newName));
//                    pages.set(i, newName);
//                }
//            }
            updateFlags();
        }
        return pages;
    }

    public void selectEndListener(final ImageAreaSelectEvent e) {
//        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Area selected",
//                "X1: " + e.getX1()
//                + ", X2: " + e.getX2()
//                + ", Y1: " + e.getY1()
//                + ", Y2: " + e.getY2()
//                + ", Image width: " + e.getImgWidth()
//                + ", Image height: " + e.getImgHeight());
//
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void rotateListener(final RotateEvent e) {
//        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image rotated",
//                "Degree:" + e.getDegree());
//
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void resizeListener(final ResizeEvent e) {
//        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image resized",
//                "Width:" + e.getWidth() + ", Height: " + e.getHeight());
//
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public int getCurrIndex() {
        return currIndex;
    }

    public void setCurrIndex(int currIndex) {
        this.currIndex = currIndex;
    }

    public StreamedContent getCurrPage() {
        System.out.println("DocumentController::getCurrPage::getCurrPage(" + currIndex + ") time=" + new java.util.Date());
        try {
            this.getPages();
            FacesContext context = FacesContext.getCurrentInstance();
            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
                return new DefaultStreamedContent();
            } else {
                // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
                //String filename = context.getExternalContext().getRequestParameterMap().get("filename");
                if (this.getPages() == null || currIndex >= this.getPages().size()) {
                    return new DefaultStreamedContent();
                } else {
                    long startTime = System.nanoTime();
                    java.io.InputStream inputStream = null;
                    String fileName = this.getPages().get(currIndex);
//                    if (fileName.toUpperCase().endsWith("TIF") || fileName.toUpperCase().endsWith("TIFF")) {
//                        inputStream = ImageConverter.convertTiffToJpg(fileName, DOCUNET_SCREEN_WIDTH);
//                    } else {
                    inputStream = new FileInputStream(new File(fileName));
//                    }
                    System.out.println(fileName + "DocumentController::getCurrPage Load time:" + (((System.nanoTime() - startTime) / 1000)));
                    return new DefaultStreamedContent(inputStream);

                }
            }
        } catch (IOException e) {
            Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE,
                    "Exception caught in getCurrPage", e);
        } catch (Exception e) {
            Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE,
                    "Exception caught in getCurrPage", e);
        }
        return new DefaultStreamedContent();
    }

    public void nextPage() {
        this.currIndex++;
        updateFlags();
    }

    public void prevPage() {
        this.currIndex--;
        updateFlags();
    }

    public void firstPage() {
        this.currIndex = 0;
        updateFlags();
    }

    public void lastPage() {
        this.currIndex = this.pages.size() - 1;
        updateFlags();
    }

    public Boolean getLastPage() {
        return lastPage;
    }

    public void setLastPage(Boolean lastPage) {
        this.lastPage = lastPage;
    }

    public Boolean getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Boolean firstPage) {
        this.firstPage = firstPage;
    }

    private void updateFlags() {
        if (this.pages != null) {
            lastPage = this.currIndex >= (this.pages.size() - 1);
        } else {
            lastPage = false;
        }

        firstPage = this.currIndex <= 0;
        //this.pageFrom = (this.currIndex + 1) + " / " + this.pages.size();
        this.pageFrom = " / " + this.pages.size();
    }

    public String getPageFrom() {
        return pageFrom;
    }

    public void setPageFrom(String pageFrom) {
        this.pageFrom = pageFrom;
    }

    public String getDndID() {
        return dndID;
    }

    public void setDndID(String dndID) {
        if (this.dndID != null && this.dndID.equalsIgnoreCase(dndID)) {
            System.out.println("DocumentController::setDndID::Same dndID ...do nothing");
            return;
        }
        this.dndID = dndID;
        docInfo = getDocumentInfo();
        pages = new ArrayList();
        currIndex = 0;
        this.getPages();
    }

    private org.json.JSONObject getDocumentInfo() {
        BufferedReader br = null;
        try {
            File file = new File(DOCUNET_DOCUMENTS_PATH + File.separator + dndID
                    + File.separator + "info.txt");
            br = new BufferedReader(new FileReader(file));
            String contents = "";
            String st;
            while ((st = br.readLine()) != null) {
                contents += st;
            }
            org.json.JSONObject obj = new org.json.JSONObject(contents);
            return obj;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(br != null) {br.close();}
            } catch (IOException ex) {
                Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public Boolean hasPages() {
        return !this.getPages().isEmpty() && this.dndID != null && tooOldDocument()==false;
    }

    public Integer getRequestedPageNo() {
        return (this.currIndex + 1);
    }

    public void setRequestedPageNo(Integer requestedPageNo) {
        if (requestedPageNo > 0 && requestedPageNo < this.pages.size()) {
            this.currIndex = requestedPageNo - 1;
            System.out.println("DocumentController::setRequestedPageNo::set currIndex to " + requestedPageNo);
            updateFlags();
        }
    }

    public boolean getPrintAllowed() {
        //docInfo.get("DndID")
        if (docInfo != null) {
            if (docInfo.get("printAllowed") != null) {
                if (docInfo.get("printAllowed") instanceof Boolean) {
                    return ((Boolean) docInfo.get("printAllowed"));
                }
            }
        }
        return false;
    }

    public boolean tooOldDocument() {
        Date docDate = getTimestamp();
        Integer parsedPageCount = getParsedPageCount();
        Date curDate = new Date();

        if (docDate != null && parsedPageCount != null && parsedPageCount>0) {
            long lMillis = curDate.getTime() - docDate.getTime();
            //if (lMillis > 0 && lMillis <= (parsedPageCount * 4 * 1000)) {
            if (lMillis > 0 && lMillis <= (60 * 60 * 1000)) {
                return false;
            }
        }
        return true;
//        return false;
    }

    public Date getTimestamp() {
        if (docInfo != null) {
            if (docInfo.get("timestamp") != null) {
                String sTimestamp = ((String) docInfo.get("timestamp"));
                try {
                    return new SimpleDateFormat("yyyyMMddHHmmss").parse(sTimestamp);
                } catch (ParseException ex) {
                    Logger.getLogger(DocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public Integer getParsedPageCount() {
        if (docInfo != null) {
            if (docInfo.get("pageCount") != null) {
                if (docInfo.get("pageCount") instanceof Integer) {
                    return ((Integer) docInfo.get("pageCount"));
                }
            }
        }
        return 0;
    }
}
