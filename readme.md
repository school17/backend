### Docker RabbitMq Configuration



sudo docker pull activiti/rabbitmq-stomp

sudo docker pull rabbitmq

docker container run -it --name rabbitmq-development -p 15672:15672 -p 5672:5672 -p 61613:61613 activiti/rabbitmq-stomp