Клонируем проект на локальную машину.
Для запуска БД необходим Докер:
Запускаем команду cmd из папки проекта.
Пишем команду docker-compose up.
Интегрируем проект в Идею.
Ссылка на свагер: http://localhost:8090/projectKubBank/swagger-ui/index.html#/
Описание ЭндПойнтов:

POST
/api/AddTaskInQueue
Метод принимает массив задач и складывает их в очередь.

GET
/api/AddThreeTasksToDB
Метод добавляет три задачи из очереди в базу данных.

GET
/api/AddWorkerToTask/{workerId}/{taskId}
Метод назначает работнику задачу

GET
/api/GetAllTasks
Метод возвращает все задачи из БД со следующими полями: id, title, status

GET
/api/GetTaskById/{taskId}
Метод выдает задачу, с полным описанием, по переданному id.

PUT
/api/UpdateTask
Метод изменяет задачу по id. Поля id и performer не изменяемы.
worker-controller

POST
/api/AddWorker
Метод добавляет работника в БД

DELETE
/api/DeleteWorkerWithOutTasks
Метод удаляет работника из БД без задач

DELETE
/api/DeleteWorkerWithTasks
Метод удаляет работника из БД вместе с задачами

GET
/api/GetAllWorker
Метод получает информацию о всех работниках и их задачах из БД

GET
/api/GetWorkerById/{workerId}
Метод получает информацию об одном работнике и его задачах из БД по id

PUT
/api/UpdateWorker
Метод обновляет работника в БД
