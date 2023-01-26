import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayServiceMockTest {
    @Mock
    Area areaMock;

    @Mock
    PlayService playServiceMock;

    @Before
    public void setUp() {
        playServiceMock.setArea(areaMock);
    }

    @Test
    public void shouldCreateMockInstance() {
        assertThat(playServiceMock, is(notNullValue()));
    }

    @Test
    public void start() {
        playServiceMock.start();
        verify(playServiceMock, atLeastOnce()).generateEmptyArea();
        verify(playServiceMock, atLeastOnce()).setObstacles();
        verify(playServiceMock, atLeastOnce()).play();
    }

    @Test
    public void generateEmptyArea() {
        playServiceMock.generateEmptyArea();
        verify(playServiceMock, atLeastOnce()).generateStartFinishCoords();
    }

    @Test
    public void setStartFinishCoordsCallsAgain() {
        ArrayList<Coords> coords = new ArrayList<Coords>();
        coords.add(new Coords(0, 0));
        coords.add(new Coords(1, 0));
        playServiceMock.setStartFinishCoords(coords);

        verify(playServiceMock, atLeast(2)).setStartFinishCoords(coords);
    }

    @Test
    public void setStartFinishCoordsCallsOnce() {
        ArrayList<Coords> coords = new ArrayList<Coords>();
        coords.add(new Coords(0, 0));
        coords.add(new Coords(10, 0));
        playServiceMock.setStartFinishCoords(coords);

        verify(playServiceMock, atMost(1)).setStartFinishCoords(coords);
    }
}