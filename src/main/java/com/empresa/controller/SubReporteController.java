package com.empresa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.empresa.entity.DataSubReporte;
import com.empresa.entity.Empleado;
import com.empresa.entity.Proveedor;
import com.empresa.service.EmpleadoService;
import com.empresa.service.ProveedorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.apachecommons.CommonsLog;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@CommonsLog
public class SubReporteController {

	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private ProveedorService proveedorService;
	
	@GetMapping("/verSubReporte")
	public String verInicio() {
		return "subReporte";
	}
	
	@GetMapping("/verSubReportePDF")
	public void reporte(HttpServletRequest request, HttpServletResponse response) {
		
		try {
		
		//PASO 1: Obtener el dataSource que va generar el reporte
		List<Empleado> lstEmpleado = empleadoService.listaTodos();
		List<Proveedor> lstProveedor = proveedorService.listaTodos();
		
		List<DataSubReporte> lstSalida = new ArrayList<DataSubReporte>();
		DataSubReporte objSubReporte = new DataSubReporte();
		objSubReporte.setLstEmpleado(lstEmpleado);
		objSubReporte.setLstProveedor(lstProveedor);
		lstSalida.add(objSubReporte);
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstSalida);
		
		//PASO 2: Obtener el archivo que contiene el diseño del reporte
		String urlSubReporteProveedor =  request.getServletContext().getRealPath("/WEB-INF/reportes/subReporteProveedor.jrxml");
		String urlSubReporteEmpleado =  request.getServletContext().getRealPath("/WEB-INF/reportes/subReporteEmpleado.jrxml");
		
		String urlMaster = request.getServletContext().getRealPath("/WEB-INF/reportes/subReporteMaster2.jrxml"); 
		log.info(">> urlMaster >> " + urlMaster);
		log.info(">> urlSubReporteProveedor >> " + urlSubReporteProveedor);
		log.info(">> urlSubReporteEmpleado >> " + urlSubReporteEmpleado);
		
		//PASO 3: Parámetros adicionales
		JasperReport jasperSubReportProveedor = JasperCompileManager.compileReport(new  FileInputStream(new File(urlSubReporteProveedor)));
		JasperReport jasperSubReportEmpleado = JasperCompileManager.compileReport(new  FileInputStream(new File(urlSubReporteEmpleado)));
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("SUBREPORT_PROVEEDOR", jasperSubReportProveedor);
		params.put("SUBREPORT_EMPLEADO", jasperSubReportEmpleado);
		
		//PASO 4: Enviamos dataSource, diseño y parámetros para generar el PDF
		JasperReport jasperReport = JasperCompileManager.compileReport(new  FileInputStream(new File(urlMaster)));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		
	
		//PASO 5: Enviar el PDF generado
		response.setContentType("application/x-pdf");
	    response.addHeader("Content-disposition", "attachment; filename=ReporteEmpleado.pdf");

		OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
}
