# YouQuiz API
**YouQuiz** is An interactive platform that empowers trainers to design engaging quizzes, while providing students the opportunity to take and excel in these quizzes.
<br>
[Click here](https://github.com/ThisMSH/YouQuiz-Front-End) to visit the front end repository of this project.

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
        "type": "VIDEO" // 'type' must be either "VIDEO", "IMAGE" or "AUDIO"
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
    > :warning: **NB:** You must use **FormData** in order to submit a post request. 
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
        "type": "SINGLE", // 'type' must be either "SINGLE", "MULTI" or "DIRECT"
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
        "parent": 1 // Can be null
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

### <ins>Students</ins>

* #### The student request object:
  An example of the student request object when creating or updating a student:
    ```js
    {
        "id": 3, // Required only when updating a student
        "name": "Margaret",
        "familyName": "Hamilton",
        "address": "Paoli, Indiana, United States",
        "birthdate": "1936-08-17", // yyyy-MM-dd
        "registrationDate": "2022-12-31" // yyyy-MM-dd
    }
    ```

* #### Get a student:
    ```http
      GET /api/students/:id
    ```

    | Parameters   | Description                                                                                 |
    |:-------------|:--------------------------------------------------------------------------------------------|
    | No parameter | Returns a student object if a valid identifier was provided, and throws an error otherwise. |

* #### Get all students:
    ```http
      GET /api/students
    ```

    | Query Parameters                                                                                        | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
    |:--------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | `fullName`: string<br/>`page`: integer<br/>`size`: integer<br/>`sortBy`: string<br/>`sortOrder`: string | **fullName:** To search for a student, default is `""`.<br/>**page:** Number of the page (starting from 0), default is `0`.<br/>**size:** Number of students in a single page, default is `24`.<br/>**sortBy:** name of the attribute to sort by (`id`, `name`, `familyName`, `address`, `birthdate`, `registrationDate`), default is `"id"`.<br/>**sortOrder:** Sorting in ascending (`ASC`) or descending (`DESC`) order, default is `"ASC"`.<br/>Returns an object that contains all the data about pagination & array of students. |

* #### Create a student:
    ```http
      POST /api/students/add
    ```

    | Parameters        | Description                                                                                          |
    |:------------------|:-----------------------------------------------------------------------------------------------------|
    | `student`: object | Returns a student object if a valid object was provided, and throws an error or exception otherwise. |

* #### Update a student:
    ```http
      PUT /api/students/update
    ```

    | Parameters        | Description                                                                                          |
    |:------------------|:-----------------------------------------------------------------------------------------------------|
    | `student`: object | Returns a student object if a valid object was provided, and throws an error or exception otherwise. |

* #### Delete a student:
    ```http
      DELETE /api/students/:id
    ```

    | Parameters   | Description                                                                                                 |
    |:-------------|:------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted student if a valid identifier was provided, and throws an error otherwise. |
---

### <ins>Trainers</ins>

* #### The trainer request object:
  An example of the trainer request object when creating or updating a trainer:
    ```js
    {
        "id": 3, // Required only when updating a trainer
        "name": "Margaret",
        "familyName": "Hamilton",
        "address": "Paoli, Indiana, United States",
        "birthdate": "1936-08-17", // yyyy-MM-dd
        "speciality": "Software Engineering"
    }
    ```

* #### Get a trainer:
    ```http
      GET /api/trainers/:id
    ```

    | Parameters   | Description                                                                                 |
    |:-------------|:--------------------------------------------------------------------------------------------|
    | No parameter | Returns a trainer object if a valid identifier was provided, and throws an error otherwise. |

* #### Get all trainers:
    ```http
      GET /api/trainers
    ```

    | Query Parameters                                                                                        | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
    |:--------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | `fullName`: string<br/>`page`: integer<br/>`size`: integer<br/>`sortBy`: string<br/>`sortOrder`: string | **fullName:** To search for a trainer, default is `""`.<br/>**page:** Number of the page (starting from 0), default is `0`.<br/>**size:** Number of trainers in a single page, default is `24`.<br/>**sortBy:** name of the attribute to sort by (`id`, `name`, `familyName`, `address`, `birthdate`, `speciality`), default is `"id"`.<br/>**sortOrder:** Sorting in ascending (`ASC`) or descending (`DESC`) order, default is `"ASC"`.<br/>Returns an object that contains all the data about pagination & array of trainers. |

* #### Create a trainer:
    ```http
      POST /api/trainers/add
    ```

    | Parameters        | Description                                                                                          |
    |:------------------|:-----------------------------------------------------------------------------------------------------|
    | `trainer`: object | Returns a trainer object if a valid object was provided, and throws an error or exception otherwise. |

* #### Update a trainer:
    ```http
      PUT /api/trainers/update
    ```

    | Parameters        | Description                                                                                          |
    |:------------------|:-----------------------------------------------------------------------------------------------------|
    | `trainer`: object | Returns a trainer object if a valid object was provided, and throws an error or exception otherwise. |

* #### Delete a trainer:
    ```http
      DELETE /api/trainers/:id
    ```

    | Parameters   | Description                                                                                                 |
    |:-------------|:------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted trainer if a valid identifier was provided, and throws an error otherwise. |
---

### <ins>Quizzes</ins>

* #### The quiz request object:
  An example of the quiz request object when creating or updating a quiz:
    ```js
    {
        "id": 3, // Required only when updating a quiz
        "title": "Java quiz",
        "duration": 45, // In minutes
        "successScore": 80.5,
        "canSeeAnswers": true, // Choose if the student can see the correct answer after answering the question
        "canSeeResult": true, // Choose if the student can see the result after finishing the quiz
        "chances": 5,
        "remark": "Remark about the quiz",
        "trainerId": 1
    }
    ```

* #### Get a quiz:
    ```http
      GET /api/quizzes/:id
    ```

    | Parameters   | Description                                                                              |
    |:-------------|:-----------------------------------------------------------------------------------------|
    | No parameter | Returns a quiz object if a valid identifier was provided, and throws an error otherwise. |

* #### Get all quizzes:
    ```http
      GET /api/quizzes
    ```

    | Query Parameters                                                                                     | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
    |:-----------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | `title`: string<br/>`page`: integer<br/>`size`: integer<br/>`sortBy`: string<br/>`sortOrder`: string | **title:** To search for a quiz, default is `""`.<br/>**page:** Number of the page (starting from 0), default is `0`.<br/>**size:** Number of quizzes in a single page, default is `24`.<br/>**sortBy:** name of the attribute to sort by (`id`, `title`, `duration`, `successScore`, `chances`, `remark`, `trainerId`), default is `"id"`.<br/>**sortOrder:** Sorting in ascending (`ASC`) or descending (`DESC`) order, default is `"ASC"`.<br/>Returns an object that contains all the data about pagination & array of quizzes. |

* #### Create a quiz:
    ```http
      POST /api/quizzes/add
    ```

    | Parameters     | Description                                                                                       |
    |:---------------|:--------------------------------------------------------------------------------------------------|
    | `quiz`: object | Returns a quiz object if a valid object was provided, and throws an error or exception otherwise. |

* #### Update a quiz:
    ```http
      PUT /api/quizzes/update
    ```

    | Parameters     | Description                                                                                       |
    |:---------------|:--------------------------------------------------------------------------------------------------|
    | `quiz`: object | Returns a quiz object if a valid object was provided, and throws an error or exception otherwise. |

* #### Delete a quiz:
    ```http
      DELETE /api/quizzes/:id
    ```

    | Parameters   | Description                                                                                              |
    |:-------------|:---------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted quiz if a valid identifier was provided, and throws an error otherwise. |
---

### <ins>QuizQuestions</ins>

* #### The quiz-question request object:
  An example of the quiz-question request object when creating or updating a quiz-question:
    ```js
    {
        "timer": 20, // In seconds
        "allowPartialPoints": false, // Choose whether to allow partial points for each question or not
        "questionId": 5,
        "quizId": 1
    }
    ```

* #### Get a quiz-question:
    ```http
      GET /api/quiz-questions/:quizId-:questionId
    ```

    | Parameters   | Description                                                                                         |
    |:-------------|:----------------------------------------------------------------------------------------------------|
    | No parameter | Returns a quiz-question object if a valid identifiers were provided, and throws an error otherwise. |

* #### Get all quiz-questions by quiz:
    ```http
      GET /api/quiz-questions/quiz/:quizId
    ```

    | Parameters   | Description                                                                                                  |
    |:-------------|:-------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an array of quiz-question objects if a valid identifier was provided, and throws an error otherwise. |

* #### Get all quiz-questions by question:
    ```http
      GET /api/quiz-questions/question/:questionId
    ```
    
    | Parameters   | Description                                                                                                  |
    |:-------------|:-------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an array of quiz-question objects if a valid identifier was provided, and throws an error otherwise. |

* #### Create a quiz-question:
    ```http
      POST /api/quiz-questions/add
    ```

    | Parameters              | Description                                                                                                |
    |:------------------------|:-----------------------------------------------------------------------------------------------------------|
    | `quiz-question`: object | Returns a quiz-question object if a valid object was provided, and throws an error or exception otherwise. |

* #### Delete a quiz-question:
    ```http
      DELETE /api/quiz-questions/:quizId-:questionId
    ```

    | Parameters   | Description                                                                                                       |
    |:-------------|:------------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted quiz-question if a valid identifier was provided, and throws an error otherwise. |
---

### <ins>QuizAssignments</ins>

* #### The quiz-assignment request object:
  An example of the quiz-assignment request object when creating a quiz-assignment:
    ```js
    {
        "reason": "Margaret",
        "startingTime": "2023-12-14 18:45:00.000",
        "endingTime": "2023-12-14 23:45:00.000",
        "passResult": 80.5, // Minimum score to pass the result
        "studentId": 4,
        "quizId": 32
    }
    ```

* #### Get a quiz-assignment (/w or /wo result & score):
    ```http
      GET /api/quiz-assignments/:id
    ```

    | Parameters   | Description                                                                                                                                                                                                                                 |
    |:-------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | No parameter | Get a quiz-assignment object with or without result & score, depends if the student is allowed to see the result & score of a quiz.<br/>Returns a quiz-assignment object if a valid identifier was provided, and throws an error otherwise. |

* #### Get a quiz-assignment (/w result & score):
    ```http
      GET /api/quiz-assignments/with-results/:id
    ```

    | Parameters   | Description                                                                                                                                                                                                                                |
    |:-------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | No parameter | Get a quiz-assignment object with result & score, regardless if the student is allowed to see the result & score of a quiz or not.<br/>Returns a quiz-assignment object if a valid identifier was provided, and throws an error otherwise. |

* #### Get quiz-assignments of a student (/w or /wo result & score):
    ```http
      GET /api/quiz-assignments/by-student/:studentId
    ```

    | Parameters   | Description                                                                                                                                                                                                                                                             |
    |:-------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | No parameter | Get a list of quiz-assignment objects of a student with or without result & score, depends if the student is allowed to see the result & score of a quiz.<br/>Returns a list quiz-assignment objects if a valid identifier was provided, and throws an error otherwise. |

* #### Get quiz-assignments of a student (/w result & score):
    ```http
      GET /api/quiz-assignments/by-student-with-results/:studentId
    ```

    | Parameters   | Description                                                                                                                                                                                                                                                            |
    |:-------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | No parameter | Get a list of quiz-assignment objects of a student with result & score, regardless if the student is allowed to see the result & score of a quiz or not.<br/>Returns a list quiz-assignment objects if a valid identifier was provided, and throws an error otherwise. |

* #### Create a quiz-assignment:
    ```http
      POST /api/quiz-assignments/add
    ```

    | Parameters                | Description                                                                                                  |
    |:--------------------------|:-------------------------------------------------------------------------------------------------------------|
    | `quiz-assignment`: object | Returns a quiz-assignment object if a valid object was provided, and throws an error or exception otherwise. |

* #### Save a selected answer:
    ```http
      POST /api/quiz-assignments/selected-answer/:quizAssignmentId-:answerValidationId
    ```

    | Parameters   | Description                                                                                                        |
    |:-------------|:-------------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns a quiz-assignment object if a valid identifiers were provided, and throws an error or exception otherwise. |

* #### Close a quiz-assignment:
    ```http
      POST /api/quiz-assignments/close-quiz-assignment/:id
    ```

    | Parameters   | Description                                                                                                                                                                           |
    |:-------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
    | No parameter | Closes & calculate the score of the quiz after passing the quiz.<br/>Returns a quiz-assignment object if a valid identifier was provided, and throws an error or exception otherwise. |

* #### Delete a quiz-assignment:
    ```http
      DELETE /api/quiz-assignments/:id
    ```

    | Parameters   | Description                                                                                                         |
    |:-------------|:--------------------------------------------------------------------------------------------------------------------|
    | No parameter | Returns an object of the deleted quiz-assignment if a valid identifier was provided, and throws an error otherwise. |
