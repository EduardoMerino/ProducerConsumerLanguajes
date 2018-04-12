import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Merino
 */
public class Consumer extends Thread{
    
    public int id;
    public Buffer buffer;
    public Operation operation;
    public Queue<Operation> the_queue;
    public int max_size_of_queue;
    public final int wait_mills = 100;
    javax.swing.table.DefaultTableModel toDoTable;
    javax.swing.table.DefaultTableModel doneTable;
    
    public Consumer(int id, Buffer buffer, Queue<Operation> the_queue, int size, javax.swing.table.DefaultTableModel toDoTable, javax.swing.table.DefaultTableModel doneTable){
        this.id = id;
        this.buffer = buffer;
        this.the_queue = the_queue;
        this.max_size_of_queue = size;
        this.toDoTable = toDoTable;
        this.doneTable = doneTable;
    }
    
    @Override
    public void run(){
        while(true) {
            String[] solution = buffer.consumeOperation(this.id);
            this.toDoTable.removeRow(0);
            buffer.max_size = buffer.max_size - 1;
            this.doneTable.insertRow(0, new Object[]{ this.id, solution[0], solution[1]});
             try {
                 Thread.sleep(this.wait_mills);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
