@echo off
:: Create the main directory structure
mkdir src\main\java\com\securefileshare\config
mkdir src\main\java\com\securefileshare\controller
mkdir src\main\java\com\securefileshare\service
mkdir src\main\java\com\securefileshare\model
mkdir src\main\java\com\securefileshare\repository
mkdir src\main\resources
mkdir src\test\java\com\securefileshare\controller
mkdir src\test\java\com\securefileshare\service
mkdir src\test\java\com\securefileshare\repository

:: Create empty files in the main directory structure
echo. > src\main\java\com\securefileshare\SecureFileShareApplication.java
echo. > src\main\java\com\securefileshare\config\SecurityConfig.java
echo. > src\main\java\com\securefileshare\controller\FileController.java
echo. > src\main\java\com\securefileshare\service\FileEncryptionService.java
echo. > src\main\java\com\securefileshare\model\File.java
echo. > src\main\java\com\securefileshare\repository\FileRepository.java
echo. > src\main\resources\application.properties
echo. > src\main\resources\logback.xml
echo. > src\test\java\com\securefileshare\SecureFileShareApplicationTests.java
echo. > src\test\java\com\securefileshare\controller\FileControllerTests.java
echo. > src\test\java\com\securefileshare\service\FileEncryptionServiceTests.java
echo. > src\test\java\com\securefileshare\repository\FileRepositoryTests.java

:: Create Docker and Maven configuration files
echo. > Dockerfile
echo. > docker-compose.yml
echo. > pom.xml
echo. > README.md
echo. > LICENSE

echo Project structure created successfully!
pause
