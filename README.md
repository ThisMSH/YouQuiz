# YouQuiz API
**YouQuiz** is An interactive platform that empowers trainers to design engaging quizzes, while providing students the opportunity to take and excel in these quizzes.

## Tech Stack

[![Back-end](https://skillicons.dev/icons?i=java,spring,hibernate,postgres,docker)](https://skillicons.dev)

## API Reference

### <ins>Response</ins>

* #### Success

    When fetching the data successfully, you will receive this response:
    ```js
    {
        "data": {/* Fetched data */},
        "message": "Success message",
        "status": 200 // HTTP status code (201 for creating new resource, else 200)
    }
    ```

* #### Errors
    * ##### In case of exception:
      When an unexpected error occurs during request processing, you will receive this response:
      ```js
      {
          "cause": {/* Optional: The cause of this exception, if available */},
          "message": "Exception message",
          "status": 404 // HTTP status code (can be adjusted for specific exceptions)
      }
      ```
    
    * ##### In case of invalid data:
      When submitting invalid data in a request, you will receive this response:
      ```js
      {
          "errors": {/* The invalid fields with specific error messages */},
          "message": "Error message",
          "status": 422 // HTTP status code (can be adjusted for specific errors)
      }
      ```
---
### <ins>Answers</ins>

* #### The answer request object:
    An example of the answer request object when creating or updating an answer:
    ```js
    {
        "id": 3, // Required only when updating an answer
        "answer": "A software development platform that provides all the tools and libraries ..."
    }
    ```

* #### Get an answer:
    ```http
      GET /api/answers/:id
    ```
    
    | Parameters   | Description                                                                                 |
    |:-------------|:--------------------------------------------------------------------------------------------|
    | No parameter | Returns an answer object if a valid identifier was provided, and throws an error otherwise. |

* #### Get all answers:
    ```http
      GET /api/answers
    ```
    
    | Query Parameters                                                                                    | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
    |:----------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | `text`: string<br/>`page`: integer<br/>`size`: integer<br/>`sortBy`: string<br/>`sortOrder`: string | **text:** To search for an answer, default is `""`.<br/>**page:** Number of the page (starting from 0), default is `0`.<br/>**size:** Number of answers in a single page, default is `24`.<br/>**sortBy:** name of the attribute to sort by (`id` or `answer`), default is `"id"`.<br/>**sortOrder:** Sorting in ascending (`ASC`) or descending (`DESC`) order, default is `"ASC"`.<br/>Returns an object that contains all the data about pagination & array of answers. |

* #### Create an answer:
    ```http
      POST /api/answers/add
    ```
    
    | Parameters       | Description                                                                                          |
    |:-----------------|:-----------------------------------------------------------------------------------------------------|
    | `answer`: object | Returns an answer object if a valid object was provided, and throws an error or exception otherwise. |

* #### Update an answer:
    ```http
      PUT /api/answers/update
    ```
    
    | Parameters       | Description                                                                                          |
    |:-----------------|:-----------------------------------------------------------------------------------------------------|
    | `answer`: object | Returns an answer object if a valid object was provided, and throws an error or exception otherwise. |

* #### Delete an answer:
    ```http
      DELETE /api/answers/:id
    ```
    
    | Parameters   | Description                                                                                                |
    |:-------------|:-----------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted answer if a valid identifier was provided, and throws an error otherwise. |
---

### <ins>Answer Validations</ins>

* #### The answer-validation request object:
    An example of the answer-validation request object when creating an answer-validation:
    ```js
    {
        "points": 3.4,
        "answerId": 2,
        "questionId": 1
    }
    ```

* #### Get an answer-validation:
  * ##### Get by ID:
    ```http
      GET /api/answers-assignment/pk/:id
    ```
    
    | Parameters   | Description                                                                                            |
    |:-------------|:-------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an answer-validation object if a valid identifier was provided, and throws an error otherwise. |

  * ##### Get by question & answer IDs:

    ```http
      GET /api/answers-assignment/fk/:questionId-:answerId
    ```
    
    | Parameters   | Description                                                                                              |
    |:-------------|:---------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an answer-validation object if a valid identifiers were provided, and throws an error otherwise. |

* #### Get a list of answer-validation objects:
  * ##### Get by question:
    ```http
      GET /api/answers-assignment/by-question/:questionId
    ```

    | Parameters   | Description                                                                                                    |
    |:-------------|:---------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns a list of answer-validation objects if a valid identifier was provided, and throws an error otherwise. |

  * ##### Get by answer:
    ```http
      GET /api/answers-assignment/by-answer/:answerId
    ```

    | Parameters   | Description                                                                                                    |
    |:-------------|:---------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns a list of answer-validation objects if a valid identifier was provided, and throws an error otherwise. |


* #### Create an answer-validation:
    ```http
      POST /api/answers-assignment/add
    ```
    
    | Parameters                 | Description                                                                                                     |
    |:---------------------------|:----------------------------------------------------------------------------------------------------------------|
    | `AnswerValidation`: object | Returns an answer-validation object if a valid object was provided, and throws an error or exception otherwise. |

* #### Delete an answer-validation:
  * ##### Delete by ID:
    ```http
      DELETE /api/answers-assignment/pk/:id
    ```
    
    | Parameters   | Description                                                                                                           |
    |:-------------|:----------------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted answer-validation if a valid identifier was provided, and throws an error otherwise. |

  * ##### Delete by question & answer IDs:
    ```http
      DELETE /api/answers-assignment/fk/:questionId-:answerId
    ```
    
    | Parameters   | Description                                                                                                             |
    |:-------------|:------------------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted answer-validation if a valid identifiers were provided, and throws an error otherwise. |
---

### <ins>Levels</ins>

* #### The level request object:
  An example of the level request object when creating or updating a level:
    ```js
    {
        "id": 3, // Required only when updating a level
        "title": "Medium",
        "description": "Optional description, can be empty.",
        "maxPoints": 29.9,
        "minPoints": 21.1
    }
    ```

* #### Get a level:
    ```http
      GET /api/levels/:id
    ```

    | Parameters   | Description                                                                               |
    |:-------------|:------------------------------------------------------------------------------------------|
    | No parameter | Returns a level object if a valid identifier was provided, and throws an error otherwise. |

* #### Get all levels:
    ```http
      GET /api/levels
    ```

    | Query Parameters                                                                                     | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
    |:-----------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | `title`: string<br/>`page`: integer<br/>`size`: integer<br/>`sortBy`: string<br/>`sortOrder`: string | **title:** To search for a level, default is `""`.<br/>**page:** Number of the page (starting from 0), default is `0`.<br/>**size:** Number of levels in a single page, default is `24`.<br/>**sortBy:** name of the attribute to sort by (`id`, `title`, `description`, `maxPoints` or `minPoints`), default is `"id"`.<br/>**sortOrder:** Sorting in ascending (`ASC`) or descending (`DESC`) order, default is `"ASC"`.<br/>Returns an object that contains all the data about pagination & array of levels. |

* #### Create a level:
    ```http
      POST /api/levels/add
    ```

    | Parameters      | Description                                                                                        |
    |:----------------|:---------------------------------------------------------------------------------------------------|
    | `level`: object | Returns a level object if a valid object was provided, and throws an error or exception otherwise. |

* #### Update a level:
    ```http
      PUT /api/levels/update
    ```

    | Parameters      | Description                                                                                        |
    |:----------------|:---------------------------------------------------------------------------------------------------|
    | `level`: object | Returns a level object if a valid object was provided, and throws an error or exception otherwise. |

* #### Delete a level:
    ```http
      DELETE /api/levels/:id
    ```

    | Parameters   | Description                                                                                               |
    |:-------------|:----------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted level if a valid identifier was provided, and throws an error otherwise. |
---

### <ins>Media</ins>

* #### The media request object:
  An example of the media request object when creating a media:
    ```js
    {
        "title": "A beautiful sunset",
        "type": "VIDEO" // type must be either "VIDEO", "IMAGE" or "AUDIO"
        "file": {/* Attached file with the request */},
        "questionId": 1
    }
    ```

* #### Get a media:
    ```http
      GET /api/media/:id
    ```

    | Parameters   | Description                                                                               |
    |:-------------|:------------------------------------------------------------------------------------------|
    | No parameter | Returns a media object if a valid identifier was provided, and throws an error otherwise. |

* #### Get media file:
    ```http
      GET /api/media/get/:file-name
    ```

    | Parameters   | Description                                                                      |
    |:-------------|:---------------------------------------------------------------------------------|
    | No parameter | Returns a file if a valid file name was provided, and throws an error otherwise. |

* #### Create a media:
    ```http
      POST /api/media/add
    ```

    | Parameters      | Description                                                                                        |
    |:----------------|:---------------------------------------------------------------------------------------------------|
    | `media`: object | Returns a media object if a valid object was provided, and throws an error or exception otherwise. |

* #### Delete a media:
    ```http
      DELETE /api/media/:id
    ```

    | Parameters   | Description                                                                                               |
    |:-------------|:----------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted media if a valid identifier was provided, and throws an error otherwise. |
---

### <ins>Questions</ins>

* #### The question request object:
  An example of the question request object when creating or updating a question:
    ```js
    {
        "id": 3, // Required only when updating a level
        "question": "What is JDK?",
        "description": "Optional description, can be empty.",
        "type": "SINGLE", // type must be either "SINGLE", "MULTI" or "DIRECT"
        "levelId": 3
        "subjectId": 4
    }
    ```

* #### Get a question:
    ```http
      GET /api/questions/:id
    ```

    | Parameters   | Description                                                                                  |
    |:-------------|:---------------------------------------------------------------------------------------------|
    | No parameter | Returns a question object if a valid identifier was provided, and throws an error otherwise. |

* #### Get all questions:
    ```http
      GET /api/questions
    ```

    | Query Parameters                                                                                                                                               | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
    |:---------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | `question`: string<br/>`type`: enum<br/>`level`: long<br/>`subject`: long<br/>`page`: integer<br/>`size`: integer<br/>`sortBy`: string<br/>`sortOrder`: string | **question:** To search for a question, default is `""`.<br/>**type:** To filter questions by their types (`SINGLE`, `MULTI`, `DIRECT` or empty string), default is `""`.<br/>**level:** To filter questions by the level, default is `0`.<br/>**subject:** To filter questions by the subject, default is `0`.<br/>**page:** Number of the page (starting from 0), default is `0`.<br/>**size:** Number of questions in a single page, default is `24`.<br/>**sortBy:** name of the attribute to sort by (`id`, `question`, `description`), default is `"id"`.<br/>**sortOrder:** Sorting in ascending (`ASC`) or descending (`DESC`) order, default is `"ASC"`.<br/>Returns an object that contains all the data about pagination & array of questions. |

* #### Create a question:
    ```http
      POST /api/questions/add
    ```

    | Parameters         | Description                                                                                           |
    |:-------------------|:------------------------------------------------------------------------------------------------------|
    | `question`: object | Returns a question object if a valid object was provided, and throws an error or exception otherwise. |

* #### Update a question:
    ```http
      PUT /api/questions/update
    ```

    | Parameters         | Description                                                                                           |
    |:-------------------|:------------------------------------------------------------------------------------------------------|
    | `question`: object | Returns a question object if a valid object was provided, and throws an error or exception otherwise. |

* #### Delete a question:
    ```http
      DELETE /api/questions/:id
    ```

    | Parameters   | Description                                                                                                  |
    |:-------------|:-------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted question if a valid identifier was provided, and throws an error otherwise. |
---

### <ins>Subjects</ins>

* #### The subject request object:
  An example of the subject request object when creating or updating a subject:
    ```js
    {
        "id": 3, // Required only when updating a subject
        "title": "Mathematics",
        "parent": 1 // can be null
    }
    ```

* #### Get a subject:
    ```http
      GET /api/subjects/:id
    ```

    | Parameters   | Description                                                                                 |
    |:-------------|:--------------------------------------------------------------------------------------------|
    | No parameter | Returns a subject object if a valid identifier was provided, and throws an error otherwise. |

* #### Get all subjects:
    ```http
      GET /api/subjects
    ```

    | Query Parameters                                                                                     | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
    |:-----------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | `title`: string<br/>`page`: integer<br/>`size`: integer<br/>`sortBy`: string<br/>`sortOrder`: string | **title:** To search for a subject, default is `""`.<br/>**page:** Number of the page (starting from 0), default is `0`.<br/>**size:** Number of subjects in a single page, default is `24`.<br/>**sortBy:** name of the attribute to sort by (`id`, `title`), default is `"id"`.<br/>**sortOrder:** Sorting in ascending (`ASC`) or descending (`DESC`) order, default is `"ASC"`.<br/>Returns an object that contains all the data about pagination & array of subjects. |

* #### Create a subject:
    ```http
      POST /api/subjects/add
    ```

    | Parameters        | Description                                                                                          |
    |:------------------|:-----------------------------------------------------------------------------------------------------|
    | `subject`: object | Returns a subject object if a valid object was provided, and throws an error or exception otherwise. |

* #### Update a subject:
    ```http
      PUT /api/subjects/update
    ```

    | Parameters        | Description                                                                                          |
    |:------------------|:-----------------------------------------------------------------------------------------------------|
    | `subject`: object | Returns a subject object if a valid object was provided, and throws an error or exception otherwise. |

* #### Delete a subject:
    ```http
      DELETE /api/subjects/:id
    ```

    | Parameters   | Description                                                                                                 |
    |:-------------|:------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted subject if a valid identifier was provided, and throws an error otherwise. |
---




