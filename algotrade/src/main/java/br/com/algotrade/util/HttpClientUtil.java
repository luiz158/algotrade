package br.com.algotrade.util;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import br.com.algotrade.exceptions.ErroInternoException;

public class HttpClientUtil {

	static int i = 0;

	public static String requestNoSite(String site) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		String responseBody = null;
		HttpGet httpget = new HttpGet(site);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {

			System.out.println("#INFO: executando request no site: "
					+ httpget.getURI());

			switch (i) {
			case 0:
				httpget.setHeader(
						"User-Agent",
						"User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:12.0) Gecko/20100101 Firefox/12.0");
				httpget.setHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				httpget.setHeader("Content-Type", "text/html; charset=UTF-8");
				httpget.setHeader("Keep-Alive", "115");
				i++;
				break;
			case 1:
				httpget.setHeader(
						"User-Agent",
						"User-Agent:  Mozilla/5.0 (Macintosh; U; Intel Mac OS X; en) AppleWebKit/418.9 (KHTML, like Gecko) Safari/419.3");
				httpget.setHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				httpget.setHeader("Content-Type", "text/html; charset=UTF-8");
				httpget.setHeader("Keep-Alive", "115");
				i++;
				break;
			case 2:
				httpget.setHeader("User-Agent",
						"User-Agent:  Opera/9.27 (Windows NT 5.1; U; en)");
				httpget.setHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				httpget.setHeader("Content-Type", "text/html; charset=UTF-8");
				httpget.setHeader("Keep-Alive", "115");
				i = 0;
				break;
			default:
				throw new ErroInternoException("ERRO", "Erro nos headers");
			}

			// Cria um response handler
			responseBody = httpclient.execute(httpget, responseHandler);
			System.out.println("#INFO: executou request no site: "
					+ httpget.getURI());
		} catch (HttpHostConnectException e) {
			System.err.println("Conexao Recusada, efetuando segunda e ultima tentativa");
			responseBody = httpclient.execute(httpget, responseHandler);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		//System.out.println(responseBody);
		return responseBody;
	}
}
