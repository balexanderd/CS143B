import java.util.*;
import Classes.Process;
import Classes.Resource;
import Classes.ReadyList;
import Classes.Resources;
import java.nio.file.*;
import java.io.IOException;
import java.io.FileOutputStream;

public class Scheduler {
    public static void print(Boolean useFile, FileOutputStream outputStream, String output) {
       if(useFile == false) //Write to console.
            System.out.print(output);
        else {               //Write to file.
            try {
                outputStream.write(output.getBytes());
            } catch(IOException e) {
                System.out.println(e);
            }
        } 
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FileOutputStream outputStream = null;
        Boolean useFile = false;
        Boolean errorSet = false;
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
        Process ready;
        processes.put(currentProcess.PID, currentProcess);
        do{
            if(!errorSet) print(useFile, outputStream, currentProcess.PID + ' ');
            else errorSet = false;
            currentCommand = sc.next();
            ArrayList<Process> children = new ArrayList<Process>();
            while(!( currentCommand.equals("cr") || currentCommand.equals("to") ||
                     currentCommand.equals("req") || currentCommand.equals("rel") ||
                     currentCommand.equals("de") || currentCommand.equals("init") ||
                     currentCommand.equals("print_proc") || currentCommand.equals("print_read") ||
                     currentCommand.equals("print_reso") || currentCommand.equals("file") )) {
                print(useFile, outputStream, "error ");
                sc.nextLine();
                currentCommand = sc.next();
            }
            switch(currentCommand) {
                case "cr"  :
                              newPID = sc.next();
                              newPriority = sc.nextInt();
                              if(newPriority > 0 && newPriority < 3 && !processes.containsKey(newPID)) {
                                  Process newProcess = new Process( newPID, newPriority, currentProcess,
                                                                    1, readylist.sizeOf(newPriority) );
                                  currentProcess.addChild(newProcess);
                                  readylist.addProcess(newProcess);
                                  processes.put(newPID, newProcess);
                                  if(newPriority > currentProcess.Priority) {
                                      readylist.addProcess(currentProcess);
                                      currentProcess = readylist.getNextProcess();
                                  }
                              }
                              else {
                                  print(useFile, outputStream, "error ");
                                  errorSet = true;
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
                              if(!currentProcess.equals(readylist.Init) && newUnits > 0 &&
                              currentResource != null && newUnits <= currentResource.K_Units) {
                                  if(!currentResource.Request(currentProcess, newUnits)) {
                                      readylist.removeProcess(currentProcess);
                                      currentProcess = readylist.getNextProcess();
                                  }
                              }
                              else {
                                  print(useFile, outputStream, "error ");
                                  errorSet = true;
                              }
                              break;
                case "rel" :
                              newRID = sc.next();
                              newUnits = sc.nextInt();
                              currentResource = resources.getResource(newRID);
                              if(!currentProcess.equals(readylist.Init) && currentResource != null &&
                              currentResource.Units_Per_Process.containsKey(currentProcess) &&
                              newUnits > 0 && newUnits <= currentResource.K_Units &&
                              newUnits <= currentResource.Units_Per_Process.get(currentProcess)) {
                                  currentResource.Release(currentProcess, newUnits);
                                  ready = resources.Unblock();
                                  while(ready != null) { //Unblock processes if resources are free.
                                      readylist.addProcess(ready);
                                      ready = resources.Unblock();
                                  }
                                  if( currentProcess.Priority < readylist.peekPriority() ) {
                                      readylist.addProcess(currentProcess);
                                      currentProcess = readylist.getNextProcess();
                                  }
                              }
                              else {
                                  print(useFile, outputStream, "error ");
                                  errorSet = true;
                              }
                              break;
                case "de"  :
                              newPID = sc.next();
                              Process toDelete = processes.get(newPID);
                              if(toDelete != null && !toDelete.equals(currentProcess))
                                  toDelete = currentProcess.removeChild(toDelete);
                              if(toDelete != null && !toDelete.equals(readylist.Init)) {
                                  processes.remove(newPID);
                                  toDelete.Creation_Tree.first.removeChild(toDelete); //remove process from parent.
                                  readylist.deleteProcess(toDelete); //take process and all children off readylist.
                                  children = toDelete.getChildren(); //get children.
                                  children.add(toDelete);
                                  resources.RemoveAll(children); //take process and all children off resources.
                                  for(int i = 0; i < children.size(); i++)
                                      processes.remove(children.get(i).PID); //remove processes from map.
                                  ready = resources.Unblock();
                                  while(ready != null) { //Unblock processes if resources are free.
                                      readylist.addProcess(ready);
                                      ready = resources.Unblock();
                                  }
                                  if( !processes.containsKey(currentProcess.PID) ||
                                      currentProcess.Priority < readylist.peekPriority() )//Preempt.
                                      currentProcess = readylist.getNextProcess();
                              }
                              else {
                                  print(useFile, outputStream, "error ");
                                  errorSet = true;
                              }
                              break;
                case "init": 
                              readylist = new ReadyList();
                              resources = new Resources();
                              currentProcess = readylist.getNextProcess();
                              print(useFile, outputStream, "\r\n");
                              break;
                case "print_proc":
                              children.add(readylist.Init);
                              for(int i = 0; i < children.size(); i++) {
                                  print(useFile, outputStream, children.get(i).Creation_Tree.first.PID + ":" + children.get(i).PID + ' ');
                                  children.addAll(children.get(i).Creation_Tree.second);
                              }
                              print(useFile, outputStream, "\r\n");
                              break;
                case "print_read":
                              readylist.printList();
                              break;
                case "print_reso":
                              resources.printList();
                              break;
                case "file":
                             String in_filename = sc.next();
                             String out_filename;
                             if(sc.hasNext())
                                 out_filename = sc.next();
                             else
                                 out_filename = in_filename.substring(0, in_filename.indexOf(".")) + ".out";
                             try {
                                 Path filePath = Paths.get(in_filename);
                                 outputStream = new FileOutputStream(out_filename);
                                 //outputStream.write((currentProcess.PID + ' ').getBytes());
                                 sc = new Scanner(filePath);
                                 useFile = true;
                             } catch(IOException e) {
                                 System.out.println("Error: File not found -- \"" + in_filename + "\"");
                                 useFile = false;
                                 sc = new Scanner(System.in);
                             }
                             break;
                default    :             
                             break;
            }
            if(useFile && !(sc.hasNext())) {
                try {
                    print(useFile, outputStream, currentProcess.PID + ' ');
                    outputStream.close();
                } catch(IOException e) {
                    System.out.println(e);
                }
                useFile = false;
                sc = new Scanner(System.in);
            }
        } while(!readylist.isEmpty());
    }
}
