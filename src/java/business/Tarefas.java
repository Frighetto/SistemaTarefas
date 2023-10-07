/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.List;

/**
 *
 * @author lucas
 */
public class Tarefas {
    
    public static Boolean verifyUnique(Long id, String nome){
        Tarefa tarefa = PersistenceTransaction.instance().tarefaByName(nome);
        return tarefa == null || id == tarefa.getId();       
    }
    
    public static void create(String nome, Float custo, String data){
        Tarefa tarefa = new Tarefa();        
        tarefa.setNome(nome);
        tarefa.setCusto(custo);
        tarefa.setData(data);        
        tarefa.setOrdem(PersistenceTransaction.instance().ultimaOrdem());
        
        PersistenceTransaction.instance().create(tarefa);                
    }
    
    public static List<Tarefa> read(){
        return PersistenceTransaction.instance().tarefas();
    }
         
    public static void update(Long id, String nome, Float custo, String data) {
        Tarefa tarefa = (Tarefa) PersistenceTransaction.instance().read(Tarefa.class, id);
        tarefa.setNome(nome);
        tarefa.setCusto(custo);
        tarefa.setData(data);        
        
        PersistenceTransaction.instance().update(tarefa);               
    }
    
    public static void delete(Long id){
        Tarefa tarefa = new Tarefa();      
        tarefa.setId(id);
        PersistenceTransaction.instance().delete(tarefa);
    }
    
    public static void trocarOrdem(Long id, String sentido){
        Tarefa tarefa = (Tarefa) PersistenceTransaction.instance().read(Tarefa.class, id);
        Tarefa outra_tarefa = (Tarefa) PersistenceTransaction.instance().trocar_ordem(tarefa.getOrdem(), sentido);  
        
        Integer ordem = tarefa.getOrdem();
        tarefa.setOrdem(outra_tarefa.getOrdem());
        outra_tarefa.setOrdem(ordem);
        
        PersistenceTransaction.instance().update(tarefa); 
        PersistenceTransaction.instance().update(outra_tarefa);  
    }          
            
}
