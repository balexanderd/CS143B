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
        if(parent == null)
            Creation_Tree = new Pair<Process, ArrayList<Process>>(this, new ArrayList<Process>());
        else
            Creation_Tree = new Pair<Process, ArrayList<Process>>(parent, new ArrayList<Process>());
    }
    @Override
    public int hashCode() {
        return PID.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if(obj==null || !(obj instanceof Process))
            return false;
        Process u=(Process) obj;
        return u.PID == PID;
    }
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
    public Process removeChild(Process child) {
        if(child != null) {
            if(Creation_Tree.second.remove(child))
                return child;
            ArrayList<Process> children = new ArrayList<Process>(Creation_Tree.second);
            for(int i = 0; i < children.size(); i++)
                children.addAll(children.get(i).Creation_Tree.second);
            if(children.contains(child))
                return child;
        }
        return null;
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
        ArrayList<Process> children = Creation_Tree.second();
        for(int i = 0; i < children.size(); i++)
            children.addAll(children.get(i).Creation_Tree.second());
        return children;
    }
}
