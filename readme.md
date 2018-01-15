Анализ тестового задания показывает, что постановка задачи недостаточно проработана.
На стадии анализа задачи я бы задал следующие вопросы (вопросы приведены ниже вместе с принятыми допущениями):

В компании “Рога&Копыта”, производящее дизайнерскую мебель ручной работы
на заказ, произошло увеличение объема заказов и штата сотрудников.
Руководителями компании было принято решение разработать систему
отслеживания расписания сотрудников и товаров, которые они должны
изготовить.
В компании работает более тридцати сотрудников, находящихся в трех отделах:
производство мягкой мебели (кровати, диваны, кресла), системы хранения
(шкафы, тумбы, полки) и офисная мебель (столы, стулья, кресла-качалки).
 Вопрос: возможно ли создание нового отдела в компании?
 Допущение: да, система должна позволять создание нового отдела без доработок программного кода.
 Вопрос: нужно ли хранить историю заказов?
 Допущение: историю заказов хранить не нужно
 Вопрос: что делать при удалении отдела, в котором есть: 1. невыполненные заказы,
 2. только выполненные заказы? 3. сотрудники?
 Допущение: при удалении отдела удаляются все его сотрудники и заказы
 (без переназначения, т.к. переназначать не на кого, закрывается целое направление деятельности организации)
 Вопрос: что делать при удалении сотрудника с выполненными этим сотрудником заказами?
 Допущение: историю заказов хранить не нужно, заказы удаляются вместе с сотрудником

(Если отделы не нужно создавать/удалять, сущность Department не нужна, заводим enum Department и поля типа Department у Employee и Order.
Назначение сотрудника выполняем по order.department = employee.department)

В
большинстве случаев заказы назначаются на отдел и имеют срок, к какому
времени их нужно сделать, однако иногда заказы могут назначаться на
определенного мастера, а не на весь отдел, где он работает.
 Вопрос: может ли в системе существовать неназначенный заказ?
 Ответ: нет, все заказы в системе должны иметь назначенного мастера.
 Вопрос: можно ли в системе завести заказ со сроком выполнения в прошлом?
 Допущение: да, ограничений на срок выполнения не накладывается.

Система
должна иметь REST-интерфейс для работы с ней, включающий:
● CRUD всех отдельных сущностей;
● создание заказа с автоматическим определением отдела и
автоназначением;
 Вопрос: 1. при назначении заказа на отдел каков должен быть алгоритм назначения сотрудника на заказ?
        2. возможно ли в будущем расширение системы, позволяющее выбирать алгоритм, например, в зависимости от отдела?
 Допущение: заказ назначается на сотрудника, имеющего минимум невыполненных заказов

● удаление мастеров с последующим автоматическим переназначением
заказов.
 Вопрос: 1. что делать, если удаляемый мастер оказывается последним в отделе, и заказы не на кого переназначать?
         2. что делать, если мастер переходит в другой отдел и оказывается последним в отделе?
 Допущение: в отделе должен существовать хотя бы один сотрудник.
 Вопрос: нужно ли хранить историю мастеров, когда-либо занимавшихся выполнением заказа?
 Допущение: нет.

При этом должна быть возможность получения следующих данных:
● все заказы;
● незавершенные заказы (вместе с их сроками выполнения и назначенным
отделам/сотрудникам);
 Вопрос: что является признаком завершенного заказа? Что происходит при наступлении срока завершения заказа?
 Допущение: признаком завершенного заказа является отдельный флаг "заказ выполнен". При наступлении срока завершения заказа
 никаких изменений с сущностью "заказ" не происходит.

● заказы по отделам;
● заказы по сотрудникам;
● количество дней и часов до окончания заказа


Перед запуском создать БД schedule:schedule@jdbc:postgresql://localhost:5432/schedule