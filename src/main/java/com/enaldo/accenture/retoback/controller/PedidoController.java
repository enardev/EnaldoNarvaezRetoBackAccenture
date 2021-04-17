package com.enaldo.accenture.retoback.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enaldo.accenture.retoback.model.FacturaModelo;
import com.enaldo.accenture.retoback.model.PedidoModelo;
import com.enaldo.accenture.retoback.model.RepuestaModelo;
import com.enaldo.accenture.retoback.service.PedidoServicio;

@RestController
@RequestMapping("/v1")
public class PedidoController {
	@Autowired
	@Qualifier("servicio")
	public PedidoServicio servicio;
	
	@PostMapping("/crearpedido")
	public RepuestaModelo crearPedido(@RequestBody @Valid PedidoModelo pedido) {
		return servicio.crearPedido(pedido);
	}
	
	@GetMapping("/obtenertodos")
	public RepuestaModelo obtenerPedidos(){
		return servicio.obtenerFacturas();
	}
	
	@PostMapping("/obtenerporidydireccion")
	public RepuestaModelo obtenerFacturaPorIDyDireccion(@RequestBody @Valid PedidoModelo pedido){
		return servicio.obtenerPorIdyDireccion(pedido.getId(),pedido.getDireccion());
	}
	
	@PostMapping("/obtenerporid")
	public RepuestaModelo obtenerFacturaPorID(@RequestBody @Valid PedidoModelo pedido){
		return servicio.obtenerPorId(pedido.getId());
	}
	
	@PostMapping("/obtenerpordireccion")
	public RepuestaModelo obtenerFacturaPorDireccion(@RequestBody @Valid PedidoModelo pedido){
		return servicio.obtenerPorDireccion(pedido.getDireccion());
	}	
	
	@PutMapping("/actualizarpedido")
	public RepuestaModelo actualizarPedido(@RequestBody @Valid PedidoModelo pedido) {
		return servicio.actualizarPedido(pedido);
	}
	
	@DeleteMapping("/eliminarpedido")
	public RepuestaModelo eliminarPedido(@RequestBody @Valid PedidoModelo pedido) {
		return servicio.cancelarPedido(pedido);
	}
	
	@GetMapping("/ejecutarprueba")
	public RepuestaModelo prueba() {
		List<PedidoModelo> pedidosejemplo = new ArrayList<PedidoModelo>();
		List<FacturaModelo> facturas = new ArrayList<FacturaModelo>();
		pedidosejemplo.add(new PedidoModelo(10001,"Cl. 32 #24-32",92000,"16/04/2021 12:04"));
		pedidosejemplo.add(new PedidoModelo(10002,"Cl. 31 #14-19",58000,"17/04/2021 7:32"));
		pedidosejemplo.add(new PedidoModelo(10003,"Cl. 11 #6-28",136000,"17/04/2021 9:43"));
		pedidosejemplo.add(new PedidoModelo(10001,"Cl. 29 #15-45",73000,"17/04/2021 16:22"));
		for(PedidoModelo pedido:pedidosejemplo) {
			crearPedido(pedido);
			facturas.add(new FacturaModelo(pedido));
		}		 
		return new RepuestaModelo("Lista de prueba cargada exitosamente.",facturas);
	}
}
