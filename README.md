# YouQuiz API
**YouQuiz** is An interactive platform that empowers trainers to design engaging quizzes, while providing students the opportunity to take and excel in these quizzes.

## Tech Stack

[![Back-end](https://skillicons.dev/icons?i=java,spring,hibernate,postgres,docker)](https://skillicons.dev)

## API Reference

### <ins>Response</ins>

#### Success

When fetching the data successfully, you will receive this response:
```json
{
    "data": {/* Fetched data */},
    "message": "Success message",
    "status": 200 // HTTP status code (201 for creating new resource, else 200)
}
```

#### Errors

##### In case of exception:
When an unexpected error occurs during request processing, you will receive this response:
```json
{
    "cause": {/* Optional: The cause of this exception, if available */},
    "message": "Exception message",
    "status": 404 // HTTP status code (can be adjusted for specific exceptions)
}
```

##### In case of invalid data:
When submitting invalid data in a request, you will receive this response:
```json
{
    "errors": {/* The invalid fields with specific error messages */},
    "message": "Error message",
    "status": 422 // HTTP status code (can be adjusted for specific errors)
}
```
---
### <ins>Answers</ins>

#### The answer request object:
An example of the answer request object when creating or updating an answer:
```json
{
    "id": 3, // Required only when updating an answer
    "answer": "What is JDK?"
}
```

#### Get an answer:
```http
  GET /api/answers/:id
```

| Parameters   | Description                                                                                 |
|:-------------|:--------------------------------------------------------------------------------------------|
| No parameter | Returns an answer object if a valid identifier was provided, and throws an error otherwise. |

#### Get all answers:
```http
  GET /api/answers
```

| Query Parameters                                                                                    | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
|:----------------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `text`: string<br/>`page`: integer<br/>`size`: integer<br/>`sortBy`: string<br/>`sortOrder`: string | **text:** To search for an answer, default is `""`.<br/>**page:** Number of the page (starting from 0), default is `0`.<br/>**size:** Number of answers in a single page, default is `24`.<br/>**sortBy:** name of the attribute to sort by (`id` or `answer`), default is `"id"`.<br/>**sortOrder:** Sorting in ascending (`ASC`) or descending (`DESC`) order, default is `"ASC"`.<br/>Returns an answer object if a valid identifier was provided, and throws an error otherwise. |

#### Create an answer:
```http
  POST /api/answers/add
```

| Parameters       | Description                                                                                          |
|:-----------------|:-----------------------------------------------------------------------------------------------------|
| `answer`: object | Returns an answer object if a valid object was provided, and throws an error or exception otherwise. |

#### Update an answer:
```http
  PUT /api/answers/update
```

| Parameters       | Description                                                                                          |
|:-----------------|:-----------------------------------------------------------------------------------------------------|
| `answer`: object | Returns an answer object if a valid object was provided, and throws an error or exception otherwise. |

#### Delete an answer:
```http
  DELETE /api/answers/:id
```

| Parameters   | Description                                                                                                |
|:-------------|:-----------------------------------------------------------------------------------------------------------|
| No parameter | Returns an object of the deleted answer if a valid identifier was provided, and throws an error otherwise. |
---


