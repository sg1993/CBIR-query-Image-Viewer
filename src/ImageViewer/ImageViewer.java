package ImageViewer;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageViewer extends javax.swing.JFrame implements Runnable, KeyListener, WindowListener {

	private BufferedImage displayImage; // variable holds the image
	// to be displayed

	private static BufferedImage[] imageList; // list of images to be displayed in in
										// the
	// viewer.

	private static String[] filename;
	private static int numImages = 0, curImageIndex = 0;
	private static int maxHeight = 0, maxWidth = 0;
	private static ImageIcon icon[];
	private static JLabel currentLabel;

	public static void main(String args[]) throws IOException {

		// load the list of images
		// FileInputStream is = new FileInputStream(title);
		BufferedReader br = new BufferedReader(new FileReader(new File(
				args[args.length - 1])));

		filename = new String[10000];
		int i = 0, j = 0;
		String f;

		while ((f = br.readLine()) != null) {
			filename[i++] = f;
		}
		br.close();
		imageList = new BufferedImage[i];
		icon = new ImageIcon[i];
		while (numImages < i) {
			File img = new File(filename[numImages]);
			imageList[numImages] = ImageIO.read(new FileImageInputStream(img));
			// System.out.println("viewinng " + filename + " "
			// + imageList[numImages].getHeight() + " X "
			// + imageList[numImages].getWidth());
			icon[numImages] = new ImageIcon(imageList[numImages]);
			maxHeight = Math.max(maxHeight, imageList[numImages].getHeight());
			maxWidth = Math.max(maxWidth, imageList[numImages].getWidth());
			numImages++;
		}
		System.out.println(maxHeight + " X " + maxWidth);
		new ImageViewer();
	}

	ImageViewer() {
		this.setVisible(true);
		this.setSize(maxWidth, maxHeight);
		currentLabel = new JLabel();
		this.currentLabel.setIcon(icon[0]);
		this.currentLabel.setVerticalAlignment(JLabel.CENTER);
		this.currentLabel.setHorizontalAlignment(JLabel.CENTER);
		this.setTitle(filename[0]);
		currentLabel.setOpaque(true);
		this.add(currentLabel);
		addKeyListener(this);
        addWindowListener(this);
		this.validate();
		//pack();
		repaint();
		this.setLocation(600, 300);
		Thread t = new Thread(this);
		t.start();
	}
	
	public void paint(Graphics g){
		this.currentLabel.setIcon(icon[curImageIndex]);
		this.setTitle(filename[curImageIndex]);
		this.currentLabel.setVerticalAlignment(JLabel.CENTER);
		this.currentLabel.setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
			//System.out.println("rp()");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(curImageIndex < numImages-1)
				curImageIndex++;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        	if(curImageIndex > 0)
				curImageIndex--;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
        	if(curImageIndex > 0)
        		curImageIndex--;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        	if(curImageIndex < numImages-1)
				curImageIndex++;
        }
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}