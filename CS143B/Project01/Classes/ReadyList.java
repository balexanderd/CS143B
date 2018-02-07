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
        ArrayList<Process> toDelete = process.getChildren();
        Level_Two.removeAll(toDelete);
        Level_One.removeAll(toDelete);
        if(process.Priority == 1) Level_One.remove(toDelete);
        else Level_Two.remove(toDelete);
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
}
