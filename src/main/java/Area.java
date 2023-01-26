import java.util.ArrayList;
import java.util.List;

public class Area {
    int width;
    int height;
    Coords start_xy;
    Coords finish_xy;
    AreaDifficulty difficulty;
    List<List<String>> areaArray = new ArrayList<>();
    List<Coords> visitedCoords = new ArrayList<>();
    List<Coords> coordsToVisit = new ArrayList<>();

    public Area(int size, AreaDifficulty difficulty) {
        this.width = size;
        this.height = size;
        this.difficulty = difficulty;
    }

    public Area(List<List<String>> areaArray) {
        this.areaArray = areaArray;
        this.width = areaArray.get(0).size();
        this.height = areaArray.size();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Coords getStart_xy() {
        return start_xy;
    }

    public void setStart_xy(Coords start_xy) {
        this.start_xy = start_xy;
    }

    public Coords getFinish_xy() {
        return finish_xy;
    }

    public void setFinish_xy(Coords finish_xy) {
        this.finish_xy = finish_xy;
    }

    public AreaDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(AreaDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<List<String>> getAreaArray() {
        return areaArray;
    }

    public void setAreaArray(List<List<String>> areaArray) {
        this.areaArray = areaArray;
    }

    public List<Coords> getVisitedCoords() {
        return visitedCoords;
    }

    public void setVisitedCoords(List<Coords> visitedCoords) {
        this.visitedCoords = visitedCoords;
    }

    public List<Coords> getCoordsToVisit() {
        return coordsToVisit;
    }

    public void setCoordsToVisit(List<Coords> coordsToVisit) {
        this.coordsToVisit = coordsToVisit;
    }
}
