
import java.util.LinkedList;
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
public class Buffer {
    
    public Queue<Operation> the_queue;
    public int max_size, num_producers, num_consumers;
    public Consumer consumer;
    public Producer producer;
    
    public Buffer(int max_size){
        this.max_size = max_size;
        this.the_queue = new LinkedList<>();
    }
    
    synchronized public String produceOperation(int id){
        Operation operation = new Operation();
        if(this.the_queue.size() >= this.max_size){
            try {
                //Thread.sleep(this.wait_mills);
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
            //return "";
        }
            this.the_queue.offer(operation);
            notifyAll();
            System.out.println("I " + id + " produced: " + operation);
            return operation.toString();
        
    }
  
   
    
    synchronized public String[] consumeOperation(int id){
        while(true){
            if(this.the_queue.isEmpty()){
                try {
                    //Thread.sleep(this.wait_mills);
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
               Operation operation = this.the_queue.poll();
               double solution = operation.resolve();
               notifyAll();
               System.out.println("I " + id + " consumed: " + operation + " and the solution was: " + solution);
               //return ("I " + id + " consumed: " + operation + " and the solution was: " + solution);
               String[] ret = {operation.toString(), "" + solution };
               return ret;
            }
        }
        
        
    }
    /**
     *
     * @param num
     */
    public void setNumberOfProducers(int num){
        this.num_producers = num;
    } 
    
    public void setNumberOfConsumers(int num){
        this.num_consumers = num;
    }
    
    @SuppressWarnings("static-access")
    public static void main(String[] args){
   
        //Start GUI: 
        MyGUIFrame gui = new MyGUIFrame();
        gui.setVisible(true);
        javax.swing.JButton start_button = gui.getStartButton();
        
        start_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            //Start Buffer:
            Buffer buf = new Buffer(gui.getMaxSizeOfBuffer());
            buf.setNumberOfProducers(gui.getNumberOfProducers());
            buf.setNumberOfConsumers(gui.getNumberOfConsumers());
            buf.produceAndConsume(gui);
        });
    }
    
    public void produceAndConsume(MyGUIFrame gui){
        for(int i = 1; i <= this.num_producers; i++){
            new Producer(i, this, this.the_queue, this.max_size, gui.getToDoTable()).start();
        }
        for(int j = 1; j <= this.num_consumers; j++){
            new Consumer(j, this, this.the_queue, this.max_size, gui.getToDoTable(), gui.getDoneTable()).start();
        }
    }
}
