package com.enaldo.accenture.retoback.model;

public class FacturaModelo {
	private String estado;
	private int id;
	private String direccion;
	//el formato de la fecha debe ser "dd/MM/yyyy HH:mm"
	private String fecha;
	private double iva=0.19;
	private double valorDomicilio;
	private double valorTotal;
	
	
	public FacturaModelo() {
		
	}
	
	public FacturaModelo(PedidoModelo pedido) {

		this.estado = pedido.getEstado();
		this.id = pedido.getId();
		this.direccion = pedido.getDireccion();
		this.fecha = pedido.getFecha();
		this.valorDomicilio=(estado.equals("activo"))?5000:0;

		this.valorTotal=(pedido.getValor()>100000)?(pedido.getValor()*(1+iva))
				:(pedido.getValor()*(1+iva)+valorDomicilio);
	}
	
	public FacturaModelo(String estado, int id, String direccion, String fecha, double iva, double valorDomicilio,double valorTotal) {

		this.estado = estado;
		this.id = id;
		this.direccion = direccion;
		this.fecha = fecha;
		this.iva = iva;
		this.valorDomicilio = valorDomicilio;
		this.valorTotal = valorTotal;
	
	}


	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.valorDomicilio=(estado.equals("activo"))?5000:0;
		this.estado = estado;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(double valor) {
		this.valorTotal=(valor>100000)?(valor*(1+iva)):(valor*(1+iva)+valorDomicilio);
	}
	
	
}
