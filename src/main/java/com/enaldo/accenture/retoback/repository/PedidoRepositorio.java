package com.enaldo.accenture.retoback.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.enaldo.accenture.retoback.model.PedidoModelo;

@Repository("repositorio")
public class PedidoRepositorio {
	
	
	private List<PedidoModelo> pedidos= new ArrayList<PedidoModelo>(); 
	
	//Funcion que retorna todos los pedidos 
	public List<PedidoModelo> getPedidos() {

		return pedidos;
	}
	
	//Funcion que busca y retorna los pedidos dependiendo de su id y direccion
	public PedidoModelo findByIdAndDirection(int id,String direccion) {

		PedidoModelo pedido =null;

		for(PedidoModelo modelo:pedidos) {
			if(modelo.getDireccion().equals(direccion) && modelo.getId()==id) pedido= modelo;
		}

		return pedido;
	}
	
	//Funcion que busca y retorna los pedidos dependiendo de su id
	public  List<PedidoModelo> findById(int id) {

		List<PedidoModelo> modelos =new ArrayList<PedidoModelo>();

		for(PedidoModelo modelo:pedidos) {
			if(modelo.getId()==id) modelos.add(modelo);
		}
		
		return modelos;
	}
	
	//Funcion que busca y retorna los pedidos dependiendo de su direccion
	public  List<PedidoModelo> findByDirection(String direccion) {

		List<PedidoModelo> modelos =new ArrayList<PedidoModelo>();

		for(PedidoModelo modelo:pedidos) {

			if(modelo.getDireccion().equals(direccion)){ 

				modelos.add(modelo);
			}
		}

		return modelos;
	} 
	
	//Funcion que busca y retorna un pedido dependiendo de su indice en la lista de datos
	public PedidoModelo findByIndex(int index) {

		return pedidos.get(index);		
	}
	
	//Funcion que busca y retorna el indice de un pedido dependiendo de su id y su direccion
	public int findIndexByIdAndDirection(int id,String direccion) {

		int index=-1;

		for(PedidoModelo modelo:pedidos) {

			if( modelo.getId()==id && direccion.equals(modelo.getDireccion())) {

				index=pedidos.indexOf(modelo);
			}
		}
		
		return index;
	}
	
	//Funcion que comprueba si un pedido especificado existe en la lista de datos
	public boolean existsByIdAndDirection(int id,String direccion) {

		if (!pedidos.isEmpty()) {

			boolean value=(findByIdAndDirection(id,direccion)!=null);
			return value;
		}

		else return false;
	}
	
	//Funcion que guarda un pedido nuevo
	public void guardar(PedidoModelo pedido) {

		pedidos.add(pedido);
	}

	//Funcion para actualizar pedidos
	public void actualizar(PedidoModelo pedido,int index) {

		pedidos.set(index, pedido);
	}

	//Funcion que cancela un pedido dentro de la lista de datos (Se usa antes de eliminar un pedido)
	public void cancelar(int id,String direccion,double descuento) {

		for(PedidoModelo modelo:pedidos) {

			if(modelo.getDireccion().equals(direccion) && modelo.getId()==id) {

				modelo.setEstado("cancelado");
				modelo.setValor(descuento*modelo.getValor());

			}
		}
	}

	//Funcion que elimina un pedido de la lista de datos (se usa despues de cancelar un pedido)
	public void eliminar(int id,String direccion) {

		pedidos.removeIf(pedido -> (pedido.getId()==id && pedido.getDireccion().equals(direccion)));

	}
}
