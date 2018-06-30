# API REST Star Wars

## Tecnologias Utilizadas
* Spring Boot
* Spring Data Mongodb
* Spring MVC
* JUnit
* Mongodb

## Endpoints
* **POST:** http://localhost:8080/planets  
Cria um novo planeta no sistema 
  * Parâmetros (Body)   
  **string**: name - O nome do planeta  
  **string**: climate - O clima do planeta  
  **string**: terrain - O terreno do planeta  
  **int**: apparitionsInFilms - A quantidade de vezes que o planeta apareceu em filmes
  
  formato JSON: 
  
  {
	 "name":"Tatooine",
	 "climate":"arid",
	 "terrain":"desert",
	 "apparitionsInFilms":5
 }

* **GET:** http://localhost:8080/planets  
Recupera a lista de planetas do sistema
  * Parâmetros (Query String)  
  **string**: name - Filtra os planetas por nome (opcional)
     
* **GET:** http://localhost:8080/planets/{id}  
Recupera um planeta pelo seu ID

* **DELETE:** http://localhost:8080/planets/{id}  
Remove do sistema o planeta pelo seu ID

