package com.enaldo.accenture.retoback.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.enaldo.accenture.retoback.model.FacturaModelo;
import com.enaldo.accenture.retoback.model.PedidoModelo;
import com.enaldo.accenture.retoback.model.RepuestaModelo;
import com.enaldo.accenture.retoback.repository.PedidoRepositorio;

@Service("servicio")
public class PedidoServicio {
	@Autowired
	@Qualifier("repositorio")
	private PedidoRepositorio repositorio;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public RepuestaModelo obtenerFacturas(){
		List<FacturaModelo> facturas = new ArrayList<FacturaModelo>();
		for(PedidoModelo pedido:repositorio.getPedidos()) {
			facturas.add(new FacturaModelo(pedido));
		}
		if (facturas.isEmpty()) {
			return  new RepuestaModelo("No se encontraron resultados.",facturas);
		}
		else {
			return  new RepuestaModelo("Consulta realizada correctamente.",facturas);
		}
	}
	
	public RepuestaModelo obtenerPorIdyDireccion(int id, String direccion) {
		if (repositorio.existsByIdAndDirection(id, direccion)) {
			return  new RepuestaModelo("Consulta realizada correctamente.",
					new FacturaModelo(repositorio.findByIdAndDirection(id, direccion)));
		}else {
			return  new RepuestaModelo("No se encontraron resultados para el id: "+id
					+"y la direccion: "+direccion+".",new FacturaModelo());
		}
	}
	public RepuestaModelo obtenerPorId(int id){
		List<FacturaModelo> facturas = new ArrayList<FacturaModelo>();
		for(PedidoModelo pedido:repositorio.findById(id)) {
			facturas.add(new FacturaModelo(pedido));
		}
		if (facturas.isEmpty()) {
			return  new RepuestaModelo("No se encontraron resultados para el id: "+id+".",facturas);
		}
		else {
			return  new RepuestaModelo("Consulta realizada correctamente.",facturas);
		}
	}
	public RepuestaModelo obtenerPorDireccion(String direccion){
		List<FacturaModelo> facturas = new ArrayList<FacturaModelo>();
		for(PedidoModelo pedido:repositorio.findByDirection(direccion)) {
			facturas.add(new FacturaModelo(pedido));
		}
		if (facturas.isEmpty()) {
			return  new RepuestaModelo("No se encontraron resultados para la dirección: "+direccion+".",facturas);
		}
		else {
			return  new RepuestaModelo("Consulta realizada correctamente.",facturas);
		}
	}
	
	public RepuestaModelo actualizarPedido(PedidoModelo modelo) {
		if (repositorio.existsByIdAndDirection(modelo.getId(), modelo.getDireccion())) {
			int index = repositorio.findIndexByIdAndDirection(modelo.getId(), modelo.getDireccion());
			PedidoModelo pedido = repositorio.findByIndex(index);
			try {
				Date initialdate = dateFormat.parse(pedido.getFecha());
				Date finaldate = dateFormat.parse(modelo.getFecha());
				long diferencia = (finaldate.getTime() - initialdate.getTime())/(60*60*1000);
				if(diferencia<5 && diferencia>=0) {
					repositorio.actualizar(modelo, index);
					return new RepuestaModelo("Pedido actualizado correctamente"
							,new FacturaModelo(repositorio.findByIndex(index)));
				}else if(diferencia>=5){
					return new RepuestaModelo("Error: pedido creado hace más de 5 horas. No se puede actualizar "
							+ "el pedido",new FacturaModelo());
				}else {
					return new RepuestaModelo("Error: fecha no válida, la hora no puede ser menor que la hora de "
							+ "creación.",new FacturaModelo());
				}
			}catch (Exception e){
				return new RepuestaModelo("Error: el formato de fecha no es válido.",new FacturaModelo());
			}
		}else if(modelo.getDireccion().equals(null)) {
			return new RepuestaModelo("Error: Ingrese un valor de dirección válida.",new FacturaModelo());
		}
		else {
			return new RepuestaModelo("Error: el pedido no existe en la base de datos.",new FacturaModelo());
		}
	}
	
	public RepuestaModelo crearPedido(PedidoModelo modelo) {
		if (!(repositorio.existsByIdAndDirection(modelo.getId(), modelo.getDireccion()))) {
			try {
				dateFormat.parse(modelo.getFecha());
				repositorio.guardar(modelo);
				return new RepuestaModelo("Pedido creado correctamente.",new FacturaModelo(modelo));
			}catch(Exception e) {
				return new RepuestaModelo("Error: el formato de fecha no es válido.",new FacturaModelo());
			}
		}
		else{
			return new RepuestaModelo("Error: Ya existe un pedido por la persona con id: "+modelo.getId()
			+" y dirección: "+modelo.getDireccion()+".",new FacturaModelo(
					repositorio.findByIdAndDirection(modelo.getId(), modelo.getDireccion())));
		}
	}
	
	public RepuestaModelo cancelarPedido(PedidoModelo modelo) {
		
		if (repositorio.existsByIdAndDirection(modelo.getId(), modelo.getDireccion())) {
			
			int index = repositorio.findIndexByIdAndDirection(modelo.getId(), modelo.getDireccion());
			PedidoModelo pedido = repositorio.findByIndex(index);
			
			try {
				
				Date initialdate = dateFormat.parse(pedido.getFecha());
				Date finaldate = dateFormat.parse(modelo.getFecha());
				long diferencia = (finaldate.getTime() - initialdate.getTime())/(60*60*1000);
				
				if(diferencia<12 && diferencia>=0) {
					
					repositorio.cancelar(modelo.getId(), modelo.getDireccion(),0);
					RepuestaModelo respuesta = new RepuestaModelo("Pedido cancelado correctamente."
							+ " Sin recargo adicional. El pedido será eliminado de la base de datos.",
							new FacturaModelo(repositorio.findByIndex(index)));
					repositorio.eliminar(modelo.getId(), modelo.getDireccion());
					return respuesta;
					
				}
				
				else if(diferencia>=12){
					repositorio.cancelar(modelo.getId(), modelo.getDireccion(),0.11);
					RepuestaModelo respuesta = new RepuestaModelo("Pedido cancelado correctamente."
							+ " Con un recargo del 10%. El pedido será eliminado de la base de datos.",
							new FacturaModelo(repositorio.findByIndex(index)));
					repositorio.eliminar(modelo.getId(), modelo.getDireccion());
					return respuesta;
				}
				
				else {
					return new RepuestaModelo("Error: fecha no válida, la hora no puede ser menor que"
							+ " la hora de creación.",new FacturaModelo());
				}
			}
			
			catch (Exception e){
				return new RepuestaModelo("Error: el formato de fecha no es válido.",new FacturaModelo());
			}
			
		}
		
		else if(modelo.getDireccion().equals(null)) {
			return new RepuestaModelo("Error: Ingrese un valor de dirección válida.",new FacturaModelo());
		}
		
		else {
			return new RepuestaModelo("Error: el pedido no existe en la base de datos.",new FacturaModelo());
		}
	}
	
}
