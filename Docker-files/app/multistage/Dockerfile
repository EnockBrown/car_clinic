# First Stage: Build the application using Maven
FROM openjdk:17 AS build_image

# Install necessary packages and clone the repository
RUN apt update && \
    apt install -y maven git && \
    git clone https://github.com/devopshydclub/vprofile-project.git

# Set the working directory and checkout the correct branch
WORKDIR /vprofile-project
RUN git checkout docker && \
    mvn install --batch-mode

# Second Stage: Deploy the application in a Tomcat server
FROM tomcat:9-jre11

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the built WAR file from the first stage
COPY --from=build_image /vprofile-project/target/vprofile-v2.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
