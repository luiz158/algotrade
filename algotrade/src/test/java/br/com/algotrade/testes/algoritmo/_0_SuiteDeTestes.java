package br.com.algotrade.testes.algoritmo;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	_1_Normal.class, _2_EstouraRange2oFundo.class, _3_EstouraRange3oFundo.class,
	_4_Ultrapassa2oFundo.class, _5_Ultrapassa3oFundo.class
})
public class _0_SuiteDeTestes {
	
	@BeforeClass 
	public static void antes() {
		
	}
	
}
