package Classes;
import java.util.*;
import Classes.myUtils.Pair;

public class ReadyList {
    public Process Init;
    public ArrayList<Process> Level_One;
    public ArrayList<Process> Level_Two;
    public ReadyList() {
        Init = new Process("init", 0, null, 0, -1);
        Level_One = new ArrayList<Process>();
        Level_Two = new ArrayList<Process>();
    }
    public void addProcess(Process process) {
        if(process.Priority == 2)
            Level_Two.add(process);
        else if(process.Priority == 1)
            Level_One.add(process);
        else if(process.Priority == 0)
            Init = process;
    }
    public void removeProcess(Process process) {
        if(process.Priority == 1) Level_One.remove(process);
        else Level_Two.remove(process);
    }
    public void deleteProcess(Process process) {
        if(process.equals(Init)) {
            Init = null;
            return;
        }
        ArrayList<Process> toDelete = process.getChildren();
        Level_Two.removeAll(toDelete);
        Level_One.removeAll(toDelete);
        removeProcess(process);
    }
    public Process getNextProcess() {
        if(!Level_Two.isEmpty())
            return Level_Two.remove(0);
        else if(!Level_One.isEmpty())
            return Level_One.remove(0);
        return Init;
    }
    public boolean isEmpty() {
        return (Level_Two.isEmpty() && Level_One.isEmpty() && Init == null);
    }
    public Integer sizeOf(Integer priority_level) {
        if(priority_level == 1)
            return Level_One.size();
        return Level_Two.size();
    }
    public Integer peekPriority() {
        if(!Level_Two.isEmpty())
            return 2;
        if(!Level_One.isEmpty())
            return 1;
        return 0;
    }
    public void printList() {
        System.out.print("Level_Two: ");
        for(int i = 0; i < Level_Two.size(); i++)
            System.out.print(Level_Two.get(i).PID + ' ');
        System.out.print("\nLevel_One: ");
        for(int i = 0; i < Level_One.size(); i++)
            System.out.print(Level_One.get(i).PID + ' ');
        System.out.println();
        System.out.println(Init.PID);
    }
}
