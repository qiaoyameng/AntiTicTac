# Use Eclipse Temurin (OpenJDK) base image for Java development
FROM eclipse-temurin:21-jdk

# Install basic development tools
RUN apt-get update && \
    apt-get install -y \
    vim \
    nano \
    curl \
    git \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy the source code
COPY . /app

# Default to bash shell for development
CMD ["/bin/bash"]