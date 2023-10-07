/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author lucas
 */
public class StringFormat {
    
    public static String dataFormatada (String data){        
        String ano = data.substring(0, data.indexOf("-"));
        data = data.substring(data.indexOf("-") + 1);
        String mes = data.substring(0, data.indexOf("-"));
        String dia = data.substring(data.indexOf("-") + 1);        
        return dia + "/" + mes + "/" + ano;        
    }
}
