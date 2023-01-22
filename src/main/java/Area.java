
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
            amountToSet = (int) Math.ceil((fields * 3) / 25);
        } else {
            amountToSet = (int) Math.ceil(fields / 5);
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
    }

    public void isSolvable(){

    }

    public Coords findStartingCoord(){
        int y = 0;
        for (List<String> row : area){
            y++;
            if(row.contains("S")){
                return new Coords(row.indexOf("S"), y);
            }
        }
        return null;
    }

    public List<Coords> findNeighbourCoords(Coords coords){
        List<Coords> coordsList = new ArrayList<>();
        //cztery opcje
        //x - 1, x + 1, y - 1, y + 1


    }

    public boolean isMoveLegal(Coords pointFrom, char move){
        //'L' - left, 'R' - right, 'U' - up, 'D' - down
        if(move == 'L'){
            return pointFrom.x > 0 && !isObstacleOnCoord(pointFrom.x - 1, pointFrom.y);
        }else if(move == 'R'){
            return pointFrom.x + 1 < width && !isObstacleOnCoord(pointFrom.x + 1, pointFrom.y);
        }else if(move == 'U'){
            return pointFrom.y > 0 && !isObstacleOnCoord(pointFrom.x, pointFrom.y - 1);
        }else if(move == 'D'){
            return pointFrom.y + 1 < height && !isObstacleOnCoord(pointFrom.x, pointFrom.y - 1);
        }else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isObstacleOnCoord(int x, int y){
        return area.get(x).get(y).equals("O");
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
