package com.enaldo.accenture.retoback.model;

public class PedidoModelo {
	private String estado="activo";
	private int id;
	private String direccion;
	//el formato de la fecha debe ser "dd/MM/yyyy HH:mm"
	private String fecha;
	private double valor;
	
	
	public PedidoModelo() {
		
	}
	
	public PedidoModelo(int id, String direccion, double valor, String fecha) {
		this.id = id;
		this.direccion = direccion;
		this.fecha = fecha;
		this.valor = valor;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getEstado() {
		return this.estado;
	}
	public void setEstado(String estado) {
		this.estado=estado;
	}
}
