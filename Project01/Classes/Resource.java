package Classes;
import java.util.*;
import Classes.myUtils.Pair;

public class Resource {
    public String RID;
    public Integer K_Units; //Maximum number of units.
    public Integer U_Units; //Available number of units.
    public ArrayList<Process> Process_List; //Processes holding resources.
    public HashMap<Process, Integer> Units_Per_Process; //Units held by process.
    public ArrayList<Process> Blocked_List; //Processes waiting for a resource.
    public HashMap<Process, Integer> Units_Per_Request; //Units Requested by blocked processes.
    public Resource(String rid, Integer k_units) {
        RID = rid;
        K_Units = U_Units = k_units;
        Process_List = new ArrayList<Process>();
        Units_Per_Process = new HashMap<Process,Integer>();
        Blocked_List = new ArrayList<Process>();
        Units_Per_Request = new HashMap<Process, Integer>();
    }
    public boolean Request(Process process, Integer units) { //Return false if placed on block list.
        if(U_Units >= units) { //Enough units available for allocation.
            U_Units -= units;
            Process_List.add(process);
            Units_Per_Process.put(process, units);
            return true;
        }
        //else Not enough units. Place on blocked list.
        Blocked_List.add(process);
        Units_Per_Request.put(process, units);
        return false;
    }
    public void Release(Process process) {
        if(Units_Per_Process.containsKey(process))
            U_Units += Units_Per_Process.remove(Process_List.remove(process));
    }
    public Process Unblock() {
        if(!Blocked_List.isEmpty())
        {
            Integer units = Units_Per_Request.get(Blocked_List.get(0));
            if(units <= U_Units) {
                Process toReturn = Blocked_List.remove(0);
                Process_List.add(toReturn);
                Units_Per_Process.put(toReturn, units);
                U_Units -= units;
                Units_Per_Request.remove(toReturn);
                return toReturn;
            }
        }
        return null;
    }
    public void removeProcesses(ArrayList<Process> processes) {
        for(int i = 0; i < processes.size(); i++) {
            Integer units = Units_Per_Process.remove(processes.get(i));
            if(units != null)
                U_Units += units;
            Units_Per_Request.remove(processes.get(i));
        }
        Process_List.removeAll(processes);
        Blocked_List.removeAll(processes);
    }
}
