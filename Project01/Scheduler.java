import java.util.*;
import Classes.Process;
import Classes.Resource;
import Classes.ReadyList;
import Classes.Resources;

public class Scheduler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String currentCommand;
        String newPID;
        String newRID;
        Integer newPriority;
        Integer newUnits;
        HashMap<String, Process> processes = new HashMap<String, Process>();
        Resources resources = new Resources();
        ReadyList readylist = new ReadyList();
        Resource currentResource;
        Process currentProcess = readylist.getNextProcess();
        processes.put(currentProcess.PID, currentProcess);
        do{
            System.out.print(currentProcess.PID + ' ');
            currentCommand = sc.next();
            ArrayList<Process> children = new ArrayList<Process>();
            while(!( currentCommand.equals("cr") || currentCommand.equals("to") ||
                     currentCommand.equals("req") || currentCommand.equals("de") ||
                     currentCommand.equals("init") || currentCommand.equals("print") )) {
                System.out.print("Error ");
                currentCommand = sc.next();
            }
            switch(currentCommand) {
                case "cr"  :
                              newPID = sc.next();
                              newPriority = sc.nextInt();
                              Process newProcess = new Process( newPID, newPriority, currentProcess,
                                                                1, readylist.sizeOf(newPriority) );
                              currentProcess.addChild(newProcess);
                              readylist.addProcess(newProcess);
                              processes.put(newPID, newProcess);
                              if(newPriority > currentProcess.Priority) {
                                  readylist.addProcess(currentProcess);
                                  currentProcess = readylist.getNextProcess();
                              }
                              break;
                case "to"  :
                              readylist.addProcess(currentProcess);
                              currentProcess = readylist.getNextProcess();
                              break;
                case "req" :
                              newRID = sc.next();
                              newUnits = sc.nextInt();
                              currentResource = resources.getResource(newRID);
                              if(!currentResource.Request(currentProcess, newUnits)) {
                                  readylist.removeProcess(currentProcess);
                                  currentProcess = readylist.getNextProcess();
                              }
                              break;
                case "de"  :
                              newPID = sc.next();
                              Process toDelete = processes.remove(newPID);
                              if(toDelete != null) {
                                  toDelete.Creation_Tree.first.removeChild(toDelete); //remove process from parent.
                                  readylist.removeProcess(toDelete); //take process and all children off readylist.
                                  children = toDelete.getChildren(); //get children.
                                  children.add(toDelete);
                                  resources.RemoveAll(children); //take process and all children off resources.
                                  for(int i = 0; i < children.size(); i++)
                                      processes.remove(children.get(i).PID); //remove processes from map.
                                  Process ready = resources.Unblock();
                                  while(ready != null) { //Unblock processes if resources are free.
                                      readylist.addProcess(ready);
                                      ready = resources.Unblock();
                                  }
                                  if( !processes.containsKey(currentProcess.PID) ||
                                      currentProcess.Priority < readylist.peekPriority() )//Preempt.
                                      currentProcess = readylist.getNextProcess();
                              }
                              break;
                case "init": 
                              readylist = new ReadyList();
                              resources = new Resources();
                              currentProcess = readylist.getNextProcess();
                              break;
                case "print":
                              children.add(readylist.Init);
                              for(int i = 0; i < children.size(); i++) {
                                  System.out.print(children.get(i).Creation_Tree.first.PID + ":" + children.get(i).PID + ' ');
                                  children.addAll(children.get(i).Creation_Tree.second);
                              }
                              System.out.println();
                              break;
                default    :             
                             break;
            }
        } while(!readylist.isEmpty());
    }
}
