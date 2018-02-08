package Classes;
import java.util.*;
import Classes.myUtils.Pair;

public class Resourse {
    public String RID;
    public Integer K_Units; //Maximum number of units.
    public Integer U_Units; //Available number of units.
    public ArrayList<Process> Unit_Tracker;
    public ArrayList<Integer> Unit_TrackerCount;
    public ArrayList<Process> Blocked_List; //Processes waiting for a resource.
    public ArrayList<Integer> Request_List; //Parallel array that goes along with Blocked_List. How many units a resource wants (by index).
    public Resource(String rid, Integer k_units) {
        RID = rid;
        K_Units = U_Units = k_units;
        Unit_Tracker = new ArrayList<Process>();
        Unit_TrackerCount = new ArrayList<Integer>();
        Blocked_List = new ArrayList<Process>();
        Request_List = new ArrayList<Integer>();
    }
    public Process Request(Process process, Integer units) { //Return false if placed on block list.
        if(U_Units >= units) { //Enough units.
            U_Units -= units;
            Unit_Tracker.add(process);
            Unit_TrackerCount.add(units);
            return process;
        }
        //else Not enough units. Place on blocked list.
        Blocked_List.add(process);
        Request_List.add(units);
        return null;
    }
    public void Release(Process process) {
        int index = Unit_Tracker.indexOf(process);
        U_Units += Unit_TrackerCount.remove(index);
        Unit_Tracker.remove(process);
    }
    public Process Unblock() {
        if(!Request_List.empty() && Request_List[0] <= U_Units)
        {
            Process toReturn = Blocked_List.remove(0);
            Unit_Tracker.add(toReturn);
            U_Units -= Request_List.get(0);
            Request_List.remove(0);
            return toReturn;
        }
        return null;
    }
    public void removeProcess(ArrayList<Process> processes) {
        //This should be fun!
    }
}
