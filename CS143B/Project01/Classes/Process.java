package Classes;
import java.util.*;
import Classes.myUtils.Pair;

public class Process {
    public String PID;
    public Integer Priority; //0 == init, 1 == user, 2 == system.
    public ArrayList<Integer> Other_Resources; //= new ArrayList<Integer>();
    public Pair<Integer, Integer> Status; //= new Pair<>(1, 2);
    public Pair<Process, ArrayList<Process>> Creation_Tree; //= new Pair<>(4, HashSet<>());
    public Process(String pid, Integer priority, Process parent, Integer type, Integer index) {
        PID = pid;
        Priority = priority;
        Other_Resources = new ArrayList<Integer>(); //Maybe better as queue.
        Status = new Pair<Integer, Integer>(type, index);
        Creation_Tree = new Pair<Process, ArrayList<Process>>(parent, new ArrayList<Process>());
    }
    /*Process(Process process) {
        PID = process.PID;
        Priority = process.Priority;
        Other_Resources = process.Other_Resources;
        Status = new Pair<Integer, Integer>(process.Status.first(), process.Status.second());
        Creation_Tree = new Pair<Process, ArrayList<Integer>>(process.Creation_Tree.first(), process.Creation_Tree.second());
    }*/
    public void setPID(String pid) {
        PID = pid;
    }
    public void setPriority(Integer priority) {
        Priority = priority;
    }
    public void setStatus(Integer type, Integer index) {
        Status = new Pair<Integer, Integer>(type, index);
    }
    public void addChild(Process child) {
        Creation_Tree.second.add(child);
    }
    public void removeChild(Process child) {
        Creation_Tree.second.remove(child);
    }
    public Process getChild(String child_pid) {
        for(int i = 0; i < Creation_Tree.second.size(); i++)
            if(Creation_Tree.second.get(i).PID.equals(child_pid))
                return Creation_Tree.second.get(i);
        return null;
    }
    public void addResource(Integer rid) {
        Other_Resources.add(rid);
    }
    public void removeResource(Integer rid) {
        Other_Resources.remove(rid);
    }
    public ArrayList<Process> getChildren() {
        return Creation_Tree.second();
    }
}
