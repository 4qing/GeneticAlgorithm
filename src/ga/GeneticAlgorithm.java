package ga;

import org.opencv.core.Core;

import lombok.Getter;
import lombok.Setter;

public class GeneticAlgorithm {
	private int populationSize;//��Ⱥ��ģ
	private double mutationRate;//������
	private double crossoverRate;//������
	@Getter @Setter
	private double populationFitness;//��Ⱥ��Ӧ��
	private static Cv c = new Cv();
	Individual[] population;//��Ⱥ
	private double theBestFit=-1;//�����Ӧ��
	private String gene;
	
	public GeneticAlgorithm(int populationSize,double mutationRate,double crossoverRate) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.population= new Individual[populationSize];
	}
	
	//��ʼ����Ⱥ
	public void initPopulation(int chromosomeLength) {
		for(int i =0;i<this.populationSize;i++) {
			population[i]= new Individual(chromosomeLength);
		}
	}
	
	//������Ӧ��
	public double calcFitness(Individual individual) {
		int m = 0;//�����صĻҶ�ֵ
		int a = 0;//ǰ�����ظ���
		int b = 0;//�������ظ���
		double pa = 0;//ǰ�����ص����
		double pb = 0;//�������ص����
		double ua = 0;//ǰ������ֵ��ֵ;
		double ub = 0;//��������ֵ��ֵ
		double u = 0;//�������ؾ�ֵ
		for(int i = 0;i<8;i++) {
			m += individual.getChromosome()[i]*Math.pow(2, 7-i);
		}
		for(int i =0;i<m;i++) {
			a+=c.getCount()[i];
			ua +=i*c.getCount()[i];
		}
		
		for(int i=m;i<256;i++) {
			b+=c.getCount()[i];
			ub +=i*c.getCount()[i];
		}
		pa=(double)a/(a+b);
		pb=(double)b/(a+b);
		u = (ua+ub)/(a+b);
		ua = ua/(a+b);
		ub = ub/(a+b);
		double f = pa*Math.pow(ua-u, 2)+pb*Math.pow(ub-u, 2);
		individual.setFitness(f);
		return f;
	}
	//������Ⱥ����Ӧ��
	public double evalPopulation(Individual[] ina) {
		double populationFitness = 0;
		for(Individual in:ina) {
			populationFitness += calcFitness(in);
			if(in.getFitness()>=this.theBestFit) {
				this.theBestFit=in.getFitness();
				this.gene = in.toString();
			}
		}
		return populationFitness;
	}
	//���̶�ѡ��
	public Individual[] wheel_selection() {
		Individual[] in = new Individual[this.populationSize];
		double[] p = new double[this.populationSize];
		double pt=0;
		for(int i=0;i<this.populationSize;i++) {
			pt +=this.population[i].getFitness()/this.populationFitness;
			p[i] = pt;
		}

		for(int i = 0;i<this.populationSize;i++) {
			double r = Math.random();
			for(int j =0;j<this.populationSize;j++) {
				if(r<p[j]) {
					Individual tmp = new Individual(this.population[j].getChromosome().clone());
					in[i] = tmp;
					break;
				}else {
					Individual tmp = new Individual(this.population[i].getChromosome().clone());
					in[i] = tmp;
				}
			}
		}
//		System.out.println();
//		for(Individual ind:in) {
//			System.out.print(ind+" ");
//		}
//		System.out.println();
//		System.out.println("old:");
//		for(Individual ind:this.population) {
//			System.out.print(ind + " ");
//		}
//		System.out.println();
		double t = this.evalPopulation(in);
		if(t>this.populationFitness) {
			this.populationFitness=t;
			this.population = in.clone();
		}
//		System.out.println("end:");
//		for(Individual ind:this.population) {
//			System.out.print(ind + " ");
//		}
		return this.population;
	}
	//����
	public Individual[] crossPopulation() {
		Individual[] in = this.population;
		for(int i=0;i<this.populationSize-1;i++) {
			if(Math.random()<this.crossoverRate) {
				for(int j=4;j<8;j++) {
					in[i].setGene(j, this.population[i+1].getGene(j));
					this.calcFitness(in[i]);
				}
			}
			
		}
		this.population = in;
		this.populationFitness=this.evalPopulation(in);
		return in;
	}
	//����
	public Individual[] mutationPopulation() {
		Individual[] in = this.population;
		for(int i=0;i<this.populationSize;i++) {
			if(Math.random()<this.mutationRate) {
				int offset = (int) ((Math.random()*100)%8);
				System.out.println(offset);
				int gene = in[i].getGene(offset)==1?0:1;
				Individual t = new Individual(in[i].getChromosome());
				t.setGene(offset, gene);
				in[i] = t;
			}
			
		}
		this.population=in;
		this.populationFitness=this.evalPopulation(in);
		return in;
	}
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		GeneticAlgorithm ga = new GeneticAlgorithm(20, 0.05, 0.95);
		c.read("C:\\Users\\Qing\\Pictures\\Saved Pictures\\test.jpg");
		System.out.println();
		ga.initPopulation(8);
		ga.populationFitness= ga.evalPopulation(ga.population);
		Individual[] test = ga.wheel_selection();
		for(int i=0;i<10000;i++) {
			ga.wheel_selection();
			ga.crossPopulation();
			ga.mutationPopulation();
			for(Individual a:ga.population) {
				System.out.print(a.toString()+" ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println(ga.gene);
	}
}
