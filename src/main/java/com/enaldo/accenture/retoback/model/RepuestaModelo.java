package com.enaldo.accenture.retoback.model;

import java.util.ArrayList;
import java.util.List;

public class RepuestaModelo {

	private String mensaje;
	private List<FacturaModelo> facturas =new ArrayList<FacturaModelo>();;
	
	public RepuestaModelo() {
		
	}

	public RepuestaModelo(String mensaje, FacturaModelo facturas) {
		this.mensaje = mensaje;
		this.facturas.add(facturas);
	}

	public RepuestaModelo(String mensaje, List<FacturaModelo> facturas) {
		this.mensaje = mensaje;
		this.facturas.addAll(facturas);
	}
	
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<FacturaModelo> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<FacturaModelo> facturas) {
		this.facturas.addAll(facturas);
	}
	
	
	

}
