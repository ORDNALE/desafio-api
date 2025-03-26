@echo off

REM Reinicia o banco de dados Docker
:: execute .\reset-db.bat para rodar..

echo Reiniciando banco de dados Docker...

docker-compose down -v
docker-compose up -d

echo Banco de dados reinicializado com sucesso!
pause