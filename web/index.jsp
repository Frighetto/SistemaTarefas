<%@page import="java.util.List"%>
<%@page import="business.Tarefa"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Sistema de Lista de Tarefas</title>
        <meta charset="utf-8">        
        <meta name="viewport" content="width=device-width, initial-scale=1"> 
        <meta name="author" content="Lucas Fernando Frighetto">        
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="bootstrap.css" rel="stylesheet">  
    </head>
    <body>
        <div class="container">   
<%= request.getAttribute("feedback") == null ? "" : request.getAttribute("feedback") %>
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>                    
                        <th>Nome da Tarefa</th>
                        <th>Custo $</th>
                        <th>Data Limite</th>                        
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>   
                    <form method="POST" action="SalvarTarefa"> 
                        <tr>    
                            <td>                            
                                <input name="id" value="<%= request.getAttribute("id") == null ? "" : request.getAttribute("id") %>" type="number" class="form-control" placeholder="ID" readonly required>
                            </td> 
                            <td>                            
                                <input name="nome" value="<%= request.getAttribute("nome") == null ? "" : request.getAttribute("nome") %>" type="text" class="form-control" placeholder="Nome da Tarefa" maxlength="50" required>
                            </td> 
                            <td>                            
                                <input name="custo" value="<%= request.getAttribute("custo") == null ? "" : request.getAttribute("custo") %>" type="number" class="form-control" placeholder="Custo $" required>
                            </td> 
                            <td>                            
                                <input name="data" value="<%= request.getAttribute("data") == null ? "" : request.getAttribute("data") %>" type="date" class="form-control" placeholder="Data Limite" required>
                            </td>                                       
                            <td>
                                <button class="btn btn-primary" type="submit">
                                    <i class="fa fa-save" style="font-size:24px;"></i>                                    
                                </button>
                            </td>    
                            <th></th>
                            <th></th>
                        </tr>
                    </form>                                     
<% 
    List<Tarefa> tarefas = business.Tarefas.read(); 
    for(Tarefa tarefa : tarefas){
%>
                    <tr <%= tarefa.getCusto() >= 1000 ? "style=\"background-color: green\"" : "" %>>                            
                        <td><%= tarefa.getId() %></td>                            
                        <td><%= tarefa.getNome()%></td>
                        <td><%= tarefa.getCusto()%></td>
                        <td><%= business.StringFormat.dataFormatada(tarefa.getData()) %></td>   
                        <td>
                            <form method="POST" action="AlterarTarefa"> 
                                <button class="btn btn-primary" type="submit" name="id" value="<%= tarefa.getId() %>">
                                    <i class="fa fa-edit" style="font-size:24px;"></i>                                    
                                </button>
                            </form>
                        </td>
                        <td>
                            <form method="POST" action="DeletarTarefa"> 
                                <button id="deletar-<%= tarefa.getId() %>" class="btn btn-danger" type="submit" name="id" value="<%= tarefa.getId() %>">
                                    <i class="fa fa-trash" style="font-size:24px;"></i>                                    
                                </button>
                            </td>
                        </form>
                        <td>                 
                            <form method="POST" action="ApresentarTarefa"> 
                                <button class="btn btn-success" type="submit" name="promover" value="<%= tarefa.getId() %>" <%= tarefa.getId() == tarefas.get(0).getId() ? "disabled" : "" %>>
                                    <i class="fa fa-arrow-up" style="font-size:24px;"></i>
                                </button>  
                                <button class="btn btn-success" type="submit" name="rebaixar" value="<%= tarefa.getId() %>" <%= tarefa.getId() == tarefas.get(tarefas.size() - 1).getId() ? "disabled" : "" %>>
                                    <i class="fa fa-arrow-down" style="font-size:24px;"></i>
                                </button>  
                            </form>
                        </td>                        
                    </tr>  
<%
    }
%>
                </tbody>
            </table>
        </div>
        <script>
<%
    
    if(request.getAttribute("deletar") != null){
%>    
        if (window.confirm("Tem certeza que deseja deletar a tarefa <%=request.getAttribute("deletar")%>?")) {
            var deleteButton = document.getElementById("deletar-<%=request.getAttribute("deletar")%>");
            deleteButton.setAttribute("name", "confirm_id");
            deleteButton.click();
        }
<%                    
    } 
%>
        </script>
    </body>
</html>
