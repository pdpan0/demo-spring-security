FROM adoptopenjdk/openjdk11
EXPOSE 8080
ADD /build/libs/imc-0.0.1-SNAPSHOT.jar imc.jar
ENTRYPOINT ["java", "$JAVA_OPTS -XX:+UseContainerSupport", "-Xmx300m -Xss512k -XX:CICompilerCount=2", "-Dserver.port=$PORT", "-Dspring.profiles.active=prod", "-jar", "imc.jar"]