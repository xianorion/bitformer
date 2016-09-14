package cmdAnimator.GameUI;

import static org.junit.Assert.*;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GameWindowTests {

	GameWindow gameWindow;
	
	@Before
	public void setUp(){
		gameWindow =  new GameWindow("Bitformer");
	}
	
	@Test
	public void isWindowPresent() {
		assertNotNull(gameWindow);
	}
	
	@Test @Ignore //doesnt really test functionality
	public void sizeOfWindowIs640by480(){
		assertEquals(480, gameWindow.getHeight());
		assertEquals(640, gameWindow.getWidth());
	}
	
	@Test
	public void checkIfWindowIsVisible(){
		assertEquals(true, gameWindow.isVisible());
	}
	
	@Test
	public void defaultCloseOperationIsExitOnClose(){
		assertEquals(JFrame.EXIT_ON_CLOSE, gameWindow.getDefaultCloseOperation());
	}
	
	@Test
	public void gameWindowTitleIsBitformer(){
		assertEquals("Bitformer", gameWindow.getTitle());
	}
	
	@Test
	public void gameWindowIsNotResizable(){
		assertEquals(false, gameWindow.isResizable());
	}
	
	@Test  @Ignore //may delete this test, not important
	public void thereIsAGamePanelOnTheGameWindow(){
		assertEquals( new GameGUIPanel().getClass(), gameWindow.getContentPane().getComponents()[0].getClass());
	}

}
