package br.com.algotrade.negocio;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.algotrade.dao.interfaces.AtivoDAO;
import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.testes.AgendadorThreadTest;
import br.com.algotrade.util.HttpClientUtil;


@Service
public class AgendadorForex {
	
	@Autowired
	private AtivoDAO ativoDAO;
	
	//http://www.forexpros.com.pt/indices/bovespa-components
	private static final String SITE_FOREX = "http://br.investing.com/indices/bovespa-components";
	@Autowired
	private AtivoBuilderForex ativoBuilderForex;
	//1 em 1 m
	//@Scheduled(fixedRate = 1 * 15 * 1000) LIBERAR PARA AGENDAMENTO
	@Transactional
	public void bateNoSite() {
		System.out.println("Entrou? ("+ new Date() + ") "  + AgendadorThreadTest.LIBERADO);

		if(AgendadorThreadTest.LIBERADO) {
			System.out.println("#### " + Thread.currentThread().getName());
			
			String responseBody;
			try {
				responseBody = HttpClientUtil.requestNoSite(SITE_FOREX);
				List<Ativo> ativos = ativoBuilderForex.constroiAtivos(responseBody);
				for (Ativo a : ativos) {
					ativoDAO.persist(a);
				}
				System.out.println("****Bateu no site e persistu -> ("+ new Date() + ")");
			} catch (Exception e) {
				System.err.println("#### Erro AGENDAMENTO");
				e.printStackTrace();
			}
		}
	}

}
