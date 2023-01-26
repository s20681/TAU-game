import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class PlayServiceTest extends TestCase {
    PlayService playService;
    ExampleBoards exampleBoards = new ExampleBoards();

    @Override
    @Before
    public void setUp() throws Exception {
        playService = new PlayService();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        playService = null;
    }

    @Test
    public void testSetObstacles() {
        PlayService playServiceObstacled = new PlayService(5, AreaDifficulty.MEDIUM);
        playServiceObstacled.generateEmptyArea();
        playServiceObstacled.setObstacles();

        int obstacleCount = 0;
        for (List<String> row : playServiceObstacled.getArea().areaArray) {
            obstacleCount += Collections.frequency(row, "O");
        }

        assert obstacleCount > 0;
        assert playServiceObstacled.isSolvable(playServiceObstacled.findStartingCoord());
    }

    @Test
    public void testSetObstacles2() {
        PlayService playServiceEasy = new PlayService(10, AreaDifficulty.LOW);
        PlayService playServiceHard = new PlayService(10, AreaDifficulty.HIGH);

        playServiceEasy.generateEmptyArea();
        playServiceEasy.setObstacles();
        playServiceHard.generateEmptyArea();
        playServiceHard.setObstacles();


        int easyObstacleCount = 0;
        for (List<String> row : playServiceEasy.getArea().areaArray) {
            easyObstacleCount += Collections.frequency(row, "O");
        }

        int hardObstacleCount = 0;
        for (List<String> row : playServiceHard.getArea().areaArray) {
            hardObstacleCount += Collections.frequency(row, "O");
        }

        assert easyObstacleCount < hardObstacleCount;
    }

    @Test
    public void testPlaceAndRemoveObstacles() {
        playService = new PlayService(exampleBoards.unsolvableAllObstacles());

        int obstacleCount = 0;
        for (List<String> row : playService.getArea().areaArray) {
            obstacleCount += Collections.frequency(row, "O");
        }
        assert obstacleCount > 0;

        playService.removeObstacles();
        obstacleCount = 0;
        for (List<String> row : playService.getArea().areaArray) {
            obstacleCount += Collections.frequency(row, "O");
        }

        assert obstacleCount == 0;
    }

    @Test
    public void testIsSolvableEasyRight() {
        playService = new PlayService(exampleBoards.solvableEasyRight());
        assert playService.isSolvable(new Coords(0, 0));
    }

    @Test
    public void testIsSolvableEasyLeft() {
        playService = new PlayService(exampleBoards.solvableEasyLeft());
        assert playService.isSolvable(new Coords(3, 0));
    }

    @Test
    public void testIsSolvableJustStartAndFinish() {
        playService = new PlayService(exampleBoards.solvableSuperSimple());
        assert playService.isSolvable(new Coords(0, 0));
    }

    @Test
    public void testIsNotSolvable() {
        playService = new PlayService(exampleBoards.unsolvableAllObstacles());
        assert !playService.isSolvable(new Coords(0, 0));
    }

    @Test
    public void testIsSolvable2d() {
        playService = new PlayService(exampleBoards.solvable2D());
        assert playService.isSolvable(playService.findStartingCoord());
    }

    @Test
    public void testIsNotSolvable2d() {
        playService = new PlayService(exampleBoards.unsolvable2D());
        assert !playService.isSolvable(playService.findStartingCoord());
    }

    @Test
    public void testIsSolvableComplex() {
        playService = new PlayService(exampleBoards.solvableComplex());
        assert playService.isSolvable(playService.findStartingCoord());
    }

    @Test
    public void testIsNotSolvableComplex() {
        playService = new PlayService(exampleBoards.unsolvableComplex());
        assert !playService.isSolvable(playService.findStartingCoord());
    }

    @Test
    public void testApplyMoveObstacleOnLeftEmptyBelow() {
        playService = new PlayService(exampleBoards.obstacleOnTheLeftEdgeOnTheRight());
        assert !playService.isMoveLegal(playService.findStartingCoord(), 'L');
        assert playService.isMoveLegal(playService.findStartingCoord(), 'D');
    }

    @Test
    public void testApplyMoveEdge() {
        playService = new PlayService(exampleBoards.obstacleOnTheLeftEdgeOnTheRight());
        assert !playService.isMoveLegal(playService.findStartingCoord(), 'R');
    }

    @Test
    public void testIsObstacleOnCoord() {
        playService = new PlayService(exampleBoards.unsolvableMixedObstacles());
        assert playService.isObstacleOnCoord(1, 0);
        assert !playService.isObstacleOnCoord(2, 0);
    }

    @Test
    public void testFindStartingCoordInTheMiddle() {
        playService = new PlayService(exampleBoards.solvable2DStartInTheMiddle());
        assert playService.findStartingCoord().equals(new Coords(2, 2));
    }

    @Test
    public void testFindStartingCoordInCorner() {
        playService = new PlayService(exampleBoards.solvable2DStartInRightCorner());
        assert playService.findStartingCoord().equals(new Coords(4, 4));
    }

    @Test
    public void testFindFinishCoord() {
        playService = new PlayService(exampleBoards.solvable2DStartInRightCorner());
        assert playService.findFinishCoord().equals(new Coords(0, 3));
    }

    @Test
    public void testFindAvailableCoord() {
        playService = new PlayService(exampleBoards.solvable2DStartInTheMiddle());
        assert !playService.findNeighbourCoords(new Coords(2, 2)).contains(new Coords(1, 2));
        assert playService.findNeighbourCoords(new Coords(2, 2)).contains(new Coords(3, 2));
        assert playService.findNeighbourCoords(new Coords(2, 2)).contains(new Coords(2, 1));
        assert playService.findNeighbourCoords(new Coords(2, 2)).contains(new Coords(2, 3));

    }
}