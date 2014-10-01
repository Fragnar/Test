import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JFrame;


public class Drive extends JFrame{
	
	private Robot robot;
	private BufferedImage loadedImage;
	private BufferedImage screenCap;
	
	public static void main(String[] args) throws Exception{
		new Drive();
	}
	
	public Drive() throws Exception{
		robot = new Robot();
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		loadedImage = loadImage("output.dat");
		screenCap = robot.createScreenCapture(
				new Rectangle(0, 0, (int) screenDim.getWidth(), (int) screenDim.getHeight()));
		int fitX = 0, fitY = 0;
		for(int i = 0; i < screenCap.getHeight(); i++){
			for(int j = 0; j < screenCap.getWidth(); j++){
				if(checkIfFit(j, i)){
					fitX = j;
					fitY = i;
				}
			}
		}
		
		robot.mouseMove(fitX + 10, fitY + 5);
		
	}
	
	private boolean checkIfFit(int x, int y){
		for(int i = 0; i < loadedImage.getHeight(); i++){
			for(int j = 0; j < loadedImage.getWidth(); j++){
				if(loadedImage.getRGB(j, i) != screenCap.getRGB(x + j, y + i)){
					return false;
				}
			}
		}
		return true;
	}
	
	private void saveImage(String file, BufferedImage image) throws Exception{
		DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
		for(int i = 0; i < image.getHeight(); i++){
			for(int j = 0; j < image.getWidth(); j++){
				out.writeInt(image.getRGB(j, i));
			}
		}
	}
	
	private BufferedImage loadImage(String file) throws Exception{
		BufferedImage image = new BufferedImage(100, 20, 1);
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		for(int i = 0; i < image.getHeight(); i++){
			for(int j = 0; j < image.getWidth(); j++){
				image.setRGB(j, i, in.readInt());
			}
		}
		return image;
	}
}
