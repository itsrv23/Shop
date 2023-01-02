FROM bellsoft/liberica-openjdk-centos

COPY ../target/shop.jar shop.jar
COPY app.properties app.properties

ENTRYPOINT ["java", "-jar", "shop.jar"]
