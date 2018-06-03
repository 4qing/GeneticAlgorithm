package ga;

import lombok.Getter;
import lombok.Setter;

public class Individual implements Comparable<Individual> {
	@Getter @Setter
	private int[] chromosome;//Ⱦɫ�����
	
	@Getter @Setter
	private double fitness = -1;//Ⱦɫ�����Ӧ��
	
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
	//�޸�Ⱦɫ���ϵĻ���
	public void setGene(int offset, int gene) {
		// TODO Auto-generated method stub
		this.chromosome[offset] = gene;
	}
	//�õ�Ⱦɫ���ϵĻ���
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
