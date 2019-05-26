package com.harnina.tienda.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.harnina.tienda.model.Recurseable;
import com.harnina.tienda.model.FuncionProcedureMetodo;

@Component
public class RecursoService {
	
	List <RecursoServiceable> serviceLists;
	
	List<Recurseable> recursos;
	
	@Autowired
	private FuncionProcedureMetodoService funcionProcedureMetodoService;
	
	public RecursoService() {
	}

	public List<Recurseable> getRecursos(long idRecursoEspecifico) {
		comprobarListas();
		List<Recurseable> retorno = new ArrayList<>();
		for (RecursoServiceable servicioRecurso : serviceLists) {
			retorno.addAll(servicioRecurso.getRecursos(idRecursoEspecifico));
		}
		return retorno;
	}

	public List<Recurseable> getRecursos() {
		comprobarListas();
		List<Recurseable> retorno = new ArrayList<>();
		for (RecursoServiceable servicioRecurso : serviceLists) {
			retorno.addAll(servicioRecurso.getRecursos());
		}
		return retorno;
	}
	
	private void comprobarListas() {
		if(this.serviceLists == null) iniciarListaServicios();
		if(this.recursos == null) recargarRecursos();
	}

	private void recargarRecursos() {
		this.recursos = new ArrayList<>();
		for (RecursoServiceable recurso : serviceLists) {
			this.recursos.addAll(recurso.getRecursos());
		}
	}

	private void iniciarListaServicios() {
		this.serviceLists = new ArrayList<>();
		this.serviceLists.add(funcionProcedureMetodoService);
	}
	
	public boolean existRecurso(Recurseable recurso) {
		comprobarListas();
		for (Recurseable recurseable : recursos) {
			if(recurseable.equals(recurso))return true;
		}
		return false;
	}
	
	public void guardarRecurso(Recurseable recurso) {
		if(!existRecurso(recurso))recurso.guardar(this);
	}

	public void borrarRecurso(Recurseable recurso) {
		if(!existRecurso(recurso))recurso.borrar(this);
	}
	
	public void guardarRecurso(FuncionProcedureMetodo funcionProcedureMetodo) {
		if(!existRecurso(funcionProcedureMetodo)){
			funcionProcedureMetodoService.guardarRecurso(funcionProcedureMetodo);
		}
	}

	public Recurseable getRecurso(String idRecurso,String idRecursoEspecifico) {
		for (Recurseable recurseable : recursos) {
			if(recurseable.getIdRecurso() == Long.valueOf(idRecurso).longValue() && 
			recurseable.getIdRecursoEspecifico() == Long.valueOf(idRecursoEspecifico)){
				return recurseable;
			}
		}
		return null;
	}

}
