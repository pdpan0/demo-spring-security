FROM adoptopenjdk/openjdk11
EXPOSE 8080
ADD /build/libs/imc-0.0.1-SNAPSHOT.jar imc.jar
ENTRYPOINT ["java", "$JAVA_OPTS -XX:+UseContainerSupport", "-Xmx300m Xss512k -XX:CIComilerCount=2", "-Dserver.port=$PORT", "--spring.profile.active=prod", "-jar", "imc.jar"]