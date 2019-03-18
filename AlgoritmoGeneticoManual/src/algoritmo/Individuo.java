package algoritmo;

import java.util.ArrayList;
import java.util.List;

public class Individuo implements Comparable<Individuo> {

	private List<Double> espacos;
	private List<Double> valores;
	private Double limiteEspacos;
	private Double notaAvalicao;
	private Double espacoUsado;
	private int geracao;
	private List<String> cromossomo;

	public Individuo(List<Double> espacos, List<Double> valores, Double limiteEspacos) {
		this.espacos = espacos;
		this.valores = valores;
		this.limiteEspacos = limiteEspacos;
		this.notaAvalicao = 0.0;
		this.geracao = 0;
		this.cromossomo = geracaoCromossomoAleatorio();

	}

	public void avaliacao() {
		double somaValores = 0.0;
		double somaEspacos = 0.0;

		for (int i = 0; i < cromossomo.size(); i++) {
			if (cromossomo.get(i).equals("1")) {
				somaValores += valores.get(i);
				somaEspacos += espacos.get(i);
			}
		}
		if (somaEspacos > limiteEspacos) {
			somaValores = 1.0;
		}
		notaAvalicao = somaValores;
		espacoUsado = somaEspacos;
	}

	public List<Individuo> crossover(Individuo outroIndividuo) {
		int corte = (int) Math.round(Math.random() * cromossomo.size());

		List<String> filho1 = new ArrayList<>();
		filho1.addAll(outroIndividuo.getCromossomo().subList(0, corte));
		filho1.addAll(cromossomo.subList(corte, cromossomo.size()));

		List<String> filho2 = new ArrayList<>();
		filho2.addAll(cromossomo.subList(0, corte));
		filho2.addAll(outroIndividuo.cromossomo.subList(corte, cromossomo.size()));

		List<Individuo> filhos = new ArrayList<>();
		filhos.add(new Individuo(espacos, valores, limiteEspacos));
		filhos.add(new Individuo(espacos, valores, limiteEspacos));

		filhos.get(0).setCromossomo(filho1);
		filhos.get(0).setGeracao(geracao + 1);
		filhos.get(1).setCromossomo(filho2);
		filhos.get(1).setGeracao(geracao + 1);

		return filhos;
	}

	public Individuo mutacao(double taxaMutacao) {
		//System.out.println("Antes da Mutação: " + cromossomo);
		for (int i = 0; i < cromossomo.size(); i++) {
			if (Math.random() < taxaMutacao) {
				if (cromossomo.get(i).equals("0")) {
					cromossomo.set(i, "1");
				} else if (cromossomo.get(i).equals("1")) {
					cromossomo.set(i, "0");
				}
			}
		}
		//System.out.println("Depois da Mutação: " + cromossomo);
		return this;
	}

	public double getEspacoUsado() {
		return espacoUsado;
	}

	public List<Double> getEspacos() {
		return espacos;
	}

	public void setEspacos(List<Double> espacos) {
		this.espacos = espacos;
	}

	public List<Double> getValores() {
		return valores;
	}

	public void setValores(List<Double> valores) {
		this.valores = valores;
	}

	public Double getLimiteEspacos() {
		return limiteEspacos;
	}

	public void setLimiteEspacos(Double limiteEspacos) {
		this.limiteEspacos = limiteEspacos;
	}

	public Double getNotaAvalicao() {
		return notaAvalicao;
	}

	public void setNotaAvalicao(Double notaAvalicao) {
		this.notaAvalicao = notaAvalicao;
	}

	public int getGeracao() {
		return geracao;
	}

	public void setGeracao(int geracao) {
		this.geracao = geracao;
	}

	public List<String> getCromossomo() {
		return cromossomo;
	}

	public void setCromossomo(List<String> cromossomo) {
		this.cromossomo = cromossomo;
	}

	public List<String> geracaoCromossomoAleatorio() {
		List<String> cromossomoAleatorio = new ArrayList<>();
		for (int i = 0; i < espacos.size(); i++) {
			if (Math.random() < 0.5) {
				cromossomoAleatorio.add("0");
			} else {
				cromossomoAleatorio.add("1");
			}
		}
		return cromossomoAleatorio;
	}

	@Override
	public int compareTo(Individuo o) {
		if (notaAvalicao > o.getNotaAvalicao()) {
			return -1;
		} else if (notaAvalicao < o.getNotaAvalicao()) {
			return 1;
		} else {
			return 0;
		}
	}
}
