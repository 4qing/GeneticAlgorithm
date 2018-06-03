package ga;

import lombok.Getter;
import lombok.Setter;

public class Individual implements Comparable<Individual> {
	@Getter @Setter
	private int[] chromosome;//染色体编码
	
	@Getter @Setter
	private double fitness = -1;//染色体的适应度
	
	public Individual(int[] chromosome) {
		this.chromosome = chromosome;
	}
	public Individual(int length) {
		this.chromosome = new int[length];
		for(int gene=0;gene<length;gene++) {
			if(0.5<Math.random()) {
				this.setGene(gene, 1);
			}else {
				this.setGene(gene, 0);
			}
		}
	}
	//修改染色体上的基因
	public void setGene(int offset, int gene) {
		// TODO Auto-generated method stub
		this.chromosome[offset] = gene;
	}
	//得到染色体上的基因
	public int getGene(int offset) {
		return this.chromosome[offset];
	}
	
	@Override
	public int compareTo(Individual o) {
		double result = this.fitness-o.fitness;
		if(0==result) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		for(int gene = 0;gene<this.chromosome.length;gene++) {
			output.append(this.chromosome[gene]);
		}
		return Integer.valueOf(output.toString(), 2).toString();
	}
	
}
