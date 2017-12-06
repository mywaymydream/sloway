package sloway.capture;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

public class DesktopCaptureApp {

	public static void main(String... args) throws IOException, AWTException {
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		Robot robot = new Robot();
		LocalDateTime startTime = LocalDateTime.now();
		final int FRAMES = 100;
		ExecutorService executor = Executors.newSingleThreadExecutor();
		for(int i = 0; i < FRAMES; i++) {
			BufferedImage capture = robot.createScreenCapture(screenRect);
			executor.submit(() -> {
				try {
					ImageIO.write(capture, "PNG", new ByteArrayOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		Duration duration = Duration.between(startTime, LocalDateTime.now());
		System.out.println("Duration: " + duration);
		System.out.println("FPS: " + (FRAMES / duration.getSeconds()));
		
		
	}
}
