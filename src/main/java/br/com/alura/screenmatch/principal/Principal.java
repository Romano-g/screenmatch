package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.models.DadosEpisodios;
import br.com.alura.screenmatch.models.DadosSerie;
import br.com.alura.screenmatch.models.DadosTemporada;
import br.com.alura.screenmatch.models.Episodios;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
	private Scanner leitura = new Scanner(System.in);
	private ConsumoApi consumoApi = new ConsumoApi();
	private ConverteDados converteDados = new ConverteDados();

	private final String ENDERECO = "http://omdbapi.com/?t=";
	private final String API_KEY = "&apikey=6498ae93";

	public void exibeMenu () {
		System.out.println("\nDigite o nome da série para busca:");
		var nomeSerie = leitura.nextLine();

		var json = consumoApi.obterDados(ENDERECO +
				nomeSerie.replace(" ", "+") +
				API_KEY);

		DadosSerie dados = converteDados.obterDados(json, DadosSerie.class);
		System.out.println(dados);


		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTemporadas(); i++) {
			var json2 = consumoApi.obterDados(ENDERECO +
							nomeSerie.replace(" ", "+") +
							"&season=" +
							i + API_KEY);

			DadosTemporada dadosTemporada = (converteDados.
					obterDados(json2, DadosTemporada.class));

			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);

		temporadas.forEach(t -> t.episodios().
				forEach(e -> System.out.println(e.titulo())));


		List<DadosEpisodios> dadosEpisodios = temporadas.stream()
				.flatMap(t -> t.episodios().stream())
				.collect(Collectors.toList());


//		System.out.println("\nTop 10 episódios:");
//		dadosEpisodios.stream()
//				.filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//				.peek(e -> System.out.println("Primeiro filtro(avaliacao != N/A) " + e))
//				.sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
//				.peek(e -> System.out.println("Odenação " + e))
//				.limit(10)
//				.map(e -> e.titulo().toUpperCase())
//				.forEach(System.out::println);

		List<Episodios> episodios = temporadas.stream()
				.flatMap(t -> t.episodios().stream()
						.map(d -> new Episodios(t.numero(), d))
				)
				.collect(Collectors.toList());

		episodios.forEach(System.out::println);

//		System.out.println("\nDigite um ep para pesquisa:");
//		var trechoTitulo = leitura.nextLine();
//
//		Optional<Episodios> episodioBuscado = episodios.stream()
//				.filter(e -> e.getTitulo()
//						.toUpperCase()
//						.contains(trechoTitulo.toUpperCase()))
//				.findFirst();
//
//		if (episodioBuscado.isPresent()) {
//			System.out.println("\nEpisódio encontrado!");
//			System.out.println("Episódio: " + episodioBuscado.get().getTitulo());
//			System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//		} else {
//			System.out.println("\nNão foi possível encontrar o episódio!");
//		}

//		System.out.println("\nA partir de que ano deseja ver os episódios?");
//		var ano = leitura.nextInt();
//		leitura.nextLine();
//
//		LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//		episodios.stream()
//				.filter(e -> e.getDataLancamento() != null &&
//								e.getDataLancamento().isAfter(dataBusca))
//				.forEach(e -> System.out.println(
//						"Temporada: " + e.getTemporada() +
//								", Episódio: " + e.getTitulo() +
//								", Data lançamento: " + e.getDataLancamento().format(formatador)
//				));

		Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
				.filter(e -> e.getAvaliacao() > 0.0)
				.collect(Collectors.groupingBy(Episodios::getTemporada,
						Collectors.averagingDouble(Episodios::getAvaliacao)));

		System.out.println("\n" + avaliacoesPorTemporada);

		DoubleSummaryStatistics est = episodios.stream()
				.filter(e -> e.getAvaliacao() > 0.0)
				.collect(Collectors.summarizingDouble(Episodios::getAvaliacao));

		System.out.println("\nNúmero de avaliações: " + est.getCount());
		System.out.println("Média: " + est.getAverage());
		System.out.println("Melhor nota: " + est.getMax());
		System.out.println("Pior nota: " + est.getMin());
	}
}
