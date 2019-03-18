package algoritmo;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jfree.ui.RefineryUtilities;







public class AlgoritmoGeneticoApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		List<Produto> produtos = new ArrayList<>();
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/produtos","root","ColocarPassword");
		Statement consulta = con.createStatement();
		ResultSet rs = consulta.executeQuery("select nome, valor, espaco, quantidade from produtos");
		while (rs.next()) {
			for (int i = 0; i < rs.getInt("quantidade"); i++) {
				//System.out.println("Nome: "+ rs.getString("nome"));
				produtos.add(new Produto(rs.getString("nome"), rs.getDouble("espaco"), rs.getDouble("valor")));
				
			}
		}
		
		
		/*
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
		*/

		List<Double> espacos = new ArrayList<>();
		List<Double> valores = new ArrayList<>();
		List<String> nomes = new ArrayList<>();
		
		for (Produto produto : produtos) {
			espacos.add(produto.getEspaco());
			valores.add(produto.getValor());
			nomes.add(produto.getNome());
		}

		/*
		Individuo individuo1 = new Individuo(espacos, valores, 3.0);
		Individuo individuo2 = new Individuo(espacos, valores, 2.0);

		System.out.println("Espacos: " + individuo1.getEspacos());
		System.out.println("Valores: " + individuo1.getValores());
		System.out.println("Cromossoma: " + individuo1.getCromossomo());

		System.out.println("\nComponentes da Carga");
		for (int i = 0; i < produtos.size(); i++) {
			if (individuo1.getCromossomo().get(i) == "1") {
				System.out.println("Nome: " + produtos.get(i).getNome() + ", R$: " + produtos.get(i).getValor());
			}
		}
		
		individuo1.avaliacao();
		System.out.println("Nota: "+individuo1.getNotaAvalicao());
		System.out.println("Espa�o usado: "+individuo1.getEspacoUsado());
		
		individuo2.avaliacao();
		System.out.println("\nNota: "+individuo2.getNotaAvalicao());
		System.out.println("Espa�o usado: "+individuo2.getEspacoUsado());
		
		individuo1.mutacao(0.1);
		individuo2.mutacao(0.2);
		*/
		
		Double limite = 3.0;
		int tamanhoPopulacao = 20;
		Double taxaMutacao = 0.05;
		int numeroGeracoes = 700;
		AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanhoPopulacao);
		List<String> resultado = ag.resolver(taxaMutacao, numeroGeracoes, espacos, valores, limite);
		for (int i = 0; i < produtos.size(); i++) {
			if (resultado.get(i).equals("1")) {
				System.out.println("Nome: "+ produtos.get(i).getNome());
			}
		}
		
		Grafico g = new Grafico("Algoritmo gen�tico", "Evolu��o das solu��es", ag.getMelhoresCromossomos());
		g.pack();
		RefineryUtilities.centerFrameOnScreen(g);
		g.setVisible(true);
		
		/*
		ag.inicializaPopulacao(espacos, valores, limite);
		
		for (Individuo individuo : ag.getPopulacao()) {
			individuo.avaliacao();
		}
		
		ag.ordenaPopulacao();
		ag.melhorIndividuo(ag.getPopulacao().get(0));	
		Double soma = ag.somaAvaliacoes();
		Double probabilidadeMutacao = 0.01;
		List <Individuo> novaPopulacao = new ArrayList<>();
		for (int i = 0; i < ag.getPopulacao().size() / 2; i++) {
			int pai1 = ag.selecionaPai(soma);
			int pai2 = ag.selecionaPai(soma);
			
			List<Individuo> filhos =ag.getPopulacao().get(pai1).crossover(ag.getPopulacao().get(pai2));
			novaPopulacao.add(filhos.get(0).mutacao(probabilidadeMutacao));
			novaPopulacao.add(filhos.get(1).mutacao(probabilidadeMutacao));
		}
		ag.setPopulacao(novaPopulacao);
		for (Individuo individuo : ag.getPopulacao()) {
			individuo.avaliacao();
		}
		ag.ordenaPopulacao();
		ag.melhorIndividuo(ag.getPopulacao().get(0));
		soma = ag.somaAvaliacoes();
		System.out.println("Melhor: "+ag.getMelhorSolucao().getCromossomo()+
				" Valor: "+ag.getMelhorSolucao().getNotaAvalicao());
		
		
		
		
		System.out.println("Melhor solu��o para o problema: "
					+ ag.getMelhorSolucao().getCromossomo() +
					"\nNota: "+ag.getMelhorSolucao().getNotaAvalicao());
		
		
		for (int i = 0; i < ag.getTamanhoPopulacao(); i++) {
			System.out.println("*** Individuo "+ i +"****\nEspa�os = " +
								ag.getPopulacao().get(i).getEspacos()+
								"\nValores = "+ag.getPopulacao().get(i).getValores()+
								"\nCromossomo = "+ag.getPopulacao().get(i).getCromossomo()+
								"\nNota = "+ag.getPopulacao().get(i).getNotaAvalicao());
		}
		*/

	}

}
