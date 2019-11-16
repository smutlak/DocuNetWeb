package com.mis.docunetweb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
    private String dndID;
    private String enablePrinting;
    private String perName;
    private List<String> pages;
    private Integer currIndex;
    private Boolean lastPage;
    private Boolean firstPage;
    private String pageFrom;

    static {
        try {
            DOCUNET_DOCUMENTS_PATH = (String) (new InitialContext().lookup("java:comp/env/DOCUNET_DOCUMENTS_PATH"));
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

        pages = new ArrayList();
        File file = new File(DOCUNET_DOCUMENTS_PATH + File.separator + dndID);
        if (file.exists()) {
            for (File p : file.listFiles()) {
                if (!p.getName().endsWith("thb")) {
                    System.out.println(p.getAbsolutePath());
                    pages.add(p.getAbsolutePath());
                }
            }
        }
        updateFlags();
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

    public StreamedContent getCurrPage() throws IOException {
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
                return new DefaultStreamedContent(new FileInputStream(new File(this.getPages().get(currIndex))));
            }
        }
    }

    public void nextPage() {
        this.currIndex++;
        updateFlags();
    }

    public void prevPage() {
        this.currIndex--;
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
        this.pageFrom = (this.currIndex + 1) + " / " + this.pages.size();
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
        if(this.dndID != null && !dndID.equalsIgnoreCase(this.dndID)){
            File oldDocumentPath = new File(DOCUNET_DOCUMENTS_PATH + File.separator + this.dndID);
            if(oldDocumentPath.exists()){
                deleteRecursive(oldDocumentPath);
            }
        }
        
        this.dndID = dndID;
        pages = new ArrayList();
        currIndex = 0;
        this.getPages();
    }

    public Boolean hasPages(){
        return !this.getPages().isEmpty();
    }
    
    private void deleteRecursive(File path) {
        File[] c = path.listFiles();
        Logger.getLogger(DocumentController.class.getName()).log(Level.INFO,
                    "DocuNetWeb deleteRecursive", "Cleaning out folder:" + path.toString());
        System.out.println("Cleaning out folder:" + path.toString());
        for (File file : c) {
            if (file.isDirectory()) {
                Logger.getLogger(DocumentController.class.getName()).log(Level.INFO,
                    "DocuNetWeb deleteRecursive", "Deleting file:" + file.toString());
                System.out.println("Deleting file:" + file.toString());
                deleteRecursive(file);
                file.delete();
            } else {
                file.delete();
            }
        }
        path.delete();
    }
}
