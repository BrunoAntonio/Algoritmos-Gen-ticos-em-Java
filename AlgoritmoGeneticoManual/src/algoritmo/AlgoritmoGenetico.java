package algoritmo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgoritmoGenetico {

	private int tamanhoPopulacao;
	private List<Individuo> populacao = new ArrayList<>();
	private int geracao;
	private Individuo melhorSolucao;
	private List<Individuo> melhoresCromossomos = new ArrayList<>();

	public AlgoritmoGenetico(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
	}

	public void inicializaPopulacao(List<Double> espacos, List<Double> valores, Double limiteEspacos) {
		for (int i = 0; i < tamanhoPopulacao; i++) {
			populacao.add(new Individuo(espacos, valores, limiteEspacos));
		}
		melhorSolucao = populacao.get(0);
	}

	public void melhorIndividuo(Individuo individuo) {
		if (individuo.getNotaAvalicao() > melhorSolucao.getNotaAvalicao()) {
			melhorSolucao = individuo;
		}
	}

	public Double somaAvaliacoes() {
		Double soma = 0.0;
		for (Individuo individuo : populacao) {
			soma += individuo.getNotaAvalicao();
		}
		return soma;
	}

	public int selecionaPai(Double somaAvaliacoes) {
		int pai = -1;
		Double valorSorteado = Math.random() * somaAvaliacoes;
		Double soma = 0.0;
		int i = 0;
		while (i < populacao.size() && soma < valorSorteado) {
			soma += populacao.get(i).getNotaAvalicao();
			pai += 1;
			i += 1;
		}
		return pai;
	}

	public void visualizaGeracao() {
		Individuo melhor = populacao.get(0);
		melhoresCromossomos.add(melhor);
		System.out.println("G: " + melhor.getGeracao() + " Valor: " + melhor.getNotaAvalicao() + " Espa�o: "
				+ melhor.getEspacoUsado() + " Cromossomo: " + melhor.getCromossomo());
	}

	public List<String> resolver(Double taxaMutacao, int numeroGeracoes, List<Double> espacos, List<Double> valores,
			Double limiteEspacos) {
		inicializaPopulacao(espacos, valores, limiteEspacos);
		for (Individuo individuo : populacao) {
			individuo.avaliacao();
		}
		ordenaPopulacao();
		visualizaGeracao();

		for (int geracao = 0; geracao < numeroGeracoes; geracao++) {
			Double somaAvaliacao = somaAvaliacoes();
			List<Individuo> novaPopulacao = new ArrayList<>();

			for (int i = 0; i < populacao.size() / 2; i++) {
				int pai1 = selecionaPai(somaAvaliacao);
				int pai2 = selecionaPai(somaAvaliacao);

				List<Individuo> filhos = getPopulacao().get(pai1).crossover(getPopulacao().get(pai2));
				novaPopulacao.add(filhos.get(0).mutacao(taxaMutacao));
				novaPopulacao.add(filhos.get(1).mutacao(taxaMutacao));
			}

			setPopulacao(novaPopulacao);
			for (Individuo individuo : getPopulacao()) {
				individuo.avaliacao();
			}
			ordenaPopulacao();
			visualizaGeracao();
			Individuo melhor  = populacao.get(0);
			melhorIndividuo(melhor);
		}

		System.out.println("Melhor solu��o G -> "+ melhorSolucao.getGeracao() +
				" Valor: "+ melhorSolucao.getNotaAvalicao()+
				" Espa�o: "+ melhorSolucao.getEspacoUsado()+
				" Cromossomo: "+ melhorSolucao.getCromossomo());
		return melhorSolucao.getCromossomo();
		
	}

	public void ordenaPopulacao() {
		Collections.sort(populacao);
	}
	
	

	public List<Individuo> getMelhoresCromossomos() {
		return melhoresCromossomos;
	}

	public void setMelhoresCromossomos(List<Individuo> melhoresCromossomos) {
		this.melhoresCromossomos = melhoresCromossomos;
	}

	public int getTamanhoPopulacao() {
		return tamanhoPopulacao;
	}

	public void setTamanhoPopulacao(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
	}

	public List<Individuo> getPopulacao() {
		return populacao;
	}

	public void setPopulacao(List<Individuo> populacao) {
		this.populacao = populacao;
	}

	public int getGeracao() {
		return geracao;
	}

	public void setGeracao(int geracao) {
		this.geracao = geracao;
	}

	public Individuo getMelhorSolucao() {
		return melhorSolucao;
	}

	public void setMelhorSolucao(Individuo melhorSolucao) {
		this.melhorSolucao = melhorSolucao;
	}

}
