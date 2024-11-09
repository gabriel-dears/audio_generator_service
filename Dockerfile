# Start with a base image that includes Java and Maven for Spring Boot
FROM openjdk:17-jdk-slim

# Set the environment variable for the directory path
ENV CLIPS_DIR=/app/clips

# Install yt-dlp and ffmpeg
RUN apt-get update && \
    apt-get install -y python3-pip ffmpeg && \
    pip3 install yt-dlp && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# Create the directory for clips using the environment variable
RUN mkdir -p $CLIPS_DIR

# Expose the port Spring Boot will run on
EXPOSE 8080

# Copy and build your application
COPY target/clipcrafter-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
