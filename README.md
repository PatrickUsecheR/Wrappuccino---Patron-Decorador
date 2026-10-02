# Cafetería · Patrón Decorator (Spring Boot + Vercel)

- backend/  → Spring Boot 3 (Java 17), POST /api/pedido/preview
- frontend/ → HTML + CSS + JS vanilla (nginx)
- vercel.json → services + rewrites (Vercel Container Images, Beta)

Local:
  cd backend && mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
  curl -X POST localhost:8080/api/pedido/preview -H "Content-Type: application/json" \
    -d '{"base":"espresso","tamano":"GRANDE","extras":["SHOT","JARABE:vainilla"]}'

Deploy:
  npm i -g vercel && vercel login && vercel --prod
