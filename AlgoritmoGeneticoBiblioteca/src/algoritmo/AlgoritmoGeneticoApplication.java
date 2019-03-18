package algoritmo;

import org.jfree.ui.RefineryUtilities;
import org.jgap.InvalidConfigurationException;

public class AlgoritmoGeneticoApplication {

	public static void main(String[] args) throws InvalidConfigurationException {

		AlgoritmoGenetico ag = new AlgoritmoGenetico();
		ag.carregar();
		ag.procurarMelhorSolucao();

		int geracao = 0;
		for (int i = 0; i < ag.melhoresChromossomos.size(); i++) {
			if (ag.melhor == null) {
				ag.melhor = ag.melhoresChromossomos.get(i);
			} else if (ag.melhor.getFitnessValue() < ag.melhoresChromossomos.get(i).getFitnessValue()) {
				ag.melhor = ag.melhoresChromossomos.get(i);
				geracao = i;
			}
		}
		System.out.println("\nMelhor Solução");
		ag.visualizaGeracao(ag.melhor, geracao);
		
		for (int i = 0; i < ag.produtos.size(); i++) {
			if (ag.melhor.getGene(i).getAllele().toString().equals("1")) {
				System.out.println("Nome: "+ag.produtos.get(i).getNome());
			}
		}
		
		Grafico g  = new Grafico ("Algoritmo Genético", "Evolução das soluções", ag.melhoresChromossomos);
		g.pack();
		RefineryUtilities.centerFrameOnScreen(g);
		g.setVisible(true);
	}

}
