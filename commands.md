 ## Шпаргалка по командам


 ### Download image:
docker pull mysql:8.0

 ### Start container:
docker-compose up

 ### Launch MySql client tool in a separate terminal:
docker-compose exec mysql mysql -u Karl -p sample-app

 ### Start SUT:
java -jar app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/sample-app -P:jdbc.user=Karl -P:jdbc.password=KlaraUkralaKlarnet

### Before re-starting SUT: we need to make sure that sample-app database has no sample data.
#### This statement to run in MySql client tool:
delete from auth_codes; delete from card_transactions; delete from cards; delete from users;


