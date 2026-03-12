#!/bin/bash
export JWT_SECRET=5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437
export JWT_EXPIRE=500000
export DB_PASS=Password
export SERVER_PORT=9000
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:6006 -jar target/*.jar