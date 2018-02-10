package Classes;
import java.util.*;
import Classes.myUtils.Pair;

public class Resources {
    Resource r1;
    Resource r2;
    Resource r3;
    Resource r4;
    public Resources() {
        r1 = new Resource("r1", 1);
        r2 = new Resource("r2", 2);
        r3 = new Resource("r3", 3);
        r4 = new Resource("r4", 4);
    }
    public Resource getResource(String rid) {
        switch(rid) {
            case "r1":
                       return r1;
            case "r2":
                       return r2;
            case "r3":
                       return r3;
            case "r4":
                       return r4;
            default  :
                       System.out.print("Error ");
                       break;
        }
        return null;
    }
    public void RemoveAll(ArrayList<Process> processes) {
        r1.removeProcesses(processes);
        r2.removeProcesses(processes);
        r3.removeProcesses(processes);
        r4.removeProcesses(processes);
    }
    public Process Unblock() {
        Process toReturn = r1.Unblock();
        if(toReturn == null) {
            toReturn = r2.Unblock();
            if(toReturn == null) {
                toReturn = r3.Unblock();
                if(toReturn == null) {
                    toReturn = r4.Unblock();
                }
            }
        }
        return toReturn;    
    }
}
