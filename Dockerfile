FROM openjdk

LABEL version="1.0"
LABEL description="Prime Service"

COPY ./target/primefinder-*.jar /opt/rbs/primefinder.jar

# ENV JAVA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005

EXPOSE 8080

# ARG privilege

CMD /opt/rbs/primefinder.jar

