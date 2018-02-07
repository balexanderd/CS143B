import java.util.Scanner;
import Classes.Process;
import Classes.ReadyList;

public class Scheduler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String currentCommand;
        String newPID;
        Integer newPriority;
        ReadyList readylist = new ReadyList();
        Process currentProcess = readylist.getNextProcess();
        do{
            System.out.print(currentProcess.PID + ' ');
            currentCommand = sc.next();
            while(!( currentCommand.equals("cr") || currentCommand.equals("to") ||
                     currentCommand.equals("req") || currentCommand.equals("de"))
                     currentCommand.equals("init") ) {
                System.out.print("Error ");
                currentCommand = sc.next();
            }
            switch(currentCommand) {
                case "cr"  :
                             newPID = sc.next();
                             newPriority = sc.nextInt();
                             Process newProcess = new Process( newPID, newPriority, currentProcess,
                                                               1, readylist.sizeOf(newPriority) );
                             currentProcess.addChild(newProcess);
                             readylist.addProcess(newProcess);
                             if(newPriority > currentProcess.Priority) {
                                 readylist.addProcess(currentProcess);
                                 currentProcess = readylist.getNextProcess();
                             }
                             break;
                case "to"  :
                             readylist.addProcess(currentProcess);
                             currentProcess = readylist.getNextProcess();
                             break;
                case "req" : 
                             break;
                case "de"  :
                             newPID = sc.next();
                             if(currentProcess.PID.equals(newPID)) {
                                 currentProcess.Creation_Tree.first.removeChild(currentProcess);
                                 readylist.removeProcess(currentProcess);
                                 currentProcess = readylist.getNextProcess();
                             }
                             else {
                                 Process toDelete = currentProcess.getChild(newPID);
                                 readylist.removeProcess(toDelete);
                                 currentProcess.removeChild(toDelete);
                             }
                             break;
                case "init":
                             readylist = new ReadyList();
                default    :             
                             break;
            }
        } while(!readylist.isEmpty());
    }
}
