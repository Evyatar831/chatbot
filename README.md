# Chuck Norris Chatbot

A Spring Boot REST API application that serves Chuck Norris jokes by integrating with the Chuck Norris API. This chatbot can search for jokes based on keywords and provides responses in a structured format.

## Features

- 🤖 RESTful API for fetching Chuck Norris jokes
- 🔍 Search jokes by keyword
- 📚 Swagger API documentation
- 🐳 Docker support
- 🌐 Integration with Chuck Norris API (https://api.chucknorris.io/)

## Technology Stack

- **Java 11/15** - Programming language
- **Spring Boot 2.5.2** - Framework
- **Maven** - Build tool
- **OkHttp** - HTTP client for API calls
- **Swagger/SpringFox** - API documentation
- **Docker** - Containerization

## Project Structure

```
src/
├── main/
│   ├── java/com/handson/chatbot/
│   │   ├── ChatbotApplication.java          # Main application class
│   │   ├── config/
│   │   │   └── SwaggerConfig.java           # Swagger configuration
│   │   ├── controller/
│   │   │   └── BotController.java           # REST endpoints
│   │   └── service/
│   │       └── ChuckNorrisService.java      # Business logic
│   └── resources/
│       └── application.properties           # Configuration
├── test/
│   └── java/com/handson/chatbot/
│       └── ChatbotApplicationTests.java     # Test class
├── Dockerfile                               # Docker configuration
└── pom.xml                                  # Maven dependencies
```

## API Endpoints

### 1. Search Chuck Norris Jokes (GET)
```http
GET /bot/chuck-norris?query={keyword}
```
**Parameters:**
- `query` (required): Keyword to search for jokes

**Example:**
```bash
curl "http://localhost:8080/bot/chuck-norris?query=programming"
```

### 2. Chatbot Webhook (POST)
```http
POST /bot
```
**Request Body:**
```json
{
  "queryResult": {
    "parameters": {
      "word": "programming"
    }
  }
}
```

**Response:**
```json
{
  "fulfillmentText": "Joke 1: [Chuck Norris joke here]",
  "source": "CHUCK_NORRIS_BOT"
}
```

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Docker (optional)

### Running Locally

1. **Clone the repository**
```bash
git clone <repository-url>
cd chatbot
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Using Docker

1. **Build the JAR file**
```bash
mvn clean package
```

2. **Build Docker image**
```bash
docker build -t chuck-norris-chatbot .
```

3. **Run the container**
```bash
docker run -p 8080:8080 chuck-norris-chatbot
```

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:
```
http://localhost:8080/swagger-ui.html
```

## Configuration

The application uses the following configuration in `application.properties`:

```properties
spring.application.name=chatbot
java.net.useSystemProxies=true
java.net.preferIPv4Stack=true
```

## Usage Examples

### Fetching jokes about "programming"
```bash
curl "http://localhost:8080/bot/chuck-norris?query=programming"
```

### Using the chatbot endpoint
```bash
curl -X POST "http://localhost:8080/bot" \
  -H "Content-Type: application/json" \
  -d '{
    "queryResult": {
      "parameters": {
        "word": "programming"
      }
    }
  }'
```

## Error Handling

The application includes proper error handling:
- Returns HTTP 500 with error message if the Chuck Norris API is unavailable
- Handles network timeouts (30 seconds for both connect and read)
- Provides fallback joke if no jokes are found for a query

## Testing

Run the tests using Maven:
```bash
mvn test
```

## Dependencies

Key dependencies include:
- `spring-boot-starter-web` - Web framework
- `okhttp` - HTTP client
- `springfox-swagger2` & `springfox-swagger-ui` - API documentation
- `testng` - Testing framework

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is available under the MIT License.

## External API

This application uses the Chuck Norris API:
- **Base URL:** https://api.chucknorris.io/
- **Endpoint:** `/jokes/search?query={query}`
- **Rate Limits:** Please refer to the Chuck Norris API documentation

## Support

For support or questions, please create an issue in the repository.
