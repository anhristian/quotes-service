## REST Endpoints  

//(this doc have to be in our fun-run docs)

* `/quotes`

    * `GET`
    
        * Request: (none)
        * Response: 
            * Body: `Quote[]`
    
    * `POST`
    
        * Request: 
            * Body: `Quote`
        * Response: 
            * Body: `Quote`
        
* `/quotes/search`

    * `GET`
    
        * Request:
            * Query: 
                * `q`: `String`
        * Response:
            * Body: `Quote[]` 
                
* `/quotes/{id}`

    * `GET`
    
        * Request
            * Path: 
                `id`: `long`
                
        * Response:
            * Body: `Quote`

* `/sources`

    * `GET`
    
    * `POST`          
    
* `/sources/{id}`

    * `GET`
    
    * `PUT`
    * `DELETE`
            
  ///And do this for all entities Source and Tags          