FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 5500

COPY . MoneyTransfer.jar

CMD ["java","-jar","MoneyTransfer.jar"]