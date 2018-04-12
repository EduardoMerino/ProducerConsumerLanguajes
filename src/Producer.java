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
public class Producer extends Thread{
    
    public int id;
    public Buffer buffer;
    public Queue<Operation> the_queue;
    public int max_size_of_queue;
    public final int wait_mills = 300;
    javax.swing.table.DefaultTableModel toDoTable;
    
    public Producer(int id, Buffer buffer, Queue<Operation> the_queue, int size, javax.swing.table.DefaultTableModel toDoTable){
        this.id = id;
        this.buffer = buffer;
        this.the_queue = the_queue;
        this.max_size_of_queue = size;
        this.toDoTable = toDoTable;
    }
    
    @Override
    public void run(){
        while(this.max_size_of_queue <= this.buffer.max_size) {
            String operation = buffer.produceOperation(this.id);
            buffer.max_size++;
            this.toDoTable.addRow(new Object[]{this.id, operation});    
            try {
                Thread.sleep(this.wait_mills);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}