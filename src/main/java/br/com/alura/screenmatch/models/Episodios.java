package br.com.alura.screenmatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodios {
	private int temporada;
	private String titulo;
	private int numeroEpisodio;
	private double avaliacao;
	private LocalDate dataLancamento;

	public Episodios(int numeroTemporada, DadosEpisodios dadosEpisodios) {
		this.temporada = numeroTemporada;
		this.titulo = dadosEpisodios.titulo();
		this.numeroEpisodio = dadosEpisodios.numero();

		try {
			this.avaliacao = Double.valueOf(dadosEpisodios.avaliacao());
		} catch (NumberFormatException ex) {
			this.avaliacao = 0.0;
		}

		try {
			this.dataLancamento = LocalDate.parse(dadosEpisodios.dataLancamento());
		} catch (DateTimeParseException ex) {
			this.dataLancamento = null;
		}
	}

	public int getTemporada() {
		return temporada;
	}

	public void setTemporada(int temporada) {
		this.temporada = temporada;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getNumeroEpisodio() {
		return numeroEpisodio;
	}

	public void setNumeroEpisodio(int numeroEpisodio) {
		this.numeroEpisodio = numeroEpisodio;
	}

	public double getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(double avaliacao) {
		this.avaliacao = avaliacao;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	@Override
	public String toString() {
		return "temporada=" + temporada +
				", titulo='" + titulo + '\'' +
				", numeroEpisodio=" + numeroEpisodio +
				", avaliacao=" + avaliacao +
				", dataLancamento=" + dataLancamento;
	}
}
