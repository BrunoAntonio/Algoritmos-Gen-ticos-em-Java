package algoritmo;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jgap.IChromosome;

public class Grafico extends ApplicationFrame{

	private static final long serialVersionUID = 1L;
	
	private List<IChromosome> melhoresCromossomos = new ArrayList<>();
	
	public Grafico(String tituloJanela, String tituloGrafico, List<IChromosome> melhores ) {
		super(tituloJanela);
		this.melhoresCromossomos = melhores;
		JFreeChart graficoLinha = ChartFactory.createLineChart(tituloGrafico, "Geração", "Valor",
				carregarDados(),
				PlotOrientation.VERTICAL,
				true, true, false);
		ChartPanel janelaGrafico = new ChartPanel(graficoLinha);
		janelaGrafico.setPreferredSize(new java.awt.Dimension(800, 600));
		setContentPane(janelaGrafico);
		
	}
	
	
	
	private DefaultCategoryDataset carregarDados() {
		DefaultCategoryDataset dados = new DefaultCategoryDataset();
		for (int i = 0; i < melhoresCromossomos.size(); i++) {
			dados.addValue(melhoresCromossomos.get(i).getFitnessValue(), "Melhor solução", ""+ i);
		}
		return dados;
	}
	
	

}
