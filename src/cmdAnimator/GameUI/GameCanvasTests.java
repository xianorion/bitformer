package cmdAnimator.GameUI;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import cmdAnimator.GameCanvasActions.CanvasText;

public class GameCanvasTests {
	
	GameCanvas canvas;
	Point point;
	
	@Before
	public void setup(){
		canvas  = new GameCanvas();
		point = new Point(1,1);
		canvas.addText(new CanvasText("Test Text", point));
	}

	@Test
	public void TextIsAddedToCanvasAtParticularPoint() {
		assertTrue(canvas.getTextToWrite().containsKey(point));
		assertEquals(canvas.getTextToWrite().get(point).getTextToAdd(), "Test Text");
	}
	
	@Test
	public void TextThatIsAddedCanBeRemoved(){
		canvas.deleteText(point);
		
		assertFalse(canvas.getTextToWrite().containsKey("Test Text"));
	}
	
	@Test
	public void TwoTextsArePresentWhenAdded(){
		Point point2 = new Point(5,5);
		canvas.addText(new CanvasText("Hi", point2));
		
		assertTrue(canvas.getTextToWrite().containsKey(point));
		assertEquals(canvas.getTextToWrite().get(point).getTextToAdd(), "Test Text" );
		
		assertTrue(canvas.getTextToWrite().containsKey(point2));
		assertEquals(canvas.getTextToWrite().get(point2).getTextToAdd(), "Hi" );
	
	}

	

}
