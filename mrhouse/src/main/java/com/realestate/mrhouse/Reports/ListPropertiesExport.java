/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.realestate.mrhouse.Reports;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

/**
 *
 * @author 2171584201008
 */

@Component("/listUsers.xlsx")
public class ListPropertiesExport extends AbstractXlsxView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
  response.setHeader("Content-Disposition", "Attachment; filename=\"lista-usuarios.xlsx\"");
    Sheet hoja=workbook.createSheet("Users");
    }
    
}
