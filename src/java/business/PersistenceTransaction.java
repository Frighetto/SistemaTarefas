/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.util.List;
import org.hibernate.SQLQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Lucas Fernando Frighetto
 * @param <TYPE>
 */
public class PersistenceTransaction <TYPE> {
    
    private Session session;
    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static final PersistenceTransaction instance = new PersistenceTransaction();

    private PersistenceTransaction() {}

    public static synchronized PersistenceTransaction instance() {
        return instance;
    }
    
    public void create(Object o) {
        session = sessionFactory.openSession();
        session.beginTransaction();         
        session.save(o);     
        session.getTransaction().commit();        
        session.close();
    }
    
    public TYPE read(Class<TYPE> type, Serializable id) {
        session = sessionFactory.openSession();
        session.beginTransaction();            
        TYPE valueObject = session.get(type, id);        
        session.getTransaction().commit();
        session.close();
        return valueObject;
    }                
    
    public void update(Object o) {
        session = sessionFactory.openSession();
        session.beginTransaction();               
        session.update(o);        
        session.getTransaction().commit();        
        session.close();
    }
    
    public void delete(Object o) {        
        session = sessionFactory.openSession();
        session.beginTransaction();          
        session.delete(o);
        session.getTransaction().commit();
        session.close();
    }
    
    public List<Tarefa> tarefas(){        
        session = sessionFactory.openSession();
        session.beginTransaction();        
        SQLQuery query = session.createSQLQuery("SELECT * FROM TAREFA ORDER BY ordem");
        query.addEntity(Tarefa.class);     
        List<Tarefa> tarefas = query.list();        
        session.close();
        return tarefas;
    }
    
    public Tarefa tarefaByName(String name){
        session = sessionFactory.openSession();
        session.beginTransaction();        
        SQLQuery query = session.createSQLQuery("SELECT * FROM TAREFA WHERE UPPER(nome) =:nometarefa");
        query.setParameter("nometarefa", name.toUpperCase());
        query.addEntity(Tarefa.class); 
        if(query.list().isEmpty()){
            return null;
        }
        Tarefa tarefa = (Tarefa) query.list().get(0);        
        session.close();
        return tarefa;
    }
    
    public Integer ultimaOrdem(){           
        session = sessionFactory.openSession();
        session.beginTransaction();        
        SQLQuery query = session.createSQLQuery("SELECT COALESCE(MAX(ORDEM), 0) + 1 FROM TAREFA");
        Integer ultimaOrdem = (Integer) query.list().get(0);
        session.close();
        return ultimaOrdem;
    }
    
    public Tarefa trocar_ordem(Integer ordem, String sentido){
        session = sessionFactory.openSession();
        session.beginTransaction();        
        String desc = sentido.equalsIgnoreCase("<") ? "DESC" : "";
        SQLQuery query = session.createSQLQuery("SELECT * FROM TAREFA WHERE ordem " + sentido + ":ordemtarefa ORDER BY ordem " + desc + " FETCH FIRST 1 ROWS ONLY");
        query.setParameter("ordemtarefa", ordem);
        query.addEntity(Tarefa.class); 
        if(query.list().isEmpty()){
            return null;
        }
        Tarefa tarefa = (Tarefa) query.list().get(0);        
        session.close();
        return tarefa;
    }
        
}