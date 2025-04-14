# === Stage 1: Maven Setup / Java Build ===
FROM maven:3.9.5-eclipse-temurin-21 AS java-builder

# Set up the working directory
WORKDIR /app

# Copy Maven settings and cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy project files and build the app
COPY . .
RUN mvn clean package -DskipTests

# === Stage 2: Prepare Python environment ===
FROM python:3.11-slim AS python-builder

# Install Python dependencies
RUN pip install --break-system-packages sympy numpy matplotlib

# === Stage 3: Runtime ===
FROM openjdk:21-slim

# Install Python Runtime (from Debian packages)
RUN apt-get update && \
    apt-get install -y --no-install-recommends python3 python3-pip libpython3.11 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copy Python packages from PYTHON-BUILDER
COPY --from=python-builder /usr/local/lib/python3.11 /usr/local/lib/python3.11
COPY --from=python-builder /usr/local/bin /usr/local/bin
COPY --from=python-builder /usr/local/include /usr/local/include
COPY --from=python-builder /usr/local/share /usr/local/share

# Set PYTHONPATH to include the directory for Python packages
ENV PYTHONPATH=/usr/local/lib/python3.11/site-packages

# Set up the working directory
WORKDIR /app

# Copy the built Spring Boot JAR from JAVA-BUILDER
COPY --from=java-builder /app/target/*.jar app.jar

# Copy your Python scripts
COPY src/main/resources/python-scripts/ ./python-scripts/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]