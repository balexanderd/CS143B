import Classes.Process;
import Classes.ReadyList;
import Classes.Resource;
import java.util.*;

public class test {
    public static void main(String[] args) {
        ReadyList readylist = new ReadyList();
        Resource r2 = new Resource("r2", 2);
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
        System.out.println(r2.U_Units);
        r2.Request(process, 2);
        System.out.println(r2.U_Units);
        
        newProcess = new Process("a", 2, process, 1, 0);
        process.addChild(newProcess);
        readylist.addProcess(newProcess);

        readylist.addProcess(process);
        process = readylist.getNextProcess();
        System.out.println(process.PID);

        if(!r2.Request(process, 2)) {
            process = readylist.getNextProcess();
        }

        System.out.println(r2.Blocked_List.get(0).PID);
        System.out.println(r2.U_Units);
        System.out.println(process.PID);

        for(int i = 0; i < readylist.Level_Two.size(); i++)
            System.out.print(readylist.Level_Two.get(i).PID + ' ');

        if(!r2.Request(process, 2)) {
            process = readylist.getNextProcess();
        }

        System.out.println(process.PID);
        
        for(int i = 0; i < r2.Blocked_List.size(); i++)
            System.out.print(r2.Blocked_List.get(i).PID + ' ');
        System.out.println();

        newProcess = new Process("b", 2, process, 1, 0);
        process.addChild(newProcess);
        readylist.addProcess(newProcess);
        readylist.addProcess(process);
        process = readylist.getNextProcess();
        System.out.println(process.PID);

        if(!r2.Request(process, 2))
            process = readylist.getNextProcess();
        System.out.println(process.PID);
        for(int i = 0; i < r2.Blocked_List.size(); i++)
            System.out.print(r2.Blocked_List.get(i).PID + ' ');
        System.out.println();
        
        Process toRemove = process.getChild("x");
        process.removeChild(toRemove);
        ArrayList<Process> children = toRemove.getChildren();
        children.add(toRemove);
        readylist.removeProcess(toRemove);
        r2.removeProcesses(children);
        System.out.println(r2.U_Units);
        readylist.addProcess(process);
        for(int i = 0; i < r2.Blocked_List.size(); i++)
            System.out.print(r2.Blocked_List.get(i).PID + ' ');
        process = r2.Unblock();
        System.out.println(r2.U_Units);
        while(process != null) {
            readylist.addProcess(process);
            process = r2.Unblock();
        }
        process = readylist.getNextProcess();
        for(int i = 0; i < r2.Blocked_List.size(); i++)
            System.out.print(r2.Blocked_List.get(i).PID + ' ');
        System.out.println();
        System.out.println(process.PID);
        
    }
}
