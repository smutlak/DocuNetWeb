/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.docunetweb;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author smutlak
 */
@ManagedBean
@RequestScoped
public class DocumentView {

    /**
     * Creates a new instance of DocumentView
     */
    private List<String> pages;

    public DocumentView() {
    }

    @PostConstruct
    public void init() {
        pages = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            pages.add("img" + i + ".jpg");
        }
    }

    public List<String> getPages() {
        return pages;
    }

}
