public class ComputerPlayer extends Player{
    private static int cpCount;

    public ComputerPlayer(Color c){
        String cpName = "ComputerPlayer " + cpCount++;
        super(cpName, c);
    }

    public void move(){
        // logic
    }
}