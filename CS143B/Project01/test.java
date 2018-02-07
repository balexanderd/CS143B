import Classes.Process;
import Classes.ReadyList;
import java.util.*;

public class test {
    public static void main(String[] args) {
        ReadyList readylist = new ReadyList();
        //Process process = readylist.getNextProcess();
        Process process = readylist.Init;
        System.out.println(process.PID);
        Process newProcess = new Process("x", 1, process, 1, 0);
        
        process.addChild(newProcess);
        readylist.addProcess(process);
        readylist.addProcess(newProcess);

        process = readylist.getNextProcess();
        System.out.println(process.PID);

        newProcess = new Process("y", 2, process, 1, 0);
        process.addChild(newProcess);
        readylist.addProcess(process);
        readylist.addProcess(newProcess);

        process = readylist.getNextProcess();
        System.out.println(process.PID);

        newProcess = new Process("z", 2, process, 1, 0);
        process.addChild(newProcess);
        readylist.addProcess(process);
        readylist.addProcess(newProcess);

        process = readylist.getNextProcess();
        System.out.println(process.PID);

        newProcess = new Process("a", 2, process, 1, 0);
        process.addChild(newProcess);
        readylist.addProcess(newProcess);
        newProcess = new Process("b", 2, process, 1, 0);
        process.addChild(newProcess);
        readylist.addProcess(newProcess);
        newProcess = new Process("c", 2, process, 1, 0);
        process.addChild(newProcess);
        readylist.addProcess(newProcess);

        ArrayList<Process> kids = process.getChildren();
        System.out.print(process.PID + ": ");
        for(int i = 0; i < kids.size(); i++)
            System.out.print(kids.get(i).PID + ' ');

        process.Creation_Tree.first.removeChild(process);
        readylist.removeProcess(process);
        process = readylist.getNextProcess();
        System.out.println();

        System.out.println(process.PID);
    }
}
