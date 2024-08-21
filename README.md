# Read Me First

This project is designed to help Java developers get started with Spring AI with just a few lines of code and minimal effort.

I use this repository's free (Ollama) and paid (OpenAI) AI tools.

In this repository, I have created six branches, each containing separate examples. Users need to switch branches to perform tasks based on the below descriptions.

Read this blog [AI for Java Developer](https://medium.com/@jai.mail67/ai-for-java-developers-embracing-the-world-of-artificial-intelligence-93d47bdf0e49) for overivew .


## Branch Names
   - main
  - 1.OllamaChatModel
  - 2.OpenAIChatModel
  - 3.OpenAIAndOllamaChatModel
  - 4.OpenAIImageGeneration
  - 5.RAGUsingOpenAI


## What's inside 

This project is based on the [Spring AI](https://spring.io/projects/spring-ai) project and uses these packages :

- Maven
- Spring AI
- Java 17
  
## Installation and api key
 - The project is created with Maven, so you just need to import it to your IDE and build the project to resolve the dependencies
  
 - This project required [Ollama](https://ollama.com/) , in local we can run our GPT model :
   
        * ollama run llama3 
        * ollama run  mistral
   
- To integrate with other paid available  service we need API key, in this example I used Open AI, so u can use and create an API key


## Ollama configuration for spring boot 
add this property in application.properties

- spring.ai.ollama.base-url=http://localhost:11434/



## Usage for main branch 

Run the project through the IDE and head out to [http://localhost:8080](http://localhost:8080) 

For main branch do the build , start server and import below 3 curl commpand in post man to check ur first AI code .

```shell

 curl --location --request GET 'http://localhost:8080/ai?userInput=tell me a joke ' \
--header 'Content-Type: application/json' \
--data-raw ''
```

```shell
 curl --location --request GET 'http://localhost:8080/aiChatResponse?userInput=tell me a joke ' \
--header 'Content-Type: application/json' \
--data-raw ''
```

```shell
 curl --location --request GET 'http://localhost:8080/aiEntityResponse' \
--header 'Content-Type: application/json' \
--data-raw ''

```



### other branch setup  TBD


    



