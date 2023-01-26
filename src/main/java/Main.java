public class Main {
    public static void main(String[] args) {
        //To play the game just add preferred arena size and difficulty level, then use the .start() method.
        PlayService playService = new PlayService(10, AreaDifficulty.MEDIUM);
        playService.start();
    }

}
