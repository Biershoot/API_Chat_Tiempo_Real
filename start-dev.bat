@echo off
echo Starting API Chat Tiempo Real in Development Mode...
echo.
echo This will use H2 in-memory database (no MySQL required)
echo.
echo Access points:
echo - Health Check: http://localhost:8081/api/test/health
echo - H2 Console: http://localhost:8081/h2-console
echo.
echo Press Ctrl+C to stop the application
echo.

mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev

pause
