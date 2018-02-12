package Classes;
import java.util.*;
import Classes.myUtils.Pair;

public class Resources {
    Resource r1;
    Resource r2;
    Resource r3;
    Resource r4;
    public Resources() {
        r1 = new Resource("R1", 1);
        r2 = new Resource("R2", 2);
        r3 = new Resource("R3", 3);
        r4 = new Resource("R4", 4);
    }
    public Resource getResource(String rid) {
        switch(rid) {
            case "R1":
                       return r1;
            case "R2":
                       return r2;
            case "R3":
                       return r3;
            case "R4":
                       return r4;
            default  :
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
    public void printList() {
        r1.print();
        r2.print();
        r3.print();
        r4.print();
    }
}
