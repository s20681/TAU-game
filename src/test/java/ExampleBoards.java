import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleBoards {
    Area area;
    List<List<String>> areaArray;

    public Area solvableSuperSimple(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList("S", "F"));
        return new Area(areaArray);
    }

    public Area solvableEasyRight(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList("S", ".", ".", "F"));
        return new Area(areaArray);
    }

    public Area solvableEasyLeft(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList("F", ".", ".", "S"));
        return new Area(areaArray);
    }

    public Area unsolvableAllObstacles(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList("S", "O", "O", "F"));
        return new Area(areaArray);
    }

    public Area unsolvableMixedObstacles(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList("S", "O", ".", "O", ".", "O", ".", "F"));
        return new Area(areaArray);
    }

    public Area solvable2D(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList(".", "O", ".", "S"));
        areaArray.add(Arrays.asList("O", "O", ".", "."));
        areaArray.add(Arrays.asList(".", "O", "O", "."));
        areaArray.add(Arrays.asList("F", ".", ".", "."));
        return new Area(areaArray);
    }

    public Area solvable2DStartInTheMiddle(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList(".", "O", ".", ".", "."));
        areaArray.add(Arrays.asList("O", "O", ".", ".", "."));
        areaArray.add(Arrays.asList(".", "O", "S", ".", "."));
        areaArray.add(Arrays.asList("F", ".", ".", ".", "."));
        areaArray.add(Arrays.asList(".", ".", ".", ".", "."));
        return new Area(areaArray);
    }

    public Area solvable2DStartInRightCorner(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList(".", "O", ".", ".", "."));
        areaArray.add(Arrays.asList("O", "O", ".", ".", "."));
        areaArray.add(Arrays.asList(".", "O", ".", ".", "."));
        areaArray.add(Arrays.asList("F", ".", ".", ".", "."));
        areaArray.add(Arrays.asList(".", ".", ".", ".", "S"));
        return new Area(areaArray);
    }

    public Area unsolvable2D(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList(".", "O", ".", "S"));
        areaArray.add(Arrays.asList("O", "O", ".", "."));
        areaArray.add(Arrays.asList(".", "O", "O", "."));
        areaArray.add(Arrays.asList("F", ".", "O", "."));
        return new Area(areaArray);
    }

    public Area unsolvableComplex(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList(".", "O", ".", ".", "O", ".", ".", "O", ".", "."));
        areaArray.add(Arrays.asList(".", "O", ".", ".", ".", "O", ".", "O", ".", "."));
        areaArray.add(Arrays.asList(".", "O", "F", "O", "O", ".", ".", ".", ".", "."));
        areaArray.add(Arrays.asList(".", "O", ".", ".", "O", ".", ".", "O", ".", "."));
        areaArray.add(Arrays.asList(".", "O", ".", "O", ".", ".", ".", "O", ".", "."));
        areaArray.add(Arrays.asList(".", "O", ".", ".", "O", ".", ".", "O", "S", "."));
        areaArray.add(Arrays.asList(".", "O", ".", "O", ".", ".", ".", "O", ".", "."));
        return new Area(areaArray);
    }

    public Area solvableComplex(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList(".", "O", ".", ".", "O", ".", ".", "O", ".", "."));
        areaArray.add(Arrays.asList(".", "O", ".", ".", ".", "O", ".", "O", ".", "."));
        areaArray.add(Arrays.asList(".", "O", "F", "O", "O", ".", ".", ".", ".", "."));
        areaArray.add(Arrays.asList(".", "O", ".", ".", "O", ".", ".", "O", ".", "."));
        areaArray.add(Arrays.asList(".", "O", "O", ".", ".", ".", "O", "O", ".", "."));
        areaArray.add(Arrays.asList(".", "O", ".", ".", "O", ".", ".", "O", "S", "."));
        areaArray.add(Arrays.asList(".", "O", ".", "O", ".", ".", ".", "O", ".", "."));
        return new Area(areaArray);
    }

    public Area obstacleOnTheLeftEdgeOnTheRight(){
        areaArray = new ArrayList<>();
        areaArray.add(Arrays.asList(".", "O", "O", "S"));
        areaArray.add(Arrays.asList("F", ".", ".", "."));
        return new Area(areaArray);
    }
}
