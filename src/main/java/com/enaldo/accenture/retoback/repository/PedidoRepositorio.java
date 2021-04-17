package com.enaldo.accenture.retoback.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.enaldo.accenture.retoback.model.PedidoModelo;

@Repository("repositorio")
public class PedidoRepositorio {
	
	
	private List<PedidoModelo> pedidos= new ArrayList<PedidoModelo>(); 
	
	public List<PedidoModelo> getPedidos() {
		return pedidos;
	}
	

	public PedidoModelo findByIdAndDirection(int id,String direccion) {
		PedidoModelo pedido =null;
		for(PedidoModelo modelo:pedidos) {
			if(modelo.getDireccion().equals(direccion) && modelo.getId()==id) pedido= modelo;
		}
		return pedido;
	}
	
	public  List<PedidoModelo> findById(int id) {
		List<PedidoModelo> modelos =new ArrayList<PedidoModelo>();
		for(PedidoModelo modelo:pedidos) {
			if(modelo.getId()==id) modelos.add(modelo);
		}
		return modelos;
	}
	
	public  List<PedidoModelo> findByDirection(String direccion) {
		List<PedidoModelo> modelos =new ArrayList<PedidoModelo>();
		for(PedidoModelo modelo:pedidos) {
			if(modelo.getDireccion().equals(direccion)) modelos.add(modelo);
		}
		return modelos;
	} 
	
	public PedidoModelo findByIndex(int index) {
		return pedidos.get(index);		
	}
	
	public int findIndexByIdAndDirection(int id,String direccion) {
		int index=-1;
		for(PedidoModelo modelo:pedidos) {
			if( modelo.getId()==id && direccion.equals(modelo.getDireccion())) index=pedidos.indexOf(modelo);
		}
		return index;
	}
	
	public boolean existsByIdAndDirection(int id,String direccion) {
		if (!pedidos.isEmpty()) {
			boolean value=(findByIdAndDirection(id,direccion)!=null);
			return value;
		}
		else return false;
	}
	
	public void guardar(PedidoModelo pedido) {
		pedidos.add(pedido);
	}
	public void actualizar(PedidoModelo pedido,int index) {
		pedidos.set(index, pedido);
	}
	public void cancelar(int id,String direccion,double descuento) {
		for(PedidoModelo modelo:pedidos) {
			if(modelo.getDireccion().equals(direccion) && modelo.getId()==id) {
				modelo.setEstado("cancelado");
				modelo.setValor(descuento*modelo.getValor());
			}
		}
	}
	public void eliminar(int id,String direccion) {
		pedidos.removeIf(pedido -> (pedido.getId()==id && pedido.getDireccion().equals(direccion)));
	}
}
