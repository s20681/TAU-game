import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PlayService {
    Area area;
    Coords playerPosition;
    int moves;
    char lastMove;
    boolean finish;

    public PlayService() {
    }

    public PlayService(int areaSize, AreaDifficulty areaDifficulty) {
        this.area = new Area(areaSize, areaDifficulty);
    }

    public PlayService(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void start() {
        generateEmptyArea();
        setObstacles();
        play();
    }

    public void play() {
        playerPosition = area.start_xy;
        this.moves = 0;
        while (!this.finish) {
            displayArea();
            displayInfo();
            makeMove();
            updateArea();
        }

        displayInfo();
    }

    public void makeMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("[U]p, [D]own, [L]eft or [R]ight.");
        String input = scanner.next();
        char move;
        try {
            move = input.toCharArray()[0];
            if (isMoveLegal(playerPosition, move)) {
                playerPosition = applyMove(playerPosition, move);
                if (area.areaArray.get(playerPosition.y).get(playerPosition.x).equals("F")) {
                    finish = true;
                }
            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            System.out.println("Something went wrong. Try [U]p, [D]own, [L]eft or [R]ight. \n" + "and make sure there is no edge or obstacle there.");
            makeMove();
        }
    }

    public void generateEmptyArea() {
        //Create empty indexes
        List<String> row = new ArrayList<>();
        for (int j = 0; j < area.width; j++) {
            row.add(".");
        }

        for (int i = 0; i < area.height; i++) {
            area.areaArray.add(new ArrayList<>(row));
        }

        generateStartFinishCoords();
    }

    public void generateStartFinishCoords() {
        //Find the place for [S]tart and [F]inish on the edges
        //X=0 dla wszystkich Y
        //X=WIDTH-1 dla wszystkich Y
        ArrayList<Coords> possibleCoords = new ArrayList<>();
        for (int y = 0; y < area.height; y++) {
            possibleCoords = addCoord(new Coords(0, y), possibleCoords);
            possibleCoords = addCoord(new Coords(area.width - 1, y), possibleCoords);
        }

        //X<WIDTH dla Y=0 i Y=HEIGHT-1
        for (int x = 0; x < area.width; x++) {
            possibleCoords = addCoord(new Coords(x, 0), possibleCoords);
            possibleCoords = addCoord(new Coords(x, area.height - 1), possibleCoords);
        }

        setStartFinishCoords(possibleCoords);
    }

    public void setStartFinishCoords(ArrayList<Coords> possibleCoords) {
        //roll start and finish randomly
        area.start_xy = possibleCoords.remove(new Random().nextInt(possibleCoords.size() - 1));
        area.finish_xy = possibleCoords.remove(new Random().nextInt(possibleCoords.size() - 1));

        //check if they are not next to each other, else reroll
        int xdiff = Math.abs(area.start_xy.x - area.finish_xy.x);
        int ydiff = Math.abs(area.start_xy.y - area.finish_xy.y);
        if (xdiff <= 1 && ydiff <= 1) {
            setStartFinishCoords(possibleCoords);
        } else {
            //place them in the area on their spot
            area.areaArray.get(area.start_xy.y).set(area.start_xy.x, "S");
            area.areaArray.get(area.finish_xy.y).set(area.finish_xy.x, "F");
        }
    }

    public void setObstacles() {
        //should set some obstacles in the area depending on the difficulty set.
        //low - 1/25 of all fields are obstacles
        //medium - 3/25 of all fields are obstacles
        //high - 1/5 of all fields are obstacles
        int amountToSet;
        int fields = area.width * area.height;
        if (area.difficulty.equals(AreaDifficulty.LOW)) {
            amountToSet = (int) Math.ceil(fields / 25);
        } else if (area.difficulty.equals(AreaDifficulty.MEDIUM)) {
            amountToSet = (int) Math.ceil(fields / 5);
        } else {
            amountToSet = (int) Math.ceil(fields / 3);
        }

        //now we want to randomly set the obstacles and check if the board is still playable
        while (amountToSet > 0) {
            int setx = new Random().nextInt(area.width);
            int sety = new Random().nextInt(area.height);
            if (area.areaArray.get(sety).get(setx).equals(".")) {
                area.areaArray.get(sety).set(setx, "O");
                amountToSet--;
            }
        }

        Coords startCoords = findStartingCoord();
        if (!isSolvable(startCoords)) {
            removeObstacles();
            area.visitedCoords.clear();
            setObstacles();
        }
    }

    public void removeObstacles() {
        for (List<String> row : area.areaArray) {
            for (String s : row) {
                while (row.contains("O")) {
                    row.set(row.indexOf("O"), ".");
                }
            }
        }
    }

    public void removePlayerMark() {
        for (List<String> row : area.areaArray) {
            for (String s : row) {
                if (row.contains("X")) {
                    row.set(row.indexOf("X"), ".");
                }
            }
        }
    }

    public boolean isSolvable(Coords startCoords) {
        area.coordsToVisit.addAll(findNeighbourCoords(startCoords));
//        System.out.println("startcoord" + startCoords);
//        System.out.println("neighbours added" + findNeighbourCoords(startCoords));
        if (area.coordsToVisit.contains(findFinishCoord())) {
            return true;
        }
        for (Coords coords : area.coordsToVisit) {
            if (!area.visitedCoords.contains(coords)) {
                area.visitedCoords.add(startCoords);
                return isSolvable(coords);
            }
        }

        return false;
    }

    public Coords findStartingCoord() {
        int y = 0;
        for (List<String> row : area.areaArray) {
            if (row.contains("S")) {
                return new Coords(row.indexOf("S"), y);
            }
            y++;
        }
        return null;
    }

    public Coords findFinishCoord() {
        int y = 0;
        for (List<String> row : area.areaArray) {
            if (row.contains("F")) {
                return new Coords(row.indexOf("F"), y);
            }
            y++;
        }
        return null;
    }

    public List<Coords> findNeighbourCoords(Coords startCoord) {
        List<Coords> coordsList = new ArrayList<>();
        if (isMoveLegal(startCoord, 'L')) {
            coordsList.add(new Coords(startCoord.x - 1, startCoord.y));
        }

        if (isMoveLegal(startCoord, 'R')) {
            coordsList.add(new Coords(startCoord.x + 1, startCoord.y));
        }

        if (isMoveLegal(startCoord, 'U')) {
            coordsList.add(new Coords(startCoord.x, startCoord.y - 1));
        }

        if (isMoveLegal(startCoord, 'D')) {
            coordsList.add(new Coords(startCoord.x, startCoord.y + 1));
        }

        return coordsList;
    }

    public boolean isMoveLegal(Coords pointFrom, char move) {
        //'L' - left, 'R' - right, 'U' - up, 'D' - down
        if (move == 'L') {
            return pointFrom.x > 0 && !isObstacleOnCoord(pointFrom.x - 1, pointFrom.y);
        } else if (move == 'R') {
            return (pointFrom.x + 1) < area.width && !isObstacleOnCoord(pointFrom.x + 1, pointFrom.y);
        } else if (move == 'U') {
            return pointFrom.y > 0 && !isObstacleOnCoord(pointFrom.x, pointFrom.y - 1);
        } else if (move == 'D') {
            return (pointFrom.y + 1) < area.height && !isObstacleOnCoord(pointFrom.x, pointFrom.y + 1);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Coords applyMove(Coords pointFrom, char move) {
        if (move == 'L') {
            return new Coords(pointFrom.x - 1, pointFrom.y);
        } else if (move == 'R') {
            return new Coords(pointFrom.x + 1, pointFrom.y);
        } else if (move == 'U') {
            return new Coords(pointFrom.x, pointFrom.y - 1);
        } else {
            return new Coords(pointFrom.x, pointFrom.y + 1);
        }
    }

    public void updateArea() {
        removePlayerMark();
        area.areaArray.get(playerPosition.y).set(playerPosition.x, "X");
    }

    public boolean isObstacleOnCoord(int x, int y) {
        return area.areaArray.get(y).get(x).equals("O");
    }


    public ArrayList<Coords> addCoord(Coords coordsToAdd, ArrayList<Coords> coordsArrayList) {
        if (!coordsArrayList.contains(coordsToAdd)) {
            coordsArrayList.add(coordsToAdd);
        }

        return coordsArrayList;
    }

    public void displayArea() {
        String result = "";
        for (List<String> row : area.areaArray) {
            for (String mark : row) {
                result = result + " " + mark;
            }
            result += "\n";
        }
        if (!result.isEmpty()) System.out.println(result);
    }

    public void displayInfo() {
        String result = "";
        if (finish) {
            result = result + "YOU WIN!!! \n";
        }

        if (moves > 0) {
            result = result + "\nMoves used : " + moves;
            result = result + "\nLast move : " + lastMove;
        }

        if (!result.isEmpty()) System.out.println(result);
    }
}