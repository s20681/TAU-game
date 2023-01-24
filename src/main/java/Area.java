
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Area {
    int width;
    int height;
    Coords start_xy;
    Coords finish_xy;
    AreaDifficulty difficulty;
    List<List<String>> area = new ArrayList<>();
    List<Coords> visitedCoords = new ArrayList<>();

    public Area(int width, int height, AreaDifficulty difficulty) {
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        generateEmptyArea();
        setObstacles();
    }

    public void generateEmptyArea(){

        //Create empty indexes
        List<String> row = new ArrayList<>();
        for (int j = 0; j < width; j++) {
            row.add(".");
        }

        for (int i = 0; i < height; i++) {
            area.add(new ArrayList<>(row));
        }

        generateStartFinishCoords();
    }

    public void generateStartFinishCoords(){
        //Find the place for [S]tart and [F]inish on the edges
        //X=0 dla wszystkich Y
        //X=WIDTH-1 dla wszystkich Y
        ArrayList<Coords> possibleCoords = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            possibleCoords = addCoord(new Coords(0, y), possibleCoords);
            possibleCoords = addCoord(new Coords(width - 1, y), possibleCoords);
        }

        //X<WIDTH dla Y=0 i Y=HEIGHT-1
        for (int x = 0; x < width; x++) {
            possibleCoords = addCoord(new Coords(x, 0), possibleCoords);
            possibleCoords = addCoord(new Coords(x, height - 1), possibleCoords);
        }

        setStartFinishCoords(possibleCoords);
    }

    public void setStartFinishCoords(ArrayList<Coords> possibleCoords){
        //roll start and finish randomly
        start_xy = possibleCoords.remove(new Random().nextInt(possibleCoords.size() - 1));
        finish_xy = possibleCoords.remove(new Random().nextInt(possibleCoords.size() - 1));

        //check if they are not next to each other, else reroll
        int xdiff = Math.abs(start_xy.x - finish_xy.x);
        int ydiff = Math.abs(start_xy.y - finish_xy.y);
        if(xdiff <= 1 && ydiff <= 1){
            setStartFinishCoords(possibleCoords);
        }else {
            //place them in the area on their spot
            area.get(start_xy.y).set(start_xy.x, "S");
            area.get(finish_xy.y).set(finish_xy.x, "F");
        }
    }

    public void setObstacles(){
        //should set some obstacles in the area depending on the difficulty set.
        //low - 1/25 of all fields are obstacles
        //medium - 3/25 of all fields are obstacles
        //high - 1/5 of all fields are obstacles
        int amountToSet;
        int fields = width * height;
        if(difficulty.equals(AreaDifficulty.LOW)){
            amountToSet = (int) Math.ceil(fields / 25);
        } else if (difficulty.equals(AreaDifficulty.MEDIUM)){
            amountToSet = (int) Math.ceil(fields / 5);
        } else {
            amountToSet = (int) Math.ceil(fields / 3);
        }

        //now we want to randomly set the obstacles and check if the board is still playable
        while(amountToSet > 0){
            int setx = new Random().nextInt(width);
            int sety = new Random().nextInt(height);
            if(area.get(sety).get(setx).equals(".")){
                area.get(sety).set(setx, "O");
                amountToSet --;
            }
        }

        Coords startCoords = findStartingCoord();
        System.out.println(this.display());
        System.out.println("Area state before checking if solvable first time");
        if(!isSolvable(startCoords)){
            System.out.println(this.display());
            System.out.println("Reloading the obstacles because not solvable?");
            removeObstacles();
            visitedCoords.clear();
            setObstacles();
        }
    }

    public void removeObstacles(){
        for(List<String> row : area){
            for(String s : row){
                while(row.contains("O")){
                    row.set(row.indexOf("O"), ".");
                }
            }
        }
    }

    public boolean isSolvable(Coords startCoords){
        List<Coords> neighbours = findNeighbourCoords(startCoords);
        System.out.println("startcoord" + startCoords);
        System.out.println("neighbours" + neighbours);
        if(neighbours.contains(findFinishCoord())){
            return true;
        }
        for(Coords coords : neighbours){
            if(!visitedCoords.contains(coords)){
                visitedCoords.add(startCoords);
                return isSolvable(coords);
            }
        }

        return false;
    }

    public Coords findStartingCoord(){
        int y = 0;
        for (List<String> row : area){
            if(row.contains("S")){
                return new Coords(row.indexOf("S"), y);
            }
            y++;
        }
        return null;
    }

    public Coords findFinishCoord(){
        int y = 0;
        for (List<String> row : area){
            if(row.contains("F")){
                return new Coords(row.indexOf("F"), y);
            }
            y++;
        }
        return null;
    }

    public List<Coords> findNeighbourCoords(Coords startCoord){
        List<Coords> coordsList = new ArrayList<>();
        if(isMoveLegal(startCoord,'L')){
            coordsList.add(new Coords(startCoord.x - 1, startCoord.y));
        }

        if(isMoveLegal(startCoord,'R')){
            coordsList.add(new Coords(startCoord.x + 1, startCoord.y));
        }

        if(isMoveLegal(startCoord,'U')){
            coordsList.add(new Coords(startCoord.x, startCoord.y - 1));
        }

        if(isMoveLegal(startCoord,'D')){
            coordsList.add(new Coords(startCoord.x, startCoord.y + 1));
        }

        return coordsList;
    }

    public boolean isMoveLegal(Coords pointFrom, char move){
        //'L' - left, 'R' - right, 'U' - up, 'D' - down
        if(move == 'L'){
            return pointFrom.x > 0 && !isObstacleOnCoord(pointFrom.x - 1, pointFrom.y);
        }else if(move == 'R'){
            return (pointFrom.x + 1) < width && !isObstacleOnCoord(pointFrom.x + 1, pointFrom.y);
        }else if(move == 'U'){
            return pointFrom.y > 0 && !isObstacleOnCoord(pointFrom.x, pointFrom.y - 1);
        }else if(move == 'D'){
            return (pointFrom.y + 1) < height && !isObstacleOnCoord(pointFrom.x, pointFrom.y + 1);
        }else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isObstacleOnCoord(int x, int y){
        return area.get(y).get(x).equals("O");
    }


    public ArrayList<Coords> addCoord(Coords coordsToAdd, ArrayList<Coords> coordsArrayList){
        if(!coordsArrayList.contains(coordsToAdd)){
            coordsArrayList.add(coordsToAdd);
        }

        return coordsArrayList;
    }

    public String display(){
        String result = "";
        for (List<String> row : area) {
            for (String mark : row) {
                result = result +" "+ mark;
            }
            result += "\n";
        }
        return result;
    }

}
