package br.com.algotrade.negocio;

import java.util.List;

import br.com.algotrade.entidades.Ativo;

public interface AtivoBuilder {

	List<Ativo> constroiAtivos(String corpoResponse) throws Exception;
}
