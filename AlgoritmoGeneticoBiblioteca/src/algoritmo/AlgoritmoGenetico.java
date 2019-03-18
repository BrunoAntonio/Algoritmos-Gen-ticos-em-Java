package algoritmo;

import java.util.ArrayList;
import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.StockRandomGenerator;
import org.jgap.impl.SwappingMutationOperator;

public class AlgoritmoGenetico {

	Configuration configuracao;
	int numeroGeracoes = 100;
	Double limite = 3.0;
	int tamanhoPopulacao = 20;
	int taxaMutacao = 100;

	List<IChromosome> melhoresChromossomos = new ArrayList<>();
	List<Produto> produtos = new ArrayList<>();
	IChromosome melhor;

	public void carregar() {
		Produto geleiraDako = new Produto("Geleira Dako", 0.751, 999.90);
		Produto iphone6 = new Produto("Iphone 6", 0.000089, 2911.12);
		Produto tv55 = new Produto("TV 55", 0.400, 4346.99);
		Produto tv50 = new Produto("TV 50", 0.290, 3999.90);
		Produto tv42 = new Produto("TV 42", 0.200, 2999.00);
		Produto notebookDell = new Produto("Notebook Dell", 0.00350, 2499.90);
		Produto ventiladorPanasonic = new Produto("Ventilador Panasonic", 0.496, 199.90);
		Produto microondasElectrolux = new Produto("Microondas Electrolux", 0.042, 308.66);
		Produto microondasLG = new Produto("Microondas LG", 0.0544, 429.90);
		Produto microondasPanasonic = new Produto("Microondas Panasonic", 0.0319, 299.29);
		Produto geladeiraBrastemp = new Produto("Geladeira Brastemp", 0.635, 849.00);
		Produto geladeiraConsul = new Produto("Geladeira Consul", 0.870, 1199.89);
		Produto notebookLenovo = new Produto("Notebook Lenovo", 0.498, 1999.90);
		Produto notebookAsus = new Produto("Notebook Asus", 0.527, 3999.00);

		produtos.add(geleiraDako);
		produtos.add(iphone6);
		produtos.add(tv55);
		produtos.add(tv50);
		produtos.add(tv42);
		produtos.add(notebookDell);
		produtos.add(ventiladorPanasonic);
		produtos.add(microondasElectrolux);
		produtos.add(microondasLG);
		produtos.add(microondasPanasonic);
		produtos.add(geladeiraBrastemp);
		produtos.add(geladeiraConsul);
		produtos.add(notebookLenovo);
		produtos.add(notebookAsus);
	}

	public Double somaEspacos(IChromosome cromossomo) {
		Double soma = 0.0;
		for (int i = 0; i < cromossomo.size(); i++) {
			if (cromossomo.getGene(i).getAllele().toString().equals("1")) {
				soma += produtos.get(i).getEspaco();
			}
		}
		return soma;
	}

	public void visualizaGeracao(IChromosome cromossomo, int geracao) {
		List<String> lista = new ArrayList<>();
		Gene[] genes = cromossomo.getGenes();
		for (int i = 0; i < cromossomo.size(); i++) {
			lista.add(genes[i].getAllele().toString() + " ");
		}
		System.out.println("G: " + geracao + " valor: " + cromossomo.getFitnessValue() + " espa�o: "
				+ somaEspacos(cromossomo) + " cromossomo " + lista);
	}

	public IChromosome criarCromossomo() throws InvalidConfigurationException {
		Gene[] genes = new Gene[produtos.size()];
		for (int i = 0; i < genes.length; i++) {
			genes[i] = new IntegerGene(configuracao, 0, 1);
			genes[i].setAllele(i);
		}
		IChromosome modelo = new Chromosome(configuracao, genes);
		return modelo;
	}

	public FitnessFunction criarFuncaoFitness() {
		return new Avaliacao(this);
	}

	public Configuration criarConfiguracao() throws InvalidConfigurationException {
		Configuration configuracao = new Configuration();
		configuracao.removeNaturalSelectors(true);

		configuracao.addNaturalSelector(new BestChromosomesSelector(configuracao, 0.4), true);
		configuracao.setRandomGenerator(new StockRandomGenerator());
		configuracao.addGeneticOperator(new CrossoverOperator(configuracao));
		configuracao.addGeneticOperator(new SwappingMutationOperator(configuracao, taxaMutacao));
		configuracao.setKeepPopulationSizeConstant(true);
		configuracao.setEventManager(new EventManager());
		configuracao.setFitnessEvaluator(new DefaultFitnessEvaluator());
		return configuracao;

	}

	public void procurarMelhorSolucao() throws InvalidConfigurationException {
		configuracao = criarConfiguracao();
		FitnessFunction funcaoFitness = criarFuncaoFitness();
		configuracao.setFitnessFunction(funcaoFitness);
		IChromosome modeloCromossomo = criarCromossomo();
		configuracao.setSampleChromosome(modeloCromossomo);
		configuracao.setPopulationSize(tamanhoPopulacao);
		IChromosome[] cromossomos = new Chromosome[tamanhoPopulacao];
		for (int i = 0; i < tamanhoPopulacao; i++) {
			cromossomos[i] = criarCromossomo();
		}
		Genotype populacao = new Genotype(configuracao, new Population(configuracao, cromossomos));
		for (int j = 0; j < numeroGeracoes; j++) {
			visualizaGeracao(populacao.getFittestChromosome(), j);
			melhoresChromossomos.add(populacao.getFittestChromosome());
			populacao.evolve();
		}
	}

}
