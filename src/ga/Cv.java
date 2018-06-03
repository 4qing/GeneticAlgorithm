package ga;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Cv {
	private Mat mat;
	private JFrame frame;
	private int[] count = new int[256];
	public Mat read(String path) {
		Mat mat1 = Imgcodecs.imread(path,Imgcodecs.CV_LOAD_IMAGE_COLOR);
		this.mat = new Mat();
		Imgproc.cvtColor(mat1, this.mat, Imgproc.COLOR_RGB2GRAY);
		
		for(int i=0;i<this.mat.height();i++) {
			for(int j= 0;j<this.mat.width();j++) {
				count[(int) mat.get(i, j)[0]]++;
			}
		}
		return mat;
	}
	public void show(Mat mat) {
		frame = new JFrame("show");
		JLabel lbl = new JLabel("nel label");
		lbl.setBounds(0,50,500,400);
		frame.getContentPane().add(lbl);
		
	}
	
	public int[] getCount() {
		
		return this.count;
	}
	
	
}
