package br.com.alura.screenmatch;

import br.com.alura.screenmatch.models.DadosSerie;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://omdbapi.com/?t=gilmore+girls&apikey=6498ae93");

		ConverteDados converteDados = new ConverteDados();

		DadosSerie dados = converteDados.obterDados(json, DadosSerie.class);

		System.out.println("\n" + json);
		System.out.println("\n" + dados);
	}
}
