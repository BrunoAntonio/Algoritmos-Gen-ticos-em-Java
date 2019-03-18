package algoritmo;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class Avaliacao extends FitnessFunction {

	private static final long serialVersionUID = 1L;

	private final AlgoritmoGenetico algoritmoGenetico;

	public Avaliacao(AlgoritmoGenetico ag) {
		this.algoritmoGenetico = ag;
	}

	@Override
	protected double evaluate(IChromosome cromossomo) {
		Double nota = 0.0;
		Double somaEspacos = 0.0;
		for (int i = 0; i < cromossomo.size(); i++) {
			if (cromossomo.getGene(i).getAllele().toString().equals("1")) {
				nota += algoritmoGenetico.produtos.get(i).getValor();
				somaEspacos += algoritmoGenetico.produtos.get(i).getEspaco();
			}
		}
		if (somaEspacos > algoritmoGenetico.limite) {
			nota = 1.0;
		}
		return nota;
	}

}
